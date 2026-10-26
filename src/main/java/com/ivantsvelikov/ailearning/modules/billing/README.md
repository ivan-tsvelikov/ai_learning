# Billing

Володіє тарифними підписками за навчальним демо. Це не реалізація оплати товарів або платіжного шлюзу.

Терміни: Subscription — підписка; Plan — basic/pro/enterprise; Next charge — наступна дата списання.

- Публікує: SubscriptionCreated; слухає Notifications.
- Слухає: OrderPlaced від Commerce; майбутній адаптер — infra/events.
- Будова майбутнього коду: domain/app/infra; app -> domain, infra -> app/domain.
- Правила підписки — domain; use cases/порти — app; JPA, REST, event handler — infra. Прямі імпорти Commerce та інших BC заборонені.

На етапі каркаса бізнес-коду немає. Джерело меж: docs/bc-map.md.
