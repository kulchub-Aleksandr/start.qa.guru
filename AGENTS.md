# QA automation agent
This project automates login scenarios and the optional flows listed in its architecture overview.
Stack: Java / Gradle / JUnit 6 / Selenide. Respond in English.
## Read only what the task needs
- Project map: [knowledge/architecture/overview.md](knowledge/architecture/overview.md).
- Commands and prerequisites: [knowledge/testing/run-tests.md](knowledge/testing/run-tests.md).
- Data and environment: [knowledge/architecture/test-data.md](knowledge/architecture/test-data.md).
- Diagnosis: [knowledge/testing/debugging.md](knowledge/testing/debugging.md).
- Version sources: [knowledge/stack/versions.md](knowledge/stack/versions.md).
- Code conventions: [knowledge/pageobjects/style.md](knowledge/pageobjects/style.md) and [knowledge/testing/assertions.md](knowledge/testing/assertions.md).
- Remote farm: [knowledge/farm/selenoid.md](knowledge/farm/selenoid.md).
- Integration runbooks: [knowledge/integrations/allure-testops.md](knowledge/integrations/allure-testops.md) (templates, not evidence of live integration).

## Skills
- Run/check existing tests: [.agents/skills/run-tests/SKILL.md](.agents/skills/run-tests/SKILL.md).
- Create/extend a UI test: [.agents/skills/create-ui-test/SKILL.md](.agents/skills/create-ui-test/SKILL.md).
- Diagnose and fix a failing test: [.agents/skills/fix-failing-test/SKILL.md](.agents/skills/fix-failing-test/SKILL.md).

## Tools
These are explicit POSIX shell entry points, not MCP tools, hooks or automatically registered agent capabilities.
- Check CLI versions: [tools/versions.sh](tools/versions.sh) (`sh tools/versions.sh`).
- Compile/collect without a browser run: [tools/compile-tests.sh](tools/compile-tests.sh) (`sh tools/compile-tests.sh`).
- List/select test scopes: [tools/run-tests.sh](tools/run-tests.sh) (`sh tools/run-tests.sh list`).
- Inspect existing work: [tools/git.sh](tools/git.sh) (`sh tools/git.sh status`, `diff`, or `check`).
- Inspect Jenkins builds and trigger runs: [tools/jenkins.sh](tools/jenkins.sh) (`sh tools/jenkins.sh status`; `trigger` needs `--allow-ci-write`). Env contract: [knowledge/integrations/ci-jenkins.md](knowledge/integrations/ci-jenkins.md).
- Inspect TestOps launches and upload results: [tools/testops.sh](tools/testops.sh) (`sh tools/testops.sh launches`, `launch N`, `--allow-sut`, `--upload`, `--finalize`; needs `ALLURE_*` env).
Read the command and permission contract in [knowledge/testing/run-tests.md](knowledge/testing/run-tests.md) before execution.

## Safeguards
- Read current source and reuse its architecture before changing anything.
- Do not add dependencies, change production code or weaken assertions just to make tests green.
- Use the existing synchronization mechanisms, not arbitrary sleeps.
- Mirror the canonical test shape from the knowledge docs: same chain form and check count — no assertions beyond the scenario's expected outcome.
- Ask before installs/downloads, external SUT access, CI jobs, uploads, integration writes or destructive cleanup.
- Do not write secrets to files or reports; do not commit/push without an explicit request.
- Use the smallest relevant scope; preserve existing work and failure evidence.
- Report the exact command, tool versions, exit code and executed/passed/failed/skipped counts.
- Compilation, collection and skill-file validation are not successful test execution or agent activation.
- If blocked, report BLOCKED and the minimal required authorization or missing prerequisite; do not repair infrastructure.

Devin CLI supports `.agents/skills/`; other clients must be checked separately.
If discovery is unavailable, read the linked SKILL.md explicitly — do not duplicate skills or modify IDE settings.
These notes describe the generated snapshot; after changes, actual source and configuration remain authoritative.
