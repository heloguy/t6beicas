package com.matt.t_6beicashelper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matt.t_6beicashelper.R;

/**
 * Created by Matt on 6/16/15.
 */
public class AboutFragment extends InfoFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);

        setHasOptionsMenu(false);
        return rootView;
    }
}
