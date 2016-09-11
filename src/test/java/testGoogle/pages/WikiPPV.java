package testGoogle.pages;

import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.support.ui.SystemClock;
import testGoogle.framework.WebUi;

import java.io.IOException;
import java.sql.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.given;

public class WikiPPV extends WebUi{

    public void getAllPPV() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();
        int n = 0;
        int o = 0;
        PreparedStatement addPPV = conn.prepareStatement
                ("INSERT INTO PPV(id, date, name, venue, main_event, url) values(?,?,?,?,?,?)");

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
                    addPPV.setString(6,row.findElement(By.xpath("td[2]/a")).getAttribute("href"));
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
        driver.get("https://en.wikipedia.org/wiki/TLC:_Tables,_Ladders,_and_Chairs_(2015)");

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

                    for (int i=0; i<group2.length; i++){
                        if(group2[i].contains("and") && !group2[i].contains("with")) {
                            System.out.println("Superstar : ");
                            split(group2[i]);
                        }
                        else if (group2[i].contains("with")) {
                            System.out.println("Accompanied with : ");
                            split(group2[i]);
                        }
                        else {
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

    public void wwePlayers() throws IOException,ClassNotFoundException, SQLException{

        String name;
        String link;
        int i = 0;

        driver.get("https://en.wikipedia.org/wiki/List_of_WWE_personnel");




            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/wwe?user=jaya";
            Connection conn = DriverManager.getConnection(url);
            Statement st = conn.createStatement();

            PreparedStatement addSuperstar = conn.prepareStatement
                    ("INSERT INTO superstar(id, name, link, alumni) values(?,?,?, False)");

            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='wikitable sortable jquery-tablesorter']/tbody/tr"));

           System.out.println("count is : " + rows.size());

            for (WebElement row : rows){
                System.out.println("Star : " + row.findElement(By.xpath("td[1]")).getText());

                if(row.findElements(By.xpath("td[1]/span[@class='vcard']")).size() > 0) {
                    name = row.findElement(By.xpath("td[1]/span[@class='vcard']")).getText();
                    link = row.findElement(By.xpath("td[1]/span[@class='vcard']/span/a")).getAttribute("href");
                    System.out.println("Name : " + name  + " link : " + link );
                    addSuperstar.setInt(1,++i);
                    addSuperstar.setString(2,name);
                    addSuperstar.setString(3,link);
                    addSuperstar.executeUpdate();
                    System.out.println(addSuperstar);
                }
            }

            conn.close();
    }



    public void wweAlum() throws IOException,ClassNotFoundException, SQLException{

        String name;
        String link;
        int i = 0;

        List<String> alumins = Arrays.asList("https://en.wikipedia.org/wiki/List_of_WWE_alumni_(A%E2%80%93C)",
                "https://en.wikipedia.org/wiki/List_of_WWE_alumni_(D%E2%80%93H)",
                "https://en.wikipedia.org/wiki/List_of_WWE_alumni_(I%E2%80%93M)",
                "https://en.wikipedia.org/wiki/List_of_WWE_alumni_(N%E2%80%93R)",
                "https://en.wikipedia.org/wiki/List_of_WWE_alumni_(S%E2%80%93Z)");

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        Statement st = conn.createStatement();

        ResultSet rs=st.executeQuery("select max(id) from superstar");
        while(rs.next()){
            i = rs.getInt("max");
            System.out.println("Max id : " + i);
        }

        PreparedStatement addSuperstar = conn.prepareStatement
                ("INSERT INTO superstar(id, name, link, alumni) values(?,?,?, True)");

        for (String alumn : alumins) {

            driver.get(alumn);
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='sortable wikitable jquery-tablesorter']/tbody/tr"));
            System.out.println("For link : --" + alumn + "-- count is : " + rows.size());

            for (WebElement row : rows) {
                System.out.println("Star : " + row.findElement(By.xpath("td[1]")).getText());

                if (row.findElements(By.xpath("td[1]/span[@class='sorttext']/a")).size() > 0) {
                    name = row.findElement(By.xpath("td[1]/span[@class='sorttext']/a")).getText();
                    link = row.findElement(By.xpath("td[1]/span[@class='sorttext']/a")).getAttribute("href");
                    System.out.println("Name : " + name + " link : " + link );
                    addSuperstar.setInt(1,++i);
                    addSuperstar.setString(2,name);
                    addSuperstar.setString(3,link);
                    addSuperstar.executeUpdate();
                    System.out.println(addSuperstar);
                }
            }
        }

        conn.close();
    }

    public void playerName() throws IOException,ClassNotFoundException, SQLException {


        int i = 0;
        String something = new String();

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from superstar");

        PreparedStatement addRingName = conn.prepareStatement
                ("INSERT INTO ringname(id, name, superstar_id) values(?,?,?)");

        while (rs.next()) {

            driver.get(rs.getString("link"));
            if(driver.findElements(By.xpath("//table[contains(@class,'infobox')]//a[@title='Ring name']")).size() > 0){
                something = driver.findElement(By.xpath("//a[@title='Ring name']/parent::span/parent::th/parent::tr/td")).getText();
                System.out.println("Ring names : " + something);

                addRingName.setInt(3, rs.getInt("id"));
                String[] names = something.split("\n");

                for(String name: names){
                    name = name.contains("[")? name.substring(0, name.indexOf('[')) : name;
                    System.out.println("names " + name);
                    addRingName.setInt(1, ++i);
                    addRingName.setString(2,name);
                    addRingName.executeUpdate();
                    System.out.println(addRingName);
                }

            }

        }

        conn.close();
    }

    public void replaceIDs() throws IOException,ClassNotFoundException, SQLException {

        driver.get("https://en.wikipedia.org/wiki/TLC:_Tables,_Ladders,_and_Chairs_(2015)");

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from ringname");
        Map<Integer,String> hM = new HashMap<Integer,String>();

        PreparedStatement getRingNameID = conn.prepareStatement("select id from ringname where name=?");

        ResultSet rs2 = st.executeQuery("select url from ppv");

            System.out.println("ppv : "+ driver.findElement(By.id("firstHeading")).getText());
            List<WebElement> rows = driver.findElements(By.xpath("//table[@class='wikitable']/tbody/tr"));
            String s = new String();
            String all = new String();
            int noOfPlayers;
            List<String> matches = new ArrayList<String>();
            for (WebElement row : rows) {
                if (row.findElements(By.xpath("td")).size() > 0) {
                    s = row.findElement(By.xpath("td")).getText();
                    System.out.println("match : " + s);
                    matches.add(s);

                    String[] groups = s.split("\\s*defeated\\s*|\\svs\\.\\s|\\s*\\(c\\)\\s*|\\s*\\(\\s*|\\s*\\)\\s*|\\s*,\\s*|\\s*with\\s*|\\sand\\s|\\sby\\s|\\svia\\s|\\s*with\\s*");

                    for (String group : groups) {
//                    System.out.println(" group : " + group);
                        getRingNameID.setString(1, group.trim());
                        ResultSet getID = getRingNameID.executeQuery();
                        if (getID.next() ) {
                            hM.put(getID.getInt(1), group.trim());
                        } else {
                            if(!group.trim().isEmpty()) System.out.println("Id not found : " + group);
                        }
                    }

                    for (Map.Entry<Integer, String> entry : hM.entrySet()) {
                        int key = entry.getKey();
                        String value = entry.getValue();
                        if (!value.isEmpty()) {

                            s = s.replace(value, String.valueOf(key));
                        }
                    }


//                    System.out.println("match with ID : " + s);
                }

                System.out.println("match with ID : " + s);
            }
    }

    public void replaceIDAlchemy() throws ParseException {

        String match = "The Usos (Jimmy Uso and Jey Uso) (c) defeated The Real Americans (Cesaro and Jack Swagger) (with Zeb Colter), RybAxel (Ryback and Curtis Axel), and Los Matadores (Diego and Fernando) (with El Torito)";



        AlchemyLanguage service = new AlchemyLanguage();
        service.setApiKey("8cc13ac542566f419e3f692d1df3301014186f63");

        Map<String,Object> params = new HashMap<String, Object>();
        params.put(AlchemyLanguage.TEXT, match);
        DocumentSentiment sentiment = service.getSentiment(params).execute();


        Entities entities = service.getEntities(params).execute();


        JSONArray persons = (JSONArray) ((JSONObject) new JSONParser().parse(entities.toString())).get("entities");

        for(Object person: persons ){
            JSONObject star = (JSONObject) person;
            String name = (String) star.get("text");

            System.out.println("The star is : " + name);

        }


        SAORelations relationObject = service.getRelations(params).execute();


        Object document = Configuration.defaultConfiguration().jsonProvider().parse(relationObject.toString());

        String result = JsonPath.read(document, "$.relations[0].action.text");

        String winner = JsonPath.read(document, "$.relations[0].subject.text");

        String others = JsonPath.read(document, "$.relations[0].object.text");

        System.out.println("winners : " + winner + " result : " + result + " opponent : " + others);


    }

}
