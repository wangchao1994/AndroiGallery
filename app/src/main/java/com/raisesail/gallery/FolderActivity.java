package com.raisesail.gallery;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SearchView;

import com.raisesail.gallery.adapter.FolderRecyclerViewAdapter;
import com.raisesail.gallery.adapter.base.MultiItemTypeAdapter;
import com.raisesail.gallery.base.BaseActivity;
import com.raisesail.gallery.data.LocalDataUtils;
import com.raisesail.gallery.data.bean.LocalFolder;
import com.raisesail.gallery.decoration.GridSpacingItemDecoration;
import com.raisesail.gallery.event.DataFreshEvent;
import com.raisesail.gallery.event.PictureReportEvent;
import com.raisesail.gallery.event.PreviewPicEvent;
import com.raisesail.gallery.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends BaseActivity implements MultiItemTypeAdapter.OnItemClickListener,Toolbar.OnMenuItemClickListener{

    private RecyclerView mRecyclerView;
    private int mSpanCount = 4;
    private int mSpacing = 50;
    private boolean isIncludeEdge = true;
    private FolderRecyclerViewAdapter mFolderRecyclerViewAdapter;
    private List<LocalFolder> allFolderList;
    private Toolbar mToolbar;
    private static final String DIR_PATH ="/storage/emulated/0/"+  Environment.DIRECTORY_DCIM + "/Camera";
    private MenuItem mSearchItem;
    private SearchView mSearchView;

    @Override
    protected void initData() {
        allFolderList = LocalDataUtils.getAllLocalFolder(DIR_PATH);
        Log.d("folder","allFolderList----->"+allFolderList);
        mFolderRecyclerViewAdapter = new FolderRecyclerViewAdapter(this,R.layout.folder_item,allFolderList);
        mFolderRecyclerViewAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mFolderRecyclerViewAdapter);
        mFolderRecyclerViewAdapter.setOnItemClickListener(this);

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_floder);
        initView();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)//数据刷新
    public void notificationData(DataFreshEvent dataFreshEvent){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.folder_menu,menu);
        mSearchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setOnQueryTextListener(mQueryTextListener);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setIconifiedByDefault(true);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchableInfo info = searchManager.getSearchableInfo(this.getComponentName());
            mSearchView.setSearchableInfo(info);
        }
        return super.onCreateOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener() {
        public boolean onQueryTextSubmit(String query) {
            LogUtils.d("QUERY","mQueryTextListener--------------->"+query);
            if (query != null && !"".equals(query)){
                List<LocalFolder> mListFolder = new ArrayList<>();
                mFolderRecyclerViewAdapter.setNewData(mListFolder);
            }
            return true;
        }
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
    private void initView() {
        mRecyclerView = findViewById(R.id.folder_recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this,mSpanCount);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(mSpanCount,mSpacing,isIncludeEdge));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mToolbar = findViewById(R.id.folder_toolbar);
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

            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
        if (null != allFolderList  && allFolderList.size() > 0){
            EventBus.getDefault().postSticky(new PictureReportEvent(allFolderList.get(position).getFolderPath()));
            startActivity(new Intent(FolderActivity.this,MainActivity.class));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
        LogUtils.d("onItemLongClick","onItemLongClick--------------------->"+position);
        if (mFolderRecyclerViewAdapter != null){
            mFolderRecyclerViewAdapter.setChangeCheckBoxVisible(true);
        }
        return true;
    }
}
