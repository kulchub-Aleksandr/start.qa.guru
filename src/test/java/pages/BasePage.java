package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {  // общая база страниц
    protected SelenideElement el(String testId) {  // элемент по data-testid
        return $("[data-testid=" + testId + "]");
    }
}
