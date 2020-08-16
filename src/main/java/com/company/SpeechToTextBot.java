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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SpeechToTextBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new SpeechToTextBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasVoice()) {
            String textMessage = "[ОШИБКА]";
            GetFile getFile = new GetFile();
            getFile.setFileId(message.getVoice().getFileId());
            try {
                File file = execute(getFile);
                String fileUrl = file.getFileUrl(getBotToken());
                InputStream in = new URL(fileUrl).openStream();
                String originalFileName = "voiceMessage.oga";
                Files.copy(in, Paths.get(originalFileName), StandardCopyOption.REPLACE_EXISTING);
                FFmpegWrapper ffMpegWrapper = new FFmpegWrapper();
                String convertedFileName = ffMpegWrapper.convertToWAV(originalFileName);
                byte[] audioData = new FileReader().getBytesFromFile(convertedFileName);
                SpeechRecognition speechRecognition = new SpeechRecognition();
                String xmlTextMessage = speechRecognition.getString(audioData);
                System.out.println(xmlTextMessage);
                textMessage = XmlParser.parseDocument(xmlTextMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendMsg(message, textMessage);
        } else {
            sendMsg(message, "Данный бот обрабатывает только голосовые сообщения");
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return ConfigReader.getTelegramBotUsername();
    }

    public String getBotToken() {
        return ConfigReader.getTelegramBotToken();
    }
}
