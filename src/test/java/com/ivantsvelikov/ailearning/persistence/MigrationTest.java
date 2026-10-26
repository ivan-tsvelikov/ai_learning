package com.ivantsvelikov.ailearning.persistence;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("database")
class MigrationTest {

    @Test
    void testBootstrapMigrationCanBeValidatedAndRepeated() {
        String password = System.getenv("DB_PASSWORD");
        assertNotNull(password, "Set DB_PASSWORD for the authorized project database");
        Flyway flyway = Flyway.configure()
                .dataSource(System.getenv().getOrDefault("DB_URL",
                                "jdbc:postgresql://localhost:55432/ai_learning"),
                        System.getenv().getOrDefault("DB_USER", "ai_learning"), password)
                .locations("classpath:db/migration")
                .cleanDisabled(true)
                .load();

        flyway.migrate();
        flyway.validate();

        assertEquals("0.0.0", flyway.info().current().getVersion().getVersion());
        assertEquals(0, flyway.migrate().migrationsExecuted);
    }
}
