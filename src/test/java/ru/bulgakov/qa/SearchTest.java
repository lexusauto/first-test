package ru.bulgakov.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.WelcomePage;
import pages.YandexSearchPage;
import pages.YandexSearchResultsPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTest {

@Test
@DisplayName("Валдиация стоимости обучения. 47000 руб.")
@Tag("Positive")
  void check47kTest(){
    /*
    * ТК - проверить, что предоплата по обучению 47к
    * 1. Открыть поисковик
    * 2. Ввести данные сайта
    * 3. Нажать кнопку "Поиск"
    * 4. В поисковой выдаче найти нужный сайт и кликнуть на него
    * 5. Нажать на кнопку "Стоимость"
    * 6. Нажать на кнопку "Хочу вкатиться в qa"
    * 7. Нажать на кнопку "Бегу оплачивать"
    * 8. Проверка, что к оплате 47к руб.
     */
    Configuration.pageLoadTimeout=10000;//таймаут для прогрузки страницы
    Configuration.timeout=10000;//таймаут для прогрузки элемента
    //Configuration.holdBrowserOpen=true;

    open("https://ya.ru/", YandexSearchPage.class)//строка поиска
    .search("bulgakov qa")
    .knopka()
    .closeDefaultBrowserSelectWindows()
    .openLink("ivanbulgakovqa.ru")
    .clickOnCost()
    .clickOnWantToRollIn()
    .clickOnRunToPay()
    .educationCostValidation("₽ 47 000.00");
}

@Test
    void ShopExistVladivostok(){
    /*
     * ТК - проверить, что в maag-fashion.com/stores/ отображается магазин "Калина Молл" после введения значения "Владивосток" в поле
     * 1. Открыть сайт
     * 2. Принять куки, если окно отобразилось
     * 3. Выбрать регион для заказа
     * 4. Открыть каталог
     * 5. Открыть раздел "Магазины" из каталога
     * 6. Ввести название города в поиск
     * 7. Провалидировать отображение магазина "Калина Молл"
     */
    Configuration.pageLoadTimeout=10000;
    Configuration.timeout=10000;
    Configuration.holdBrowserOpen=true;
    open("https://maag-fashion.com/");
    sleep(1000);
    if ($(".cookie-notification__control--accept").exists()){
        $(".cookie-notification__control--accept").click();
    }
    if ($(".city-select__dropdown").exists()){
        $(".city-select__dropdown-city").click();
        $$(".city-select__dropdown-item").findBy(text("Владивосток")).click();
        sleep(200);
        $(".city-select__dropdown-select").click();
    } else {
        $(".header__buttons-block").$(".city-select__icon").click();
        $(".city-select__dropdown-city").click();
        $$(".city-select__dropdown-item").findBy(text("Владивосток")).click();
        sleep(200);
        $(".city-select__dropdown-select").click();
    }
    $(".menu-trigger").click();
    $$(".menu-popup__list-dividered li").findBy(text("Магазины")).click();
    sleep(200);
    $("#search-stores").setValue("Владивосток");
    ElementsCollection stores= $$("p.stores-item__title").filter(text("Калина Молл"));
    assertEquals(1, stores.size());

}
}