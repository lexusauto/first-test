package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class YandexSearchPage {
    // search, submit
private final SelenideElement searchInput=$("#text");
private final SelenideElement submitButton=$("[type=submit]");

    public YandexSearchPage search(String query){
        searchInput.setValue(query);//ввод названия сайта в поиск bulgakov qa
        return this;
    }

    public YandexSearchResultsPage knopka(){
        submitButton.click();//страница с поисковой выдачей
        return new YandexSearchResultsPage();
    }
}
