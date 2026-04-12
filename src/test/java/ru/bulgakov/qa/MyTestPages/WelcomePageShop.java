package ru.bulgakov.qa.MyTestPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

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
    private final ElementsCollection stores = $$("p.stores-item__title");

    @Step("Закрыть окно с куками")
    public WelcomePageShop closeCookieWindow() {
        if (acceptCookieButton.exists()) {
            acceptCookieButton.click();
        }
        return this;
    }

    @Step("Выбрать город для заказа")
    public WelcomePageShop chooseCityForOrderWindow(String city) {
        if (!citySelectDropdownWindow.exists()) {
            citySelectIcon.click();
        }
        cityForOrder.click();
        cityElement.findBy(text(city)).click();
        acceptCityButton.click();
        return this;
    }

    @Step("Открыть/Закрыть меню сайта")
    public WelcomePageShop openOrCloseMenu() {
        menuButton.click();
        return this;
    }

    @Step("Открыть страницу с локациями магазинов")
    public WelcomePageShop openShopLocations() {
        shops.findBy(text("Магазины")).click();
        return this;
    }

    @Step("Указать город на странице с локациями магазинов")
    public WelcomePageShop setValueCityLocation(String city) {
        cityValueField.setValue(city);
        return this;
    }

    @Step("Указать название магазина на странице с локациями магазинов")
    public ElementsCollection getStoresByName(String shopName) {
        return stores.filter(text(shopName));
    }

}
