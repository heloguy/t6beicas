package com.matt.t_6beicashelper;

import android.content.Context;

/**
 * Created by Matt on 6/12/15.
 */
public class NavMenuItem implements NavDrawerItem {

    public static final int ITEM_TYPE = 1 ;

    private int id ;
    private String label ;
    private int icon ;
    private boolean updateActionBarTitle ;
    private String mRawFileName;

    private NavMenuItem() {
    }

    public static NavMenuItem create(int id, String label, String rawFileName, String icon, boolean updateActionBarTitle, Context context) {
        NavMenuItem item = new NavMenuItem();
        item.setId(id);
        item.setLabel(label);
        item.setRawFileName(rawFileName);
        item.setUpdateActionBarTitle(updateActionBarTitle);
        return item;
    }

    @Override
    public int getType() {
        return ITEM_TYPE;
    }

    public String getRawFileName() { return this.mRawFileName; };

    public int getId() {
        return id;
    }

    private void setRawFileName(String rawFileName) { this.mRawFileName = rawFileName; }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean updateActionBarTitle() {
        return this.updateActionBarTitle;
    }

    public void setUpdateActionBarTitle(boolean updateActionBarTitle) {
        this.updateActionBarTitle = updateActionBarTitle;
    }
}
