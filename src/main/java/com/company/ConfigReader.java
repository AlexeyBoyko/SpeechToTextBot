package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static String TELEGRAM_BOT_TOKEN;
    private static String TELEGRAM_BOT_USERNAME;
    private static String YANDEX_SPEECHKIT_API_KEY;

    static {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("private.config")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TELEGRAM_BOT_TOKEN = props.getProperty("TELEGRAM_BOT_TOKEN");
        TELEGRAM_BOT_USERNAME = props.getProperty("TELEGRAM_BOT_USERNAME");
        YANDEX_SPEECHKIT_API_KEY = props.getProperty("YANDEX_SPEECHKIT_API_KEY");
    }

    public static String getTelegramBotToken() {
        return TELEGRAM_BOT_TOKEN;
    }

    public static String getTelegramBotUsername() {
        return TELEGRAM_BOT_USERNAME;
    }

    public static String getYandexSpeechkitApiKey() {
        return YANDEX_SPEECHKIT_API_KEY;
    }
}
