package com.better.appbase.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.better.appbase.R;
import com.better.appbase.base.BaseCustomView;
import com.socks.library.KLog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;

/**
 * Created by chenliu on 2016/8/26.<br/>
 * 描述：添加圆角支持 on 2016/11/11
 * </br>
 */
public class BetterUpGradeProgressBar extends BaseCustomView {

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressBarType {

        //开始，未下载
        int START = 0;

        //下载
        int LOADING = 1;

        //暂停
        int PAUSE = 2;

        //下载完成
        int FINISH = 3;

        //暂停
        int ERROR = 4;
    }

    private float borderWidth;

    private float maxProgress = 1f;

    private Paint textPaint;

    private Paint bgPaint;

    private Paint pgPaint;

    private String progressText;

    private Rect textRect;

    private RectF bgRectf;

    /**
     * 进度条 bitmap ，包含滑块
     */
    private Bitmap pgBitmap;

    private Canvas pgCanvas;

    private float progress;

    private int progressColor;
    private float textSize;
    private float radius;

    private int color_stop;
    private int color_normal;
    private int color_loading;
    private int color_finish;

    private String title_stop;
    private String title_normal;
    private String title_loading;
    private String title_finish;

    BitmapShader bitmapShader;
    private NumberFormat numberFormat;

    @ProgressBarType
    int progressBarType = ProgressBarType.START;

    public BetterUpGradeProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterUpGradeProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initCustomView(Context context, AttributeSet attrs) {

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BetterUpGradeProgressBar);
        try {
            textSize = typedArray.getDimensionPixelSize(R.styleable.BetterUpGradeProgressBar_textSize, 12);

            radius = typedArray.getDimensionPixelSize(R.styleable.BetterUpGradeProgressBar_radius, 0);
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.BetterUpGradeProgressBar_borderWidth, 1);

            color_stop = typedArray.getColor(R.styleable.BetterUpGradeProgressBar_color_stop, Color.parseColor("#333333"));
            color_normal = typedArray.getColor(R.styleable.BetterUpGradeProgressBar_color_normal, Color.parseColor("#333333"));
            color_loading = typedArray.getColor(R.styleable.BetterUpGradeProgressBar_color_loading, Color.parseColor("#40c4ff"));
            color_finish = typedArray.getColor(R.styleable.BetterUpGradeProgressBar_color_finish, Color.parseColor("#ff9800"));

            title_normal = typedArray.getString(R.styleable.BetterUpGradeProgressBar_title_normal);
            title_loading = typedArray.getString(R.styleable.BetterUpGradeProgressBar_title_loading);
            title_stop = typedArray.getString(R.styleable.BetterUpGradeProgressBar_title_stop);
            title_finish = typedArray.getString(R.styleable.BetterUpGradeProgressBar_title_finish);

            onNormal();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(borderWidth);

        pgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pgPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);

        textRect = new Rect();
        bgRectf = new RectF(borderWidth, borderWidth, mViewWidth - borderWidth, mViewHeight - borderWidth);

        initPgBimap();
    }

    private void initPgBimap() {
        pgBitmap = Bitmap.createBitmap(mViewWidth - (int) borderWidth, mViewHeight - (int) borderWidth, Bitmap.Config.ARGB_8888);
        pgCanvas = new Canvas(pgBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景
        drawBackGround(canvas);

        //进度
        drawProgress(canvas);

        //进度text
        drawProgressText(canvas);

        //变色处理
        drawColorProgressText(canvas);

        isInEditMode();
    }

    private void drawBackGround(Canvas canvas) {
        bgPaint.setColor(progressColor);
        //left、top、right、bottom不要贴着控件边，否则border只有一半绘制在控件内,导致圆角处线条显粗
        canvas.drawRoundRect(bgRectf, radius, radius, bgPaint);
    }

    private void drawProgress(Canvas canvas) {
        pgPaint.setColor(progressColor);

        float right = (progress / maxProgress) * mViewWidth;
        pgCanvas.save();
        pgCanvas.clipRect(0, 0, right, mViewHeight);
        pgCanvas.drawColor(progressColor);
        pgCanvas.restore();

        //控制显示区域
        bitmapShader = new BitmapShader(pgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        pgPaint.setShader(bitmapShader);
        canvas.drawRoundRect(bgRectf, radius, radius, pgPaint);
    }

    /**
     * 进度提示文本
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        textPaint.setColor(progressColor);
        textPaint.getTextBounds(progressText, 0, progressText.length(), textRect);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (mViewWidth - tWidth) / 2;
        float yCoordinate = (mViewHeight + tHeight) / 2;
        canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
    }

    /**
     * 变色处理
     *
     * @param canvas
     */
    private void drawColorProgressText(Canvas canvas) {
        textPaint.setColor(Color.WHITE);
        int tWidth = textRect.width();
        int tHeight = textRect.height();
        float xCoordinate = (mViewWidth - tWidth) / 2;
        float yCoordinate = (mViewHeight + tHeight) / 2;
        float progressWidth = (progress / maxProgress) * mViewWidth;
        if (progressWidth > xCoordinate) {
            canvas.save();
            float right = Math.min(progressWidth, xCoordinate + tWidth * 1.1f);
            canvas.clipRect(xCoordinate, 0, right, mViewHeight);
            canvas.drawText(progressText, xCoordinate, yCoordinate, textPaint);
            canvas.restore();
        }
    }

    public void setProgress(float progress) {
        if (progress == maxProgress) {
            onFinish();
        } else {
            onLoading();
        }

        this.progress = progress < maxProgress ? progress : maxProgress;
        invalidate();
    }

    public void onNormal() {
        progressBarType = ProgressBarType.START;
        progressText = title_normal == null ? "立即下载" : title_normal;
        progressColor = color_normal;
        invalidate();
    }

    public void onLoading() {
        progressBarType = ProgressBarType.LOADING;

        progressText = (title_loading == null ? "正在下载" : title_loading) + numberFormat.format(progress);
        progressColor = color_loading;
        invalidate();
    }

    public void onError(String value) {
        progressBarType = ProgressBarType.ERROR;

        progressText =value;
        progressColor = ContextCompat.getColor(getContext(), R.color.main_red);
        invalidate();
    }

    public void onPause() {

        KLog.e("onPause");

        progressBarType = ProgressBarType.PAUSE;

        progressText = title_stop == null ? "继续下载" : title_stop;
        progressColor = color_stop;
        invalidate();
    }

    public void onFinish() {
        progressBarType = ProgressBarType.FINISH;

        progressText = title_finish == null ? "下载完成！" : title_finish;
        progressColor = color_finish;
        invalidate();
    }

    public void pause() {

        KLog.e("pause");
        if (!(progressBarType == ProgressBarType.FINISH)) {
            onPause();
        }
    }

    public int getProgressBarType() {
        return progressBarType;
    }

    public float getProgress() {
        return progress;
    }
}
