package testGoogle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.SystemClock;
import testGoogle.framework.WebUi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class WikiPPV extends WebUi{

    public void getAllPPV() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
//        DriverManager.registerDriver(new org.postgresql.Driver());
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        String statement = new String();
        int n = 0;

        List<WebElement> PPVs = driver.findElements(By.className("wikitable")) ;

        for (WebElement PPV:PPVs) {
            List<WebElement> rows = PPV.findElements(By.xpath("tbody/tr"));
            System.out.println("1");
            for (WebElement row:rows) {
                System.out.println("2");

                if (row.findElements(By.xpath("td")).size()>4) {
                    statement = "INSERT INTO PPV(id, date, name, venue, main_event) " +
                            "VALUES (" + ++n + ", '" + row.findElement(By.xpath("td")).getText()
                            + "', ' " + row.findElement(By.xpath("td[2]")).getText()
                            + "', '" + row.findElement(By.xpath("td[3]")).getText()
                            + "', '" + row.findElement(By.xpath("td[5]")).getText()
                            + "')";
                    System.out.println(statement);
                    st.executeUpdate(statement);
                }

                List<WebElement> columns = row.findElements(By.xpath("td"));

                writer.writeToFile(String.valueOf(n));
                writer.writeToFile("\t");
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
        conn.close();
    }


}
