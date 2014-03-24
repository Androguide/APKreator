package com.fima.cardsui.objects;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

    public Card(ActionBarActivity fa) {
        this.fa = fa;
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

    public Card(String title, String desc, String url, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.url = url;
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

    public Card(String title, String desc, String titleColor, String url, int duration, int rating, int likes, int comments) {
        this.title = title;
        this.desc = desc;
        this.titleColor = titleColor;
        this.url = url;
        this.duration = duration;
        this.rating = rating;
        this.likes = likes;
        this.comments = comments;
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
    public Card(String title, String desc, ArrayList<String> spinnerEntries, ActionBarActivity fa, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.title = title;
        this.desc = desc;
        this.spinnerEntries = spinnerEntries;
        this.fa = fa;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    // Card with Spinner
    public Card(String title, String desc, String prop, ArrayList<String> spinnerEntries, ActionBarActivity fa, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.title = title;
        this.desc = desc;
        this.spinnerEntries = spinnerEntries;
        this.fa = fa;
        this.onItemSelectedListener = onItemSelectedListener;
        this.prop = prop;
    }

    // Card with Switch
    public Card(String title, String desc, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    // Card with Switch
    public Card(String title, String desc, String[] props, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.props = props;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    // Card with Switch (Shell variant)
    public Card(String title, String desc, String cmd1, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.cmd1 = cmd1;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    // Card with Button
    public Card(String title, String desc, String buttonText1, String cmd1, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.buttonText1 = buttonText1;
        this.fa = fa;
        this.cmd1 = cmd1;
    }

    // Card with 2 Buttons
    public Card(String title, String desc, String buttonText1, String buttonText2, String cmd1, String cmd2, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.buttonText1 = buttonText1;
        this.buttonText2 = buttonText2;
        this.fa = fa;
        this.cmd1 = cmd1;
        this.cmd2 = cmd2;
    }

    // Card for downloading files
    public Card(String title, String desc, String url, String filePath, String buttonText1, ActionBarActivity fa) {
        this.title = title;
        this.desc = desc;
        this.fa = fa;
        this.url = url;
        this.filePath = filePath;
        this.buttonText1 = buttonText1;
    }

    // Card with dynamic image loading using Picasso by Square
    public Card(String title, String desc, String titleColor, String url) {
        this.title = title;
        this.desc = desc;
        this.titleColor = titleColor;
        this.url = url;
    }

    public Card(String username, String annotation, String avatarUrl, String imageUrl, String imageTitle, String resharedDesc,
                String resharedFrom, String originalTitle, int plusOnes, Boolean isReshared) {
        this.username = username;
        this.annotation = annotation;
        this.avatarUrl = avatarUrl;
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
        this.resharedDesc = resharedDesc;
        this.resharedFrom = resharedFrom;
        this.originalTitle = originalTitle;
        this.plusOnes = plusOnes;
        this.isReshared = isReshared;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(Context context, boolean swipable) {

        View view = LayoutInflater.from(context).inflate(getCardLayout(), null);

        mCardLayout = view;

        try {
            if (view != null) ((FrameLayout) view.findViewById(R.id.cardContent))
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

        if (view != null) {
            view.setLayoutParams(lp);
            view.setHasTransientState(true);
        }

        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(Context context) {

        View view = LayoutInflater.from(context).inflate(getCardLayout(), null);

        mCardLayout = view;

        try {
            if (view != null) ((FrameLayout) view.findViewById(R.id.cardContent))
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

        if (view != null) {
            view.setLayoutParams(lp);
            view.setHasTransientState(true);
        }

        return view;
    }

    @SuppressLint("NewApi")
    public View getViewLast(Context context) {

        View view = LayoutInflater.from(context).inflate(getLastCardLayout(),
                null);

        mCardLayout = view;

        try {
            if (view != null) ((FrameLayout) view.findViewById(R.id.cardContent))
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

        if (view != null) {
            view.setLayoutParams(lp);
            view.setHasTransientState(true);
        }
        return view;
    }

    @SuppressLint("NewApi")
    public View getViewFirst(Context context) {

        View view = LayoutInflater.from(context).inflate(getFirstCardLayout(),
                null);

        mCardLayout = view;

        try {
            if (view != null) ((FrameLayout) view.findViewById(R.id.cardContent))
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

        if (view != null) {
            view.setLayoutParams(lp);
            view.setHasTransientState(true);
        }

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

    protected int getId() {
        return R.id.cardContent;
    }

    protected int getLastCardLayout() {
        return R.layout.item_card_empty_last;
    }

    protected int getFirstCardLayout() {
        return R.layout.item_play_card_empty_first;
    }

    /**
     * Attempt to reuse convertCardView.  Should not modify convertCardView if it's
     * not compatible.  The implementer should check the card content part and
     * verify that it matches.
     *
     * @param convertCardView the view to convert, with root Id equal to Card.getId()
     * @return true on success, false if not compatible
     */
    public abstract boolean convert(View convertCardView);

    public interface OnCardSwiped {
        public void onCardSwiped(Card card, View layout);
    }

}