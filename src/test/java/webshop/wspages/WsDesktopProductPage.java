package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WsDesktopProductPage {

    private final SelenideElement itemName = $("[itemprop=name]");
    private final SelenideElement itemPrice = $("[itemprop=price]");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement addToCartButton = $("#add-to-cart-button-72");
    private final SelenideElement sucessNotification = $("#bar-notification p.content");
    private final SelenideElement shoppingCartQuantity = $("span.cart-qty");
    private final SelenideElement cartButton = $(".ico-cart");
    private final ElementsCollection attributesForDesktopComputer = $$("dl dd ul li input");

    public WsDesktopProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;
    }

    public WsDesktopProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    public WsDesktopProductPage addToCartValidation() {
        sucessNotification.shouldHave(text("The product has been added to your "));
        return this;
    }

    public WsDesktopProductPage shopCartQuantityValidation(String expectedQuantity) {
        shoppingCartQuantity.shouldHave(text("(" + expectedQuantity + ")"));
        return this;
    }

    public WsDesktopProductPage selectProcessor(int index) {
        attributesForDesktopComputer.get(index).click();
        return this;
    }

    public WsCartPage goToCart() {
        cartButton.click();
        return new WsCartPage();
    }

    public String getItemName() {
        return itemName.getText();
    }

    public float getItemPrice() {
        return Float.parseFloat(itemPrice.getText());
    }

}
