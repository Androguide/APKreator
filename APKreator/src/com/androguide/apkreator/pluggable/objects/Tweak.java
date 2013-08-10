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

public class Tweak {

    private String name, desc, type, control, unit, prop, booleanOn, booleanOff, shellCmd, shellCmd2, buttonText, buttonText2;
    private int min, max, def;
    private ArrayList<String> tabs = new ArrayList<String>(), spinnerEntries = new ArrayList<String>();

    public Tweak() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public void setProp(String prop) {
        this.prop = prop;
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
        this.shellCmd = shellCmd;
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
        this.shellCmd2 = shellCmd2;
    }

    public String getButtonText2() {
        return buttonText2;
    }

    public void setButtonText2(String buttonText2) {
        this.buttonText2 = buttonText2;
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
        return  spinnerEntries;
    }

    public void setSpinnerEntries(ArrayList<String> spinnerEntries) {
        this.spinnerEntries = spinnerEntries;
    }
}
