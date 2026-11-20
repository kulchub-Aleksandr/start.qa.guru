package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage {  // Page Object — элементы и действия страницы
    private final SelenideElement loginForm = el("login-form");  // элемент по data-testid
    private final SelenideElement loginInput = el("login-input");  // элемент по data-testid
    private final SelenideElement passwordInput = el("password-input");  // элемент по data-testid
    private final SelenideElement submitButton = el("submit-button");  // элемент по data-testid
    private final SelenideElement errorMessage = el("error-message");  // элемент по data-testid

    @Step("Открыть страницу /login")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Ввести логин: {userName}")
    public LoginPage setUserName(String userName) {
        loginInput.setValue(userName);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Отправить форму логина")
    public HomePage submit() {
        submitButton.click();
        return new HomePage();
    }
    
    @Step("Отправить форму логина и остаться на странице")
    public LoginPage submitAndStay() {
        submitButton.click();
        return this;
    }
    
    @Step("Проверить сообщение об ошибке: {expectedText}")
    public void verifyErrorMessage(String expectedText) {
        errorMessage.shouldBe(visible).shouldHave(text(expectedText));
    }
}
