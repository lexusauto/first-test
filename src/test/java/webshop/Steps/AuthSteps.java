package webshop.Steps;

import net.datafaker.Faker;
import webshop.wspages.WsRegistrationPage;

import static com.codeborne.selenide.Selenide.open;
import static webshop.Config.Config.WEBSHOP_REGISTRATIONPAGE_URL;

public class AuthSteps {
    private static final Faker faker = new Faker();

    public void registerNewUser() {
        open(WEBSHOP_REGISTRATIONPAGE_URL, WsRegistrationPage.class)
                .register(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.harryPotter().character() + faker.number().positive());
    }
}
