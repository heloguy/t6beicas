package com.matt.t_6beicashelper;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;

import java.util.ArrayList;

/**
 * Created by Matt on 6/11/15.
 */
public class EngineDemoFragment extends Fragment {
    public static final String ENGINE_RAW_FILE = "engineDemoFilename";
//    public static final String TITLE = "title";

    private static final String ITT_GAUGE_JSON_KEY = "itt";
    private static final String N1_GAUGE_JSON_KEY = "n1";
    private static final String TORQUE_GAUGE_JSON_KEY = "torque";
    private static final String OIL_PRESS_GAUGE_JSON_KEY = "oilPress";
    private static final String OIL_TEMP_GAUGE_JSON_KEY = "oilTemp";
    private static final String HYD_PRESS_GAUGE_JSON_KEY = "hydPress";
    private static final String PROP_RPM_GAUGE_JSON_KEY = "propRpm";

    private T6Gauge torque;
    private T6Gauge oilPress;
    private T6Gauge itt;
    private T6Gauge oilTemp;
    private T6Gauge n1;
    private T6Gauge hydPress;
    private T6NpGauge propRpm;

    private final ArrayList<Pair<String, T6Gauge>> gauges = new ArrayList<>();

//    private String mRawFileName;

    public EngineDemoFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gauges, container, false);

        String rawFileName = getArguments().getString(ENGINE_RAW_FILE);

        torque = (T6Gauge) rootView.findViewById(R.id.torque);
        oilPress = (T6Gauge) rootView.findViewById(R.id.oilpress);
        itt = (T6Gauge) rootView.findViewById(R.id.itt);
        oilTemp = (T6Gauge) rootView.findViewById(R.id.oiltemp);
        n1 = (T6Gauge) rootView.findViewById(R.id.n1);
        hydPress = (T6Gauge) rootView.findViewById(R.id.hydpress);
        propRpm = (T6NpGauge) rootView.findViewById(R.id.prop_rpm);

        gauges.add(new Pair<>(ITT_GAUGE_JSON_KEY, itt));
        gauges.add(new Pair<>(N1_GAUGE_JSON_KEY, n1));
        gauges.add(new Pair<>(TORQUE_GAUGE_JSON_KEY, torque));
        gauges.add(new Pair<>(OIL_PRESS_GAUGE_JSON_KEY, oilPress));
        gauges.add(new Pair<>(OIL_TEMP_GAUGE_JSON_KEY, oilTemp));
        gauges.add(new Pair<>(HYD_PRESS_GAUGE_JSON_KEY, hydPress));
        gauges.add(new Pair<>(PROP_RPM_GAUGE_JSON_KEY, (T6Gauge) propRpm));

        startGauges(rawFileName);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        resetGauges();
        super.onPause();
    }

    private void resetGauges() {
        for(Pair<String, T6Gauge> pair : gauges) {
            pair.second.reset();
        }
    }

    public void startGauges(String rawFileName) {
        resetGauges();
        int resourceId = getResources().getIdentifier(rawFileName, "raw", getActivity().getPackageName());

        try {
            EngineEventParser.parseFile(getActivity(), resourceId, gauges);
        } catch (Exception ex) {
            Log.i("Err", "Something went wrong!");
        }

        for(Pair<String, T6Gauge> pair : gauges) {
            pair.second.start();
        }
    }
}
