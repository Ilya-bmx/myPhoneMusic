package com.business;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\ponomarev_projects_git\\myPhoneMusic\\sources\\Скриптонит Райда - Baby mama.mp3");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
        byte[] contents = new byte[(int)file.length()];

        byteArrayInputStream.read(contents);
        byteArrayInputStream.close();

        isSuccessfulReadMp3File(contents);


        // testing request
        RequestSender requestSender = new RequestSender();
        //requestSender.sendGET();
        requestSender.sendPOST(contents);
    }

    private static void isSuccessfulReadMp3File(byte[] contents) {
        byte result = (byte)0;

        for (byte b: contents) {
            result = (byte) (result ^ b);
        }
        if (result == 0) {
            throw new RuntimeException("Nothing read from file");
        }
    }
}
