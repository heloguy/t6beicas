package com.matt.t_6beicashelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/12/15.
 */
public class T6NpGauge extends T6TextGauge{
    private static final float TEXT_SIZE = 0.5f;

    public T6NpGauge(Context ctx) {
        super(ctx);
    }

    public T6NpGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public Paint getDefaultTextUnitPaint() {
        Paint p = super.getDefaultTextUnitPaint();
        p.setTextSize(TEXT_SIZE);
        return p;
    }

    @Override
    public Paint getDefaultTextValuePaint() {
        Paint p = super.getDefaultTextValuePaint();
        p.setTextSize(TEXT_SIZE);
        return p;
    }

    public T6NpGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void drawText(Canvas canvas) {
        final String textValue = !TextUtils.isEmpty(mTextValue) ? mTextValue : valueString(mCurrentValue);
        final float textUnitWidth = !TextUtils.isEmpty(mTextUnit) ? mTextUnitPaint.measureText(mTextUnit) : 0;

        float startX = 0; //CENTER - textUnitWidth / 2;
        float startY = CENTER+0.15f;

        if(!TextUtils.isEmpty(mTextUnitLabel)) {
            drawTextOnCanvasWithMagnifier(canvas, mTextUnitLabel, startX, startY, mTextUnitPaint);
            startX += 1.0f;
        }

        drawTextOnCanvasWithMagnifier(canvas, textValue.concat("%"), startX, startY, mTextValuePaint);
    }
}
