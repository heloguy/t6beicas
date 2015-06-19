package com.matt.t_6beicashelper.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.matt.t_6beicashelper.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Matt on 6/17/15.
 */
public class FlashcardsActivity extends BaseSingleFragmentActivity {
    private EngineDemoFragment mFragment = null;
    private int mCurrentFlashcardIndex = 0;
    private int mButtonToShow = -1;

    private ArrayList<EngineDemoFragment.EngineDemoInfo> mFlashcardItems = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_fragment_layout);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Fragment fragment = new InfoFragment() {
                @Nullable
                @Override
                public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    View rootView = inflater.inflate(R.layout.flashcards_intro, container, false);

                    setHasOptionsMenu(false);
                    return rootView;
                }
            };

            getFragmentManager().beginTransaction()
                    .add(R.id.content_frame, fragment)
                    .commit();
        }
    }

    private ArrayList<EngineDemoFragment.EngineDemoInfo> getFlashcardArray() {
        if (mFlashcardItems != null) {
            return mFlashcardItems;
        }

        mFlashcardItems = new ArrayList<>();

        // Hydraulic

        String[] labels = getResources().getStringArray(R.array.hydraulic_navigation_labels);
        String[] fileNames = getResources().getStringArray(R.array.hydraulic_file_names);

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            String filename = fileNames[i];

            mFlashcardItems.add(
                    EngineDemoFragment.EngineDemoInfo.create()
                            .setFileName(filename)
                            .setTitle(label));
        }

        labels = getResources().getStringArray(R.array.engine_ep_navigation_labels);
        fileNames = getResources().getStringArray(R.array.engine_ep_navigation_file_names);

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            String filename = fileNames[i];

            mFlashcardItems.add(
                    EngineDemoFragment.EngineDemoInfo.create()
                            .setFileName(filename)
                            .setTitle(label));
        }

        labels = getResources().getStringArray(R.array.start_navigation_labels);
        fileNames = getResources().getStringArray(R.array.start_navigation_file_names);

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            String filename = fileNames[i];

            mFlashcardItems.add(
                    EngineDemoFragment.EngineDemoInfo.create()
                            .setFileName(filename)
                            .setTitle(label));
        }

        Collections.shuffle(mFlashcardItems);
        return mFlashcardItems;
    }

    @Override
    protected Fragment getFragment() {
        if (mFragment == null) {
            mFragment = EngineDemoFragment.newInstance(getNextFlashcard());
        }

        return mFragment;
    }

    private Fragment getEngineDemoFragment() {
        return getFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.flashcards_actions, menu);

        switch (getControlButtonId()) {
            case R.id.action_start:
                showStartButton(menu);
                break;
            case R.id.action_next:
                showNextButton(menu);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_start:
                flashcardsStart();
                break;
            case R.id.action_next:
                EngineDemoFragment.EngineDemoInfo flashcard = getNextFlashcard();

                if (flashcard == null) {
                    return true;
                }

                mFragment.setEngineDemoContext(flashcard).resetGauges();
                break;
            default:
                return mFragment.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void flashcardsStart() {
        showLoadingIndicator();
        EngineDemoFragment fragment = (EngineDemoFragment) getFragment();

        fragment.setEngineDemoContext(getNextFlashcard());
        fragment.setHasOptionsMenu(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, getEngineDemoFragment())
                .commit();

        setButtonToShow(R.id.action_next);
    }

    private void setButtonToShow(int id) {
        mButtonToShow = id;
        invalidateOptionsMenu();
    }

    private int getControlButtonId() {
        return mButtonToShow;
    }

    private void showNextButton(Menu menu) {
        menu.findItem(R.id.action_start).setVisible(false);
        menu.findItem(R.id.action_next).setVisible(true);
    }

    private void showStartButton(Menu menu) {
        menu.findItem(R.id.action_start).setVisible(true);
        menu.findItem(R.id.action_next).setVisible(false);
    }

    private EngineDemoFragment.EngineDemoInfo getNextFlashcard() {
        if (mCurrentFlashcardIndex >= getFlashcardArray().size()) {
            mCurrentFlashcardIndex = 0;
        }

        return getFlashcardArray().get(mCurrentFlashcardIndex++);
    }
}
