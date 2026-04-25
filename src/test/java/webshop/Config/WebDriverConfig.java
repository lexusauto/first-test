package webshop.Config;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/${run}.properties"
})
public interface WebDriverConfig extends Config {

    @DefaultValue("local")
    String run();

    @DefaultValue("chrome")
    String browser();

    String browserVersion();

    @DefaultValue("1920x1080")
    String browserSize();

    String selenoidUrl();

    String selenoidUser();

    String selenoidPassword();

    boolean enableVideo();

    boolean enableVNC();
}
