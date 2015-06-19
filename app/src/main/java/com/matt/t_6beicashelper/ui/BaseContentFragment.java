package com.matt.t_6beicashelper.ui;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Matt on 6/19/15.
 */
public class BaseContentFragment extends Fragment {
    public interface OnFragmentLoadedListener {
        public void onLoaded();
    }

    private OnFragmentLoadedListener mFragmentLoadedListener;

    protected OnFragmentLoadedListener getFragmentLoadedListener() {
        return mFragmentLoadedListener;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mFragmentLoadedListener = (BaseContentActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement " + OnFragmentLoadedListener.class.getSimpleName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentLoadedListener().onLoaded();
    }
}
