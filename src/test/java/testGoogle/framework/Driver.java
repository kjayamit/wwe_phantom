package testGoogle.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import java.net.MalformedURLException;

public class Driver {

    protected static WebDriver driver;

    public static WebDriver getInstance() throws MalformedURLException {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/jaya/tools/phantomjs-2.1.1-macosx/bin/phantomjs");
        driver = new PhantomJSDriver(caps);
        return driver;

    }

}
