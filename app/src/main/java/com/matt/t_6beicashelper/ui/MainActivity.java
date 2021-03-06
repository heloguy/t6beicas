package com.matt.t_6beicashelper.ui;

/*******************************************************************************
 * Copyright (c) 2012 Evelina Vrabie
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *******************************************************************************/

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.matt.t_6beicashelper.R;

import java.util.ArrayList;

public class MainActivity extends BaseContentActivity implements ListView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavDrawerAdapter mDrawerAdapter;
    private static final int NAV_MENU_SECTION_ID = 100;
    private static final int NAV_MENU_DEMO_ITEM = 101;
    private static final int NAV_MENU_ABOUT = 102;
    private static final int NAV_MENU_FLASHCARDS = 103;
    private static final int NAV_MENU_RANDOM = 104;
    private static final int DEFAULT_NAV_ITEM = 0;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerAdapter = new NavDrawerAdapter(this, R.layout.drawer_list_item, getNavigationDrawerList());
        mDrawerList.setAdapter(mDrawerAdapter);
        mDrawerList.setOnItemClickListener(this);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.app_name,
                R.string.app_name
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
//                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
//                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(mDrawerAdapter.getItem(DEFAULT_NAV_ITEM));
        }

        mDrawerLayout.openDrawer(mDrawerList);
    }

    // Create sliding navigation drawer items.
    private NavDrawerItem[] getNavigationDrawerList() {
        ArrayList<NavDrawerItem> drawerItems = new ArrayList<>();

        // Miscellaneous

        drawerItems.add(NavMenuItem.create(NAV_MENU_ABOUT, getString(R.string.about_item_title), "", "ic_launcher", false, this));
        drawerItems.add(NavMenuItem.create(NAV_MENU_FLASHCARDS, getString(R.string.flashcards_item_title), "", "ic_launcher", false, this));
//        drawerItems.add(NavMenuItem.create(NAV_MENU_ABOUT, getString(R.string.random_item_title), "", "ic_launcher", false, this));

        // Engine Starts

        String[] drawerTitles = getResources().getStringArray(R.array.start_navigation_labels);
        String[] fileNames = getResources().getStringArray(R.array.start_navigation_file_names);

        drawerItems.add(NavMenuSection.create(NAV_MENU_SECTION_ID, getString(R.string.engine_start_section_title)));

        for (int i = 0; i < drawerTitles.length; i++) {
            String title = drawerTitles[i];
            String filename = fileNames[i];

            drawerItems.add(NavMenuItem.create(NAV_MENU_DEMO_ITEM, title, filename, "ic_launcher", true, this));
        }

        // Engine EP's

        drawerItems.add(NavMenuSection.create(NAV_MENU_SECTION_ID, getString(R.string.engine_eps_section_title)));

        drawerTitles = getResources().getStringArray(R.array.engine_ep_navigation_labels);
        fileNames = getResources().getStringArray(R.array.engine_ep_navigation_file_names);

        for (int i = 0; i < drawerTitles.length; i++) {
            String title = drawerTitles[i];
            String filename = fileNames[i];

            drawerItems.add(NavMenuItem.create(NAV_MENU_DEMO_ITEM, title, filename, "ic_launcher", true, this));
        }

        // Hydraulic Malfunctions

        drawerItems.add(NavMenuSection.create(NAV_MENU_SECTION_ID, getString(R.string.hydraulics_section_title)));

        drawerTitles = getResources().getStringArray(R.array.hydraulic_navigation_labels);
        fileNames = getResources().getStringArray(R.array.hydraulic_file_names);

        for (int i = 0; i < drawerTitles.length; i++) {
            String title = drawerTitles[i];
            String filename = fileNames[i];

            drawerItems.add(NavMenuItem.create(NAV_MENU_DEMO_ITEM, title, filename, "ic_launcher", true, this));
        }

        return drawerItems.toArray(new NavDrawerItem[]{});
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        final NavDrawerItem item = mDrawerAdapter.getItem(position);
        int itemId = item.getId();

        if(itemId == NAV_MENU_SECTION_ID)
            return;

        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
        showLoadingIndicator();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectItem(item);
            }
        }, 350);
    }

    /** Swaps fragments in the main content view */
    private void selectItem(NavDrawerItem item) {
        switch (item.getId()) {
            case NAV_MENU_ABOUT:
                handleAboutSelected();
                break;
            case NAV_MENU_FLASHCARDS:
                handleFlashcardsSelected();
                break;
            case NAV_MENU_RANDOM:
                break;
            case NAV_MENU_DEMO_ITEM:
                handleNavDemoItemSelected((NavMenuItem) item);
                break;
        }
    }

    private void handleFlashcardsSelected() {
        Intent i = new Intent(this, FlashcardsActivity.class);
        startActivity(i);
    }

    private EngineDemoFragment mEngineDemoFragment;

    private void handleNavDemoItemSelected(NavMenuItem item) {
        setTitle(item.getLabel());
        EngineDemoFragment.EngineDemoInfo demoInfo = EngineDemoFragment.EngineDemoInfo.create()
                .setFileName(item.getRawFileName())
                .setTitle(item.getLabel());

        if (mEngineDemoFragment == null) {
            mEngineDemoFragment = EngineDemoFragment.newInstance(demoInfo);
            mEngineDemoFragment.setHasOptionsMenu(true);
        }
        if(!mEngineDemoFragment.isVisible()) {

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, mEngineDemoFragment)
                    .commit();
        } else {
            mEngineDemoFragment.setEngineDemoContext(demoInfo).resetGauges();
        }
    }

    private void handleAboutSelected() {
        AboutFragment fragment = new AboutFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        hideLoadingIndicator();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawers();
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
