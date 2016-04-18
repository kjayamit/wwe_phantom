package testGoogle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.support.ui.SystemClock;
import testGoogle.framework.WebUi;

import java.io.IOException;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    public void splitPlayers() throws IOException,ClassNotFoundException, SQLException   {
        driver.get("https://en.wikipedia.org/wiki/WrestleMania_32");

//        driver.get("https://en.wikipedia.org/wiki/List_of_WWE_pay-per-view_events");

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select name from superstar");

        ArrayList<String> values = new ArrayList<String>();
        while (rs.next()) {
            values.add(rs.getString("name"));
        }

        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='wikitable']/tbody/tr"));
        String s = new String();
        String all = new String();
        int noOfPlayers;
        for( WebElement row:rows) {
            if(row.findElements(By.xpath("td")).size() > 0) {
                s = row.findElement(By.xpath("td")).getText();
                for (int i =0; i< values.size(); i++){
                    if (s.contains(values.get(i).trim()))
                        s = s.replace(values.get(i).trim(),String.valueOf(i));
                }
                System.out.println( "Match " + s);
                String[] groups = s.split("defeated|vs\\.");
                for (String s2 : groups) {
                    System.out.println( "Group/Player " + s2);

                    String[] group2 = s2.split("\\(|\\)");

//                    for (String s3 : group2) {
//                        System.out.println("some2 " + s3);
//                    }

                    for (int i=0; i<group2.length; i++){
//                        System.out.println("some2 " + group2[i]);
                        if(group2[i].contains("and") && !group2[i].contains("with")) {
//                            all = "Superstar : " + group2[i - 1];
                            System.out.println("Superstar : ");
                            split(group2[i]);
                        }
                        else if (group2[i].contains("with")) {
//                            all = "Superstar : " + group2[i - 1];
                            System.out.println("Accompanied with : ");
                            split(group2[i]);
                        }
                        else {
//                            all = "Superstar : " + group2[i - 1];
                            System.out.println("Superstar : ");
                            split(group2[i]);
                        }
                    }
                }
            }
        }

        conn.close();
    }

    public String[] split(String s) {
        String[] players = s.split("and|,");
        for( String player: players){
            System.out.println("player : " + player);
        }
        return players;
    }


}
