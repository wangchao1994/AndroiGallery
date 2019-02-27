package com.raisesail.gallery.adapter;


import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.raisesail.gallery.R;
import com.raisesail.gallery.adapter.base.CommonAdapter;
import com.raisesail.gallery.adapter.base.ViewHolder;
import com.raisesail.gallery.data.LocalDataUtils;
import com.raisesail.gallery.data.bean.LocalThumb;
import com.raisesail.gallery.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoRecyclerviewAdapter extends CommonAdapter<LocalThumb> {

    private Context mContext;
    private List<LocalThumb> mLocalDataList;
    private List<Boolean> mCheckBooleanList;
    private SelectedStatus mSelectedStatus;
    private CheckBox mCheckbox;

    public PhotoRecyclerviewAdapter(Context context, int layoutId, List<LocalThumb> data_list) {
        super(context, layoutId, data_list);
        mContext = context;
        mLocalDataList = data_list;
        mCheckBooleanList = new ArrayList<>();
        setDefaultStatus();
    }
    //设置Checkbox默认显示
    public void setDefaultStatus(){
        for (int i = 0; i< mLocalDataList.size();i++){
            mCheckBooleanList.add(false);//默认全部不选中
        }
        mSelectedStatus = SelectedStatus.UN_SELECT_ALL;
    }
    @Override
    protected void convert(ViewHolder holder, LocalThumb detailPhotoInfo, final int position) {
        holder.setText(R.id.tv_photo_number,detailPhotoInfo.getPhotoFileNumber());
        ImageView holderView = (ImageView) holder.getView(R.id.iv_photo_detail);
        String photoFile = detailPhotoInfo.getPhotoFilePath();
        if (!"".equals(photoFile) && null != photoFile){
            Glide.with(mContext).load(photoFile).thumbnail(0.1f).into(holderView);//缩略图
        }
        mCheckbox = holder.getView(R.id.checkbox);
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBooleanList.set(position,isChecked);
            }
        });
        LogUtils.d("BOOLEAN","mCheckBooleanList----->"+mCheckBooleanList.size() +"  position==="+position+" mLocalDataList.size()="+mLocalDataList.size());
        mCheckbox.setChecked(mCheckBooleanList.get(position));
    }

    //添加数据
    public void addLocalData(List<LocalThumb> mLocalData){
        mLocalDataList.addAll(mLocalData);
        for (int i = 0; i < mLocalData.size(); i++) {
            mCheckBooleanList.add(false);
        }
        notifyDataSetChanged();
    }
    /**
     * 设置勾选状态显示
     * @param flag
     */
    public void initCheckStatus(boolean flag){
        for (int i = 0; i < mLocalDataList.size(); i++) {
            mCheckBooleanList.set(i,flag);
        }
    }
    /**
     * 删除所有数据
     */
    public void clearAllData(){
        LocalDataUtils.deleteAllLocalData(mLocalDataList,mContext);
        mLocalDataList.clear();
        mCheckBooleanList.clear();
        notifyDataSetChanged();
    }
    /**
     * 删除选中的数据
     */
    public void deleteSelectedData(){
        int y = 0;
        for (int i = 0; i < mLocalDataList.size(); i++) {
            if (mCheckBooleanList.get(i) != null && mCheckBooleanList.get(i)){
                LocalThumb localThumb = mLocalDataList.get(i);
                LocalDataUtils.deleteLocalData(localThumb,mContext);
                mLocalDataList.remove(i);
                mCheckBooleanList.remove(i);
                y++;
                i--;
            }
        }
        notifyDataSetChanged();
        if (y == 0){
            Toast.makeText(mContext, "当前没有选中任何条目!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 全选
     */
    public void selectAllData(){
        mSelectedStatus = SelectedStatus.SELECT_ALL;
        initCheckStatus(true);
        notifyDataSetChanged();
    }

    /**
     * 取消全选
     */
    public void unSelectAllData(){
        mSelectedStatus = SelectedStatus.UN_SELECT_ALL;
        initCheckStatus(false);
        notifyDataSetChanged();
    }


    /**
     * 获取当前全选状态
     */
    public SelectedStatus getSelectedStatus(){
        return mSelectedStatus;
    }

    /**
     * 当前选中状态
     */
    public enum SelectedStatus{
        SELECT_ALL,
        UN_SELECT_ALL,
    }

    public void setLocalDataList(List<LocalThumb> localDataList){
        mLocalDataList = localDataList;
        setDefaultStatus();
    }

    /**
     * 获取当前选中的条目
     * @return
     */
    public int getSelectedCount(){
        int selectedCount = 0;
        if (mCheckBooleanList == null)return selectedCount;
        for (int i = 0; i < mCheckBooleanList.size(); i++) {
            if (mCheckBooleanList.get(i) != null && mCheckBooleanList.get(i)){
                selectedCount ++ ;
            }
        }
        return selectedCount;
    }

}
