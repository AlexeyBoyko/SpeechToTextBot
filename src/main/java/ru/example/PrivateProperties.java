package ru.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PrivateProperties {
    public static Properties props = new Properties();

    static {
        try {
            props.load(new FileInputStream(new File("private.config")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
