package MyTestPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WelcomePageShop {

    private final SelenideElement acceptCookieButton = $(".cookie-notification__control--accept");
    private final SelenideElement citySelectDropdownWindow = $(".city-select__dropdown");
    private final ElementsCollection cityElement = $$(".city-select__dropdown-item");
    private final SelenideElement acceptCityButton = $(".city-select__dropdown-select");
    private final SelenideElement citySelectIcon = $(".header__buttons-block").$(".city-select__icon");
    private final SelenideElement menuButton = $(".menu-trigger");
    private final ElementsCollection shops = $$(".menu-popup__list-dividered li");
    private final SelenideElement cityForOrder = $(".city-select__dropdown-city");
    private final SelenideElement cityValueField = $("#search-stores");
    //private final ElementsCollection stores = $$("p.stores-item__title");

    public WelcomePageShop closeCookieWindow() {
        if (acceptCookieButton.shouldBe(visible, Duration.ofSeconds(15)).exists()) {
            acceptCookieButton.click();
        }
        return this;
    }

    public WelcomePageShop choiseCityForOrderWindow(String city){
            if (citySelectDropdownWindow.exists()) {
                cityForOrder.click();
                cityElement.findBy(text(city)).click();
                sleep(200);
                acceptCityButton.click();
            } else {
                citySelectIcon.click();
                cityForOrder.click();
                cityElement.findBy(text(city)).click();
                sleep(200);
                acceptCityButton.click();
            }
            return this;
        }

        public WelcomePageShop openOrCloseMenu(){
            menuButton.click();
            return this;
        }

        public WelcomePageShop openShopLocations(){
            shops.findBy(text("Магазины")).click();
            sleep(200);
            return this;
        }

        public WelcomePageShop setValueCityLocation(String city){
            cityValueField.setValue(city);
            return this;
        }

        public WelcomePageShop validateDisplayShop(String shopName){
            //sleep(1000);
            ElementsCollection stores = $$("p.stores-item__title").filter(text(shopName));
            assertEquals(1, stores.size());
            return this;
        }

}
