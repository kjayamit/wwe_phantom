package testGoogle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.SystemClock;
import testGoogle.framework.WebUi;

import java.sql.*;
import java.util.List;

public class WikiPPV extends WebUi{

    public void getAllPPV() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        int n = 0;
        PreparedStatement addRow = conn.prepareStatement
                ("INSERT INTO PPV(id, date, name, venue, main_event) values(?,?,?,?,?)");

        List<WebElement> PPVs = driver.findElements(By.className("wikitable")) ;

        for (WebElement PPV:PPVs) {
            List<WebElement> rows = PPV.findElements(By.xpath("tbody/tr"));
            System.out.println("1");
            for (WebElement row:rows) {
                System.out.println("2");

                if (row.findElements(By.xpath("td")).size()>4) {
                    addRow.setInt(1,++n);
                    addRow.setString(2,row.findElement(By.xpath("td")).getText());
                    addRow.setString(3,row.findElement(By.xpath("td[2]")).getText());
                    addRow.setString(4,row.findElement(By.xpath("td[3]")).getText());
                    addRow.setString(5,row.findElement(By.xpath("td[4]")).getText());
                    addRow.executeUpdate();
                    System.out.println(addRow);
                }

                List<WebElement> columns = row.findElements(By.xpath("td"));

                writer.writeToFile(String.valueOf(n));
                writer.writeToFile("\t");
                for (WebElement column:columns) {
                    System.out.println("3");
                    writer.writeToFile(column.getText());
                    writer.writeToFile("\t");

                }
                writer.writeToFile("\n");
            }
        }
        conn.close();
    }


}
