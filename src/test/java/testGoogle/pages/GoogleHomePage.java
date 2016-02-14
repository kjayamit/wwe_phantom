package testGoogle.pages;


import org.openqa.selenium.By;
import testGoogle.Helper.MrWriter;
import testGoogle.framework.WebUi;

public class GoogleHomePage extends WebUi {

    private static By searchBox = By.name("q");
    private static By searchButton = By.name("btnG");
//    private static By definitionBox = By.className("_Tgc");
    private static By definitionBox = By.xpath("//span[@class='_Tgc']");

    public void openGoogleHomePage(String URL) throws Exception {
        open(URL);
    }

    public void verifyHomePageTitle() throws Exception {
        verifyPageTitle("Google");
        writer.writeToFile("verifyHome");
    }

    public void enterSearchText(String searchText) throws Exception {
        getElement(searchBox).sendKeys(searchText);
        writer.writeToFile("searchText")  ;
    }

    public void clickSearchButton() throws Exception {
        click(searchButton);

        writer.writeToFile("clickSearch")  ;
    }

    public void readDefinitionBox() throws Exception {
       System.out.println("Definition : " + getElement(definitionBox).getText());
    }
}
