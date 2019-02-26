package com.raisesail.gallery.base;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import com.raisesail.gallery.R;
import com.raisesail.gallery.handler.GlobalHandler;
import com.raisesail.gallery.immersive.StatusBar;
import com.raisesail.gallery.utils.AppManager;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends BasePermissionsActivity implements GlobalHandler.HandleMsgListener{

    private final int PERMISSION_REQUEST_CODE = 1000;
    private GlobalHandler mGlobalHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        initStatusBar();
        initContentView();
        mGlobalHandler = GlobalHandler.getInstance();
        //requestPermission(new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
    }

    private void initStatusBar() {
        StatusBar.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimary), 1f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    protected abstract void initData();
    protected abstract void initContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        EventBus.getDefault().unregister(this);
    }
}
