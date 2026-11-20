# Allure TestOps — launches and results upload

Secrets only via env / CI variables — never in the repository.

```bash
export ALLURE_ENDPOINT='https://allure.qa.guru'
export ALLURE_TOKEN='<token>'            # CI secret
export ALLURE_PROJECT_ID='<project-id>'  # CI variable

allurectl watch --results build/allure-results -- \
  gradle --offline test
```

Locally, `sh tools/testops.sh --allow-sut` runs the same contract.
`--upload` uploads already collected results; `--finalize` prints the current
run's `TESTOPS_LAUNCH_ID`/`TESTOPS_LAUNCH_URL` and writes `testops-launch.env` (CI post step).
Read verbs: `launches` lists recent launches and `launch N` fetches one via
REST (`Api-Token` header — the same `ALLURE_*` env, sent through a stdin
curl config so it never reaches argv).
Case binding is generated via `@TmsLink`.

