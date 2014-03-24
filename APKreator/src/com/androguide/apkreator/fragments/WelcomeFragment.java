package com.androguide.apkreator.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.androguide.apkreator.R;
import com.fima.cardsui.views.CardImageLocal;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import java.util.ArrayList;

public class WelcomeFragment extends Fragment {

    private static ArrayList<Card> mCards = new ArrayList<Card>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ScrollView ll = (ScrollView) inflater.inflate(R.layout.welcome_page,
                container, false);
        ActionBarActivity fa = (ActionBarActivity) super.getActivity();
        SharedPreferences prefs = fa.getSharedPreferences("CONFIG", 0);

        assert ll != null;
        CardUI mCardUI = (CardUI) (ll.findViewById(R.id.cards_ui));
        mCardUI.addStack(new CardStack(""), true);
        CardImageLocal card = new CardImageLocal(
                prefs.getString("WELCOME_TITLE", "Welcome!"),
                prefs.getString("WELCOME_DESC", ""),
                "#444444",
                "");
        mCardUI.addCard(card, true);

       return ll;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
