package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsLoginPage {

    private final SelenideElement loginText = $("div.login-page");
    private final SelenideElement emailField= $("input#Email");
    private final SelenideElement passwordField= $("input#Password");
    private final SelenideElement rememberMeCheckbox = $("input#RememberMe");
    private final SelenideElement loginButton= $("input.login-button");

            public WsLoginPage loginPageValidation() {
                loginText.shouldHave(text("Welcome, Please Sign In!"));
                return this;
            }

    public WsLoginPage setValueEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    public WsLoginPage setValuePassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public WsLoginPage setValueCheckboxRememberMe() {
        rememberMeCheckbox.click();
        return this;
    }

    public WsWelcomePage clickLoginAction() {
                loginButton.click();
                return new WsWelcomePage();
    }

}
