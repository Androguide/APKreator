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
        View v = LayoutInflater.from(context).inflate(R.layout.card_play, null);

        ((TextView) v.findViewById(R.id.title)).setText(titlePlay);
        ((TextView) v.findViewById(R.id.title)).setTextColor(Color
                .parseColor(titleColor));
        ((TextView) v.findViewById(R.id.description)).setText(description);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(96, 16, 96, 0);

        ((LinearLayout) v.findViewById(R.id.rootLayout))
                .setLayoutParams(layoutParams);

        ((ImageView) v.findViewById(R.id.icon)).setImageDrawable(context
                .getResources().getDrawable(imageRes));

        if (isClickable)
            ((LinearLayout) v.findViewById(R.id.contentLayout))
                    .setBackgroundResource(R.drawable.selectable_background_cardbank);

//		if (hasOverflow)
//			((ImageView) v.findViewById(R.id.overflow))
//					.setVisibility(View.VISIBLE);
//		else
//			((ImageView) v.findViewById(R.id.overflow))
//					.setVisibility(View.GONE);

        return v;
    }
}
