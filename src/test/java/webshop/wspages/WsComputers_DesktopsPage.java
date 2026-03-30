package webshop.wspages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class WsComputers_DesktopsPage {

private final ElementsCollection productDesktop = $$("div.product-grid div");

    public WsDesktopProductPage clickOnProductDesktop(int product) {
        productDesktop.get(product).click();
        return new WsDesktopProductPage();
    }
}
