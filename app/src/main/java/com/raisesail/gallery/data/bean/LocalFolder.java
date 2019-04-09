package com.raisesail.gallery.data.bean;

public class LocalFolder {
    private String folderName;
    private String folderPath;
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public String toString() {
        return "LocalFolder{" +
                "folderName='" + folderName + '\'' +
                ", folderPath='" + folderPath + '\'' +
                '}';
    }
}
