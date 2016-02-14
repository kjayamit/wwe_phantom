package testGoogle.framework;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testGoogle.Helper.MrWriter;

public class WebUi extends Driver{

    private int DEFAULT_WAIT = 10;

    protected MrWriter writer = new MrWriter();

    protected WebElement getElement(By locator) {
        return findElementWithWait(locator, DEFAULT_WAIT);

    }

    private static WebElement findElementWithWait(By by, int defaultWait) {
        WebDriverWait wait = new WebDriverWait(driver, defaultWait);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);

    }

    protected void open(String url) throws Exception {
        try {
            driver.get(url);

        } catch (Exception e) {
            Logger.error("Exception in open()");
            Logger.error(e.toString());
            throw new Exception(e);
        }
    }


    protected void verifyPageTitle(String pageTitle) throws Exception {
        try {
            Assert.assertEquals(pageTitle, driver.getTitle());
        } catch (Exception e) {
            Logger.error("Exception in verifyPageTitle()");
            Logger.error(e.toString());
            throw new Exception(e);

        }

    }


    protected void type(By by, String testdata) throws Exception {
        try {

            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(testdata);
        } catch (Exception e) {
            Logger.error("Exception in type()");
            Logger.error(e.toString());
            throw new Exception(e);

        }

    }

    protected void click(By locator) throws Exception {
        try {
            driver.findElement(locator).click();
        } catch (Exception e) {
            Logger.error("Exception in click()");
            Logger.error(e.toString());
            throw new Exception(e);

        }
    }


    protected boolean isElementDisplayed(By locator) {
        if (driver.findElements(locator).size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
