package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SpeechRecognition {

    private final String API_KEY = "YOUR_YANDEX_SPEECHKIT_API_KEY";

    private final String REQUEST = "https://asr.yandex.net/asr_xml?" +
            "uuid=01ae133b744628b58fb536d496daa1e6&" +
            "key=" + API_KEY + "&" +
            "topic=queries";

    public String getString(byte[] data) {
        String res = "";
        try {

            URL url = new URL(REQUEST);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            System.out.println("Done");
            System.out.println();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                res += decodedString;
            }
            connection.disconnect();
        } catch (MalformedURLException c) {
            c.printStackTrace();
        } catch (ProtocolException p) {
            p.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }

        return res;
    }
}