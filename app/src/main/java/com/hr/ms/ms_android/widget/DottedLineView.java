package com.hr.ms.ms_android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hr.ms.ms_android.R;


/**
 * Created by Dino on 2018/3/8.
 */

public class DottedLineView extends View {
    // 创建画笔
    private Paint mPaint; // 画笔
    private float radius; // 圆的半径
    private float dividerWidth; // 圆的间距
    private Context mContext;
    private Boolean isHaveArrow = false;
    private int color; // 圆点的颜色

    public DottedLineView(Context context) {
        this(context, null);
    }


    public DottedLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DottedLineView, defStyleAttr, 0);
        isHaveArrow = a.getBoolean(R.styleable.DottedLineView_isHaveArrow, false);
        color = a.getColor(R.styleable.DottedLineView_color, getResources().getColor(R.color.main_color));
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 限死高度为space_10(实际是10dp)，如果要需要动态设置，可以通过attrs参数获取设置。
//        setMeasuredDimension(widthMeasureSpec, (int) mContext.getResources().getDimension(R.dimen.space_10));
        setMeasuredDimension((int) mContext.getResources().getDimension(R.dimen.sx_dp5), heightMeasureSpec);
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        mPaint = new Paint();
        radius =  mContext.getResources().getDimension(R.dimen.sx_dp1);
        dividerWidth = mContext.getResources().getDimension(R.dimen.sx_dp6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(color);// 设置颜色
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth() / 2;
        // int maxCount = measuredWidth / (dividerWidth + radius * 2);
        for (float i = radius; i < measuredHeight-10; ) {
            canvas.drawCircle(measuredWidth, i, radius, mPaint);// 小圆
            i += dividerWidth;
        }
//        mPaint.setStrokeWidth(15);
        if(isHaveArrow) {
            canvas.drawLine(0, measuredHeight - 10, measuredWidth, measuredHeight, mPaint);
            canvas.drawLine(getMeasuredWidth(), measuredHeight - 10, measuredWidth, measuredHeight, mPaint);
        }
    }
}
