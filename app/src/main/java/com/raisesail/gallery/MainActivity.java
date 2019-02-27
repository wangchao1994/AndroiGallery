package com.raisesail.gallery;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.raisesail.gallery.adapter.PhotoRecyAdapter;
import com.raisesail.gallery.adapter.base.MultiItemTypeAdapter;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.data.LocalDataUtils;
import com.raisesail.gallery.data.bean.LocalThumb;
import com.raisesail.gallery.event.DataFreshEvent;
import com.raisesail.gallery.decoration.GridSpacingItemDecoration;
import com.raisesail.gallery.event.PreviewPicEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

public class MainActivity extends BaseActivity implements MultiItemTypeAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private int mSpanCount = 4;
    private int mSpacing = 8;
    private boolean isIncludeEdge = true;
    private PhotoRecyAdapter mPhotoRecyAdapter;
    private List<LocalThumb> allLocalPhotos;
    private Toolbar mToolbar;
    @Override
    protected void setOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
    }

    @Override
    protected void setMenuItemClick(MenuItem menuItem) {

    }

    @Override
    protected void initData() {
        allLocalPhotos = LocalDataUtils.getAllLocalPhotos(this);
        if (mPhotoRecyAdapter == null){
            mPhotoRecyAdapter = new PhotoRecyAdapter(this,R.layout.photo_detail_item,allLocalPhotos);
        }
        mRecyclerView.setAdapter(mPhotoRecyAdapter);
        mPhotoRecyAdapter.notifyDataSetChanged();
        mPhotoRecyAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NotifiedDataRefresh(DataFreshEvent dataFreshEvent){
        if (dataFreshEvent.isRefresh){
            //删除数据后进行数据刷新
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.list_recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,mSpanCount);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mSpanCount,mSpacing,isIncludeEdge));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mToolbar = findViewById(R.id.main_toolbar);
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
        return false;
    }


}
