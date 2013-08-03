package com.fima.cardsui.objects;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public abstract class AbstractCard {

    protected String description, color, titleColor, desc, title, titlePlay, unit, type, prop;
    protected ArrayList<String> spinnerEntries;
    protected Boolean hasOverflow, isClickable;
    protected int imageRes, image, seekBarMax, seekBarProgress, marginBottom = 0;
    protected ActionBarActivity fa;
    protected AdapterView.OnItemSelectedListener onItemSelectedListener;
    protected CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    protected android.support.v7.view.ActionMode.Callback callback;
    protected SeekBar.OnSeekBarChangeListener listener;

    public abstract View getView(Context context);

    public abstract View getView(Context context, boolean swipeable);

    public String getTitle() {
        return title;
    }

    public String getTitlePlay() {
        return titlePlay;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public String getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public ArrayList<String> getSpinnerEntries() {
        return spinnerEntries;
    }

    public Boolean getHasOverflow() {
        return hasOverflow;
    }

    public Boolean getIsClickable() {
        return isClickable;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getSeekBarMax() {
        return seekBarMax;
    }

    public String getProp(){
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public int getSeekBarProgress() {
        return seekBarProgress;
    }

    public ActionBarActivity getSherlockFragmentActivity() {
        return fa;
    }

    public android.support.v7.view.ActionMode.Callback getCallback() {
        return callback;
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public SeekBar.OnSeekBarChangeListener getListener() {
        return listener;
    }

    public void setListener(SeekBar.OnSeekBarChangeListener listener) {
        this.listener = listener;
    }
}
