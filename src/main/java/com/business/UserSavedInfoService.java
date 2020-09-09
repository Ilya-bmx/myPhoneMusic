package com.business;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

//TODO: в другом потоке надо save сделать
public class UserSavedInfoService {

    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String USER_INFO_FILE_PATH = "/userInfo.txt";
    private static final String DOWNLOAD_FOLDER_PATH = "/folderInfo.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSavedInfoService.class);

    // для jar другой путь для писания код другой
    public ObservableList<String> getExistAddresses() throws FileNotFoundException {
        return getInfoList(USER_INFO_FILE_PATH);
    }

    public ObservableList<String> getExistDownloadingFolder() throws FileNotFoundException {
        return getInfoList(DOWNLOAD_FOLDER_PATH);
    }

    private ObservableList<String> getInfoList(String path) throws FileNotFoundException {
        File file = new File(CURRENT_DIR + path);

        if (!file.exists()) {
            createEmptyFile(file);
        }

        return getExistFileLines(file);
    }

    private ObservableList<String> getExistFileLines(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        BufferedReader inStream = new BufferedReader(fileReader);
        ObservableList<String> list = FXCollections.observableArrayList();
        String inString = "";
        while (true) {
            try {

                if ((inString = inStream.readLine()) == null) break;

            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(inString);
        }
        return list;
    }

    public void save(String phoneInterfaceAddress, String downloadingFolderPath) {
        saveDownloadingFolderPath(downloadingFolderPath);
        savePhoneInterfaceAddress(phoneInterfaceAddress);
    }

    private void savePhoneInterfaceAddress(String phoneInterfaceAddress) {
        String userInfoPath = CURRENT_DIR + USER_INFO_FILE_PATH;
        File file = createFile(userInfoPath);

        if (isExist(phoneInterfaceAddress, file)) return;

        writeInfoIntoFile(userInfoPath, phoneInterfaceAddress);
    }

    private void saveDownloadingFolderPath(String downloadingFolderPathToSave) {
        String folderInfoPath = CURRENT_DIR + DOWNLOAD_FOLDER_PATH;
        File file = createFile(folderInfoPath);

        if (isExist(downloadingFolderPathToSave, file)) return;

        writeInfoIntoFile(folderInfoPath, downloadingFolderPathToSave);
    }

    private File createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error("File for user info not created", e);
            }
        }
        return file;
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
        FileReader fileReader = new FileReader(file);
        BufferedReader inStream = new BufferedReader(fileReader);
        String inString;
        while (true) {
            try {
                if ((inString = inStream.readLine()) == null) break;
                if (inString.equals(word))
                    count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
