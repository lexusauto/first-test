package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class WsDesktopsPage {

    private final ElementsCollection productList = $$("div.product-grid div");

    public WsDesktopProductPage selectProduct(int index) {
        productList.get(index).click();
        return new WsDesktopProductPage();
    }
}
