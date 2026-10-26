# Java + Quarkus

## Context
Навчальний e-commerce HTTP API без фронтенду. Власник обрав Java та Quarkus замість запропонованих Spring Boot/TypeScript. Java, Maven та IDEA вже доступні.

## Decision
Java 21 language level, Quarkus 3.40.1 (stable 3.x, не 4.0 beta), Maven Wrapper 3.9.16. Quarkus REST Jackson для JSON, JUnit і REST Assured для smoke-тестів. Єдиний Maven-модуль та один deployable застосунок; GitHub Actions замість GitLab CI.

## Status
Accepted — 2026-10-26, вибір власника в чаті.

## Consequences
Можна працювати у знайомій Java-екосистемі. Maven Wrapper і зафіксований BOM роблять збірку повторюваною. Заміна фреймворку потребуватиме змін адаптерів. Немає native image, Lombok або додаткових сервісів без потреби.
