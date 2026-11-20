package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;  // освободить сессию

public class BaseTest {  // общий lifecycle: setup, ресурсы, teardown

    protected final LoginPage loginPage = new LoginPage();  // page object'ы теста

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

    @AfterEach  // закрытие сессии после теста
    void afterEach() {
        printSelenoidSession();  // id сессии в stdout — по нему цепляются видео/логи
        closeWebDriver();  // освободить сессию
    }

    private void printSelenoidSession() {  // id сессии в stdout — по нему цепляются видео/логи
        if (WebDriverRunner.hasWebDriverStarted() &&
                WebDriverRunner.getWebDriver() instanceof RemoteWebDriver) {
            System.out.println("SELENOID_SESSION=" +  // id сессии в stdout — по нему цепляются видео/логи
                ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getSessionId());
        }
    }
}
