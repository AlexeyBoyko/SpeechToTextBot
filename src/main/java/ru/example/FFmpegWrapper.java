package ru.example;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

public class FFmpegWrapper {

    public static String convertToWAV(String inputFileName) throws IOException {
        String outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + ".wav"; // меняем расширение
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFileName)     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true)
                .addOutput(outputFileName)
                //.setFormat("mp4")        // Format is inferred from filename, or can be set
                //.setTargetSize(250_000)  // Aim for a 250KB file
                .setAudioCodec("pcm_s16le")
                .setAudioSampleRate(16000)
                .done();
        // Run a one-pass encode
        new FFmpegExecutor(new FFmpeg("ffmpeg.exe")).createJob(builder).run();
        return outputFileName;
    }
}

