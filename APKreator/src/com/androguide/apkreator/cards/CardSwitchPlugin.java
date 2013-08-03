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
        assert v != null;
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.desc)).setText(desc);

        Boolean b = (fa.getSharedPreferences(prop, 0)).getBoolean("isChecked", false);
        ((Switch) v.findViewById(R.id.toggle)).setChecked(b);
        ((Switch) v.findViewById(R.id.toggle)).setOnCheckedChangeListener(onCheckedChangeListener);

        return v;
    }
}
