package com.raisesail.gallery.adapter;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raisesail.gallery.R;
import com.raisesail.gallery.adapter.base.CommonAdapter;
import com.raisesail.gallery.adapter.base.ViewHolder;
import com.raisesail.gallery.bean.DetailPhotoInfo;

import java.util.List;


public class PhotoRecyAdapter extends CommonAdapter<DetailPhotoInfo> {

    public PhotoRecyAdapter(Context context, int layoutId, List<DetailPhotoInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, DetailPhotoInfo detailPhotoInfo, int position) {
        holder.setText(R.id.tv_photo_number,detailPhotoInfo.getPhotoNumber());
        ImageView holderView = (ImageView) holder.getView(R.id.iv_photo_detail);
        String photoFile = detailPhotoInfo.getPhotoFile();
        if (!"".equals(photoFile) && null != photoFile){
            Glide.with(mContext).load(photoFile).into(holderView);
        }
    }
}
