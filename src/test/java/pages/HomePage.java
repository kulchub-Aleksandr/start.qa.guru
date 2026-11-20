package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.format;

public class HomePage extends BasePage {  // Page Object — элементы и действия страницы
    private final SelenideElement layout = el("multistack-layout");  // элемент по data-testid
    private final SelenideElement welcomeMessage = el("welcome-message");  // элемент по data-testid
    private final SelenideElement logoutButton = el("logout-button");  // элемент по data-testid

    private static final String SUCCESSFUL_LOGIN_TEMPLATE = "Welcome, %s!";

    @Step("Открыть страницу /")
    public HomePage openPage() {
        open("/");
        return this;
    }

    @Step("Проверить приветствие")
    public HomePage verifyWelcomeMessage(String userName) {
        welcomeMessage.shouldHave(text(format(SUCCESSFUL_LOGIN_TEMPLATE, userName)));
        return this;
    }
}
