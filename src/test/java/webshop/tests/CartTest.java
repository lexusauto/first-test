package webshop.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import webshop.Config.TestBase;
import webshop.Steps.AuthSteps;
import webshop.wspages.WsCartPage;
import webshop.wspages.WsDesktopProductPage;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.Config.Config.WEBSHOP_URL;

public class CartTest extends TestBase {

    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach() {
        Configuration.timeout = 5000;
        authSteps.registerNewUser();
    }


    @Test
    @DisplayName("Добавление товара в корзину и валдиация продукта, количества и итоговой суммы")
    @Owner("Alex")
    @Tag("Positive")
    @Severity(CRITICAL)
    void addItemToCartTest() {

        int processorIndex = 2;
        String quantity = "3";

        WsDesktopProductPage productPage = open(WEBSHOP_URL, WsWelcomePage.class)
                .hoverComputerProducts()
                .selectDesktops()
                .selectProduct(0)//Выбираем продукт из списка по его индексу на странице
                .selectProcessor(processorIndex)
                .setQuantity(quantity)
                .addToCart()
                .addToCartValidation()
                .shopCartQuantityValidation(quantity);

        String itemName = productPage.getItemName();
        float baseItemPrice = productPage.getItemPrice();
        float processorPrice = getProcessorPrice(processorIndex);
        float expectedSumTotal = (baseItemPrice + processorPrice) * Float.parseFloat(quantity);

        WsCartPage cartPage = productPage.goToCart();

        assertEquals(itemName, cartPage.getItemName());//Валдиация имение продукта

        assertEquals(quantity, cartPage.getQuantity());//Валдиация количества продукта

        assertEquals(expectedSumTotal, Float.parseFloat(cartPage.getTotalSum()));//Валидация суммы продукта
    }

    private float getProcessorPrice(int processorIndex) {
        return switch (processorIndex) {
            case 0 -> 0f;      // slow - без надбавки
            case 1 -> 15f;     // medium - +15$
            case 2 -> 100f;    // fast - +100$
            default -> throw new IllegalArgumentException(
                    "Unknown processor index: " + processorIndex);
        };
    }

}
