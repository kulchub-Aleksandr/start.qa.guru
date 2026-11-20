# Page Objects

Style: fluent.
Canonical: [src/test/java/pages/BasePage.java](../../src/test/java/pages/BasePage.java), [src/test/java/pages/LoginPage.java](../../src/test/java/pages/LoginPage.java), [src/test/java/pages/HomePage.java](../../src/test/java/pages/HomePage.java).
Usage: [src/test/java/tests/LoginTest.java](../../src/test/java/tests/LoginTest.java).

Shape of a page action — verbatim from this project:

```java
    @Step("Ввести логин: {userName}")
    public LoginPage setUserName(String userName) {
        loginInput.setValue(userName);
        return this;
    }

    @Step("Отправить форму логина")
    public HomePage submit() {
        submitButton.click();
        return new HomePage();
    }
```

## Do
- Keep locators and page actions in these existing classes/modules; reuse an existing object before adding one.
- Fluent actions return the current or next page. Follow the actual return types of submit/error/logout methods.
- Follow the synchronous calls and return types demonstrated by the existing test.
- Assertions belong in the existing verify* methods — see [../testing/assertions.md](../testing/assertions.md).
- Preserve the configured application URL prefix and existing relative navigation conventions.

## Don't
- Do not move locators into test bodies when extending this architecture.
- Transitions are implemented by the existing form submission and logout methods, not by a separate routing framework.
- There are no generated ComponentObjects. Add one only for a real reused component, not for this documentation.
