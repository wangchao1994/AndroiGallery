package com.raisesail.gallery.bean;

public class DetailPhotoInfo {
    private String PhotoFile;
    private String photoNumber;
    private boolean checked;

    public String getPhotoFile() {
        return PhotoFile;
    }

    public void setPhotoFile(String photoFile) {
        PhotoFile = photoFile;
    }

    public String getPhotoNumber() {
        return photoNumber;
    }

    public void setPhotoNumber(String photoNumber) {
        this.photoNumber = photoNumber;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public String toString() {
        return "DetailPhotoInfo{" +
                "PhotoFile='" + PhotoFile + '\'' +
                ", photoNumber='" + photoNumber + '\'' +
                ", checked=" + checked +
                '}';
    }
}
