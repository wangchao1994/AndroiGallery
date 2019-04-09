package com.raisesail.gallery.adapter;


import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raisesail.gallery.R;
import com.raisesail.gallery.adapter.base.CommonAdapter;
import com.raisesail.gallery.adapter.base.ViewHolder;
import com.raisesail.gallery.data.bean.LocalFolder;
import java.util.List;

public class FolderRecyclerViewAdapter extends CommonAdapter<LocalFolder> {
    private static final String TAG = FolderRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<LocalFolder> mLocalFolderList;
    private CheckBox mCheckBox;
    private boolean isCheckBoxShow;

    public FolderRecyclerViewAdapter(Context context, int layoutId, List<LocalFolder> datas) {
        super(context, layoutId, datas);
        mContext = context;
        mLocalFolderList = datas;
    }
    @Override
    protected void convert(ViewHolder holder, LocalFolder localFolder, int position) {
        holder.setText(R.id.tv_folder_name,localFolder.getFolderName());
        ImageView holderView = (ImageView) holder.getView(R.id.iv_folder_view);
        mCheckBox = (CheckBox) holder.getView(R.id.folder_checkbox);
        if (isCheckBoxShow){
            setCheckBoxVisible();
        }
        Glide.with(mContext).load(R.drawable.ic_launcher_background).thumbnail(0.2f).into(holderView);//缩略图
    }

    public void setCheckBoxVisible(){
        if (mCheckBox.getVisibility() == View.GONE){
            mCheckBox.setVisibility(View.VISIBLE);
        }else{
            mCheckBox.setVisibility(View.GONE);
        }
    }
    public void setChangeCheckBoxVisible(boolean isCheckBoxShow) {
        this.isCheckBoxShow = isCheckBoxShow;
        notifyDataSetChanged();
    }
}
