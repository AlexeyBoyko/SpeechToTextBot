package com.company;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

public class FFmpegWrapper {

    public String convertToWAV(String inputFileName) throws IOException {
        String outputFileName = "output.wav";
        FFmpeg ffmpeg = new FFmpeg("ffmpeg.exe");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFileName)     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists
                .addOutput(outputFileName)   // Filename for the destination
                //.setFormat("mp4")        // Format is inferred from filename, or can be set
                //.setTargetSize(250_000)  // Aim for a 250KB file
                .setAudioCodec("pcm_s16le")
                .setAudioSampleRate(16000)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        // Run a one-pass encode
        executor.createJob(builder).run();
        return outputFileName;
    }
}

