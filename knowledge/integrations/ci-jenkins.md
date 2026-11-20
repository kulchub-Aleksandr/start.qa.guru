# Jenkins — build reads and gated triggers

Job: `${JENKINS_JOB:-test-evolution/run}` on `$JENKINS_URL` — the plain
`folder/job` path; the tool maps it to `job/folder/job/…` REST segments.

```bash
export JENKINS_URL='https://jenkins.qa.guru'
export JENKINS_USER='<user>'
export JENKINS_TOKEN='<api-token>'        # CI secret — never committed
# export JENKINS_JOB='test-evolution/run' # optional override

sh tools/jenkins.sh status            # JENKINS_BUILD/RESULT/BUILDING of lastBuild
sh tools/jenkins.sh console 42        # raw consoleText
sh tools/jenkins.sh artifacts 42      # one relativePath per line
sh tools/jenkins.sh --allow-ci-write trigger TEST_FILTER=tests.LoginTest
```

Raw equivalent — credentials travel through a stdin curl config, never argv:

```bash
printf 'user = "%s:%s"\n' "$JENKINS_USER" "$JENKINS_TOKEN" |
  curl -sfS -K - "$JENKINS_URL/job/test-evolution/job/run/lastBuild/api/json?tree=number,result"
```

Endpoints: `GET <build>/api/json?tree=…`, `GET <build>/consoleText`,
`POST build`/`buildWithParameters` (API-token auth needs no crumb).

## Do
- Read `consoleText` before calling a CI failure flaky or a test defect.
- Poll `status` after `trigger`; the tool prints the `Location` queue URL.

## Don't
- Never echo the token, embed it in the URL (`user:token@host` leaks via the
  process list) or write it to files, logs or the report.
- Do not spam `trigger` — every call queues a real build.

