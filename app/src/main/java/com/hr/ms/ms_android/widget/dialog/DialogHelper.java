package com.hr.ms.ms_android.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.better.appbase.recyclerview.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hr.ms.ms_android.R;


public class DialogHelper {
    private Activity    mActivity;
    private AlertDialog mAlertDialog;
    private Toast       mToast;

    public DialogHelper(Activity activity) {
        mActivity = activity;
    }

    /**
     * 弹对话框
     * 
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     */
    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative, final DialogInterface.OnClickListener negativeListener) {
        alert(title, msg, positive, positiveListener, negative, negativeListener, false);
    }

    /**
     * 弹对话框
     * 
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     * @param isCanceledOnTouchOutside
     *            是否可以点击外围框
     */
    public void alert(final String title, final String msg, final String positive,
                      final DialogInterface.OnClickListener positiveListener,
                      final String negative,
                      final DialogInterface.OnClickListener negativeListener,
                      final Boolean isCanceledOnTouchOutside) {
        dismissProgressDialog();

        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                if (title != null) {
                    builder.setTitle(title);
                }
                if (msg != null) {
                    builder.setMessage(msg);
                }
                if (positive != null) {
                    builder.setPositiveButton(positive, positiveListener);
                }
                if (negative != null) {
                    builder.setNegativeButton(negative, negativeListener);
                }
                mAlertDialog = builder.show();
                mAlertDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
                mAlertDialog.setCancelable(false);
            }
        });
    }




    public AlertDialog getDialog(){
        return mAlertDialog;
    }
    /**
     * TOAST
     * 
     * @param msg
     *            消息
     * @param period
     *            时长
     */
    public void toast(final String msg, final int period) {
        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mToast==null){
                    mToast = new Toast(mActivity);
                }
                View view = LayoutInflater.from(mActivity).inflate(
                    R.layout.transient_notification, null);
                TextView tv = (TextView) view.findViewById(android.R.id.message);
                tv.setText(msg);
                mToast.setView(view);
                mToast.setDuration(period);

                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        });
    }

    /**
     * 显示对话框
     *
     * @param showProgressBar
     *            是否显示圈圈
     * @param msg
     *            对话框信息
     */
    public void showProgressDialog(boolean showProgressBar, String msg) {
        showProgressDialog(msg, true, null, showProgressBar);
    }

    /**
     * 显示进度对话框
     *
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg) {
        showProgressDialog(msg, true, null, true);
    }

    /**
     * 显示可取消的进度对话框
     * 
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener,
                                   final boolean showProgressBar) {
        dismissProgressDialog();

        mActivity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }

                mAlertDialog = new GenericProgressDialog(mActivity);
                mAlertDialog.setMessage(msg);
                ((GenericProgressDialog) mAlertDialog).setProgressVisiable(showProgressBar);
                mAlertDialog.setCancelable(cancelable);
                mAlertDialog.setOnCancelListener(cancelListener);

                mAlertDialog.show();

                mAlertDialog.setCanceledOnTouchOutside(false);
            }
        });
    }


    public void dismissProgressDialog() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAlertDialog != null && mAlertDialog.isShowing() && !mActivity.isFinishing()) {
                    mAlertDialog.dismiss();
                    mAlertDialog = null;
                }
            }
        });
    }


    public void showChooseDialog(final String content, final String cancel, final String sure, final View.OnClickListener listener1, final View.OnClickListener listener2) {
        dismissProgressDialog();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity,R.style.dialog);
                mAlertDialog = builder.show();

                WindowManager m = mActivity.getWindowManager();
                Window dialogWindow=mAlertDialog.getWindow();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的
                p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的
                dialogWindow.setAttributes(p);

                View view =  LayoutInflater.from(mActivity).inflate(R.layout.dialog_createcard_choose,null);
                mAlertDialog.setContentView(view);

                TextView next_tv = (TextView) view.findViewById(R.id.next_tv);
                TextView now_tv = (TextView) view.findViewById(R.id.now_tv);
                TextView content_tv = (TextView) view.findViewById(R.id.content_tv);
                next_tv.setText(cancel);
                now_tv.setText(sure);
                content_tv.setText(content);
                next_tv.setOnClickListener(listener1);
                now_tv.setOnClickListener(listener2);

                mAlertDialog.setCancelable(true);
                mAlertDialog.setCanceledOnTouchOutside(true);
//                mAlertDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
            }
        });
    }

    public void showCreateCard(final View.OnClickListener listener1, final View.OnClickListener listener2) {
        dismissProgressDialog();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity == null || mActivity.isFinishing()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity,R.style.dialog);
                mAlertDialog = builder.show();

                WindowManager m = mActivity.getWindowManager();
                Window dialogWindow=mAlertDialog.getWindow();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的
                p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的
                dialogWindow.setAttributes(p);
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//设置可弹出输入法

                View view =  LayoutInflater.from(mActivity).inflate(R.layout.dialog_createcard,null);
                mAlertDialog.setContentView(view);

                TextView cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
                TextView sure_tv = (TextView) view.findViewById(R.id.sure_tv);
                EditText num_et = (EditText) view.findViewById(R.id.num_et);
                cancel_tv.setOnClickListener(listener1);
                sure_tv.setOnClickListener(listener2);

                mAlertDialog.setCancelable(false);
                mAlertDialog.setCanceledOnTouchOutside(false);
//                mAlertDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
            }
        });
    }


    public class ListDataDialogAdapter extends BaseRecyclerViewAdapter<DataBean> {

        public ListDataDialogAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.listdata_dialog_item, null);
        }

        @Override
        protected void convert(BaseViewHolder helper, final DataBean item) {
            helper.setText(R.id.content_tv,item.getContent());
        }

    }

}
