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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.R;
import com.fima.cardsui.objects.Card;

public class CardLoadMore extends Card {

    public CardLoadMore(String title, String buttonText1, ActionBarActivity fa) {
        super(title, buttonText1, fa);
    }

    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_loadmore, null);
        final SharedPreferences config = fa.getSharedPreferences("CONFIG", 0);

        assert v != null;
        TextView btn = (TextView) v.findViewById(R.id.button);
        btn.setText(title);
        btn.setTextColor(Color.parseColor(config.getString("APP_COLOR", "#96AA39")));
        return v;
    }

    public int getCardContentId() {
        return R.layout.card_loadmore;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }
}

