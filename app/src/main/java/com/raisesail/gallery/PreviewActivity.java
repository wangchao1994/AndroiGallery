package com.raisesail.gallery;

import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.event.PreviewPicEvent;
import com.raisesail.gallery.photoview.PhotoView;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PreviewActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener{
    private static final String TAG = PreviewActivity.class.getSimpleName();
    private PhotoView mPhotoView;
    private Toolbar mToolbar;
    private TextView mPictureName;
    @Override
    protected void initData() {

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_preview);
        mPhotoView = findViewById(R.id.photo_view);
        mToolbar = findViewById(R.id.toolbar);
        mPictureName = findViewById(R.id.tv_picture_name);
        mToolbar.setOnMenuItemClickListener(this);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void previewEvent(PreviewPicEvent previewPicEvent){
        String fileName = previewPicEvent.fileName;
        if (null != fileName && !"".equals(fileName)){
            //mToolbar.setTitle(fileName);
            mPictureName.setText(fileName);
        }
        String filePath = previewPicEvent.filePath;
        if (null != filePath && !"".equals(filePath)){
            Glide.with(this).load(filePath).into(mPhotoView);
        }
    }

    @Override
    public void handleMsg(Message msg) {

    }

    private void doPrintf() {
        Log.d(TAG,"doPrintf------------------");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_printf:
                doPrintf();
                break;
            case R.id.action_more:
                break;
        }
        return true;
    }
}
