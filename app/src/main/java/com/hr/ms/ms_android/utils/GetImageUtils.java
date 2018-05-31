package com.hr.ms.ms_android.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Administrator on 2017/5/5.
 * Info：从相册中获取拍照选取图片工具类
 */

public class GetImageUtils {
    private Activity activity = null;

    public GetImageUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 从相册中选中图片，返回图片的本地路径地址
     *
     * @param resultCode：
     * @param data：
     * @return
     */
    public String getPhotosImage(int resultCode, Intent data) {
        String picturePath = "";
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            picturePath = cursor.getString(columnIndex);
            try {
                cursor.close();
            } catch (Exception e) {
                KLog.e(e.getMessage(), e);
            }
        }
        return picturePath;
    }

    /**
     * 拍照获取图片，返回图片的本地路径地址
     *
     * @param resultCode：
     * @param data：
     * @return
     */
    public String getCameraImage(int resultCode, Intent data) {
        String picturePath = "";
        if (resultCode == Activity.RESULT_OK && data != null) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                //检测SD卡是否可用
                return null;
            }
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            File file = new File("/sdcard/sxzxImage/");
            if (!file.exists()) {
                file.mkdirs();
            }
            picturePath = file.getPath() + "/" + name;
            try {
                fout = new FileOutputStream(picturePath);
                fout.flush();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (Exception e) {
                KLog.e(e.getMessage(), e);
            } finally {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return picturePath;
    }

}
