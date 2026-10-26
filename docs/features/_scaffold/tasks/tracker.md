# Scaffold tracker

План: tasks.json. Реалізація вручну за кроком 3 уроку; скіл implement-tasks не запускався.

| Task | State | Evidence |
| --- | --- | --- |
| S1 | done | Maven verify succeeds on Java 21 and 25; Quarkus boots; five BC README files, no business Java classes |
| S2 | done | 2 HTTP tests + 5 ArchUnit tests; zero failures/errors; invalid/valid fixtures checked |
| S3 | configured; DB execution deferred | Compose config validated without starting containers; ORM validate and bootstrap SQL present; MigrationTest not executed without authorization |
| S4 | local done; remote CI pending push | Wrapper verify succeeds on Java 21; GitHub workflow invokes same command |
| S5 | repository artifacts done; Claude session pending | AGENTS 77 lines, root ARCHITECTURE 42 lines, SPEC and submission guide present; /context screenshot and fresh-session answers require user session |

DB DoD is not marked complete: no database has been created or migrated. This follows the lesson's no-Docker path for local tests; database verification remains opt-in. Independent read-only review: PASS, no actionable code findings. Actual command evidence is recorded in docs/lesson-submission.md.
