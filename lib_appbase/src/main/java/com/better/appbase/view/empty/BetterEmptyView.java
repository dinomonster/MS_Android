package com.better.appbase.view.empty;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.better.appbase.R;
import com.better.appbase.imageload.ImageLoadUtils;
import com.better.appbase.utils.NetworkUtils;
import com.better.appbase.view.BetterLoadingView;
import com.better.appbase.view.TextView.SuperTextView;

/**
 * 用于显示界面的 loading、错误信息提示等状态。
 * <p>
 * 提供了一个 LoadingView、一行标题、一行说明文字、一个按钮, 可以使用 show 系列方法控制这些控件的显示内容
 * </p>
 */
public class BetterEmptyView extends FrameLayout {

    private EmptyBuilderControl emptyBuilderControl;

    private BetterLoadingView emptyLoading;
    private TextView emptyLoadingText;

    private ImageView emptyTypeImage;
    private TextView emptyTypeContent;
    private TextView emptyTypeButton;

    public BetterEmptyView(Context context) {
        this(context, null);
    }

    public BetterEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BetterEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EmptyBuilderControl getEmptyBuilderControl() {
        return emptyBuilderControl;
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.better_empty_view, this, true);
        emptyLoading = findViewById(R.id.empty_loading);
        emptyLoadingText = findViewById(R.id.empty_loading_text);

        emptyTypeImage = findViewById(R.id.empty_type_image);
        emptyTypeContent = findViewById(R.id.empty_type_content);
        emptyTypeButton = findViewById(R.id.empty_type_button);

        emptyBuilderControl = new EmptyBuilderControl();
        emptyBuilderControl.setEmptyType(EmptyType.LODING);
        show();

        isInEditMode();
    }

    public void showNetWorkError(OnClickListener onButtonClickListener) {
        showNetWorkError(NetworkUtils.isConnected(getContext()) ? "加载失败" : "无网络连接，请检查网络！", "点击重试", onButtonClickListener);
    }

    public void showNetWorkError(String titleText, OnClickListener onButtonClickListener) {
        showNetWorkError(titleText, "点击重试", onButtonClickListener);
    }

    public void showNetWorkError(String infoContent, String btnText, OnClickListener listener) {
        emptyBuilderControl.setEmptyType(EmptyType.NET_ERROR).getNetErrorEmptyBuilder()
                .setEmptyContent(infoContent)
                .setBtnText(btnText)
                .setBtnListener(listener);
        show();
    }

    public void show() {
        switch (emptyBuilderControl.getEmptyType()) {
            case EmptyType.LODING:
                emptyTypeContent.setVisibility(GONE);
                emptyTypeImage.setVisibility(GONE);
                emptyTypeButton.setVisibility(GONE);

                emptyLoading.setVisibility(VISIBLE);
                emptyLoadingText.setVisibility(VISIBLE);
                emptyLoadingText.setText(emptyBuilderControl.getLoadingEmptyBuilder().getEmptyContent());
                emptyLoadingText.setTextColor(emptyBuilderControl.getLoadingEmptyBuilder().getEmptyContentTextColor());
                break;

            case EmptyType.NODATA:
                emptyLoading.setVisibility(GONE);
                emptyLoadingText.setVisibility(GONE);

                emptyTypeContent.setVisibility(VISIBLE);
                setImageResource(emptyBuilderControl.getNodataEmptyBuilder().getEmptyImage());
                emptyTypeContent.setText(emptyBuilderControl.getNodataEmptyBuilder().getEmptyContent());
                emptyTypeContent.setTextColor(emptyBuilderControl.getNodataEmptyBuilder().getEmptyContentTextColor());
                setButton(emptyBuilderControl.getNodataEmptyBuilder().getBtnText(), emptyBuilderControl.getNodataEmptyBuilder().getBtnListener());
                break;

            case EmptyType.NET_ERROR:
                emptyLoading.setVisibility(GONE);
                emptyLoadingText.setVisibility(GONE);

                emptyTypeContent.setVisibility(VISIBLE);
                setImageResource(emptyBuilderControl.getNetErrorEmptyBuilder().getEmptyImage());
                emptyTypeContent.setText(emptyBuilderControl.getNetErrorEmptyBuilder().getEmptyContent());
                emptyTypeContent.setTextColor(emptyBuilderControl.getNetErrorEmptyBuilder().getEmptyContentTextColor());
                setButton(emptyBuilderControl.getNetErrorEmptyBuilder().getBtnText(), emptyBuilderControl.getNetErrorEmptyBuilder().getBtnListener());
                break;
        }
    }

    /**
     * 隐藏emptyView
     */
    public void hide() {
        setVisibility(GONE);
    }

    public void setImageResource(@DrawableRes int res) {
        ImageLoadUtils.loadImageNoHolder(getContext(), res, emptyTypeImage);
        emptyTypeImage.setVisibility(res != 0 ? VISIBLE : GONE);
    }

    public void setButton(String text, OnClickListener onClickListener) {
        emptyTypeButton.setText(text);
        emptyTypeButton.setVisibility(!TextUtils.isEmpty(text) ? VISIBLE : GONE);
        emptyTypeButton.setOnClickListener(onClickListener);
    }
}
