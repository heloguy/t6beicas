package com.matt.t_6beicashelper;

/**
 * Created by Matt on 6/12/15.
 */
public interface NavDrawerItem {
    public int getId();
    public String getLabel();
    public int getType();
    public boolean isEnabled();
    public boolean updateActionBarTitle();
}
