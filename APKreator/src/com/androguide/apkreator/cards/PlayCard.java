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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androguide.apkreator.R;
import com.fima.cardsui.objects.Card;

public class PlayCard extends Card {

    public PlayCard(String titlePlay, String description, int imageRes,
                    String titleColor, Boolean hasOverflow, Boolean isClickable) {

        super(titlePlay, description, imageRes, titleColor, hasOverflow,
                isClickable);
    }

    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.text_card, null);

        assert v != null;
        ((TextView) v.findViewById(R.id.title)).setText(titlePlay);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color
                .parseColor(titleColor));
        ((TextView) v.findViewById(R.id.description)).setText(description);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(96, 16, 96, 0);

        (v.findViewById(R.id.rootLayout))
                .setLayoutParams(layoutParams);

        ((ImageView) v.findViewById(R.id.icon)).setImageDrawable(context
                .getResources().getDrawable(imageRes));

        if (isClickable)
            (v.findViewById(R.id.contentLayout))
                    .setBackgroundResource(R.drawable.selectable_background_cardbank);

        return v;
    }
}
