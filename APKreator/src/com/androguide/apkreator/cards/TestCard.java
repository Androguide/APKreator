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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androguide.apkreator.R;
import com.fima.cardsui.objects.Card;

public class TestCard extends Card {

    public TestCard(String title, String desc, String type) {
        super(title, desc, type);
    }

    @Override
    public View getCardContent(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_switch, null);
        ((TextView) v.findViewById(R.id.title)).setText(title + "(type: " + type + ")");
        ((TextView) v.findViewById(R.id.desc)).setText(desc);


        return v;
    }
}
