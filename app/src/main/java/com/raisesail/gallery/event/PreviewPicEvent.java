package com.raisesail.gallery.event;

public class PreviewPicEvent {
    public String filePath;
    public String fileName;
    public PreviewPicEvent(String mFilePath,String mFileName){
        filePath = mFilePath;
        fileName = mFileName;
    }
}
