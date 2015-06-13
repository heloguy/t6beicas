package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/13/15.
 */
public class T6VoltGauge extends T6SideGauge {
    public T6VoltGauge(Context ctx) {
        super(ctx);
    }

    public T6VoltGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6VoltGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected String getValueConcatUnit() {
        return " VOLTS";
    }
    protected float getLabelToValueSpacing() {
        return 0f;
    }
}
