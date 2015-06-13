package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/12/15.
 */
public abstract class T6TextGauge extends T6Gauge {
    public T6TextGauge(Context ctx) {
        super(ctx);
    }

    public T6TextGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public Paint getDefaultTextUnitPaint() {
        Paint p = super.getDefaultTextUnitPaint();
        p.setTextSize(getTextSize());
        p.setTextAlign(getTextAlign());
        return p;
    }

    @Override
    protected Paint getDefaultTextValuePaint() {
        Paint p = super.getDefaultTextValuePaint();
        p.setTextSize(getTextSize());
        p.setTextAlign(getTextAlign());
        return p;
    }

    protected Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    };
    protected abstract String getValueConcatUnit();
    protected String getValuePrependUnit() { return null; };
    protected abstract float getDrawStartX();
    protected abstract float getDrawStartY();
    protected abstract float getLabelToValueSpacing();
    protected abstract float getTextSize();

    public T6TextGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void drawText(Canvas canvas) {
        final String textValue = !TextUtils.isEmpty(mTextValue) ? mTextValue : valueString(mCurrentValue);
        float offset = 0;

        String prepend = getValuePrependUnit();

        if(!TextUtils.isEmpty(prepend)) {
            drawTextOnCanvasWithMagnifier(canvas, prepend, getDrawStartX(), getDrawStartY(), mTextUnitPaint);
            offset = getLabelToValueSpacing();
        }

        drawTextOnCanvasWithMagnifier(canvas, textValue.concat(getValueConcatUnit()), getDrawStartX()+offset, getDrawStartY(), mTextValuePaint);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        //drawBackground(canvas);

        final float scale = Math.min(getWidth(), getHeight());
        canvas.scale(scale, scale);
        canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2) / scale : 0
                , (scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);

        drawText(canvas);
        computeCurrentValue();
    }

    @Override
    protected boolean showText() {
        return true;
    }
}
