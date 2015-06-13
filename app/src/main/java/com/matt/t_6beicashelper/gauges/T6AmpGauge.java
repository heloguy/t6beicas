package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Matt on 6/13/15.
 */
public class T6AmpGauge extends T6SideGauge {

    public T6AmpGauge(Context ctx) {
        super(ctx);
    }

    public T6AmpGauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public T6AmpGauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected String getValuePrependUnit() {
        if (mCurrentValue > 0)
            return "+";
        if (mCurrentValue < 0)
            return "-";

        return "";
    }

    protected String getValueConcatUnit() {
        return " AMPS";
    }

    protected float getLabelToValueSpacing() {
        return 0.5f;
    }
}
