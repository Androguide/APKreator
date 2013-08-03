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
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androguide.apkreator.R;
import com.androguide.apkreator.helpers.CMDProcessor.CMDProcessor;
import com.androguide.apkreator.helpers.CMDProcessor.Shell;
import com.androguide.apkreator.helpers.Helpers;
import com.androguide.apkreator.helpers.SystemPropertiesReflection;
import com.fima.cardsui.objects.Card;

public class CardSeekBarPlugin extends Card {

    private SeekBar seekBar;
    private SharedPreferences prefs;

    public CardSeekBarPlugin(String title, String desc, String unit, String prop, int seekBarMax, int seekBarProgress, ActionBarActivity fa) {
        super(title, desc, unit, prop, seekBarMax, seekBarProgress, fa);
    }

    @Override
    public View getCardContent(Context context) {
        final View v = LayoutInflater.from(context).inflate(R.layout.card_seekbar, null);
        prefs = fa.getSharedPreferences(title, 0);

        assert v != null;
        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.desc)).setText(desc);
        ((TextView) v.findViewById(R.id.unit)).setText(unit);

        String curr = Helpers.getSystemProp(fa, prop, String.valueOf(seekBarProgress));
        if (!curr.isEmpty())
            seekBarProgress = Integer.parseInt(curr);
        else
            seekBarProgress = prefs.getInt(title, seekBarProgress);

        seekBar = (SeekBar) v.findViewById(R.id.seek);
        seekBar.setMax(seekBarMax);
        seekBar.setProgress(seekBarProgress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((EditText) v.findViewById(R.id.seek_val)).setText(i + "");
                seekBarProgress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                CMDProcessor.runSuCommand(Shell.MOUNT_SYSTEM_RW);
                                CMDProcessor.runSuCommand(Shell.SED + prop + "/d\" " + Shell.BUILD_PROP);
                                CMDProcessor.runSuCommand(Shell.ECHO + "\"" + prop + "=" + seekBarProgress + "\" >> " + Shell.BUILD_PROP);
                                CMDProcessor.runSuCommand("setprop " + prop + " " + seekBarProgress);
                                SystemPropertiesReflection.set(fa, prop, seekBarProgress + "");
                                CMDProcessor.runSuCommand(Shell.MOUNT_SYSTEM_RO);
                                prefs.edit().putInt(title, seekBarProgress).commit();
                            }
                        }).start();
            }
        });

        ((EditText) v.findViewById(R.id.seek_val)).setText(seekBarProgress + "");
        ((EditText) v.findViewById(R.id.seek_val)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    seekBarProgress = Integer.parseInt(((EditText) v.findViewById(R.id.seek_val)).getText() + "");
                } catch (Exception e) {
                    Log.e("ParseInt() ERROR", e + "");
                    Log.e("ParseInt() ERROR", e + "");
                }
            }
        });

        seekBar = (SeekBar) v.findViewById(R.id.seek);
        return v;
    }
}
