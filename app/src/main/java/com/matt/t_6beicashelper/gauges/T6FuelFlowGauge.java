package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/13/15.
 */
public class T6FuelFlowGauge extends T6SideGauge {
    public T6FuelFlowGauge(Context ctx) {
        super(ctx);
    }

    public T6FuelFlowGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6FuelFlowGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected String getValuePrependUnit() {
        return "FF ";
    }

    protected String getValueConcatUnit() { return " PPH"; }
    protected float getLabelToValueSpacing() { return 1.0f; }
    public float getTextSize() {
        return 0.6f;
    }
}
