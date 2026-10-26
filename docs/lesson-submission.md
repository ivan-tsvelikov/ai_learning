# Результат уроку: scaffold

## Що виконано

1. SDLC уже встановлений; використано протокол map-architecture у цьому Codex-сеансі. Це не твердження про запуск slash-команди у Claude Code.
2. Погоджено Java + Quarkus, Hibernate ORM, PostgreSQL, Flyway, Hexagonal для всіх п'яти BC. Створено карту, три Accepted ADR та tasks.json. Коміт: 281e36f — map-architecture: greenfield foundation + scaffold plan.
3. Каркас зібрано за задачами вручну, без запуску implement-tasks, як вимагає урок. Бізнес-коду в BC немає.
4. Фінальний scaffold-коміт і актуальну історію перевір командою git log --oneline -3 після завершення роботи.
5. /clear, /context та перевірка відповідей у чистій Claude Code сесії потребують дій користувача. Вони не виконані й не підмінені відповідями Codex.

## Вибір архітектури для здачі

Усі п'ять контекстів мають Hexagonal domain/app/infra за явним вибором власника: бізнес-правила не залежать від Quarkus і Hibernate, а інфраструктура реалізує порти ядра. Єдина структура полегшує навчання та автоматичну перевірку меж, хоча для простих Auth/Catalog означатиме більше mapping-коду, ніж прості шари.

## Локальні перевірки, 2026-10-26

Фактично виконано Maven verify на JDK 25.0.2, потім на JDK 21.0.8 (як language level і CI). Обидва запускали реальний Quarkus test runtime без БД. Оригінальні повні логи — локальні build-verification.log та java21-verification.log (ігноруються Git; можуть містити локальні шляхи). Surefire XML і текстові звіти — target/surefire-reports/.

```text
Command: .\mvnw.cmd -B -ntp verify
JDK: 21.0.8
Quarkus: 3.40.1
HealthResourceTest: Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
ArchitectureTest: Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

GET /health перевірено на HTTP 200, application/json, status=UP. Невідомий шлях — 404. ArchUnit перевірив production-класи та fixtures: cross-BC, domain->infra, app->infra відхиляються; infra->власне ядро дозволено. Звичайний test profile відключає JDBC/ORM/Flyway; попередження Agroal про відсутність datasource у цьому профілі очікуване.

```text
docker compose config --quiet: PASS
Containers started: none
Database migrations executed: none
```

Compose перевірено лише як конфігурацію з тимчасовим несекретним значенням змінної середовища; контейнерів та БД не створено. Прямого дозволу на DB-run не отримано. Для перевірки міграцій потрібні дозвіл, Docker Desktop, DB_PASSWORD та Maven -Pdbtest test. Flyway Community не має automatic undo; порожня стартова міграція не змінює бізнес-схему.

Інші перевірки: AGENTS.md — 77 рядків (ліміт 200), кореневий ARCHITECTURE.md — 42 (ліміт 60). Рівно 5 BC з README, без Java-коду; .env/.idea/target/.cache ігноруються. C4 пройшов структурну перевірку посилань/вузлів; окремий Mermaid renderer не запускався. Незалежне read-only code review — PASS, без actionable findings. Віддалений CI перевіряється окремо після push.

## Дерево каркаса

Скорочене дерево, еквівалент tree -L 3 з додатковим розкриттям довгого Java package path:

```text
.
├── AGENTS.md
├── ARCHITECTURE.md
├── SPEC.md
├── README.md
├── pom.xml
├── mvnw / mvnw.cmd
├── .env.example
├── compose.yaml
├── .mvn/
│   ├── maven.config
│   ├── settings.xml
│   └── wrapper/maven-wrapper.properties
├── .github/workflows/ci.yml
├── scripts/dev.ps1 / dev.sh
├── docs/
│   ├── bc-map.md
│   ├── architecture-map.md
│   ├── ARCHITECTURE.md (reference demo)
│   ├── adr/0001-java-quarkus.md
│   ├── adr/0002-hexagonal-contexts.md
│   ├── adr/0003-persistence.md
│   ├── features/_scaffold/tasks/tasks.json
│   ├── features/_scaffold/tasks/tracker.md
│   ├── development.md
│   └── lesson-submission.md
└── src/
    ├── main/java/com/ivantsvelikov/ailearning/
    │   ├── bootstrap/HealthResource.java
    │   └── modules/
    │       ├── auth/README.md
    │       ├── catalog/README.md
    │       ├── commerce/README.md
    │       ├── billing/README.md
    │       └── notifications/README.md
    ├── main/resources/application.properties
    ├── main/resources/db/migration/V0.0.0__bootstrap.sql
    ├── test/java/com/ivantsvelikov/ailearning/architecture/ArchitectureTest.java
    ├── test/java/com/ivantsvelikov/ailearning/bootstrap/HealthResourceTest.java
    ├── test/java/com/ivantsvelikov/ailearning/persistence/MigrationTest.java
    └── test/java/fixtures/ (valid and deliberately invalid dependencies)
```

## Кроки користувача для фінальної здачі

1. Самостійно запусти .\mvnw.cmd test (або sh ./mvnw test) і додай свій вивід.
2. У Claude Code в цій теці виконай /clear, потім /context. Додай скрін Memory files: очікуються AGENTS.md та ARCHITECTURE.md. Це фактична перевірка, не обіцянка підтримки імпортів конкретною версією клієнта.
3. Якщо AGENTS.md не видно, виконай fallback із уроку: оновлення Claude Code за власним рішенням; якщо не допомогло — CLAUDE.md з одним @AGENTS.md. За основним варіантом CLAUDE.md не створений.
4. У чистій сесії постав промпт нижче й додай реальну відповідь. Орієнтир для самоперевірки: notifications/infra/events; заборонені імпорти інших BC і залежності ядра від infra/frameworks; Maven test/verify. Це лише очікування з документів, а не запис відповіді Claude.

```text
Не відкривай жодних файлів і нічого не змінюй. Відповідай лише з того,
що вже є в контексті цієї сесії, і біля кожної відповіді назви файл,
з якого це взяв.
1. Куди ти покладеш обробник UserRegistered і чому саме туди?
2. Що коду в цьому контексті заборонено імпортувати?
3. Якою командою перевіриш, що зміна нічого не зламала?
Якщо чогось із цього в контексті немає, так і скажи і нічого не вгадуй.
```

До здачі додай git log --oneline, дерево, свій test output, скрін /context, розділ Architecture з AGENTS.md блоком коду, реальну відповідь Claude та два речення з розділу вибору архітектури. Не додавай справжні ключі, токени, .env чи локальні приватні шляхи.
