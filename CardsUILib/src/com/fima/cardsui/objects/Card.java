package com.fima.cardsui.objects;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fima.cardsui.R;
import com.fima.cardsui.Utils;

import java.util.ArrayList;

public abstract class Card extends AbstractCard {

    protected View mCardLayout;
    private OnCardSwiped onCardSwipedListener;
    private OnClickListener mListener;

    public Card() {

    }

    public Card(String title) {
        this.title = title;
    }

    public Card(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Card(String title, String desc, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
    }

    public Card(String title, String desc, String type) {
        this.title = title;
        this.desc = desc;
        this.type = type;
    }

    public Card(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public Card(String title, String desc, int image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public Card(String titlePlay, String description, String color,
                String titleColor, Boolean hasOverflow, Boolean isClickable) {

        this.titlePlay = titlePlay;
        this.description = description;
        this.color = color;
        this.titleColor = titleColor;
        this.hasOverflow = hasOverflow;
        this.isClickable = isClickable;
    }

    public Card(String titlePlay, String description, int imageRes, String titleColor, Boolean hasOverflow,
                Boolean isClickable) {

        this.titlePlay = titlePlay;
        this.description = description;
        this.titleColor = titleColor;
        this.hasOverflow = hasOverflow;
        this.isClickable = isClickable;
        this.imageRes = imageRes;
    }

    public Card(String title, String desc, String titleColor, Boolean hasOverflow,
                Boolean isClickable) {

        this.title = title;
        this.desc = desc;
        this.titleColor = titleColor;
        this.hasOverflow = hasOverflow;
        this.isClickable = isClickable;
    }

    // Card with Seekbar & EditText
    public Card(String title, String desc, String unit, int seekBarMax, int seekBarProgress, ActionBarActivity fa, ActionMode.Callback callback) {
        this.title = title;
        this.desc = desc;
        this.unit = unit;
        this.seekBarMax = seekBarMax;
        this.seekBarProgress = seekBarProgress;
        this.fa = fa;
        this.callback = callback;
    }


    // Card with Seekbar & EditText for PmR build.prop Plugins
    public Card(String title, String desc, String unit, String prop, int seekBarMax, int seekBarProgress, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.unit = unit;
        this.seekBarMax = seekBarMax;
        this.seekBarProgress = seekBarProgress;
        this.fa = fa;
        this.prop = prop;
    }


    // Card with Seekbar & EditText for PmR build.prop Plugins
    public Card(String title, String desc, String unit, String prop, int seekBarMax, int seekBarProgress, ActionBarActivity fa, ActionMode.Callback callback) {
        this.title = title;
        this.desc = desc;
        this.unit = unit;
        this.seekBarMax = seekBarMax;
        this.seekBarProgress = seekBarProgress;
        this.fa = fa;
        this.prop = prop;
        this.callback = callback;
    }

    // Card with Spinner
    public Card (String title, String desc, ArrayList<String> spinnerEntries, ActionBarActivity fa, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.title = title;
        this.desc = desc;
        this.spinnerEntries = spinnerEntries;
        this.fa = fa;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    // Card with Spinner
    public Card (String title, String desc, String prop, ArrayList<String> spinnerEntries, ActionBarActivity fa, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.title = title;
        this.desc = desc;
        this.spinnerEntries = spinnerEntries;
        this.fa = fa;
        this.onItemSelectedListener = onItemSelectedListener;
        this.prop = prop;
    }

    // Card with Switch
    public Card (String title, String desc, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    // Card with Switch
    public Card (String title, String desc, String prop, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.prop = prop;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    // Card with Button
    public Card (String title, String desc, String buttonText, String cmd, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.buttonText = buttonText;
        this.fa = fa;
        this.cmd = cmd;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    @Override
    public View getView(Context context, boolean swipeable) {
        return getView(context, false);
    }

    @Override
    public View getView(Context context) {

        View view = LayoutInflater.from(context).inflate(getCardLayout(), null);

        mCardLayout = view;

        try {
            ((FrameLayout) view.findViewById(R.id.cardContent))
                    .addView(getCardContent(context));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // ((TextView) view.findViewById(R.id.title)).setText(this.title);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int bottom = Utils.convertDpToPixelInt(context, 12);
        lp.setMargins(0, 0, 0, bottom);

        view.setLayoutParams(lp);

        return view;
    }

    public View getViewLast(Context context) {

        View view = LayoutInflater.from(context).inflate(getLastCardLayout(),
                null);

        mCardLayout = view;

        try {
            ((FrameLayout) view.findViewById(R.id.cardContent))
                    .addView(getCardContent(context));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // ((TextView) view.findViewById(R.id.title)).setText(this.title);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int bottom = Utils.convertDpToPixelInt(context, 12);
        lp.setMargins(0, 0, 0, bottom);

        view.setLayoutParams(lp);

        return view;
    }

    public View getViewFirst(Context context) {

        View view = LayoutInflater.from(context).inflate(getFirstCardLayout(),
                null);

        mCardLayout = view;

        try {
            ((FrameLayout) view.findViewById(R.id.cardContent))
                    .addView(getCardContent(context));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // ((TextView) view.findViewById(R.id.title)).setText(this.title);

        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int bottom = Utils.convertDpToPixelInt(context, 12);
        lp.setMargins(0, 0, 0, bottom);

        view.setLayoutParams(lp);*/

        return view;
    }

    public abstract View getCardContent(Context context);

    public OnClickListener getClickListener() {
        return mListener;
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public void OnSwipeCard() {
        if (onCardSwipedListener != null)
            onCardSwipedListener.onCardSwiped(this, mCardLayout);
        // TODO: find better implementation to get card-object's used content
        // layout (=> implementing getCardContent());
    }

    public OnCardSwiped getOnCardSwipedListener() {
        return onCardSwipedListener;
    }

    public void setOnCardSwipedListener(OnCardSwiped onEpisodeSwipedListener) {
        this.onCardSwipedListener = onEpisodeSwipedListener;
    }

    protected int getCardLayout() {
        return R.layout.item_card;
    }

    protected int getLastCardLayout() {
        return R.layout.item_card_empty_last;
    }

    protected int getFirstCardLayout() {
        return R.layout.item_play_card_empty_first;
    }

    public interface OnCardSwiped {
        public void onCardSwiped(Card card, View layout);
    }

}
