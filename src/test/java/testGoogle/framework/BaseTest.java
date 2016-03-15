package testGoogle.framework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import testGoogle.Helper.MrWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;


public class BaseTest extends PageFactory {

//    File file = new File("test1.txt");
    WebDriver driver = null;
//    private String AppUrl = "https://www.google.com"; //TODO: Move it outside the Base test
    private String AppUrl = "https://en.wikipedia.org/wiki/List_of_WWE_pay-per-view_events"; //TODO: Move it outside the Base test

    //    FileWriter fileWriter;
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
//        try {
//            fileWriter = new FileWriter(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mrWriter.initialize();
    }



    @AfterTest
    public void something(){

        System.out.println("This is something ! " + driver.getCurrentUrl() )  ;


//        System.out.println("This is fucking something bitch ! " + driver.getCurrentUrl() + " " + file.getAbsolutePath())  ;
    }

    @AfterSuite(alwaysRun = true)
    public void close() {
//        try {
//        fileWriter.flush();
//        fileWriter.close();} catch (IOException e) {
//            e.printStackTrace();
//        }
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
