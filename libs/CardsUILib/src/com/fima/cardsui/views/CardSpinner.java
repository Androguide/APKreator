/**
 * @author Louis Teboul (Androguide)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fima.cardsui.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fima.cardsui.R;
import com.fima.cardsui.objects.Card;

import java.util.ArrayList;

public class CardSpinner extends Card {

    public CardSpinner(String title, String desc, String prop, ArrayList<String> spinnerEntries, ActionBarActivity fa, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        super(title, desc, prop, spinnerEntries, fa, onItemSelectedListener);
    }

    @Override
    public View getCardContent(Context context) {
        final View v = LayoutInflater.from(context).inflate(R.layout.card_spinner, null);
        SharedPreferences prefs = fa.getSharedPreferences(title, 0);
        SharedPreferences config = fa.getSharedPreferences("CONFIG", 0);

        assert v != null;
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.desc)).setText(desc);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color.parseColor(config.getString("APP_COLOR", "#96AA39")));

        int curr = prefs.getInt("CURRENT", 0);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(fa, android.R.layout.simple_spinner_dropdown_item, spinnerEntries);
        spinner.setAdapter(adapter);
        spinner.setSelection(curr);
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        return v;
    }

    public int getCardContentId() {
        return R.layout.card_spinner;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }

}
