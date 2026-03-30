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
    private final SelenideElement barNotificationSuccesAdded=$("#bar-notification p.content");
    private final SelenideElement shoppingCartQuantity = $("span.cart-qty");
    private final SelenideElement cartButton=$(".ico-cart");
    private final ElementsCollection attributesForDesktopComputer = $$("dl dd ul li input");


    private String savedItemName;
    private String savedPriceName;
    private String savedQuantity;

    public WsDesktopProductPage setQuantity(String quantity) {
        this.savedQuantity=quantity;
        quantityInput.setValue(quantity);
        return this;
    }

    public WsDesktopProductPage addToCart() {
        savedItemName=itemName.getText();
        savedPriceName=itemPrice.getText();
        addToCartButton.click();
        return this;
    }

    public WsDesktopProductPage addToCartValidation() {
        barNotificationSuccesAdded.shouldHave(text("The product has been added to your "));
        return this;
    }

    public WsDesktopProductPage shopCartQuantityValidation() {
        shoppingCartQuantity.shouldHave(text("("+savedQuantity+")"));
        return this;
    }

    public WsDesktopProductPage selectProcessor(int index) {
        attributesForDesktopComputer.get(index).click();
        return this;
    }

    public WsCartPage goToCartPage() {
        cartButton.click();
        return new WsCartPage();
    }

    public String getSavedQuantity() {
        return savedQuantity;
    }

    public String getSavedItemName() {
        return savedItemName;
    }

    public String getSavedPriceName() {
        return savedPriceName;
    }

}
