package com.androguide.apkreator.pluggable.objects;

import java.util.ArrayList;

/**
 * Created by androguide on 8/1/13.
 */
public class Tweak {

    private String name, desc, type, control, unit, prop, booleanOn, booleanOff;
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

    public void setProp(String prop) {
        this.prop = prop;
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
