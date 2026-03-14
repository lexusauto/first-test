package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LavaPage {

    private final SelenideElement educationPrice = $(".styles-module-scss-module__t92_WG__price");

    public LavaPage educationCostValidation(String cost){
        educationPrice.shouldHave(text(cost));
        return this;
    }

}
