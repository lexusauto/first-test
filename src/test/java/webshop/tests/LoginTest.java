package webshop.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import webshop.Config.TestBase;
import webshop.wspages.WsLoginPage;
import webshop.wspages.WsRegistrationPage;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static webshop.Config.Config.*;

public class LoginTest extends TestBase {

    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @Nested
    public class PostitveTests {
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
        @DisplayName("Успешная авторизация webshop")
        @Owner("Alex")
        @Tag("Positive")
        @Severity(CRITICAL)
        void succesLoginTest() {
            Configuration.timeout=5000;
            Configuration.holdBrowserOpen=true;

            open(WEBSHOP_URL, WsWelcomePage.class)
                    .clickLoginButton()
                    .loginPageValidation()
                    .setValueEmail(email)
                    .setValuePassword(password)
                    .setValueCheckboxRememberMe()
                    .clickLoginAction()
                    .checkUserLoggedIn(email);
        }

    }


    @ParameterizedTest
    @DisplayName("Валидация сообщения об ошибке при некорректном пароле")
    @Owner("Alex")
    @CsvFileSource(resources = "/email.csv")
    @Tag("Negative")
    @Severity(CRITICAL)
    void invalidEmailLoginest(String email) {
        open(WEBSHOP_LOGIN_URL, WsLoginPage.class)
                .setValueEmail(email)
                .setValuePassword("password")
                .verifyEmailValidationErrorAppear()
                .clickLoginAction();


    }

}
