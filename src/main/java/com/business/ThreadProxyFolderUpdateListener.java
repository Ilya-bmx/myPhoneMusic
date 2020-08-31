package com.business;

public class ThreadProxyFolderUpdateListener extends Thread{

    private final String PHONE_INTERFACE_URL;
    private final String MUSIC_DOWNLOADED_FOLDER;

    ThreadProxyFolderUpdateListener(String phoneUrl, String folderPath) {
        this.PHONE_INTERFACE_URL = phoneUrl;
        this.MUSIC_DOWNLOADED_FOLDER = folderPath;
    }

    @Override
    public void run() {
        try {
            new FolderUpdateListener(PHONE_INTERFACE_URL, MUSIC_DOWNLOADED_FOLDER);
        } catch (InterruptedException e) {
            System.out.println("Smth goes wrong while listening folders update");
            e.printStackTrace();
        }
    }
}
