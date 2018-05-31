package com.better.appbase.view.empty;

import android.view.View;

public class EmptyBuilder {
    @EmptyType private int emptyType;
    private int emptyImage;

    private String emptyContent;
    private int emptyContentTextColor;

    private String btnText;
    private View.OnClickListener btnListener;

    public int getEmptyType() {
        return emptyType;
    }

    public EmptyBuilder setEmptyType(int emptyType) {
        this.emptyType = emptyType;
        return this;
    }

    public int getEmptyImage() {
        return emptyImage;
    }

    public EmptyBuilder setEmptyImage(int emptyImage) {
        this.emptyImage = emptyImage;
        return this;
    }

    public String getEmptyContent() {
        return emptyContent;
    }

    public EmptyBuilder setEmptyContent(String emptyContent) {
        this.emptyContent = emptyContent;
        return this;
    }

    public int getEmptyContentTextColor() {
        return emptyContentTextColor;
    }

    public EmptyBuilder setEmptyContentTextColor(int emptyContentTextColor) {
        this.emptyContentTextColor = emptyContentTextColor;
        return this;
    }

    public String getBtnText() {
        return btnText;
    }

    public EmptyBuilder setBtnText(String btnText) {
        this.btnText = btnText;
        return this;
    }

    public View.OnClickListener getBtnListener() {
        return btnListener;
    }

    public EmptyBuilder setBtnListener(View.OnClickListener btnListener) {
        this.btnListener = btnListener;
        return this;
    }
}
