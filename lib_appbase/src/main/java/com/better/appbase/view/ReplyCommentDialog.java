package com.better.appbase.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.better.appbase.R;
import com.better.appbase.utils.KeyboardUtils;
import com.better.appbase.utils.ToastTools;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Edit by lianghuiyong on 2017/8/31.
 */

public class ReplyCommentDialog {
    private Context context;
    private View mRootView;
    private AlertDialog mDialog;
    private TextView tv_publish_comment;
    private EditText et_write_comment;
    private OnCommentTextListener onCommentTextListener = null;

    public ReplyCommentDialog(@NonNull final Context context) {
        this.context = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_comment, null);

        mDialog = new AlertDialog.Builder(context, R.style.dialog).create();

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;//(int) (d.getHeight() * 0.3); // 高度设置为屏幕的
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的
        dialogWindow.setAttributes(params);

        mDialog.setView(mRootView);
        initView();

        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setWindowAnimations(R.style.FromBottomAnimation);
    }

    public void initView() {
        tv_publish_comment = mRootView.findViewById(R.id.tv_publish_comment);
        et_write_comment = mRootView.findViewById(R.id.et_write_comment);

        mRootView.findViewById(R.id.tv_cancel_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mRootView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        KeyboardUtils.hideSoftInput(context, et_write_comment);
                    }
                }, 10);
            }
        });

        et_write_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String curStr = editable.toString().trim();
                int length = curStr.length();
                if (length > 0) {
                    tv_publish_comment.setTextColor(ContextCompat.getColor(context, R.color.main_color));
                } else {
                    tv_publish_comment.setTextColor(ContextCompat.getColor(context, R.color.color_d7d7d7));
                }
                if (length > 500) {
                    ToastTools.showToast("最大支持输入500个字符");
                }

                tv_publish_comment.setEnabled(length > 0);
            }
        });

        tv_publish_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentStr = et_write_comment.getText().toString();
                if (TextUtils.isEmpty(contentStr)) {
                    ToastTools.showToast("评论内容不能为空");
                    return;
                }
                if (contentStr.length() > 500) {
                    ToastTools.showToast("评论的最大长度不能超过500个字符");
                    return;
                }
                if (onCommentTextListener != null) {
                    onCommentTextListener.getCommentText(contentStr);
                }
                dismiss();
                clearComment();
            }
        });
    }

    public void show() {
        mDialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                showKeyboard();
            }
        }, 100);
    }

    public void showKeyboard() {
        if (et_write_comment != null) {
            et_write_comment.postInvalidate();

            //设置可获得焦点
            et_write_comment.setFocusable(true);
            et_write_comment.setFocusableInTouchMode(true);
            //请求获得焦点
            et_write_comment.requestFocus();
            //调用系统输入法
            KeyboardUtils.showSoftInput(context, et_write_comment);
        }
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    // 清空内容
    public void clearComment() {
        if (et_write_comment != null) {
            et_write_comment.setText("");
        }
    }

    public void setOnCommentTextListener(OnCommentTextListener listener) {
        this.onCommentTextListener = listener;
    }

    public interface OnCommentTextListener {
        void getCommentText(String commentText);
    }

}

