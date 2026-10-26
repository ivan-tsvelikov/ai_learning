package com.ivantsvelikov.ailearning.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import java.util.List;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArchitectureTest {

    private static final String BASE = "com.ivantsvelikov.ailearning";

    static List<ArchRule> rules(String root) {
        return List.of(
                slices().matching(root + ".modules.(*)..")
                        .should().notDependOnEachOther().allowEmptyShould(true),
                classes().that().resideInAPackage(root + ".modules.*.domain..")
                        .should().onlyDependOnClassesThat()
                        .resideInAnyPackage("java..", root + ".modules.*.domain..")
                        .allowEmptyShould(true),
                classes().that().resideInAPackage(root + ".modules.*.app..")
                        .should().onlyDependOnClassesThat()
                        .resideInAnyPackage("java..", root + ".modules.*.app..",
                                root + ".modules.*.domain..", root + ".shared.events..")
                        .allowEmptyShould(true),
                noClasses().that().resideInAPackage(root + ".shared..")
                        .should().dependOnClassesThat().resideInAPackage(root + ".modules..")
                        .allowEmptyShould(true));
    }

    @Test
    void testProductionDependenciesRespectHexagonalBoundaries() {
        JavaClasses production = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(BASE);

        rules(BASE).forEach(rule -> rule.check(production));
    }

    @Test
    void testCrossContextImportIsRejected() {
        JavaClasses fixtures = fixtures("cross");

        assertTrue(rules("fixtures.cross").get(0).evaluate(fixtures).hasViolation());
    }

    @Test
    void testDomainImportingInfrastructureIsRejected() {
        JavaClasses fixtures = fixtures("invalid");

        assertTrue(rules("fixtures.invalid").get(1).evaluate(fixtures).hasViolation());
    }

    @Test
    void testApplicationImportingInfrastructureIsRejected() {
        JavaClasses fixtures = fixtures("invalid");

        assertTrue(rules("fixtures.invalid").get(2).evaluate(fixtures).hasViolation());
    }

    @Test
    void testInfrastructureDependingOnOwnCoreIsAllowed() {
        JavaClasses fixtures = fixtures("valid");

        rules("fixtures.valid").forEach(rule ->
                assertFalse(rule.evaluate(fixtures).hasViolation(), rule.getDescription()));
    }

    private JavaClasses fixtures(String scenario) {
        return new ClassFileImporter().importPackages("fixtures." + scenario);
    }
}
