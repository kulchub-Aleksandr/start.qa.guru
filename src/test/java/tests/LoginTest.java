package tests;

import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.Test;
import pages.*;

import static testdata.TestData.*;

public class LoginTest extends BaseTest {  // тестовый класс
    // Логин валидной парой
    @Test
    @TmsLink("QAG-001")
    void successfulLoginTest() {
        loginPage.openPage()
                .setUserName(USERNAME)
                .setPassword(PASSWORD)
                .submit()
                .verifyWelcomeMessage(USERNAME);
    }
    
    // Неудачная авторизация
    @Test
    @TmsLink("QAG-002")
    void unsuccessfulLoginTest() {
        loginPage.openPage()
                .setUserName(USERNAME)
                .setPassword(WRONG_PASSWORD)
                .submitAndStay()
                .verifyErrorMessage(WRONG_CREDENTIALS_MESSAGE);
    }
}
