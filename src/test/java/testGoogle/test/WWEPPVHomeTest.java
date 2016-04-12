package testGoogle.test;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;
import testGoogle.framework.BaseTest;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.*;

/**
 * Created by jayachk on 2/13/16.
 */
public class WWEPPVHomeTest extends BaseTest{

    @Test(groups = {"smoke"})
    public void doGoogleSearch() throws Exception {

        wikiPPV().getAllPPV();

    }

    @Test(groups = {"smoke"})
    public void doPlayers() throws Exception {

        wikiPPV().splitPlayers();

    }

    @Test(groups = {"smoke"})
    public void something() throws IOException {

        String jsonString = callURL("http://www.wwe.com/api/superstars");
        System.out.println("\n\njsonString: " + jsonString);

        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            System.out.println("\n\njsonObject: " + jsonObject);

            Iterator<?> keys = jsonObject.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                if ( jsonObject.get(key) instanceof JSONObject ) {
                    System.out.println("\n\nKey : " + key);
                }
            }

            for(int i = 0; i<jsonObject.names().length(); i++){
                System.out.println( "key = " + jsonObject.names().getString(i) + " value = " + jsonObject.get(jsonObject.names().getString(i)));
            }

            JSONArray jsonArray = jsonObject.getJSONArray("talent");

            System.out.println("\n\njsonArray: " + jsonArray);

            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject rec = jsonArray.getJSONObject(i);
                String name = rec.getString("name");
                String link = rec.getString("link");
                System.out.println("Superstar " + i + " : " + name + " link : " + link);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static String callURL(String myURL) {
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }

        return sb.toString();
    }

}
