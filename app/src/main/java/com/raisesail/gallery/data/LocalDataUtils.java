package com.raisesail.gallery.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import com.raisesail.gallery.data.bean.LocalThumb;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocalDataUtils {
    private static final String TAG = LocalDataUtils.class.getSimpleName();
    /**
     * 获取本地数据库中所有的图片
     *
     * @return list
     */
    public static List<LocalThumb> getAllLocalPhotos(Context context) {
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
            LocalThumb localThumb = new LocalThumb();
            //获取图片的名称
            //materialBean.setPhotoFileNumber(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            localThumb.setPhotoFileNumber("Android");
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)); // 大小
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String path = new String(data, 0, data.length - 1);
//            File file = new File(path);//生成文件
            if (size < 5 * 1024 * 1024) {//<5M
                localThumb.setPhotoFilePath(path);
                list.add(localThumb);
            }
        }
        cursor.close();
        return list;
    }

    /**
     * 删除选中本地数据
     * @param localThumb
     */
    public static void deleteLocalData(LocalThumb localThumb,Context context){
        String photoFilePath = localThumb.getPhotoFilePath();
        Log.d(TAG,"deleteLocalData photoFilePath------>"+photoFilePath);
        File mFile = new File(photoFilePath);
        mFile.delete();
        notificationGallery(mFile,context);
    }

    /**
     * 删除全部数据
     */
    public static void deleteAllLocalData(List<LocalThumb> localThumbs,Context context){
        for (int i = 0; i < localThumbs.size(); i++) {
            Log.d(TAG,"deleteAllLocalData photoFilePath------>"+localThumbs.get(i).getPhotoFilePath());
            File mFile = new File(localThumbs.get(i).getPhotoFilePath());
            mFile.delete();
            notificationGallery(mFile,context);
        }
    }

    private static void notificationGallery(File mFile, Context context){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(mFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static String[] getStorgePaths(Context context) {
        Method mMethodGetPaths = null;
        String[] paths = null;
        //通过调用类的实例mStorageManager的getClass()获取StorageManager类对应的Class对象
        //getMethod("getVolumePaths")返回StorageManager类对应的Class对象的getVolumePaths方法，这里不带参数
        StorageManager mStorageManager = (StorageManager)context
                .getSystemService(Context.STORAGE_SERVICE);//storage
        try {
            mMethodGetPaths = mStorageManager.getClass().getMethod("getVolumePaths");
            paths = (String[]) mMethodGetPaths.invoke(mStorageManager);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return paths;
    }
}
