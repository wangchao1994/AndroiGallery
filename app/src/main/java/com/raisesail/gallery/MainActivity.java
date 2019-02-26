package com.raisesail.gallery;

import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raisesail.gallery.adapter.PhotoRecyAdapter;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.bean.DetailPhotoInfo;
import com.raisesail.gallery.event.DataFreshEvent;
import com.raisesail.gallery.decoration.GridSpacingItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private int mSpanCount = 4;
    private int mSpacing = 8;
    private boolean isIncludeEdge = true;
    private List<DetailPhotoInfo> mDetailPhotoInfoList;
    private PhotoRecyAdapter mPhotoRecyAdapter;

    @Override
    protected void initData() {
        for (int i = 0; i < 16; i++) {
            DetailPhotoInfo detailPhotoInfo = new DetailPhotoInfo();
            detailPhotoInfo.setChecked(true);
            detailPhotoInfo.setPhotoFile("");
            detailPhotoInfo.setPhotoNumber("number="+i);
            mDetailPhotoInfoList.add(detailPhotoInfo);
        }
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,mSpanCount);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mSpanCount,mSpacing,isIncludeEdge));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        if (mPhotoRecyAdapter == null){
            mPhotoRecyAdapter = new PhotoRecyAdapter(this,R.layout.photo_detail_item,mDetailPhotoInfoList);
        }
        mRecyclerView.setAdapter(mPhotoRecyAdapter);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NotifiedDataRefresh(DataFreshEvent dataFreshEvent){
        if (dataFreshEvent.isRefresh){
            //进行数据刷新
        }
    }

    private void initView() {
        mDetailPhotoInfoList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.list_recycler_view);
    }
    @Override
    public void handleMsg(Message msg) {

    }
}
