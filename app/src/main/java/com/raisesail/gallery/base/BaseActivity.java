package com.raisesail.gallery.base;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import com.raisesail.gallery.R;
import com.raisesail.gallery.handler.GlobalHandler;
import com.raisesail.gallery.immersive.StatusBar;
import com.raisesail.gallery.utils.AppManager;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends BasePermissionsActivity implements GlobalHandler.HandleMsgListener,Toolbar.OnMenuItemClickListener {
    private final int PERMISSION_REQUEST_CODE = 1000;
    private GlobalHandler mGlobalHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initStatusBar();
        initContentView();
        mGlobalHandler = GlobalHandler.getInstance();
        mGlobalHandler.setHandleMsgListener(this);
        //requestPermission(new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
        AppManager.getAppManager().addActivity(this);
        EventBus.getDefault().register(this);
    }

    private void initStatusBar() {
        StatusBar.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimaryDark), 1f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setOptionsMenu(menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        EventBus.getDefault().unregister(this);
    }
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        setMenuItemClick(menuItem);
        return true;
    }

    protected abstract void setOptionsMenu(Menu menu);
    protected abstract void setMenuItemClick(MenuItem menuItem);
    protected abstract void initData();
    protected abstract void initContentView();
}
