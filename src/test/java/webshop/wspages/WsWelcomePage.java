package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsWelcomePage {

    private final SelenideElement registerButton = $("a.ico-register");
    private final SelenideElement loginButton =$("a.ico-login");
    private final ElementsCollection emailHeader = $$("div.header-links ul li a");

    public WsRegistrationPage clickRegisterButton(){
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
}
