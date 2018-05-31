package com.hr.ms.ms_android.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hr.ms.ms_android.R;


/**
 *  Created by 小李
 * Info：获取图片(从相册选取、拍照)
 */

public class GetImageDialog extends Dialog implements View.OnClickListener {
    private TextView tv_photos = null; // 从相册中选取
    private TextView tv_camera = null; // 拍照

    private OnGetImageListener listener = null;

    public GetImageDialog(Context context) {
        this(context, R.style.dialog_style);
    }

    public GetImageDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_get_image);
        tv_photos = findViewById(R.id.tv_photos);
        tv_camera = findViewById(R.id.tv_camera);

        tv_photos.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_photos:
                // 从相册选取
                if (listener != null) {
                    listener.fromPhotos();
                }
                break;
            case R.id.tv_camera:
                // 拍照
                if (listener != null) {
                    listener.fromCamera();
                }
                break;
        }
        dismiss();
    }

    public void setOnGetImageListener(OnGetImageListener listener) {
        this.listener = listener;
    }

    public interface OnGetImageListener {
        void fromPhotos();

        void fromCamera();
    }

}
