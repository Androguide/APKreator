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

package com.androguide.apkreator.fragments;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.androguide.apkreator.cards.CardSeekBarPlugin;
import com.androguide.apkreator.cards.CardSwitchPlugin;
import com.androguide.apkreator.helpers.Helpers;
import com.androguide.apkreator.pluggable.objects.Tweak;
import com.androguide.apkreator.pluggable.parsers.PluginParser;
import com.fima.cardsui.views.CardUI;

public class PagerFragments extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;
    public static LinearLayout ll;
    private ActionBarActivity fa;
    private CardUI mCardView;
    private ArrayList<String> name = new ArrayList<String>(), desc = new ArrayList<String>(), type = new ArrayList<String>(),
            control = new ArrayList<String>(), unit = new ArrayList<String>(), prop = new ArrayList<String>(),
            on = new ArrayList<String>(),off = new ArrayList<String>();
    private ArrayList<Integer> min = new ArrayList<Integer>(), max = new ArrayList<Integer>(), def = new ArrayList<Integer>();

    public static PagerFragments newInstance(int position) {
        PagerFragments f = new PagerFragments();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // UI & Context initialization
        fa = (ActionBarActivity) super.getActivity();
        ll = (LinearLayout) inflater.inflate(com.androguide.apkreator.R.layout.cardsui,
                container, false);

        assert ll != null;
        CardUI mCardsView = (CardUI) ll.findViewById(com.androguide.apkreator.R.id.cardsui);
        SharedPreferences prefs = fa.getSharedPreferences("CONFIG", 0);

        List<Tweak> pluginTweaks = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(Environment.getExternalStorageDirectory() + "/PimpMyRom/tab-" + position + ".xml");
            FileInputStream fis = new FileInputStream(file);
            pluginTweaks = parser.parse(fis);
        } catch (IOException e) {
            Helpers.sendMsg(fa, "Couldn't load plugin file: tab-" + position +".xml");
            e.printStackTrace();
        }

        for (int i = 0; i < (pluginTweaks != null ? pluginTweaks.size() : 0); i++) {
        	final int posHolder = i;
            name.add(i, pluginTweaks.get(i).getName());
            desc.add(i, pluginTweaks.get(i).getDesc());
            type.add(i, pluginTweaks.get(i).getType());
            unit.add(i, pluginTweaks.get(i).getUnit());
            control.add(i, pluginTweaks.get(i).getControl());
            min.add(i, pluginTweaks.get(i).getMin());
            max.add(i, pluginTweaks.get(i).getMax());
            def.add(i, pluginTweaks.get(i).getDef());
            prop.add(i, pluginTweaks.get(i).getProp());
            on.add(i, pluginTweaks.get(i).getBooleanOn());
            off.add(i, pluginTweaks.get(i).getBooleanOff());

            if (type.get(i).equalsIgnoreCase("build.prop")) {
            	
            	// SeekBar + EditText Combo Card
                if (control.get(i).equalsIgnoreCase("seekbar-combo")) {
                    CardSeekBarPlugin card = new CardSeekBarPlugin(name.get(i), desc.get(i), unit.get(i), prop.get(i),
                            max.get(i), def.get(i), fa);
                    mCardsView.addCard(card, true);
                    
                // Switch Card
                } else if (control.get(i).equalsIgnoreCase("switch")) {
                	CardSwitchPlugin card = new CardSwitchPlugin(name.get(i), desc.get(i), prop.get(i), fa, new OnCheckedChangeListener() {
					
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							
							if (isChecked) {
								Helpers.applyBuildPropTweak(prop.get(posHolder),on.get(posHolder));
								SharedPreferences prefs = fa.getSharedPreferences(prop.get(posHolder), 0);
						        prefs.edit().putBoolean("isChecked", true).commit();
							} else {
								Helpers.applyBuildPropTweak(prop.get(posHolder),off.get(posHolder));
								SharedPreferences prefs = fa.getSharedPreferences(prop.get(posHolder), 0);
								prefs.edit().putBoolean("isChecked", false).commit();
							}
						}
					});
                mCardsView.addCard(card, true);
                }
            }
        }

        return ll;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
