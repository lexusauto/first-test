package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class WsComputers_DesktopsPage {

    private final ElementsCollection productDesktop = $$("div.product-grid div");

    @Step("Выбрать продукт из представленного списка {product}")
    public WsDesktopProductPage selectProduct(int product) {
        productDesktop.get(product).click();
        return new WsDesktopProductPage();
    }
}
