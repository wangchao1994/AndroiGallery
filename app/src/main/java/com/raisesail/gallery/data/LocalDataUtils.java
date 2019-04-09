package com.raisesail.gallery.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.util.Log;

import com.raisesail.gallery.data.bean.LocalFolder;
import com.raisesail.gallery.data.bean.LocalThumb;
import com.raisesail.gallery.utils.LogUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LocalDataUtils {
    private static final String TAG = LocalDataUtils.class.getSimpleName();
    /**
     * 获取本地路径的所有文件夹
     * @return
     */
    public static List<LocalFolder> getAllLocalFolder(String path){
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<LocalFolder> mFolderList = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            LocalFolder localFolder = new LocalFolder();
            localFolder.setFolderPath(files[i].getAbsolutePath());
            localFolder.setFolderName(files[i].getName());
            mFolderList.add(localFolder);
        }
        return mFolderList;
    }
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
            localThumb.setPhotoFileNumber(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)); // 大小
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String path = new String(data, 0, data.length - 1);
            Log.d(TAG,"deleteLocalData photoFilePath------>"+path);
            if (size < 5 * 1024 * 1024) {//<5M
                localThumb.setPhotoFilePath(path);
                list.add(localThumb);
            }
        }
        cursor.close();
        return list;
    }

    public static List<LocalThumb> getImagePathFromStorage(String mFolderPath) {
        // 图片列表
        List<LocalThumb> imagePathList = new ArrayList<LocalThumb>();
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(mFolderPath);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                LocalThumb localThumb = new LocalThumb();
                localThumb.setPhotoFilePath(file.getPath());
                localThumb.setPhotoFileNumber(file.getName());
                imagePathList.add(localThumb);
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }


    private static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("jpeg") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
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
}
