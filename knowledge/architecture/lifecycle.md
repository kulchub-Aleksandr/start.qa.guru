# Lifecycle

Source: [src/test/java/tests/BaseTest.java](../../src/test/java/tests/BaseTest.java) — shared lifecycle code for this snapshot.
Selected configuration: browserLevel=full, browserPlace=class, teardown=afterEach.

```java
    @BeforeAll  // setup: конфигурация браузера на класс
    static void beforeAll() {
        Configuration.baseUrl = "https://autotests.ai/stack/backend-java-spring/frontend-typescript-react";  // адрес тестируемого приложения
        Configuration.browser = "chrome";  // браузер прогона
        Configuration.browserSize = "1920x1080";  // размер окна
        Configuration.headless = false;  // без видимого окна
        Configuration.timeout = 5_000;  // таймаут ожиданий
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true,
                "enableLog", true,
                "enableHAR", true));  // capabilities сессии фермы
        Configuration.browserCapabilities = caps;  // capabilities сессии фермы
        SelenideLogger.addListener("allure", new AllureSelenide());  // скриншот и page source на падении
    }
```

## Do
- Reuse the shared setup actually present here; a teardown-only file does not configure a browser.

## Don't
- Do not move setup or cleanup across scopes without checking the selected lifecycle configuration and resource ownership.
