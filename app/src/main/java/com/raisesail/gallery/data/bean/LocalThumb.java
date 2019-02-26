package com.raisesail.gallery.data.bean;

public class LocalThumb {
    private String photoFilePath;
    private String photoFileNumber;

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getPhotoFileNumber() {
        return photoFileNumber;
    }

    public void setPhotoFileNumber(String photoFileNumber) {
        this.photoFileNumber = photoFileNumber;
    }

    @Override
    public String toString() {
        return "LocalThumb{" +
                "photoFilePath='" + photoFilePath + '\'' +
                ", photoFileNumber='" + photoFileNumber + '\'' +
                '}';
    }
}
