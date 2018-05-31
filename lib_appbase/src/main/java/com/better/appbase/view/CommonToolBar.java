package com.better.appbase.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.better.appbase.R;

/**
 * Created by lianghuiyong on 2017/9/22.
 */

public class CommonToolBar extends CollapsingToolbarLayout {

    private View layoutView;
    private Context context;
    private CommonStatusBar status_bar;
    private FrameLayout layoutBack;
    private AppCompatImageView imageBack;
    private TextView toolbarTitle;
    private FrameLayout layoutMore;
    private TextView moreTitle;
    private TextView textBack;
    private AppCompatImageView moreImage;

    public CommonToolBar(@NonNull Context context) {
        this(context, null);
    }

    public CommonToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs, defStyleAttr);
    }

    public void initCustomAttrs(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonToolBar, defStyleAttr, 0);
        int btnBackColor = typedArray.getColor(R.styleable.CommonToolBar_btn_back_color, ContextCompat.getColor(context, android.R.color.black));
        int textBackColor = typedArray.getColor(R.styleable.CommonToolBar_back_text_color, ContextCompat.getColor(context, android.R.color.black));
        String backContent = typedArray.getString(R.styleable.CommonToolBar_back_content);

        int titleColor = typedArray.getColor(R.styleable.CommonToolBar_title_color, ContextCompat.getColor(context, R.color.color_333333));
        String titleContent = typedArray.getString(R.styleable.CommonToolBar_title_content);

        int moreTitleColor = typedArray.getColor(R.styleable.CommonToolBar_more_color, ContextCompat.getColor(context, R.color.main_color));
        float moreTitleSize = typedArray.getDimensionPixelSize(R.styleable.CommonToolBar_more_size, 0);
        String moreTitleContent = typedArray.getString(R.styleable.CommonToolBar_more_content);
        int moreImageRes = typedArray.getResourceId(R.styleable.CommonToolBar_more_image, 0);
        boolean showBottomLine = typedArray.getBoolean(R.styleable.CommonToolBar_show_bottom_line, false);

        typedArray.recycle();

        layoutView = LayoutInflater.from(context).inflate(R.layout.common_toolbar, this);

        status_bar = layoutView.findViewById(R.id.status_bar);
        layoutBack = layoutView.findViewById(R.id.layout_back);
        imageBack = layoutView.findViewById(R.id.image_back);
        textBack = layoutView.findViewById(R.id.text_back);
        toolbarTitle = layoutView.findViewById(R.id.toolbar_title);
        layoutMore = layoutView.findViewById(R.id.layout_more);
        moreTitle = layoutView.findViewById(R.id.more_title);
        moreImage = layoutView.findViewById(R.id.more_image);

        setTextBackColor(textBackColor);
        setBtnBackColor(btnBackColor);
        setBackTitleContent(backContent);

        setTitleColor(titleColor);
        setTitleContent(titleContent);

        setMoreColor(moreTitleColor);
        setMoreTitleContent(moreTitleContent);
        setMoreTitleSize(moreTitleSize);

        setMoreImage(moreImageRes);

        setBottomLine(showBottomLine);

        isInEditMode();
    }

    public void setBottomLine(boolean isShow) {
        layoutView.findViewById(R.id.bottom_line).setVisibility(isShow ? VISIBLE : GONE);
    }

    public View getBottomLine() {
        return layoutView.findViewById(R.id.bottom_line);
    }

    public View getToolbarTitle() {
        return toolbarTitle;
    }

    public void setStatusBarFontDark(Activity activity) {
        //适配Android状态栏的字体颜色为黑色
        status_bar.setStatusBarDark(activity);
    }

    public void setStatusBarFontLight(Activity activity) {
        //适配Android状态栏的字体颜色为白色
        status_bar.setStatusBarLight(activity);
    }

    public void setBackTitleContent(String titleContent) {
        textBack.setVisibility(TextUtils.isEmpty(titleContent) ? GONE : VISIBLE);
        imageBack.setVisibility(TextUtils.isEmpty(titleContent) ? VISIBLE : GONE);
        textBack.setText(titleContent);
    }

    public void setTextBackColor(int titleColor) {
        if (titleColor == 0) {
            return;
        }
        textBack.setTextColor(titleColor);
    }

    public void setMoreTransitionName(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            moreImage.setTransitionName(name);
        }
    }

    public View getMoreView() {
        return moreImage;
    }

    public void setBtnBackColor(int btnBackColor) {
        if (btnBackColor == 0) {
            return;
        }
        Drawable originalBitmapDrawable = imageBack.getDrawable();
        imageBack.setImageDrawable(tintDrawable(originalBitmapDrawable, ColorStateList.valueOf(btnBackColor)));
    }

    public void setTitleColor(int titleColor) {
        if (titleColor == 0) {
            return;
        }
        toolbarTitle.setTextColor(titleColor);
    }

    public void setTitleContent(String titleContent) {
        toolbarTitle.setVisibility(VISIBLE);
        toolbarTitle.setText(titleContent);
    }


    public void setMoreColor(int titleColor) {
        if (titleColor == 0) {
            return;
        }
        moreTitle.setTextColor(titleColor);

        Drawable originalBitmapDrawable = moreImage.getDrawable();
        moreImage.setImageDrawable(tintDrawable(originalBitmapDrawable, ColorStateList.valueOf(titleColor)));
    }

    public void setMoreTitleContent(String titleContent) {
        layoutMore.setVisibility(TextUtils.isEmpty(titleContent) ? GONE : VISIBLE);
        moreTitle.setVisibility(TextUtils.isEmpty(titleContent) ? GONE : VISIBLE);
        moreTitle.setText(titleContent);
    }

    public void setMoreTitleSize(float titleSize) {
        if (titleSize == 0) {
            return;
        }
        moreTitle.setTextSize(titleSize);
    }

    public void setMoreImage(int resId) {
        if (resId == 0) {
            return;
        }
        moreImage.setVisibility(VISIBLE);
        layoutMore.setVisibility(VISIBLE);
        moreImage.setImageDrawable(context.getResources().getDrawable(resId));
    }

    public void setMoreResource(int resId) {
        if (resId == 0) {
            return;
        }
        moreImage.setVisibility(VISIBLE);
        layoutMore.setVisibility(VISIBLE);
        moreImage.setImageResource(resId);
    }
    public void removeMoreResource() {
        moreImage.setVisibility(GONE);
    }

    public void setBackResource(int resId) {
        if (resId == 0) {
            return;
        }
        imageBack.setVisibility(VISIBLE);
        layoutBack.setVisibility(VISIBLE);
        imageBack.setImageResource(resId);
    }

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public void addOnBackListener(OnClickListener listener) {
        layoutBack.setOnClickListener(listener);
    }

    public void addOnMoreListener(OnClickListener listener) {
        layoutMore.setOnClickListener(listener);
    }
}
