package testGoogle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.support.ui.SystemClock;
import testGoogle.framework.WebUi;

import java.sql.*;
import java.sql.ResultSet;
import java.util.List;

public class WikiPPV extends WebUi{

    public void getAllPPV() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        int n = 0;
        int o = 0;
        PreparedStatement addPPV = conn.prepareStatement
                ("INSERT INTO PPV(id, date, name, venue, main_event) values(?,?,?,?,?)");

        PreparedStatement addVenue = conn.prepareStatement
                ("INSERT INTO Venue(id, location, latlong, url) values(?,?,?,?)");

        PreparedStatement getVenue = conn.prepareStatement("select id from venue where url=?");

        List<WebElement> PPVs = driver.findElements(By.className("wikitable")) ;

        for (WebElement PPV:PPVs) {
            List<WebElement> rows = PPV.findElements(By.xpath("tbody/tr"));
            System.out.println("1");
            for (WebElement row:rows) {
                System.out.println("2");

                if (row.findElements(By.xpath("td")).size()>4) {
                    getVenue.setString(1,row.findElement(By.xpath("td[3]/a")).getAttribute("href"));
                    System.out.println(getVenue);
                    ResultSet rs = getVenue.executeQuery();


                    if(!rs.next()){
                        addVenue.setInt(1,++o);
                        addVenue.setString(2,row.findElement(By.xpath("td[4]")).getText());
                        addVenue.setString(3,"summa");
                        addVenue.setString(4,row.findElement(By.xpath("td[3]/a")).getAttribute("href"));
                        addVenue.executeUpdate();
                        System.out.println(addVenue);
                        addPPV.setString(4, String.valueOf(o));
                    }
                    else {
                        int i = rs.getInt(1);
                        addPPV.setString(4, String.valueOf(i));
                    }

                    addPPV.setInt(1,++n);
                    addPPV.setString(2,row.findElement(By.xpath("td")).getText());
                    addPPV.setString(3,row.findElement(By.xpath("td[2]")).getText());

                    addPPV.setString(5,row.findElement(By.xpath("td[5]")).getText());
                    addPPV.executeUpdate();
                    System.out.println(addPPV);
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

    public void splitPlayers() {
        driver.get("https://en.wikipedia.org/wiki/No_Mercy_(2000)");

        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='wikitable']/tbody/tr"));
        String s = new String();
        for( WebElement row:rows) {
            if(row.findElements(By.xpath("td")).size() > 0) {
                s = row.findElement(By.xpath("td")).getText();
                String[] groups = s.split("defeated|vs\\.");
                for (String s2 : groups) {
                    System.out.println( "some" + s2);
                }
            }
        }
    }


}
