package com.fima.cardsui.objects;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.util.ArrayList;

public abstract class AbstractCard {

    protected String description, color, titleColor, stripeColor, desc, title, titlePlay, unit, type,
            prop, cmd1, cmd2, buttonText1, buttonText2, url, filePath, videoId;
    protected String[] props;
    protected String username, annotation, avatarUrl, imageUrl, imageTitle, resharedDesc, resharedFrom, originalTitle;
    protected ArrayList<String> spinnerEntries;
    protected Boolean hasOverflow, isClickable, isReshared;
    protected int imageRes, image, seekBarMax, seekBarProgress, marginBottom = 0, differenciator, duration, rating, likes, comments, plusOnes;
    protected ActionBarActivity fa;
    protected AdapterView.OnItemSelectedListener onItemSelectedListener;
    protected CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    protected android.support.v7.view.ActionMode.Callback callback;
    protected SeekBar.OnSeekBarChangeListener listener;
    protected View.OnClickListener clickListener;

    public abstract View getView(Context context);

    public abstract View getView(Context context, boolean swipeable);

    public String getTitle() {
        return title;
    }

    public String getTitlePlay() {
        return titlePlay;
    }

    public String getDescription() {
        return desc;
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

    public String getCmd1() {
        return cmd1;
    }

    public void setCmd1(String cmd1) {
        this.cmd1 = cmd1;
    }

    public String getCmd2() {
        return cmd2;
    }

    public String getResharedDesc() {
        return resharedDesc;
    }

    public void setCmd2(String cmd2) {
        this.cmd2 = cmd2;
    }

    public String getButtonText1() {
        return buttonText1;
    }

    public void setButtonText1(String buttonText1) {
        this.buttonText1 = buttonText1;
    }

    public String getButtonText2() {
        return buttonText2;
    }

    public void setButtonText2(String buttonText2) {
        this.buttonText2 = buttonText2;
    }

    public String getStripeColor() {
        return stripeColor;
    }

    public void setStripeColor(String stripeColor) {
        this.stripeColor = stripeColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Boolean getPostIsReshared() {
        return isReshared;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getSeekBarMax() {
        return seekBarMax;
    }

    public String[] getProps() {
        return props;
    }

    public void setProps(String[] props) {
        this.props = props;
    }

    public String getProp() {
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

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
