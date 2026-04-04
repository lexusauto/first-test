package webshop.wspages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class WsCartPage {

    private final SelenideElement productName = $(".product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement sumTotal = $("span.product-subtotal");

    public String getItemName() {
        return productName.getText();
    }

    public String getQuantity() {
        return quantityInput.getAttribute("value");
    }

    public String getSubtotal() {
        return sumTotal.getText();
    }
}
