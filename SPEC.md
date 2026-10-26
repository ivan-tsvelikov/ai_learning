# E-commerce scaffold

## Goal
Створити основу навчального e-commerce HTTP API за п'ятьма контекстами з docs/bc-map.md. Цей етап готує місця для наступних фіч, без реалізації бізнес-сценаріїв.

## Non-goals
Реєстрація, логін, каталог, замовлення, підписки, надсилання повідомлень, бізнес-таблиці, фронтенд, production deployment і native image.

## Decisions
- Java 21 + Quarkus; PostgreSQL, Hibernate ORM, Flyway.
- Hexagonal Architecture для Auth, Catalog, Commerce, Billing, Notifications.
- Domain objects відокремлені від JPA entities. Міжконтекстна взаємодія — події.
- GitHub Actions, Maven Wrapper, JUnit, REST Assured, ArchUnit.
- AGENTS.md — єдиний файл правил із @ARCHITECTURE.md; CLAUDE.md не створюється для варіанта без власного проєкту.

## Acceptance criteria
1. Рівно п'ять BC мають README з метою, 3–5 термінами, вхідними/вихідними подіями; Java-коду в BC немає.
2. GET /health повертає HTTP 200 та JSON {"status":"UP"}; невідомий шлях — 404.
3. Maven verify збирає застосунок і запускає тести без Docker та БД.
4. ArchUnit забороняє cross-BC imports, залежності domain від infra/frameworks і app від infra; негативні fixtures доводять працездатність правил.
5. Hibernate має validate, Flyway — порожню стартову міграцію. Немає schema update/drop-and-create/clean-at-start.
6. DB-тест явно відокремлений; після дозволу власника перевіряє migrate/validate/repeated migrate на навчальній PostgreSQL.
7. CI GitHub запускає Maven verify. .env і кеші ігноруються; .env.example містить лише порожні змінні.
8. Є архітектурна карта, ADR, план/трекер, ARCHITECTURE.md <=60 рядків та AGENTS.md <=200 рядків.
9. Для здачі збережені фактичні виводи перевірок. /clear, /context і відповіді із чистої Claude Code сесії не підмінені вигаданими результатами.
