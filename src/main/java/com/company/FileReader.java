package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    //private String filePath = "output.wav";
//    file must be less than 1Mb!

    public byte[] getBytesFromFile(String filePath) {
        Path path = Paths.get(filePath);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
