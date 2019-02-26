package com.raisesail.gallery.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.raisesail.gallery.data.bean.LocalThumb;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LocalDataUtils {
    /**
     * 获取本地所有的图片
     *
     * @return list
     */
    public static List<LocalThumb> getAllLocalPhotos(Context context/*, int uid*/) {
       // long totalUploadCount = MPSManager.getInstance(context).getMpsRecordCount(uid) + 1000;
        List<LocalThumb> list = new ArrayList<>();
        String[] projection = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };
        //全部图片
        String where = MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=?";
        //指定格式
        String[] whereArgs = {"image/jpeg", "image/png", "image/jpg"};
        //查询
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where, whereArgs,
                MediaStore.Images.Media.DATE_MODIFIED + " desc ");
        if (cursor == null) {
            return list;
        }
        //遍历
        while (cursor.moveToNext()) {
            LocalThumb materialBean = new LocalThumb();
            //获取图片的名称
            //materialBean.setPhotoFileNumber(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            materialBean.setPhotoFileNumber("");
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)); // 大小
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String path = new String(data, 0, data.length - 1);
            File file = new File(path);
            if (size < 5 * 1024 * 1024) {//<5M
                long time = file.lastModified();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String t = format.format(time);
//                materialBean.setTime(t);
//                materialBean.setLogo(path);
//                materialBean.setFilePath(path);
//                materialBean.setFileSize(size);
//                materialBean.setChecked(false);
//                materialBean.setFileType(6);
//                materialBean.setFileId(totalUploadCount++);
//                materialBean.setUploadedSize(0);
//                materialBean.setTimeStamps(System.currentTimeMillis() + "");
                materialBean.setPhotoFilePath(path);
                list.add(materialBean);
            }
        }
        cursor.close();
        return list;
    }
}
