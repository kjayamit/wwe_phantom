package testGoogle.test;

import org.testng.annotations.Test;
import testGoogle.framework.BaseTest;

import java.sql.*;
//import org.


public class HomePageTest extends BaseTest{

    @Test(groups = {"smoke"})
    public void doGoogleSearch() throws Exception {

        googleHomePage().verifyHomePageTitle();
        googleHomePage().enterSearchText("selenium");
        googleHomePage().clickSearchButton();
        googleHomePage().readDefinitionBox();

    }

    @Test(groups = {"smoke"})
    public void summa() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
//        DriverManager.registerDriver(new org.postgresql.Driver());
        String url = "jdbc:postgresql://localhost/wwe?user=jaya";
        Connection conn = DriverManager.getConnection(url);
        System.out.println("Opened database successfully");

        DatabaseMetaData dbmd = conn.getMetaData();
        String[] types = {"TABLE"};

        ResultSet rs = dbmd.getTables(null, null, "%", types);
        while (rs.next()) {
            String tableCatalog = rs.getString(1);
            String tableSchema = rs.getString(2);
            String tableName = rs.getString(3);

            System.out.printf("%s - %s - %s%n", tableCatalog, tableSchema, tableName);
        }
    }
}
