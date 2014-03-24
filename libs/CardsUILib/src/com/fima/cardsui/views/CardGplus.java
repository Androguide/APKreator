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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fima.cardsui.R;
import com.fima.cardsui.objects.Card;
import com.squareup.picasso.Picasso;

public class CardGplus extends Card {

    protected static final String DEV_KEY = "AIzaSyCaIJoWM1Ft-8_9NMXTcno2jtNxLl64XHk";

    public CardGplus(String username, String annotation, String avatarUrl, String imageUrl, String imageTitle, String resharedDesc,
                     String resharedFrom, String originalTitle, int plusOnes, Boolean isReshared) {
        super(username, annotation, avatarUrl, imageUrl, imageTitle, resharedDesc, resharedFrom, originalTitle, plusOnes, isReshared);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_gplus, null);

        if (v != null) {

            if (imageTitle.isEmpty() || imageTitle.equals(""))
                v.findViewById(R.id.imageWrapper).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            if (!isReshared) v.findViewById(R.id.reshared).setVisibility(View.GONE);

            if (annotation.isEmpty() || annotation.equals("")) {
                annotation = resharedDesc;
                if (annotation.isEmpty() || annotation.equals("")) {
                    annotation = originalTitle;
                    if (annotation.isEmpty() || annotation.equals("")) {
                        v.findViewById(R.id.description).setVisibility(View.GONE);
                        v.findViewById(R.id.divider).setVisibility(View.GONE);
                    }
                }
            }

            if (imageUrl.isEmpty()) {
                v.findViewById(R.id.image).setVisibility(View.GONE);
                v.findViewById(R.id.articleTitle).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            } else
                Picasso.with(context).load(imageUrl).placeholder(R.drawable.no_thumbnail).into((ImageView) v.findViewById(R.id.image));

            Picasso.with(context).load(avatarUrl).placeholder(R.drawable.no_thumbnail).into((ImageView) v.findViewById(R.id.avatar));
            ((TextView) v.findViewById(R.id.title)).setText(username);
            ((TextView) v.findViewById(R.id.description)).setText(annotation);
            ((TextView) v.findViewById(R.id.articleTitle)).setText(imageTitle);
            ((TextView) v.findViewById(R.id.reshared)).setText(resharedFrom + " originally shared this");
            ((TextView) v.findViewById(R.id.plusOnes)).setText("+" + plusOnes);
        }
        return v;
    }

    public int getCardContentId() {
        return R.layout.card_gplus;
    }

    @Override
    public boolean convert(View convertCardView) {
        return true;
    }
}
