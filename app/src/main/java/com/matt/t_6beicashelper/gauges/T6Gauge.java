package com.matt.t_6beicashelper.gauges;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import com.matt.gaugeview.GaugeView;

import java.util.LinkedList;

public class T6Gauge extends GaugeView {
    private static final float SCALE_START_ANGLE = 50.0f;
    private static final float NEEDLE_WIDTH = 0.025f;
    private static final float NEEDLE_HEIGHT = 0.2f;
    private final LinkedList<EngineEvent> mEngineEvents = new LinkedList<EngineEvent>();

    public T6Gauge(Context ctx) {
        super(ctx);
    }

    public T6Gauge(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        this.SHOW_TEXT = true;
    }

    public T6Gauge(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }


    @Override
    public Paint getDefaultFacePaint() {
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new RadialGradient(0.5f, 0.5f, getFaceRect().width() / 2, new int[]{Color.rgb(0, 0, 0), Color.rgb(0, 0, 0),
                Color.rgb(0, 0, 0)}, new float[]{0.5f, 0.96f, 0.99f}, Shader.TileMode.MIRROR));
        return paint;
    }

    @Override
    public boolean getShowOuterRim(TypedArray a) {
        return false;
    }

    @Override
    public boolean getShowInnerRim(TypedArray a) {
        return false;
    }

    @Override
    public float getScaleStartAngle(TypedArray a) {
        return SCALE_START_ANGLE;
    }

    @Override
    public Paint getDefaultNeedleLeftPaint() {
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        return paint;
    }

    @Override
    public Paint getDefaultNeedleRightPaint() {
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setShadowLayer(0.01f, 0.005f, -0.005f, Color.argb(127, 0, 0, 0));
        return paint;
    }

    @Override
    public float getDefaultNeedleHeight(TypedArray a) {
        return NEEDLE_HEIGHT;
    }

    @Override
    public float getDefaultNeedleWidth(TypedArray a) {
        return NEEDLE_WIDTH;
    }

    @Override
    public float getDefaultNeedleY() {
        return 0.38f;
    }

    public void addEvent(EngineEvent e) {
        if (e != null) {
            mEngineEvents.add(e);
        }
    }

    private EngineEvent getNextEvent() {
        if (!mEngineEvents.isEmpty()) {
            return mEngineEvents.removeFirst();
        }

        return null;
    }

    private void executeNextEvent() {
//        Log.i("Time", Float.toString((System.currentTimeMillis() - startTime) / 1000.0f));

        EngineEvent e = getNextEvent();

        if (e == null) {
            return;
        }

//        Log.i(getTextUnitLabel(), String.format("Target Value: %d, Time to target: %f", e.targetValue, e.timeToTarget));
        executeNextAction(e);
    }

    private void executeNextAction(EngineEvent e) {
        this.setTargetValue(e.targetValue);
        this.setTimeToTarget(e.timeToTarget);
    }

    public void start() {
        EventReceivedListener listener = new EventReceivedListener() {
            @Override
            public void notifyOfEvent(ChangeEvent event) {
                switch(event.name) {
                    case TARGET_REACHED:
                        executeNextEvent();
                }
            }
        };

        setEventListener(listener);
        executeNextEvent();
    }

    private static final float LIGHTSPEED = 0.00000000001f;

    public void reset() {
        clearEvents();
        setTimeToTarget(LIGHTSPEED);
        addEvent(new EngineEvent(0, LIGHTSPEED));
    }

    public void clearEvents() {
        if (mEngineEvents == null) {
            return;
        }

        mEngineEvents.clear();
    }

    public static class EngineEvent {
        int targetValue;
        float timeToTarget;

        public EngineEvent(int value, float timeToTgt) {
            targetValue = value;
            timeToTarget = timeToTgt * 1000;
        }
    }
}
