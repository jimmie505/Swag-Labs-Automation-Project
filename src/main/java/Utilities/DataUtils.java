package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {
    private static final String TEST_DATA = "src/test/resources/TestData/";
    //TODO : reading data from JSON file
    public static String jsonData (String fileName , String key ) {
        try {
            FileReader fileReader = new FileReader(TEST_DATA + fileName +".json");
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return "";
    }

    //TODO : reading data from properties file
    public static String propertiesData(String fileName , String key) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA + fileName + ".properties"));
        return properties.getProperty(key);
    }

}
