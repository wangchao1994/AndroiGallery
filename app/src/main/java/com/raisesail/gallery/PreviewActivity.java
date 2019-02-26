package com.raisesail.gallery;

import android.os.Message;

import com.bumptech.glide.Glide;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.event.PreviewPicEvent;
import com.raisesail.gallery.photoview.PhotoView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PreviewActivity extends BaseActivity {
    private PhotoView mPhotoView;
    @Override
    protected void initData() {
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_preview);
        mPhotoView = findViewById(R.id.photo_view);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void previewEvent(PreviewPicEvent previewPicEvent){
        String filePath = previewPicEvent.filePath;
        if (null != filePath){
            Glide.with(this).load(filePath).into(mPhotoView);
        }
    }

    @Override
    public void handleMsg(Message msg) {

    }
}
