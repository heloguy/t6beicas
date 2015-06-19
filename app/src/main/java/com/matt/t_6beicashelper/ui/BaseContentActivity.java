package com.matt.t_6beicashelper.ui;

import android.app.Activity;
import android.view.View;

import com.matt.t_6beicashelper.R;

/**
 * Created by Matt on 6/19/15.
 */
public abstract class BaseContentActivity extends Activity implements BaseContentFragment.OnFragmentLoadedListener {
    protected void showLoadingIndicator() {
        View contentFrame = findViewById(R.id.content_frame);
        View spinner = findViewById(R.id.loading_spinner);

        if(contentFrame == null || spinner == null) {
            throw new NullPointerException(this.toString() + "must contain a view with id 'loading_spinner' and view with id 'content_frame' to extend " + this.getClass().getSimpleName());
        }

        contentFrame.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
    }

    protected void hideLoadingIndicator() {
        View contentFrame = findViewById(R.id.content_frame);
        View spinner = findViewById(R.id.loading_spinner);

        if(contentFrame == null || spinner == null) {
            throw new NullPointerException(this.toString() + "must contain a view with id 'loading_spinner' and view with id 'content_frame' to extend BaseContentActivity");
        }

        spinner.setVisibility(View.GONE);
        contentFrame.setVisibility(View.VISIBLE);
    }

    public void onLoaded() {
        hideLoadingIndicator();
    }
}
