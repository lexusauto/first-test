package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class YandexSearchResultsPage {

    private final SelenideElement firstWindow = $(".DistributionButtonClose_view_button");
    private final SelenideElement secondWindow =$(".DistributionSplashScreenModalCloseButtonBeside");
    private final SelenideElement thirdWindow =$("button.DistributionButtonClose_view_cross");

    public <T> T openLink(String webSiteName, Class<T> pageClass){
        $(byText(webSiteName)).click();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        }
        catch (Exception error) {
            throw new RuntimeException("Ошибка создания объекта", error);
        }
    }

    public <T> T switchToWindow(int index, Class<T> pageClass){
        switchTo().window(index);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException("Ошибка создания объекта", e);
        }
    }

    public YandexSearchResultsPage closeDefaultBrowserSelectWindows(){
        sleep(1000);
        if (firstWindow.exists()){
            firstWindow.click();
        }
        sleep(2000);
        if (secondWindow.exists()){
            secondWindow.click();
        }
        sleep(2000);
        if (thirdWindow.exists()){
            thirdWindow.click();
        }
        return this;
    }

}
