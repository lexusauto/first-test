package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsWelcomePage {

    private final SelenideElement registerButton = $("a.ico-register");
    private final SelenideElement loginButton = $("a.ico-login");
    private final ElementsCollection emailHeader = $$("div.header-links ul li a");
    private final ElementsCollection computerProducts = $$("ul.top-menu li a");
    private final SelenideElement desktopsLink = $(byText("Desktops"));

    public WsRegistrationPage clickRegisterButton() {
        registerButton.click();
        return new WsRegistrationPage();
    }

    public WsLoginPage clickLoginButton() {
        loginButton.click();
        return new WsLoginPage();
    }

    public WsWelcomePage checkUserLoggedIn(String email) {
        emailHeader.get(0).shouldHave(text(email));
        return this;
    }

    public WsWelcomePage hoverComputersMenu() {
        computerProducts.get(1).hover();
        return this;
    }

    public WsDesktopsPage selectDesktops() {
        desktopsLink.click();
        return new WsDesktopsPage();
    }
}
