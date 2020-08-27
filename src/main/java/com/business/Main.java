package com.business;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\ponomarev_projects_git\\myPhoneMusic\\sources\\Скриптонит Райда - Baby mama.mp3");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));

        StringBuilder stringBuilder = new StringBuilder();
        byte[] contents = new byte[100];
        while (fin.read(contents) != -1) {
            for (byte b : contents) {
                for (int x = 0; x < 8; x++) {
                    stringBuilder.append(b >> x & 1);
                }
            }
        }
        // testing request
        RequestSender requestSender = new RequestSender();
        //requestSender.sendGET();
        requestSender.sendPOST(stringBuilder.toString());
    }
}
