package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SpeechToTextBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init(); // Инициализируем апи
            new TelegramBotsApi().registerBot(new SpeechToTextBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return Config.TELEGRAM_BOT_USERNAME;
    }

    public String getBotToken() {
        return Config.TELEGRAM_BOT_TOKEN;
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || !message.hasVoice()) {
            sendReplyMessage(message, "***ГОЛОСОВОЕ СООБЩЕНИЕ НЕ НАЙДЕНО***");
        } else {
            try {
                GetFile getFile = new GetFile();
                getFile.setFileId(message.getVoice().getFileId());
                File file = execute(getFile);
                InputStream in = new URL(file.getFileUrl(getBotToken())).openStream();
                String originalFileName = "voiceMessage.oga";
                Files.copy(in, Paths.get(originalFileName), StandardCopyOption.REPLACE_EXISTING);
                String convertedFileName = FFmpegWrapper.convertToWAV(originalFileName);
                byte[] audioData = Files.readAllBytes(Paths.get(convertedFileName));
                String xmlTextMessage = SpeechRecognition.getString(audioData);
                System.out.println(xmlTextMessage);
                sendReplyMessage(message, XmlParser.parseYandexResponse(xmlTextMessage));
            } catch (Exception e) {
                e.printStackTrace();
                sendReplyMessage(message, "***ТЕХНИЧЕСКАЯ ОШИБКА***");
            }
        }
    }

    private void sendReplyMessage(Message replyToMessage, String text) {
        try {
            SendMessage sendMessage = new SendMessage()
                    .enableMarkdown(true)
                    .setChatId(replyToMessage.getChatId().toString())
                    .setReplyToMessageId(replyToMessage.getMessageId())
                    .setText(text);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
