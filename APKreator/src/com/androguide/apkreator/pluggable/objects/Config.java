package com.androguide.apkreator.pluggable.objects;

import java.util.ArrayList;

/**
 * Created by androguide on 8/1/13.
 */
public class Config {

    private String appName, appColor;
    private int tabsAmount;
    private ArrayList<String> tabs = new ArrayList<String>();

    public Config() {
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppColor() {
        return appColor;
    }

    public void setAppColor(String appColor) {
        this.appColor = appColor;
    }

    public int getTabsAmount() {
        return tabsAmount;
    }

    public void setTabsAmount(int tabsAmount) {
        this.tabsAmount = tabsAmount;
    }

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<String> tabs) {
        this.tabs = tabs;
    }
}
