package webshop.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webshop.Steps.AuthSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
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
Configuration.holdBrowserOpen=true;

open(WEBSHOP_URL);
$$("ul.top-menu li a").get(1).hover();
$(byText("Desktops")).click();
$$("div.product-grid div").get(0).click();
String itemName = $("[itemprop=name]").getText();
String itemPrice = $("[itemprop=price]").getText();
String itemQuantity = "2";
$$("dl dd ul li input").get(0).click();
$("input.qty-input").setValue("2");
$("#add-to-cart-button-72").click();
$("#bar-notification p.content").shouldHave(text("The product has been added to your "));
$("span.cart-qty").shouldHave(text("("+itemQuantity+")"));
$(".ico-cart").click();

$(".product-name").shouldHave(text(itemName));
String itemQuantityInCart = $("input.qty-input").getAttribute("value");
assertEquals(itemQuantity, itemQuantityInCart);
$("span.product-subtotal").shouldHave(text(String.valueOf(
        Float.parseFloat(itemPrice)*Float.parseFloat(itemQuantity))));

}
}
