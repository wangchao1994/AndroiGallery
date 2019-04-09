package com.raisesail.gallery.adapter.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId) {
        this(context, layoutId, new ArrayList<T>());
    }

    public void setNewData(List<T> data){
        this.mDatas = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {

			@Override
			public int getItemViewLayoutId() {
				// TODO Auto-generated method stub
				return layoutId;
			}

			@Override
			public boolean isForViewType(T item, int position) {
				// TODO Auto-generated method stub
				return true;
			}

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }

        });
        
    }
    protected abstract void convert(ViewHolder holder, T t, int position);
}
