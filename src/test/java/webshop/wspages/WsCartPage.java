package webshop.wspages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class WsCartPage {

    private final SelenideElement productName = $(".product-name");
    private final SelenideElement quantityInput = $("input.qty-input");
    private final SelenideElement sumTotal = $("span.product-subtotal");

    @Step("Получить имя продукта")
    public String getItemName() {
        return productName.getText();
    }

    @Step("Получить количество добавленных продуктов для покупки на странице товара")
    public String getQuantity() {
        return quantityInput.getAttribute("value");
    }

    @Step("Получить общую сумму продуктов корзины")
    public String getTotalSum() {
        return sumTotal.getText();
    }
}
