# Allure steps

Canonical: the emitted step idiom for `annotation` policy on `allure3` report family.

```java
    @Step("Открыть страницу /login")
    public LoginPage openPage() {
        open("/login");
        return this;
    }
```

## Do
- Verb plus parameters in the step text.
- Steps are declared on page-object methods; use the method parameter names shown in the emitted example.

## Don't
- Do not mix both idioms in one scenario — steps stay in one layer, not on the PO method and the test at once.
