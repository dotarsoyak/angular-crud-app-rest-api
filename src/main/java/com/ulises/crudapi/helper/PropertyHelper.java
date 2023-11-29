package com.ulises.crudapi.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHelper {

    public static String getProperty(String property) throws IOException {
        InputStream input = new FileInputStream("src/main/resources/application.properties");
        Properties prop = new Properties();

        // load a properties file
        prop.load(input);

        return prop.getProperty(property);
    }
}
