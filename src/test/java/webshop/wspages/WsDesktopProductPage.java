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
    private final SelenideElement successNotification = $("#bar-notification p.content");
    private final SelenideElement shoppingCartQuantity = $("span.cart-qty");
    private final SelenideElement cartButton = $(".ico-cart");
    private final ElementsCollection processorOptions = $$("dl dd ul li input");

    public String getProductName() {
        return itemName.getText();
    }

    public String getProductPrice() {
        return itemPrice.getText();
    }

    public WsDesktopProductPage selectProcessor(int index) {
        processorOptions.get(index).click();
        return this;
    }

    public WsDesktopProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;
    }

    public WsDesktopProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    public WsDesktopProductPage checkSuccessNotification() {
        successNotification.shouldHave(text("The product has been added to your "));
        return this;
    }

    public WsDesktopProductPage checkQtyItemsInCart(String quantity) {
        shoppingCartQuantity.shouldHave(text("(" + quantity + ")"));
        return this;
    }

    public WsCartPage goToCart() {
        cartButton.click();
        return new WsCartPage();
    }
}
