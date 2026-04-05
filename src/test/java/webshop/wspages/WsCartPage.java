package webshop.wspages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WsCartPage {

    private final SelenideElement productName = $(".product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement sumTotal = $("span.product-subtotal");

    public String getItemName() {
        return productName.getText();
    }

    public String getQuantityValue() {
        return quantityInput.getAttribute("value");
    }

    public String getSumTotalValue() {
        return sumTotal.getText();
    }
}
