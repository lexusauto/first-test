package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class YandexSearchResultsPage {

    private final SelenideElement firstWindow = $(".DistributionButtonClose_view_button");
    private final SelenideElement secondWindow =$(".DistributionSplashScreenModalCloseButtonBeside");
    private final SelenideElement thirdWindow =$("button.DistributionButtonClose_view_cross");

    public WelcomePage openLink(String webSiteName){
        $(byText(webSiteName)).click();
        switchTo().window(1);
        return new WelcomePage();
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
