package com.matt.t_6beicashelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Matt on 6/12/15.
 */
public class T6TextGauge extends T6Gauge {
    public T6TextGauge(Context ctx) {
        super(ctx);
    }

    public T6TextGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6TextGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
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
