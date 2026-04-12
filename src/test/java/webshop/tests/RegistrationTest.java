package webshop.tests;

import com.codeborne.selenide.Configuration;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static webshop.Config.Config.WEBSHOP_URL;

public class RegistrationTest {

private static final Faker faker = new Faker();

@Test
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
