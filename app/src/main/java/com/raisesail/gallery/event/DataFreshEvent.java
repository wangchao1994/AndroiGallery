package com.raisesail.gallery.event;

import com.raisesail.gallery.data.bean.LocalThumb;

import java.util.List;

public class DataFreshEvent {
    public List<LocalThumb> mLocalThumbList;
    public DataFreshEvent(List<LocalThumb> localThumbs){
        mLocalThumbList = localThumbs;
    }
}
