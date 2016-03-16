package testGoogle.framework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import testGoogle.Helper.MrWriter;


import java.net.MalformedURLException;


public class BaseTest extends PageFactory {

    WebDriver driver = null;
    private String AppUrl = "https://en.wikipedia.org/wiki/List_of_WWE_pay-per-view_events"; //TODO: Move it outside the Base test

    MrWriter mrWriter= new MrWriter();


    @BeforeSuite(alwaysRun = true)
    public void LaunchApp() throws MalformedURLException {
        initialize();
        clearCookies();
        goToHomePage();
    }


    public void initialize() throws MalformedURLException {
        driver = Driver.getInstance();
        initializePageObjects();
        mrWriter.initialize();
    }



    @AfterTest
    public void something(){
        System.out.println("This is something ! " + driver.getCurrentUrl() )  ;
    }

    @AfterSuite(alwaysRun = true)
    public void close() {
        driver.close();
        mrWriter.close();
    }


    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public void goToHomePage() {
        driver.get(AppUrl);
    }


}
