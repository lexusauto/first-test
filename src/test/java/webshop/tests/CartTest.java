package webshop.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webshop.Steps.AuthSteps;
import webshop.wspages.WsCartPage;
import webshop.wspages.WsDesktopProductPage;
import webshop.wspages.WsWelcomePage;

import static com.codeborne.selenide.Selenide.open;
import static java.util.Locale.US;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.Config.Config.WEBSHOP_URL;

public class CartTest {

    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach() {
        Configuration.timeout = 5000;
        authSteps.registerNewUser();
    }

    @Test
    void addItemToCartTest() {
        int processorIndex = 1;

        WsDesktopProductPage productPage = open(WEBSHOP_URL, WsWelcomePage.class)
                .hoverComputersMenu()
                .selectDesktops()
                .selectProduct(0);

        String itemName = productPage.getProductName();
        String itemPrice = productPage.getProductPrice();
        String itemQuantity = "5";

        WsCartPage cartPage = productPage
                .selectProcessor(processorIndex)
                .setQuantity(itemQuantity)
                .addToCart()
                .checkSuccessNotification()
                .checkQtyItemsInCart(itemQuantity)
                .goToCart();

        float processorPrice = getProcessorPrice(processorIndex);
        String expectedTotal = String.format(US, "%.2f",
                (Float.parseFloat(itemPrice) + processorPrice) * Float.parseFloat(itemQuantity));

        assertAll(
                () -> assertEquals(itemName, cartPage.getItemName()),
                () -> assertEquals(expectedTotal, cartPage.getSubtotal()),
                () -> assertEquals(itemQuantity, cartPage.getQuantity())
        );
    }

    private float getProcessorPrice(int processorIndex) {
        return switch (processorIndex) {
            case 0 -> 0f;
            case 1 -> 15f;
            case 2 -> 100f;
            default -> throw new IllegalArgumentException("Unknown processor index: " + processorIndex);
        };
    }
}
