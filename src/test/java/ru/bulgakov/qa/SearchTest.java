package ru.bulgakov.qa;

import ru.bulgakov.qa.MyTestPages.WelcomePageShop;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.bulgakov.qa.pages.WelcomePage;
import ru.bulgakov.qa.pages.YandexSearchPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchTest {

private static final String YANDEX_URL = "https://ya.ru/";
private static final String MAAG_FASHION_URL = "https://maag-fashion.com/";

@Test
@DisplayName("Валидация стоимости обучения. 47000 руб.")
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

    open(YANDEX_URL, YandexSearchPage.class)//строка поиска
    .search("bulgakov qa")
    .knopka()
    .closeDefaultBrowserSelectWindows()
    .openLink("ivanbulgakovqa.ru")
    .switchToWindow(1, WelcomePage.class)

    .clickOnCost()
    .clickOnWantToRollIn()
    .clickOnRunToPay()
    .educationCostValidation("₽ 47 000.00");
}

@Test
    void shopExistVladivostok(){
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

    ElementsCollection foundStores = open(MAAG_FASHION_URL, WelcomePageShop.class)
        .closeCookieWindow()
        .chooseCityForOrderWindow("Владивосток")
        .openOrCloseMenu()
        .openShopLocations()
        .setValueCityLocation("Владивосток")
        .getStoresByName("Калина Молл");

    assertEquals(1, foundStores.size());
}
}
