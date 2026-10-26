# Hexagonal Architecture for all five contexts

## Context
bc-map спочатку пропонував прості шари для Auth/Catalog. Власник явно обрав Hexagonal для всіх п'яти BC.

## Decision
Модульний моноліт з Auth, Catalog, Commerce, Billing, Notifications. Кожен BC має цільову будову domain/app/infra, залежності app -> domain, infra -> app/domain. Ядро не залежить від Quarkus/Hibernate. JPA entities і domain objects розділені. Прямі cross-BC імпорти заборонені, інтеграція через події. ArchUnit перевіряє межі та негативні fixtures. На старті у BC тільки README, без Java-заглушок.

## Status
Accepted — 2026-10-26, явне рішення власника замінює попередній mixed-style варіант.

## Consequences
Єдина структура зручна для навчання. Auth/Catalog отримають більше файлів і mapping, ніж потрібно простому CRUD; приймаємо цю ціну свідомо. Альтернативи: Layered для простих BC або окремі мікросервіси; відхилені на цьому етапі.
