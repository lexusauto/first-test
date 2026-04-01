package webshop.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webshop.Steps.AuthSteps;
import webshop.wspages.WsCartPage;
import webshop.wspages.WsDesktopProductPage;
import webshop.wspages.WsWelcomePage;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static webshop.Config.Config.WEBSHOP_URL;

public class CartTest {

private final AuthSteps authSteps = new AuthSteps();

@BeforeEach
    void beforeEach() {
    authSteps.registerNewUser();
    }


@Test
void addItemToCartTest() {

Configuration.timeout=5000;

WsDesktopProductPage productPage = open(WEBSHOP_URL, WsWelcomePage.class)
    .clickOnComputers_Desktops()
    .clickOnProductDesktop(0)//Выбираем продукт из списка по его индексу на странице
    .selectProcessor(2)
    .setQuantity("5")
    .addToCart()
    .addToCartValidation()
    .shopCartQuantityValidation();

String itemName = productPage.getSavedItemName();
String itemPrice = productPage.getSavedPriceName();
String itemQuantity = productPage.getSavedQuantity();

WsCartPage cartPage = productPage.goToCartPage()
     .validationProductName(itemName);

String itemQuantityInCart = cartPage.getQuantityValue();
assertEquals(itemQuantity, itemQuantityInCart);

float expectedSumTotal = Float.parseFloat(itemPrice)*Float.parseFloat(itemQuantity);
float actualSumTotal = Float.parseFloat(cartPage.getSumTotalValue());
assertEquals(expectedSumTotal, actualSumTotal);

}
}
