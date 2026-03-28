package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsRegistrationPage {

    private final SelenideElement registerText = $("div.registration-page");
    private final SelenideElement maleChoiseButton = $("div.registration-page");
    private final SelenideElement firstNameField = $("input#FirstName");
    private final SelenideElement lastNameField = $("input#LastName");
    private final SelenideElement emailField = $("input#Email");
    private final SelenideElement passwordField = $("input#Password");
    private final SelenideElement confirmPasswordField = $("input#ConfirmPassword");
    private final SelenideElement registerButton = $("input#register-button");
    private final SelenideElement registrationResult = $("div.result");
    private final ElementsCollection registerEmail =  $$("div.header-links ul li a");

public WsRegistrationPage register(String firstName, String lastName, String email, String password) {
            chooseGender()
            .setValueFirstName(firstName)
            .setValueLastName(lastName)
            .setValueEmail(email)
            .setValuePassword(password)
            .setValueConfirmPassword(password)
            .clickRegisterAction()
            .registerResultValidation();
            return this;
}

public WsRegistrationPage registerPageValidation() {
    registerText.shouldHave(text("Register"));
    return this;
}

public WsRegistrationPage chooseGender() {
    maleChoiseButton.click();
    return this;
}

public WsRegistrationPage setValueFirstName(String firstname) {
    firstNameField.setValue(firstname);
    return this;
}

    public WsRegistrationPage setValueLastName(String lastname) {
        lastNameField.setValue(lastname);
        return this;
    }

    public WsRegistrationPage setValueEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    public WsRegistrationPage setValuePassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public WsRegistrationPage setValueConfirmPassword(String password) {
        confirmPasswordField.setValue(password);
        return this;
    }

    public WsRegistrationPage clickRegisterAction() {
        registerButton.click();
        return this;
    }

    public WsRegistrationPage registerResultValidation() {
        registrationResult.shouldHave(text("Your registration completed"));
        return this;
    }

    public WsRegistrationPage checkEmailIsShown(String email) {
        registerEmail.get(0).shouldHave(text(email));
        return this;
    }

}
