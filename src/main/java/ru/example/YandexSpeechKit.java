package ru.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YandexSpeechKit {

    private static final String API_KEY = Config.YANDEX_SPEECHKIT_API_KEY;

    private static final String REQUEST = "https://asr.yandex.net/asr_xml?" +
            "uuid=01ae133b744628b58fb536d496daa1e6&" +
            "key=" + API_KEY + "&" +
            "topic=queries";

    public static String recognizeText(byte[] data) throws IOException {
        String res = "";
        HttpURLConnection connection = ((HttpURLConnection) new URL(REQUEST).openConnection());
        try {
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "audio/x-pcm;bit=16;rate=16000");
            connection.setRequestProperty("User-Agent", "BoBa");
            connection.setRequestProperty("Host", "asr.yandex.net");
            connection.setRequestProperty("Content-Length", "" + data.length);
            connection.setRequestProperty("Transfer-Encoding", "chunked");
            connection.setUseCaches(false);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(data);
            }

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()))) {
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    res += decodedString;
                }
            }

            return res;
        } finally {
            connection.disconnect();
        }
    }
}