package webshop.Config;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public static final String WEBSHOP_URL = "https://demowebshop.tricentis.com/";
    public static final String WEBSHOP_REGISTRATIONPAGE_URL=WEBSHOP_URL+"register";
    public static final String WEBSHOP_LOGIN_URL=WEBSHOP_URL+"login";

    private static final WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

    public static WebDriverConfig getWebDriverConfig() {
        return config;

    }

    public static ChromeOptions getSelenoidChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", config.browserVersion());

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("name", "mmmTest");            // имя сессии
        selenoidOptions.put("sessionTimeout", "15m");           // таймаут сессии
        selenoidOptions.put("env", List.of("TZ=UTC"));          // временная зона
        selenoidOptions.put("labels", Map.of("manual", "true")); // кнопка ручного удаления
        selenoidOptions.put("enableVideo", config.enableVideo());               // запись видео
        selenoidOptions.put("enableVNC", config.enableVNC());

        options.setCapability("selenoid:options", selenoidOptions);
        return options;
    }
}
