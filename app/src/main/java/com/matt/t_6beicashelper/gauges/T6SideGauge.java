package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/13/15.
 */
public abstract class T6SideGauge extends T6TextGauge {
    public T6SideGauge(Context ctx) {
        super(ctx);
    }

    public T6SideGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6SideGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected float getDrawStartX() { return -1.0f; }
    protected float getDrawStartY() { return CENTER+0.15f; }
    public float getTextSize() { return 0.6f; }
}
