package com.raisesail.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.raisesail.gallery.adapter.PhotoRecyclerviewAdapter;
import com.raisesail.gallery.adapter.base.MultiItemTypeAdapter;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.data.LocalDataUtils;
import com.raisesail.gallery.data.bean.LocalThumb;
import com.raisesail.gallery.decoration.GridSpacingItemDecoration;
import com.raisesail.gallery.event.DataFreshEvent;
import com.raisesail.gallery.event.PreviewPicEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends BaseActivity implements MultiItemTypeAdapter.OnItemClickListener,Toolbar.OnMenuItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private int mSpanCount = 4;
    private int mSpacing = 8;
    private boolean isIncludeEdge = true;
    private PhotoRecyclerviewAdapter mPhotoRecyAdapter;
    private List<LocalThumb> allLocalPhotos;
    private Toolbar mToolbar;
    private AlertDialog mAlertDialog;

    @Override
    protected void initData() {
        allLocalPhotos = LocalDataUtils.getAllLocalPhotos(this);
        Log.d(TAG,"allLocalPhotos----->"+allLocalPhotos.size());
        mPhotoRecyAdapter = new PhotoRecyclerviewAdapter(this,R.layout.photo_detail_item,allLocalPhotos);
        mPhotoRecyAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mPhotoRecyAdapter);
        mPhotoRecyAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notificationData(DataFreshEvent dataFreshEvent){
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.list_recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,mSpanCount);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mSpanCount,mSpacing,isIncludeEdge));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            //actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setNavigationIcon(R.mipmap.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果当前有选中项则进行取消
                if (mPhotoRecyAdapter != null && mPhotoRecyAdapter.getSelectedCount() > 0){
                    mPhotoRecyAdapter.unSelectAllData();
                }else{
                    finish();
                }
            }
        });

    }
    @Override
    public void handleMsg(Message msg) {
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
        if (null !=allLocalPhotos  && allLocalPhotos.size() > 0){
            EventBus.getDefault().postSticky(new PreviewPicEvent(allLocalPhotos.get(position).getPhotoFilePath(),allLocalPhotos.get(position).getPhotoFileNumber()));
            startActivity(new Intent(this,PreviewActivity.class));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_printf:
                Log.d(TAG,"select status---->"+mPhotoRecyAdapter.getSelectedStatus());
                if (mPhotoRecyAdapter.getSelectedStatus() == PhotoRecyclerviewAdapter.SelectedStatus.SELECT_ALL){
                    mPhotoRecyAdapter.unSelectAllData();
                }else if(mPhotoRecyAdapter.getSelectedStatus() == PhotoRecyclerviewAdapter.SelectedStatus.UN_SELECT_ALL){
                    mPhotoRecyAdapter.selectAllData();
                }
                break;
            case R.id.action_delete:
                int selectedCount = mPhotoRecyAdapter.getSelectedCount();
                String format = String.format(getResources().getString(R.string.alert_delete), selectedCount);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage(format)
                        .setNegativeButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mPhotoRecyAdapter.getSelectedStatus() == PhotoRecyclerviewAdapter.SelectedStatus.SELECT_ALL) {
                                    mPhotoRecyAdapter.clearAllData();
                                } else {
                                    mPhotoRecyAdapter.deleteSelectedData();
                                }
                                mAlertDialog.dismiss();
                            }
                        })
                        .setPositiveButton(getResources().getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlertDialog.dismiss();
                            }
                        });
                mAlertDialog = mBuilder.create();
                if (allLocalPhotos.size()>0 && selectedCount >0){
                    mAlertDialog.show();
                }
                break;
        }
        return true;
    }
}
