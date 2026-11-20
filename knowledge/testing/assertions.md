# Assertions

Canonical: checks live in the Page Object verify* methods; tests call them as steps.

```java
    @Step("Проверить приветствие")
    public HomePage verifyWelcomeMessage(String userName) {
        welcomeMessage.shouldHave(text(format(SUCCESSFUL_LOGIN_TEMPLATE, userName)));
        return this;
    }
```

## Do
- Add new checks as verify* methods next to the existing ones; keep their synchronization intact.
- Reuse the framework's retrying conditions; the expected texts come from the test data source.
- Mirror the canonical test verbatim: same chain form and check count — one scenario asserts only its expected outcome.

## Don't
- No weakened assertions, arbitrary sleeps or expected-text edits to obtain a green run.
- No redundant checks: a retrying assertion already waits for the element state; never prepend visibility checks to a text/state assertion.
- No assertion padding: assert exactly the scenario's expected outcome — no extra visibility checks, no re-verifying page or form state after the terminal assertion.
