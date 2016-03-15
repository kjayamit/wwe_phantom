package testGoogle.framework;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Driver {

    protected static WebDriver driver;

    public static WebDriver getInstance() throws MalformedURLException {

//        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setBrowserName("Mozilla/5.0 (X11; Linux x86_64; rv:24.0) Gecko/20100101 Firefox/24.0");
//        capabilities.setVersion("24.0");
//        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/jaya/tools/phantomjs-2.1.1-macosx/bin/phantomjs");

        driver = new PhantomJSDriver(caps);
//        driver.setJavascriptEnabled(true);
        return driver;

    }

}
