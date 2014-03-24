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

import android.os.Environment;

import java.util.ArrayList;

public class CardPlugin {

    private String title, desc, type, control, unit, prop, booleanOn, booleanOff, shellCmd, shellCmd2, buttonText, buttonText2, url, filePath, stripeColor;
    private int min, max, def, entriesAmount;
    private ArrayList<String> tabs = new ArrayList<String>(), spinnerEntries = new ArrayList<String>(), spinnerCommands = new ArrayList<String>();
    private String[] props;

    public CardPlugin() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc.replaceAll("%sdcard%", Environment.getExternalStorageDirectory() +  "/");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProp() {
        return prop;
    }

    public String[] getProps() {
        return props;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public void setProps(String[] props) {
        this.props = props;
    }

    public String getBooleanOn() {
        return booleanOn;
    }

    public void setBooleanOn(String booleanOn) {
        this.booleanOn = booleanOn;
    }

    public String getBooleanOff() {
        return booleanOff;
    }

    public void setBooleanOff(String booleanOff) {
        this.booleanOff = booleanOff;
    }

    public String getShellCmd() {
        return shellCmd;
    }

    public void setShellCmd(String shellCmd) {
        this.shellCmd = shellCmd.replaceAll("%sdcard%", Environment.getExternalStorageDirectory() +  "/");
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getShellCmd2() {
        return shellCmd2;
    }

    public void setShellCmd2(String shellCmd2) {
        this.shellCmd2 = shellCmd2.replaceAll("%sdcard%", Environment.getExternalStorageDirectory() +  "/");;
    }

    public String getButtonText2() {
        return buttonText2;
    }

    public void setButtonText2(String buttonText2) {
        this.buttonText2 = buttonText2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStripeColor() {
        return stripeColor;
    }

    public void setStripeColor(String stripeColor) {
        this.stripeColor = stripeColor;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<String> tabs) {
        this.tabs = tabs;
    }

    public ArrayList<String> getSpinnerEntries() {
        return spinnerEntries;
    }

    public void setSpinnerEntries(ArrayList<String> spinnerEntries) {
        this.spinnerEntries = spinnerEntries;
    }

    public ArrayList<String> getSpinnerCommands() {
        return spinnerCommands;
    }

    public void setSpinnerCommands(ArrayList<String> spinnerCommands) {
        this.spinnerCommands = spinnerCommands;
    }

    public int getEntriesAmount() {
        return entriesAmount;
    }

    public void setEntriesAmount(int entriesAmount) {
        this.entriesAmount = entriesAmount;
    }
}
