/**   Copyright (C) 2013  Louis Teboul (a.k.a Androguide)
 *
 *    admin@pimpmyrom.org  || louisteboul@gmail.com
 *    http://pimpmyrom.org || http://androguide.fr
 *    71 quai Clémenceau, 69300 Caluire-et-Cuire, FRANCE.
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License along
 *      with this program; if not, write to the Free Software Foundation, Inc.,
 *      51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 **/

package com.androguide.apkreator.pluggable.objects;

import java.util.ArrayList;

public class Config {

    private String appName, appColor, website, xda, twitter, gplus, facebook, youtubeUser, welcomeTitle, welcomeDesc;
    private int tabsAmount, cpuControlPos, phoneGapFragmentPos;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getXda() {
        return xda;
    }

    public void setXda(String xda) {
        this.xda = xda;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGplus() {
        return gplus;
    }

    public void setGplus(String gplus) {
        this.gplus = gplus;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getYoutubeUser() {
        return youtubeUser;
    }

    public void setYoutubeUser(String youtubeUser) {
        this.youtubeUser = youtubeUser;
    }

    public String getWelcomeTitle() {
        return welcomeTitle;
    }

    public void setWelcomeTitle(String welcomeTitle) {
        this.welcomeTitle = welcomeTitle;
    }

    public String getWelcomeDesc() {
        return welcomeDesc;
    }

    public void setWelcomeDesc(String welcomeDesc) {
        this.welcomeDesc = welcomeDesc;
    }

    public int getTabsAmount() {
        return tabsAmount;
    }

    public void setTabsAmount(int tabsAmount) {
        this.tabsAmount = tabsAmount;
    }

    public int getCpuControlPos() {
        return cpuControlPos;
    }

    public void setCpuControlPos(int cpuControlPos) {
        this.cpuControlPos = cpuControlPos;
    }

    public int getPhoneGapFragmentPos() {
        return phoneGapFragmentPos;
    }

    public void setPhoneGapFragmentPos(int phoneGapFragmentPos) {
        this.phoneGapFragmentPos = phoneGapFragmentPos;
    }

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<String> tabs) {
        this.tabs = tabs;
    }
}
