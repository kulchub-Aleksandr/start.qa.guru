# Locators

Canonical: [src/test/java/pages/LoginPage.java](../../src/test/java/pages/LoginPage.java) field declarations via the `el` helper in [src/test/java/pages/BasePage.java](../../src/test/java/pages/BasePage.java).

```java
    private final SelenideElement loginForm = el("login-form");  // элемент по data-testid
    private final SelenideElement loginInput = el("login-input");  // элемент по data-testid
    private final SelenideElement passwordInput = el("password-input");  // элемент по data-testid
    private final SelenideElement submitButton = el("submit-button");  // элемент по data-testid
    private final SelenideElement errorMessage = el("error-message");  // элемент по data-testid
```

## Do
- New elements get a field/attribute declared the same way; the name mirrors the SUT `data-testid`.

## Don't
- No inline CSS/XPath selectors in test bodies or methods — the helper is the single locator convention.
