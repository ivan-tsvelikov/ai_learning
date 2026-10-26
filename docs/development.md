# Development

Потрібна JDK 21 або сумісна новіша JDK. CI використовує JDK 21. Maven Wrapper завантажує Maven 3.9.16. Maven-артефакти та дистрибутив лежать у .cache/ цього проєкту, не у глобальному кеші. .mvn/settings.xml використовує Maven Central без персональних mirrors. Перший запуск потребує мережі.

## Commands from repository root

Windows PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd '-Dtest=ArchitectureTest' test
.\mvnw.cmd verify
```

Linux/macOS:

```sh
sh ./mvnw test
sh ./mvnw -Dtest=ArchitectureTest test
sh ./mvnw verify
```

verify запускає тести, перевірки архітектури і пакує Quarkus. Це аналог make test/lint/build. Звичайні тести не підключають БД і не запускають Docker. Негативні архітектурні fixtures знаходяться лише в src/test/java/fixtures, не входять у production artifact.

## Local PostgreSQL and dev

Виконувати лише після дозволу власника на контейнери й навчальну БД та після запуску Docker Desktop. Не застосовувати до наявних чи зовнішніх БД.

Налаштуй змінну DB_PASSWORD у локальному терміналі без запису токена або пароля в Git. DB_USER за замовчуванням ai_learning, DB_URL — jdbc:postgresql://localhost:55432/ai_learning. При зміні DB_USER онови його і для Compose, і для застосунку. .env.example містить лише назви змінних, не робочі значення. Compose може читати локальний .env; для Quarkus найпростіше експортувати ті самі значення в середовище процесу.

```powershell
& .\scripts\dev.ps1
```

```sh
sh ./scripts/dev.sh
```

Скрипт запускає тільки сервіс db Compose-проєкту ai-learning, чекає healthcheck, потім Quarkus dev. Flyway виконує bootstrap при запуску застосунку. Зупини Quarkus через Ctrl+C. Контейнер зупиняється командою docker compose stop db; дані лишаються у project volume.

GET http://localhost:8080/health — технічний JSON {"status":"UP"}. Це liveness процесу, не перевірка готовності БД та не бізнес-сценарій.

## Migration check (opt-in)

Після дозволу, з увімкненою навчальною БД та DB_PASSWORD у середовищі:

```powershell
docker compose up -d --wait db
.\mvnw.cmd -Pdbtest test
```

Maven-профіль dbtest запускає лише тест із тегом database, який застосовує Flyway, перевіряє checksum/версію й повторний migrate без нових змін. Тест може створити лише Flyway history table на переданій БД; bootstrap не створює бізнес-таблиць. Не передавай DB_URL іншого середовища.

Hibernate ORM налаштований на validate. До появи JPA entities Quarkus може повідомляти, що persistence unit не створений — це очікувано для каркаса. Не додавати фіктивні сутності для усунення цього повідомлення.

Flyway Community не має автоматичного undo SQL migrations. Перша V0.0.0 порожня, тому бізнес-змін для відкочування немає. Для наступних міграцій окремо проєктуємо rollback, не використовуємо clean або видалення volume як заміну.

## Project memory

AGENTS.md містить правила й @ARCHITECTURE.md. CLAUDE.md за варіантом уроку не створюється. У новій Claude Code сесії перевір /context. Якщо AGENTS.md не завантажився, дотримуйся fallback із тексту уроку; сам результат перевірки фіксує користувач.

## Third-party attribution

- docs/ARCHITECTURE.md — демо genkovich, MIT; docs/demo-LICENSE.txt.
- mvnw, mvnw.cmd і wrapper properties — Apache Maven Wrapper 3.3.4, Apache-2.0; docs/maven-wrapper-LICENSE.txt і docs/maven-wrapper-NOTICE.txt. Локальна адаптація: wrapper distribution cache у .cache/maven; URL дистрибутива — публічний Maven Central.
