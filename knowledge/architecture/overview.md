# Architecture

Generated stack: Java / Gradle / JUnit 6 / Selenide.
The project contains browser tests against the configured application; no API client, mock server or separate API test layer is generated.

## Source map
- Build/dependencies: [build.gradle](../../build.gradle).
- Test modules: [src/test/java/tests/LoginTest.java](../../src/test/java/tests/LoginTest.java).
- Shared setup/teardown: [src/test/java/tests/BaseTest.java](../../src/test/java/tests/BaseTest.java).
- Page Object conventions: [../pageobjects/style.md](../pageobjects/style.md).
- Lifecycle contract: [lifecycle.md](lifecycle.md).
- Data/environment: [test-data.md](test-data.md).
- Test commands: [../testing/run-tests.md](../testing/run-tests.md).
- Checks placement: [../testing/assertions.md](../testing/assertions.md).
- Remote farm contract: [../farm/selenoid.md](../farm/selenoid.md).

## Existing scenarios
- Successful login.

Canonical test shape — verbatim from this project:

```java
    @Test
    @TmsLink("QAG-001")
    void successfulLoginTest() {
        loginPage.openPage()
                .setUserName(USERNAME)
                .setPassword(PASSWORD)
                .submit()
                .verifyWelcomeMessage(USERNAME);
    }
```

## Checks and synchronization
Assertions are inside the generated Page Object verification methods.
Reuse Selenide conditions and their retrying shouldHave/shouldBe checks.
Each check is a single terminal retrying assertion on the expected outcome — never prepend weaker precondition checks (e.g. visibility) or append state re-verification after it.
No component-object layer is generated; do not document nonexistent components.

## Configuration and CI
CI template: [Jenkinsfile](../../Jenkinsfile). Its presence does not prove a successful CI run.
Integration files, if present, are templates, not evidence of live TestOps/Jira/Confluence integration.
Do not import the course pack's Gradle wrappers, tags, mock environments or testE2e task: they are not configured in this project.
