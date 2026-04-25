package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsWelcomePage {

    private final SelenideElement registerButton = $("a.ico-register");
    private final SelenideElement loginButton =$("a.ico-login");
    private final ElementsCollection emailHeader = $$("div.header-links ul li a");
    private final ElementsCollection computerProducts = $$("ul.top-menu li a");
    private final SelenideElement computerProductsDesktops = $(byText("Desktops"));

    @Step("Переход на страницу регистрации")
    public WsRegistrationPage clickRegisterButton(){
       registerButton.click();
        return new WsRegistrationPage();
    }

    @Step("Перейти на страницу авторизации")
    public WsLoginPage clickLoginButton() {
      loginButton.click();
      return new WsLoginPage();
    }

    @Step("Валидация успешной авторизации пользователя {email}")
    public WsWelcomePage checkUserLoggedIn(String email) {
        emailHeader.get(0).shouldHave(text(email));
        return this;
    }

    @Step("Навести курсор на Computer Products")
    public WsWelcomePage hoverComputerProducts() {
        computerProducts.get(1).hover();
        return this;
    }

    @Step("Выбрать Desktops продукты")
    public WsComputers_DesktopsPage selectDesktops() {
        computerProductsDesktops.click();
        return new WsComputers_DesktopsPage();
    }
}
