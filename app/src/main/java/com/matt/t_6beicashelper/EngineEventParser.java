package com.matt.t_6beicashelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.matt.t_6beicashelper.gauges.T6Gauge;
import com.matt.t_6beicashelper.gauges.T6Gauge.EngineEvent;

/**
 * Created by Matt on 6/10/15.
 */
public class EngineEventParser {

    private static final String EXECUTE_JSON_KEY = "execute";
    private static final String INITIALIZE_JSON_KEY = "initialize";
    private static final String EVENTS_JSON_KEY = "events";
    private static final String TARGET_VALUE_JSON_KEY = "targetValue";
    private static final String TIME_TO_TARGET_JSON_KEY = "secondsToTarget";

    public static void parseFile(Context ctx, int fileId, ArrayList<Pair<String, T6Gauge>> gauges) throws IOException, JSONException {
        Resources resources = ctx.getResources();
        String json;

        // Parse entire JSON file first.
        InputStream is = resources.openRawResource(fileId);

        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        json = new String(buffer, "UTF-8");
        JSONObject obj = new JSONObject(json);

        for (Pair<String, T6Gauge> pair : gauges) {
            int initialValue = 0;

            try {
                initialValue = obj.getJSONObject(INITIALIZE_JSON_KEY).getInt(pair.first);
            } catch (JSONException e) {
            }

            JSONArray events = obj.getJSONObject(EXECUTE_JSON_KEY).getJSONObject(pair.first).getJSONArray(EVENTS_JSON_KEY);
            populateGaugeExecuteEvents(events, pair.second);
            setInitialGaugeEvent(initialValue, pair.second);
        }

        is.close();
    }

    private static void setInitialGaugeEvent(int initialValue, T6Gauge gauge){
        gauge.setInitialEvent(new EngineEvent(initialValue, T6Gauge.LIGHTSPEED));
    }

    private static void populateGaugeExecuteEvents(JSONArray events, T6Gauge gauge) throws JSONException {
        for (int i = 0; i < events.length(); i++) {
            JSONObject rawEvent = events.getJSONObject(i);
            EngineEvent event = new EngineEvent(rawEvent.getInt(TARGET_VALUE_JSON_KEY), rawEvent.getInt(TIME_TO_TARGET_JSON_KEY));

            if(event != null) {
                gauge.addEvent(event);
            }
        }
    }
}
