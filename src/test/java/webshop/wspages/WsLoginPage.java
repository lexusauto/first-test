package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsLoginPage {

    private final SelenideElement loginText = $("div.login-page");
    private final SelenideElement emailField = $("input#Email");
    private final SelenideElement passwordField = $("input#Password");
    private final SelenideElement rememberMeCheckbox = $("input#RememberMe");
    private final SelenideElement loginButton = $("input.login-button");

    @Step("Валидация отображения страницы авторизации")
    public WsLoginPage loginPageValidation() {
        loginText.shouldHave(text("Welcome, Please Sign In!"));
        return this;
    }

    @Step("Ввести email {email}")
    public WsLoginPage setValueEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    @Step("Ввести пароль {password}")
    public WsLoginPage setValuePassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Установить чек-бокс 'Запомнить меня'")
    public WsLoginPage setValueCheckboxRememberMe() {
        rememberMeCheckbox.click();
        return this;
    }

    @Step("Нажать на кнопку входа")
    public WsWelcomePage clickLoginAction() {
        loginButton.click();
        return new WsWelcomePage();
    }

    @Step("Проверить, что появилось сообщение с ошибкой валидации почты")
    public WsLoginPage verifyEmailValidationErrorAppear() {
        $("span.field-validation-error").shouldBe(visible);
        return this;

    }

}
