package com.ngallazzi.myinstaggallery.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ngallazzi.myinstaggallery.R;

public class DynamicHeightImageView extends android.support.v7.widget.AppCompatImageView {
    Paint borderPaint;
    private float mAspectRatio = 1.0f;
    private final float STROKE_WIDTH = 10.0f;
    private final String TAG = DynamicHeightImageView.class.getSimpleName();
    private Context mContext;
    private int measuredWidth,forecastHeight;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DynamicHeightImageView,
                0, 0);
        mContext = context;
        initBorderPaint(a);
    }

    public void initBorderPaint(TypedArray a){
        borderPaint = new Paint();
        int borderColor = a.getColor(R.styleable.DynamicHeightImageView_borderColor, Color.parseColor("#212121"));
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(STROKE_WIDTH);
    }

    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        forecastHeight = (int) (measuredWidth / mAspectRatio);
        setMeasuredDimension(measuredWidth,forecastHeight );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, measuredWidth, forecastHeight, borderPaint);
    }
}
