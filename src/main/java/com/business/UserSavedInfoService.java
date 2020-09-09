package com.business;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TODO: в другом потоке надо save сделать
public class UserSavedInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSavedInfoService.class);

    public ObservableList<String> getExistAddresses() throws FileNotFoundException {
        ObservableList<String> list = FXCollections.observableArrayList();
        // для jar другой путь для писания код другой
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + currentDir);
        File file = new File(currentDir + "/userInfo.txt");
        System.out.println(file.getAbsolutePath());

        if (!file.exists()) {
            createEmptyFile(file);
        }


        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            list.add(scanner.next());
        }
        return list;
    }

    public ObservableList<String> getExistDownloadingFolder() throws FileNotFoundException {
        ObservableList<String> list = FXCollections.observableArrayList();
        // для jar другой путь для писания код другой
        String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "/folderInfo.txt");

        if (!file.exists()) {
            createEmptyFile(file);
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            list.add(scanner.next());
        }
        return list;
    }

    public void save(String phoneInterfaceAddress, String downloadingFolderPath) {
        saveDownloadingFolderPath(downloadingFolderPath);
        String currentDir = System.getProperty("user.dir");
        String userInfoPath = currentDir + "/userInfo.txt";
        File file = new File(userInfoPath);
        createFile(userInfoPath);

        if (isExist(phoneInterfaceAddress, file)) return;

        writeInfoIntoFile(userInfoPath, phoneInterfaceAddress);
    }

    private void saveDownloadingFolderPath(String downloadingFolderPathToSave) {
        String currentDir = System.getProperty("user.dir");
        String folderInfoPath = currentDir + "/folderInfo.txt";
        createFile(folderInfoPath);

        writeInfoIntoFile(folderInfoPath, downloadingFolderPathToSave);
    }

    private void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("File for user info not created", e);
            }
        }
    }

    // TODO: добавить док
    private void writeInfoIntoFile(String path, String info) {
        try {
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.append(info)
                    .append("\n");
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

    private int countWord(String word, File file) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String nextToken = scanner.next();
            if (nextToken.equals(word))
                count++;
        }
        return count;
    }

    private void createEmptyFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
