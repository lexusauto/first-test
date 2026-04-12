package webshop.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import webshop.Config.TestBase;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static webshop.Config.Config.WEBSHOP_URL;

public class RegistrationTest extends TestBase {

private static final Faker faker = new Faker();

@Test
@DisplayName("Успешная регистрация нового пользователя")
@Owner("Alex")
@Tag("positive")
@Severity(CRITICAL)
@Epic("Авторизация")
@Feature("Регистрация")
@Story("Регистрация нового пользователя")
@Link("https://maag-fashion.com/")
@Description("бла бла бла")
void registrationTest() {

Configuration.timeout=5000;
Configuration.holdBrowserOpen=true;

String password = faker.harryPotter().character() + faker.number().positive();
String email = faker.internet().emailAddress();

open(WEBSHOP_URL, WsWelcomePage.class)
.clickRegisterButton()
.registerPageValidation()
.chooseGender()
.setValueFirstName(faker.name().firstName())
.setValueLastName(faker.name().lastName())
.setValueEmail(email)
.setValuePassword(password)
.setValueConfirmPassword(password)
.clickRegisterAction()
.registerResultValidation()
.checkEmailIsShown(email);
    }
}
