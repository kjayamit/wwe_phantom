package testGoogle.test;

import org.testng.annotations.Test;
import testGoogle.framework.BaseTest;


public class HomePageTest extends BaseTest{

    @Test(groups = {"smoke"})
    public void doGoogleSearch() throws Exception {

        googleHomePage().verifyHomePageTitle();
        googleHomePage().enterSearchText("selenium");
        googleHomePage().clickSearchButton();
        googleHomePage().readDefinitionBox();

    }
}
