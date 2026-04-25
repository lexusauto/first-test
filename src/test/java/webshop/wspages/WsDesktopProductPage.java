package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Указать количество продуктов для покупки {quantity}")
    public WsDesktopProductPage setQuantity(String quantity) {
        quantityInput.setValue(quantity);
        return this;
    }

    @Step("Нажать на кнопку 'Добавить в корзину'")
    public WsDesktopProductPage addToCart() {
        addToCartButton.click();
        return this;
    }

    @Step("Валидация успешного добавление продукта в корзину")
    public WsDesktopProductPage addToCartValidation() {
        sucessNotification.shouldHave(text("The product has been added to your "));
        return this;
    }

    @Step("Валидация количества продуктов корзины {expectedQuantity}")
    public WsDesktopProductPage shopCartQuantityValidation(String expectedQuantity) {
        shoppingCartQuantity.shouldHave(text("(" + expectedQuantity + ")"));
        return this;
    }

    @Step("Выбрать процессор для desktop продукта")
    public WsDesktopProductPage selectProcessor(int index) {
        attributesForDesktopComputer.get(index).click();
        return this;
    }

    @Step("Перейти на страницу корзины")
    public WsCartPage goToCart() {
        cartButton.click();
        return new WsCartPage();
    }

    @Step("Получить название продукта")
    public String getItemName() {
        return itemName.getText();
    }

    @Step("Получить цену продукта")
    public float getItemPrice() {
        return Float.parseFloat(itemPrice.getText());
    }

}
