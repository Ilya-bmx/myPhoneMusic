package com.business;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class UserSavedInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSavedInfoService.class);

    public void save(String phoneInterfaceAddress) {
        File file = new File("src/main/resources/test.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("File for user info not created", e);
            }
        }

        if(isExist(phoneInterfaceAddress, file)) return;

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/test.txt", true);
            fileWriter.append("\n")
                    .append(phoneInterfaceAddress);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // все обработки ошибок обернуть в прокси
        }
    }

    private boolean isExist(String phoneInterfaceAddress, File file) {
        try {
            return countWord(phoneInterfaceAddress, file) > 0;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while checking existing phoneAddress " + e);
        }
    }

    public int countWord(String word, File file) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String nextToken = scanner.next();
            if (nextToken.equals(word))
                count++;
        }
        return count;
    }

}
