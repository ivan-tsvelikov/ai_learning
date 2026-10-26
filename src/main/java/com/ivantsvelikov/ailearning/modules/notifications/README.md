# Notifications

Володіє доставкою повідомлень користувачам. Не приймає бізнес-рішень замість інших BC.

Терміни: Notification — повідомлення; Channel — email/push; Payload — вміст; Sender — порт доставки.

- Публікує: жодних подій у поточній карті.
- Слухає: UserRegistered від Auth, OrderPlaced від Commerce, SubscriptionCreated від Billing.
- Будова майбутнього коду: domain/app/infra; app -> domain, infra -> app/domain.
- Порт Sender — app; адаптери доставки та обробники подій — infra, зокрема infra/events. JPA entity окрема від domain object; прямі cross-BC імпорти заборонені.

На етапі каркаса бізнес-коду немає. Джерело меж: docs/bc-map.md.
