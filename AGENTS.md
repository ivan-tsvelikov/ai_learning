# Working preferences

- Act autonomously only inside the project folder I identified.
- Modify databases, Windows/system files, external services, or resources outside the project only with my explicit approval. The same approval requirement applies to commands that could affect resources outside the project.
- Keep responses brief unless I request a thorough explanation.
- Do not commit or push unless I explicitly ask. When I request a commit, use a short, descriptive message prefixed with the relevant ticket number, for example [DEV-1234].

## Required MCP readiness

Before investigating logs, checking a Jira ticket, reviewing code, or changing code, verify every MCP required for that activity:

| Activity | Required MCP servers |
| --- | --- |
| Log investigation | opensearch_prod, opensearch_uat |
| Jira ticket investigation | opensearch_prod, opensearch_uat, atlassian_mcp, gitlab_mcp, serena_mcp |
| Code review or code changes | gitlab_mcp, serena_mcp, idea_mcp |

- If a task includes multiple activities, check the combined set of required servers before starting those activities.
- Readiness means a successful, safe, read-only call to each required server: use its health/status operation if available; otherwise use a minimal read-only request. A server appearing in the tool list alone is insufficient.
- If any required server is unavailable, fails its readiness check, or cannot be checked safely, stop execution, report the server and failure, and ask me how to proceed. Do not continue the investigation or substitute another tool without my instruction.
- Node MCP is optional; its failure does not block work.

## Java skill

- Use personal-java-engineer for Java implementation, refactoring, bug fixes, reviews, design, and tests. It is not required for non-Java work.
- If the skill is missing from the session's skill list, check ~/.codex/skills/personal-java-engineer/SKILL.md before concluding it is unavailable.
- If its SKILL.md cannot be read, stop Java work and notify me. Read the supporting references required by the skill for the task.

## Scope and decisions

- Establish the intended outcome, scope, and acceptance criteria; distinguish the underlying goal from a proposed solution.
- Ask when uncertainty or competing interpretations affect scope, correctness, or authorization; otherwise state a reasonable assumption and continue.
- Challenge assumptions using evidence, consider alternative causes, and explain a simpler approach when appropriate.
- For multi-step tasks, state a brief plan and preserve constraints across follow-ups.
- Handle routine work autonomously within the authorized scope; leave unresolved goals, consequential tradeoffs, and required approvals to me.
- Support my learning when exploring new technology.
- Use sub-agents only when permitted, and keep delegated results reviewable.

## Implementation

- Choose the simplest solution that satisfies the requirements and matches the project's existing style.
- Do not add unrequested features, speculative flexibility, single-use abstractions, or handling for impossible scenarios.
- Keep every changed line tied to the request. Do not modify unrelated code, comments, or formatting, or perform unrelated refactoring.
- Remove imports, variables, and functions made unused by your changes. Mention unrelated dead code without deleting it unless asked.

## Verification and reporting

- Inspect actual code, callers, tests, and configuration before making behavioral claims.
- Define verifiable success criteria and use focused checks proportional to the task.
- For validation changes, test invalid inputs; for bug fixes, add a reproducing test; for refactoring, check relevant tests before and after.
- Report checks performed, failures, and remaining uncertainty honestly; distinguish facts, inferences, and unknowns.
- Protect secrets and sensitive data; verify third-party licenses and attribution.
- Follow team rules for disclosing AI assistance. Never fabricate authorship, review, or approval.

## Work with a migration scripts

- When working with a flyway migrations follow next migration file naming rules: 
The first digit increments only if a new table is created or an old table is deleted
The second digit increments only for the creation or deletion of auxiliary DB features, such as triggers and procedures.
The third digit increments only for altering existing tables and procedures
