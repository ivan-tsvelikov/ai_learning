# PostgreSQL, Hibernate ORM and Flyway

## Context
Власник погодив PostgreSQL/Flyway та обрав Hibernate ORM замість ручних JDBC repositories. Наявні чи зовнішні БД змінювати без окремого дозволу заборонено.

## Decision
PostgreSQL 17 у Docker Compose проєкті ai-learning. Hibernate ORM без Panache; entity classes живуть в infra. Flyway керує схемою, Hibernate лише validate. На старті тільки V0.0.0__bootstrap.sql без бізнес-таблиць. Flyway Community, без clean та без обіцянки автоматичного undo. Unit/smoke/architecture tests не використовують БД; opt-in dbtest перевіряє migrate/validate/idempotent migrate.

## Status
Accepted — 2026-10-26. Запуск контейнерів і зміни навчальної БД потребують окремого дозволу власника.

## Consequences
DDL лишається явним, ORM не змінює схему самовільно. Потрібні мапери domain/JPA. Справжня DB-перевірка потребує Docker, а звичайні тести працюють без нього. Нумерація Flyway відповідає AGENTS.md. SQL rollback для майбутніх змін проєктується явно; безповоротні зміни не маскуються командою clean.
