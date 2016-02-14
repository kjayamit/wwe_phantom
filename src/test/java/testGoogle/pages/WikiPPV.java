package testGoogle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testGoogle.framework.WebUi;

import java.util.List;

public class WikiPPV extends WebUi{

    public void getAllPPV(){
       List<WebElement> PPVs = driver.findElements(By.className("wikitable")) ;

        for (WebElement PPV:PPVs) {
            List<WebElement> rows = PPV.findElements(By.xpath("tbody/tr"));
            System.out.println("1");
            for (WebElement row:rows) {
                System.out.println("2");
                List<WebElement> columns = row.findElements(By.xpath("td"));
                for (WebElement column:columns) {
                    System.out.println("3");
                    writer.writeToFile(column.getText());
                    writer.writeToFile("\t");
//                    writer.writeToFile(column.findElement(By.xpath("//tr[2]")).getText());
//                    writer.writeToFile("\t");
//                    writer.writeToFile(column.findElement(By.xpath("//tr[3]")).getText());
//                    writer.writeToFile("\t");
//                    writer.writeToFile(column.findElement(By.xpath("//tr[4]")).getText());
//                    writer.writeToFile("\t");
//                    writer.writeToFile(column.findElement(By.xpath("//tr[5]")).getText());

                }
                writer.writeToFile("\n");
            }
        }
    }


}
