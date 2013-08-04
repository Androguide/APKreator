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

package com.androguide.apkreator.cards;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import de.ankri.views.Switch;
import android.widget.TextView;

import com.androguide.apkreator.R;
import com.fima.cardsui.objects.Card;

public class CardSwitchPlugin extends Card {

    public CardSwitchPlugin(String title, String desc, String prop, ActionBarActivity fa, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super(title, desc, prop, fa, onCheckedChangeListener);
    }

    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_switch, null);
        SharedPreferences config = fa.getSharedPreferences("CONFIG", 0);

        assert v != null;
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color.parseColor(config.getString("APP_COLOR", "#96AA39")));
        ((TextView) v.findViewById(R.id.desc)).setText(desc);

        Boolean b = (fa.getSharedPreferences(prop, 0)).getBoolean("isChecked", false);
        ((Switch) v.findViewById(R.id.toggle)).setChecked(b);
        ((Switch) v.findViewById(R.id.toggle)).setOnCheckedChangeListener(onCheckedChangeListener);

        return v;
    }
}
