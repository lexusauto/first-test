package webshop.tests;

import com.codeborne.selenide.Configuration;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webshop.wspages.WsRegistrationPage;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static webshop.Config.Config.WEBSHOP_REGISTRATIONPAGE_URL;
import static webshop.Config.Config.WEBSHOP_URL;

public class LoginTest {

    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @BeforeEach
    void beforeEach() {
        password = faker.harryPotter().character() + faker.number().positive();
        email = faker.internet().emailAddress();

        open(WEBSHOP_REGISTRATIONPAGE_URL, WsRegistrationPage.class)
        .register(
                faker.name().firstName(),
                faker.name().lastName(),
                email,
                password)
        .checkEmailIsShown(email);

        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void succesLoginTest() {
        Configuration.timeout=5000;
        Configuration.holdBrowserOpen=true;

        open("https://demowebshop.tricentis.com/", WsWelcomePage.class)
                .clickLoginButton()
                .loginPageValidation()
                .setValueEmail(email)
                .setValuePassword(password)
                .setValueCheckboxRememberMe()
                .clickLoginAction()
                .checkUserLoggedIn(email);
    }

}
