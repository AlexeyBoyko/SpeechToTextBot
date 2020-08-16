package com.company;

public class Config {

    public static final String TELEGRAM_BOT_TOKEN = PrivateProperties.props.getProperty("TELEGRAM_BOT_TOKEN");
    public static final String TELEGRAM_BOT_USERNAME = PrivateProperties.props.getProperty("TELEGRAM_BOT_USERNAME");
    public static final String YANDEX_SPEECHKIT_API_KEY = PrivateProperties.props.getProperty("YANDEX_SPEECHKIT_API_KEY");
}
