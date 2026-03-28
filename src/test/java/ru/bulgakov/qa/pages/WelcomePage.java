package ru.bulgakov.qa.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WelcomePage {

    private final ElementsCollection stoimost = $$(".t-menu__list li");
    private final SelenideElement wantToRoll = $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a");
    private final SelenideElement runToPay = $(byText("Бегу оплачивать"));

    public WelcomePage clickOnCost(){
        stoimost.get(4).click();
        return this;
    }

    public WelcomePage clickOnWantToRollIn(){
        wantToRoll.click();
        return this;
    }

    public LavaPage clickOnRunToPay(){
        runToPay.click();
        switchTo().window(2);
        return new LavaPage();
    }
}
