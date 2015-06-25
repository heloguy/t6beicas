package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Matt on 6/12/15.
 */
public class T6NpGauge extends T6TextGauge {
    public T6NpGauge(Context ctx) {
        super(ctx);
    }

    public T6NpGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6NpGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }


    protected String getValueConcatUnit() {
        return "%";
    }

    protected float getDrawStartX() {
        return 0f;
    }

    @Override
    protected String getValuePrependUnit() {
        return "NP: ";
    }

    protected float getDrawStartY() {
        return CENTER + 0.15f;
    }

    protected float getLabelToValueSpacing() {
        return 1.0f;
    }

    protected Paint.Align getTextAlign() {
        return Paint.Align.CENTER;
    }

    protected float getTextSize() {
        return 0.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinearLayout parent = (LinearLayout) getParent();
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        parent.setLayoutParams(params);
    }
}
