package com.matt.t_6beicashelper.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.matt.t_6beicashelper.EngineEventParser;
import com.matt.t_6beicashelper.R;
import com.matt.t_6beicashelper.gauges.T6AmpGauge;
import com.matt.t_6beicashelper.gauges.T6FuelFlowGauge;
import com.matt.t_6beicashelper.gauges.T6Gauge;
import com.matt.t_6beicashelper.gauges.T6NpGauge;
import com.matt.t_6beicashelper.gauges.T6VoltGauge;

import java.util.ArrayList;

/**
 * Created by Matt on 6/11/15.
 */
public class EngineDemoFragment extends Fragment implements T6Gauge.GaugeActionsCompleteObserver {
    public static class EngineDemoInfo {
        public String fileName;
        public String title;

        private EngineDemoInfo() {

        }

        public static EngineDemoInfo create() {
            return new EngineDemoInfo();
        }

        public EngineDemoInfo setFileName(String name) {
            fileName = name;
            return this;
        }

        public EngineDemoInfo setTitle(String title) {
            this.title = title;
            return this;
        }
    }

    public static final String ENGINE_RAW_FILE = "engineDemoFilename";

    private static final String ITT_GAUGE_JSON_KEY = "itt";
    private static final String N1_GAUGE_JSON_KEY = "n1";
    private static final String TORQUE_GAUGE_JSON_KEY = "torque";
    private static final String OIL_PRESS_GAUGE_JSON_KEY = "oilPress";
    private static final String OIL_TEMP_GAUGE_JSON_KEY = "oilTemp";
    private static final String HYD_PRESS_GAUGE_JSON_KEY = "hydPress";
    private static final String PROP_RPM_GAUGE_JSON_KEY = "propRpm";
    private static final String FUEL_FLOW_GAUGE_JSON_KEY = "fuelFlow";
    private static final String VOLT_GAUGE_JSON_KEY = "voltage";
    private static final String AMP_GAUGE_JSON_KEY = "amps";
    private boolean mIsComplete = false;


    private T6Gauge torque;
    private T6Gauge oilPress;
    private T6Gauge itt;
    private T6Gauge oilTemp;
    private T6Gauge n1;
    private T6Gauge hydPress;
    private T6NpGauge propRpm;
    private T6FuelFlowGauge ffGauge;
    private T6VoltGauge voltGauge;
    private T6AmpGauge ampGauge;
    private final ArrayList<Pair<String, T6Gauge>> gauges = new ArrayList<>();

    private AlertDialog mDialog;
    private EngineDemoInfo mDemoInfo;

    public EngineDemoFragment() {
    }

    public EngineDemoFragment(EngineDemoInfo demoInfo) {
        mDemoInfo = demoInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gauges, container, false);

        torque = (T6Gauge) rootView.findViewById(R.id.torque);
        oilPress = (T6Gauge) rootView.findViewById(R.id.oilpress);
        itt = (T6Gauge) rootView.findViewById(R.id.itt);
        oilTemp = (T6Gauge) rootView.findViewById(R.id.oiltemp);
        n1 = (T6Gauge) rootView.findViewById(R.id.n1);
        hydPress = (T6Gauge) rootView.findViewById(R.id.hydpress);
        propRpm = (T6NpGauge) rootView.findViewById(R.id.prop_rpm);
        ffGauge = (T6FuelFlowGauge) rootView.findViewById(R.id.fuel_flow);
        voltGauge = (T6VoltGauge) rootView.findViewById(R.id.voltage);
        ampGauge = (T6AmpGauge) rootView.findViewById(R.id.amps);

        gauges.add(new Pair<>(ITT_GAUGE_JSON_KEY, itt));
        gauges.add(new Pair<>(N1_GAUGE_JSON_KEY, n1));
        gauges.add(new Pair<>(TORQUE_GAUGE_JSON_KEY, torque));
        gauges.add(new Pair<>(OIL_PRESS_GAUGE_JSON_KEY, oilPress));
        gauges.add(new Pair<>(OIL_TEMP_GAUGE_JSON_KEY, oilTemp));
        gauges.add(new Pair<>(HYD_PRESS_GAUGE_JSON_KEY, hydPress));
        gauges.add(new Pair<>(PROP_RPM_GAUGE_JSON_KEY, (T6Gauge) propRpm));
        gauges.add(new Pair<>(FUEL_FLOW_GAUGE_JSON_KEY, (T6Gauge) ffGauge));
//        gauges.add(new Pair<>(VOLT_GAUGE_JSON_KEY, (T6Gauge) voltGauge));
//        gauges.add(new Pair<>(AMP_GAUGE_JSON_KEY, (T6Gauge) ampGauge));

        resetGauges();
        setHasOptionsMenu(true);
        return rootView;
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mDemoInfo.title);
        mDialog = builder.create();
    }

    private void showPlayButton(Menu menu) {
        menu.findItem(R.id.action_play).setVisible(true);
        menu.findItem(R.id.action_restart).setVisible(false);
        menu.findItem(R.id.action_stop).setVisible(false);
    }

    private void showRestartButton(Menu menu) {
        menu.findItem(R.id.action_play).setVisible(false);
        menu.findItem(R.id.action_restart).setVisible(true);
        menu.findItem(R.id.action_stop).setVisible(false);
    }

    private void showStopButton(Menu menu) {
        menu.findItem(R.id.action_play).setVisible(false);
        menu.findItem(R.id.action_restart).setVisible(false);
        menu.findItem(R.id.action_stop).setVisible(true);
    }

    public void resetGauges() {
        mCompleteGauges = 0;
        mIsComplete = false;

        for (Pair<String, T6Gauge> pair : gauges) {
            pair.second.resetGauge(null);
        }

        int resourceId = getResources().getIdentifier(mDemoInfo.fileName, "raw", getActivity().getPackageName());

        try {
            EngineEventParser.parseFile(getActivity(), resourceId, gauges);
        } catch (Exception ex) {
            Log.i("Err", "Something went wrong!");
        }

        for (Pair<String, T6Gauge> pair : gauges) {
            pair.second.initialize(null);
        }

        setButtonToShow(R.id.action_play);
        getActivity().invalidateOptionsMenu();
        createDialog();
    }

    public EngineDemoFragment setEngineDemoContext(EngineDemoInfo context) {
        mDemoInfo = context;
        return this;
    }

    public void startGauges() {
        setButtonToShow(R.id.action_stop);
        getActivity().invalidateOptionsMenu();

        for (Pair<String, T6Gauge> pair : gauges) {
            pair.second.start(EngineDemoFragment.this);
        }
    }

    private boolean allGaugesComplete() {
        return mCompleteGauges == gauges.size();
    }

    private int mCompleteGauges;

    @Override
    public void onGaugeActionsComplete() {
        mCompleteGauges++;
        Log.i("Progress", Integer.toString(mCompleteGauges));

        if (allGaugesComplete()) {
            setButtonToShow(R.id.action_restart);
            getActivity().invalidateOptionsMenu();
        }
    }

    private int mButtonToShow;

    private void setButtonToShow(int id) {
        mButtonToShow = id;
        getActivity().invalidateOptionsMenu();
    }

    private int getControlButtonId() {
        return mButtonToShow;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.engine_demo_actions, menu);

        switch(getControlButtonId()) {
            case R.id.action_play:
                showPlayButton(menu);
                break;
            case R.id.action_restart:
                showRestartButton(menu);
                break;
            case R.id.action_stop:
                showStopButton(menu);
                break;
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_play:
                startGauges();
                return true;
            case R.id.action_stop:
            case R.id.action_restart:
                resetGauges();
                return true;
            case R.id.action_info:
                mDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
