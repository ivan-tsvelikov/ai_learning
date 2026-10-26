# Architecture

Java 21 + Quarkus; модульний моноліт із п'ятьма BC.
Повна карта: docs/architecture-map.md; межі й словник: docs/bc-map.md.

## Contexts
- modules/auth: User, реєстрація й логін.
- modules/catalog: Product, Category, каталог.
- modules/commerce: Order, оформлення замовлення.
- modules/billing: Subscription, тарифні підписки.
- modules/notifications: Notification, доставка через Sender.
Корінь: src/main/java/com/ivantsvelikov/ailearning/.

## Hexagonal dependency rule
Усі BC: domain/app/infra; app -> domain, infra -> app/domain.
domain: лише JDK та власні domain types; без Quarkus/JPA/CDI/HTTP.
app: use cases і порти, без infra/JPA/CDI/HTTP.
infra: REST, CDI wiring, Hibernate entities, repositories, mappers, event adapters.
Domain objects і JPA entities окремі.
Прямі імпорти між BC заборонені; shared не залежить від BC.
Спільні контракти подій: shared/events, без бізнес-логіки.
На етапі каркаса в кожному BC лише README; шарів і бізнес-коду ще немає.

## Events
- Auth publishes UserRegistered; Notifications handles it in notifications/infra/events.
- Commerce publishes OrderPlaced; Billing and Notifications handle it in their infra/events.
- Billing publishes SubscriptionCreated; Notifications handles it in notifications/infra/events.
- Catalog publishes no events; Notifications only consumes events.
Bus/handlers і payloads будуть реалізовані з фічами, не в каркасі.

## Persistence
PostgreSQL + Hibernate ORM; JPA mappings лише в infra.
Flyway керує DDL; Hibernate validate, без schema update/drop-and-create.
Початкова V0.0.0__bootstrap.sql не створює бізнес-таблиць.
Flyway Community без automatic undo; ніколи не використовуємо clean для rollback.

## Commands
Windows: .\mvnw.cmd test; .\mvnw.cmd verify; .\mvnw.cmd -Dtest=ArchitectureTest test.
Unix: ./mvnw test; ./mvnw verify; ./mvnw -Dtest=ArchitectureTest test.
Dev: scripts/dev.ps1 або scripts/dev.sh (потрібні PostgreSQL і DB_PASSWORD).
Default tests без Docker/БД; явна DB-перевірка: Maven -Pdbtest test.
Технічний HTTP endpoint /health, не бізнес API і не DB readiness.
