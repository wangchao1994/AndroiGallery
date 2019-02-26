package com.raisesail.gallery.adapter;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raisesail.gallery.R;
import com.raisesail.gallery.adapter.base.CommonAdapter;
import com.raisesail.gallery.adapter.base.ViewHolder;
import com.raisesail.gallery.data.bean.LocalThumb;

import java.util.List;


public class PhotoRecyAdapter extends CommonAdapter<LocalThumb> {

    public PhotoRecyAdapter(Context context, int layoutId, List<LocalThumb> datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, LocalThumb detailPhotoInfo, int position) {
        holder.setText(R.id.tv_photo_number,detailPhotoInfo.getPhotoFileNumber());
        ImageView holderView = (ImageView) holder.getView(R.id.iv_photo_detail);
        String photoFile = detailPhotoInfo.getPhotoFilePath();
        if (!"".equals(photoFile) && null != photoFile){
            Glide.with(mContext).load(photoFile).into(holderView);
        }
    }
}
