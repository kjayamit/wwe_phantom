package testGoogle.Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jayachk on 2/13/16.
 */
public class MrWriter {

    File file = new File("test1.txt");

    public static FileWriter fileWriter;

    public void initialize() {

        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToFile(String s){
        try {

            fileWriter.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            fileWriter.flush();
            fileWriter.close();} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
