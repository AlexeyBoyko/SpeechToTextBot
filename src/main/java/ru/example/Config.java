package ru.example;

public class Config {

    public static final String TELEGRAM_BOT_TOKEN = new PrivateProperties().getProperty("TELEGRAM_BOT_TOKEN");
    public static final String TELEGRAM_BOT_USERNAME = new PrivateProperties().getProperty("TELEGRAM_BOT_USERNAME");
    public static final String YANDEX_SPEECHKIT_API_KEY = new PrivateProperties().getProperty("YANDEX_SPEECHKIT_API_KEY");
}
