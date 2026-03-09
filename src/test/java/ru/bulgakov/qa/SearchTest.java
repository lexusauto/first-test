package ru.bulgakov.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchTest {

@Test
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
    Configuration.holdBrowserOpen=true;
    open("https://ya.ru/");
    $("#text").setValue("bulgakov qa");
    $("[type=submit]").click();
    sleep(1000);
    if ($(".DistributionButtonClose_view_button").exists()){
    $(".DistributionButtonClose_view_button").click();
    }
    sleep(2000);
    if ($(".DistributionSplashScreenModalCloseButtonBeside").exists()){
        $(".DistributionSplashScreenModalCloseButtonBeside").click();
    }
    sleep(2000);
    if ($("button.DistributionButtonClose_view_cross").exists()){
        $("button.DistributionButtonClose_view_cross").click();
    }
    //$("button.DistributionButtonClose_view_cross").click();
    //$("button.DistributionButtonClose_view_button").click();
    $(byText("ivanbulgakovqa.ru")).click();
    switchTo().window(1);
    $$(".t-menu__list li").get(4).click();
    //$(byText("Хочу вкатиться в QA")).click();
    $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a").click();
    $(byText("Бегу оплачивать")).click();
    switchTo().window(2);
    $(".styles-module-scss-module__t92_WG__price").shouldHave(text("₽ 47 000.00"));
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
    $(".stores-item .stores-item__title").shouldHave(text("Калина Молл"));
    //$(".stores-item").$("[itemprop=streetAddress]").shouldHave(text("Калина Молл"));
    //$("[itemprop=name]").shouldHave(text("Калина Молл"), Duration.ofSeconds(3));

}
}