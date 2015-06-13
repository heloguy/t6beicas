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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.matt.t_6beicashelper.T6Gauge.EngineEvent;

/**
 * Created by Matt on 6/10/15.
 */
public class EngineEventParser {

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
            JSONArray events = obj.getJSONObject(pair.first).getJSONArray(EVENTS_JSON_KEY);
            populateGaugeEvents(events, pair.second);
        }

        is.close();
    }

    private static void populateGaugeEvents(JSONArray events, T6Gauge gauge) throws JSONException {
        for (int i = 0; i < events.length(); i++) {
            JSONObject rawEvent = events.getJSONObject(i);
            EngineEvent event = new EngineEvent(rawEvent.getInt(TARGET_VALUE_JSON_KEY), rawEvent.getInt(TIME_TO_TARGET_JSON_KEY));

            if(event != null) {
                gauge.addEvent(event);
            }
        }
    }
}
