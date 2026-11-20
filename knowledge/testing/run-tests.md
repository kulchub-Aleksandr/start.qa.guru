# Running tests

POSIX shell commands, run from the project root.
The harness does not install dependencies, launch services or run tests when enabled.

## Prerequisites and permissions
Read [../stack/versions.md](../stack/versions.md) and verify the installed versions:
```bash
java -version
javac -version
gradle --version
git --version
```
Verify dependencies, browser/driver availability and SUT readiness before execution; first use may download or resolve them — ask before installs/downloads, network access or environment repair.
The configured external SUT requires explicit authorization; registration additionally writes persistent server data.

## Tools
Run the scripts with `sh` — no exec bit needed; POSIX sh + dirname + sed required, PowerShell unsupported.
Scripts resolve the project root relative to themselves, including paths with spaces.
- [tools/versions.sh](../../tools/versions.sh): verify selected CLI pins. Git has no separate pin; only its availability/version command is checked.
- [tools/compile-tests.sh](../../tools/compile-tests.sh): compile or collect, never a browser-test PASS.
- [tools/run-tests.sh](../../tools/run-tests.sh): list selected case IDs and groups; execute only an explicit scope.
- [tools/git.sh](../../tools/git.sh): status, staged/unstaged diff, and whitespace checks in this project; no add/commit/push/reset or repository initialization.
```bash
sh tools/git.sh status
sh tools/versions.sh
sh tools/compile-tests.sh
sh tools/run-tests.sh list
```
```bash
sh tools/run-tests.sh --allow-sut one 'login-success'
sh tools/run-tests.sh --allow-sut group 'login'
sh tools/run-tests.sh --allow-sut all
```
`--allow-sut` acknowledges approval for the selected target and any registration writes; it is not a sandbox and does not replace client permissions.
Gradle commands use offline dependency resolution; a missing cached dependency is BLOCKED, not permission to install it.
Build scripts, plugins and driver managers can execute code or use the network — offline flags do not sandbox them; verify permissions separately.
Preflight checks CLI versions only — not dependency, browser or SUT readiness.
Tools preserve native output and exit codes: 64 = invalid arguments/scope, 69 = missing/mismatched prerequisites or no selected cases, 77 = SUT approval not acknowledged.
Tools never interpret shell text or forward runner flags; after adding/renaming tests, update the explicit selectors from source — this is a snapshot, not runtime discovery.
Git tools disable external diff/textconv helpers, fsmonitor and index-refresh writes; existing Git configuration and repository boundaries still apply.
No hooks are installed or activated.

## Bootstrap reference
Only execute setup commands after authorization. Wrappers are not supplied.
```bash
# wrapper в проекте нет; при необходимости: gradle wrapper --gradle-version 9.6.0 → ./gradlew
```

## Compile / syntax / discovery only
```bash
gradle --offline testClasses
```
These checks do not execute the browser scenarios. Discovery for the full selection should contain 1 tests; a focused filter intentionally selects fewer.

## Focused execution
One test:
```bash
gradle --offline test --tests 'tests.LoginTest.successfulLoginTest'
```
One class/module:
```bash
gradle --offline test --tests 'tests.LoginTest'
```

## All selected tests
Use the full suite only when that scope is requested and the target/data changes are authorized:
```bash
gradle --offline test
```
No tags, categories/groups or custom testE2e task are configured. Do not guess tag filters from another project.
Commands omit clean so the previous failure evidence is not silently deleted.

## Evidence
JUnit-style XML: `build/test-results/test/`; Gradle HTML: `build/reports/tests/test/` after an actual test run.
Allure results: `build/allure-results/`. Verify files were produced by this run.
Record command, scope, actual versions, exit code and executed/passed/failed/skipped counts. A zero exit code with no executed tests is not a test pass.
Reusing results from an earlier run is not verification of the current changes.

## CI and integrations
Read the real CI template: [Jenkinsfile](../../Jenkinsfile).
CI jobs and TestOps/Jira/Confluence writes are separate permission-gated actions, not part of a local test request by default.
Jenkins runs: [tools/jenkins.sh](../../tools/jenkins.sh) (`status`, `console [N]`, `artifacts N`; `trigger` only with `--allow-ci-write`). Env: `JENKINS_URL`/`JENKINS_USER`/`JENKINS_TOKEN` (+ `JENKINS_JOB` override) — see [knowledge/integrations/ci-jenkins.md](../../knowledge/integrations/ci-jenkins.md).
TestOps: [tools/testops.sh](../../tools/testops.sh) — `launches`/`launch N` read the REST API; `--allow-sut`/`--upload`/`--finalize` wrap allurectl — requires real `ALLURE_ENDPOINT`, `ALLURE_TOKEN` and `ALLURE_PROJECT_ID` from approved env/CI storage; see [knowledge/integrations/allure-testops.md](../../knowledge/integrations/allure-testops.md). Never execute placeholder tokens.
