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

package com.fima.cardsui.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fima.cardsui.R;
import com.fima.cardsui.objects.Card;

public class CardEditText extends Card {

    private SharedPreferences prefs;

    public CardEditText(String title, String desc, String unit, String prop, int seekBarMax, int seekBarProgress, ActionBarActivity fa, ActionMode.Callback callback) {
        super(title, desc, unit, prop, seekBarMax, seekBarProgress, fa, callback);
    }

    @Override
    public View getCardContent(Context context) {
        final View v = LayoutInflater.from(context).inflate(R.layout.card_edit_text, null);

        assert v != null;
        final EditText editText = (EditText) v.findViewById(R.id.edit_text);

        prefs = fa.getSharedPreferences(title, 0);
        SharedPreferences config = fa.getSharedPreferences("CONFIG", 0);
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color.parseColor(config.getString("APP_COLOR", "#96AA39")));
        ((TextView) v.findViewById(R.id.desc)).setText(desc);
        ((TextView) v.findViewById(R.id.unit)).setText(unit);
        editText.setText(seekBarProgress + "");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int value;
                        if (!editText.getText().toString().isEmpty())
                            value = Integer.parseInt(editText.getText().toString());
                        else value = 0;

                        SharedPreferences p = fa.getSharedPreferences("TO_APPLY", 0);
                        p.edit().putString("PROP", prop).commit();
                        p.edit().putInt("VALUE", value).commit();
                        p.edit().putString("TO_SAVE", title).commit();
                    }
                }).start();
                fa.startSupportActionMode(callback);
            }
        });

        return v;
    }

    public int getCardContentId() {
        return R.layout.card_seekbar;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }
}
