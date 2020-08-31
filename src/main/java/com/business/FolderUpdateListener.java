package com.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FolderUpdateListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderUpdateListener.class);
    private static final String DIRECTORY_SEPARATOR = "/";

    private final RequestSender requestSender;
    private final FileNameToLatinConverter fileNameToLatinConverter;
    private WatchService watchService;

    private final String MUSIC_FOLDER_PATH;
    private final String VALID_MUSIC_FOLDER;

    public FolderUpdateListener(String phoneUrl, String folderPath) throws InterruptedException {
        this.requestSender = new RequestSender(phoneUrl);
        this.fileNameToLatinConverter = new FileNameToLatinConverter();
        this.MUSIC_FOLDER_PATH = folderPath;
        this.VALID_MUSIC_FOLDER = MUSIC_FOLDER_PATH + DIRECTORY_SEPARATOR + "IPhoneMusic";
        init(folderPath);
        listen();
    }

    private void init(String folderPath) {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new RuntimeException("bad path or something else", e);
        }
        Path path = Paths.get(folderPath);
        try {
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch (IOException e) {
            throw new RuntimeException("can t register actions or something else", e);
        }
    }

    private void listen() throws InterruptedException {
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                if (event.kind() == ENTRY_CREATE && isMP3File(event.context().toString())) {
                    uploadNewFileOnIPhone(event.context().toString());
                }
            }
            key.reset();
        }
    }

    private void uploadNewFileOnIPhone(String fileName) {
        File file = new File(MUSIC_FOLDER_PATH + DIRECTORY_SEPARATOR + fileName);
        File validFile = renameFile(file);
        requestSender.sendPOST(validFile);
    }

    // renamedToLatin надо создавать при старте программы
    private File renameFile(File file) {
        String rightNameInLatinChars = fileNameToLatinConverter.convert(file.getName());
        makeDirForRenamedMusic();
        File newName = new File(VALID_MUSIC_FOLDER + DIRECTORY_SEPARATOR + rightNameInLatinChars);
        if (!file.renameTo(newName)) {
            throw new RuntimeException("file was not renamed");
        }
        return new File(VALID_MUSIC_FOLDER + DIRECTORY_SEPARATOR + rightNameInLatinChars);
    }

    private void makeDirForRenamedMusic() {
        File directoryOfRenamedMusic = new File(VALID_MUSIC_FOLDER);
        boolean isDirCreated = directoryOfRenamedMusic.mkdir();
        if (!isDirCreated) {
            System.out.println("Dir for music not created");
        }
    }

    private boolean isMP3File(String fileName) {
        if (fileName.length() < 3) return false;

        return fileName.endsWith("mp3");
    }
}
