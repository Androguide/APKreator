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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.androguide.apkreator.R;
import com.androguide.apkreator.cards.CardButton;
import com.androguide.apkreator.cards.CardButtonDouble;
import com.androguide.apkreator.cards.CardDownload;
import com.androguide.apkreator.cards.CardSeekBar;
import com.androguide.apkreator.cards.CardSeekBarCombo;
import com.androguide.apkreator.cards.CardSpinner;
import com.androguide.apkreator.cards.CardSwitchPlugin;
import com.androguide.apkreator.cards.CardText;
import com.androguide.apkreator.cards.CardTextStripe;
import com.androguide.apkreator.helpers.CMDProcessor.CMDProcessor;
import com.androguide.apkreator.helpers.CMDProcessor.Shell;
import com.androguide.apkreator.helpers.Helpers;
import com.androguide.apkreator.helpers.SystemPropertiesReflection;
import com.androguide.apkreator.pluggable.objects.Tweak;
import com.androguide.apkreator.pluggable.parsers.ParserInterface;
import com.androguide.apkreator.pluggable.parsers.PluginParser;
import com.fima.cardsui.views.CardUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.androguide.apkreator.helpers.CMDProcessor.CMDProcessor.runSuCommand;

/**
 * Every tab in the application contains an instance of this unique Fragment Object.
 * I send & retrieve the desired position of the tab which each PluginFragment instance
 * should belong to via the Activity Bundle.
 * This 0-based index also determines which XML plugin file the PluginFragment instance
 * will load (tab0.xml, tab1.xml, tab2.xml etc...)
 *
 * @see com.androguide.apkreator.MainActivity
 */
public class PluginFragment extends Fragment implements ParserInterface {

    private static final String ARG_POSITION = "position";
    private int position;
    public static LinearLayout ll;
    private ActionBarActivity fa;
    private ActionMode mActionMode;

    /**
     * PluginFragment constructor
     *
     * @param position : The 0-based index of the tab each instance of
     *                 PluginFragment belongs to, passed-in via the parent Activity's Bundle.
     *                 Determines which tab-?.xml file to load for each instance of PluginFragment.
     */
    public static PluginFragment newInstance(int position) {
        PluginFragment f = new PluginFragment();
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

        fa = (ActionBarActivity) super.getActivity();
        ll = (LinearLayout) inflater.inflate(com.androguide.apkreator.R.layout.cardsui,
                container, false);

        assert ll != null;
        CardUI mCardsView = (CardUI) ll.findViewById(com.androguide.apkreator.R.id.cardsui);

        List<Tweak> pluginTweaks = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(Environment.getExternalStorageDirectory() + "/.APKreator/tabs/tab" + position + ".xml");
            FileInputStream fis = new FileInputStream(file);
            pluginTweaks = parser.parse(fis);
        } catch (IOException e) {
            Helpers.sendMsg(fa, "Couldn't load plugin file: tab" + position + ".xml");
            e.printStackTrace();
        }

        /* Retrieve the right attributes based on the position parameter passed to this instance of PluginFragment,
         * and store them in the separate ArrayLists<?> we declared above the constructor */
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
            shellCmds.add(i, pluginTweaks.get(i).getShellCmd());
            shellCmds2.add(i, pluginTweaks.get(i).getShellCmd2());
            on.add(i, pluginTweaks.get(i).getBooleanOn());
            off.add(i, pluginTweaks.get(i).getBooleanOff());
            buttons.add(i, pluginTweaks.get(i).getButtonText());
            buttons2.add(i, pluginTweaks.get(i).getButtonText2());
            spinners.add(i, pluginTweaks.get(i).getSpinnerEntries());
            urls.add(i, pluginTweaks.get(i).getUrl());
            paths.add(i, pluginTweaks.get(i).getFilePath());

            /************************************************
             *               Build.prop Cards               *
             ************************************************/
            if (type.get(i).equalsIgnoreCase("build.prop")) {

                /** SeekBar + EditText Combo Card
                 **** @see com.androguide.apkreator.cards.CardSeekBarCombo */
                if (control.get(i).equalsIgnoreCase("seekbar-combo")) {
                    CardSeekBarCombo card = new CardSeekBarCombo(name.get(i), desc.get(i), unit.get(i), prop.get(i),
                            max.get(i), def.get(i), fa);
                    mCardsView.addCard(card, true);

                    /** SeekBar Card
                     **** @see com.androguide.apkreator.cards.CardSeekBar */
                } else if (control.get(i).equalsIgnoreCase("seekbar")) {
                    CardSeekBar card = new CardSeekBar(name.get(i), desc.get(i), unit.get(i), prop.get(i),
                            max.get(i), def.get(i), fa, mActionModeCallback);
                    mCardsView.addCard(card, true);

                    /** Switch Card
                     **** @see com.androguide.apkreator.cards.CardSwitchPlugin */
                } else if (control.get(i).equalsIgnoreCase("switch")) {
                    CardSwitchPlugin card = new CardSwitchPlugin(name.get(i), desc.get(i), prop.get(i), fa, new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                Helpers.applyBuildPropTweak(prop.get(posHolder), on.get(posHolder));
                                SharedPreferences prefs = fa.getSharedPreferences(prop.get(posHolder), 0);
                                prefs.edit().putBoolean("isChecked", true).commit();
                            } else {
                                Helpers.applyBuildPropTweak(prop.get(posHolder), off.get(posHolder));
                                SharedPreferences prefs = fa.getSharedPreferences(prop.get(posHolder), 0);
                                prefs.edit().putBoolean("isChecked", false).commit();
                            }
                        }
                    });
                    mCardsView.addCard(card, true);

                    /** Spinner Card
                     **** @see com.androguide.apkreator.cards.CardSwitchPlugin */
                } else if (control.get(i).equalsIgnoreCase("spinner")) {
                    CardSpinner card = new CardSpinner(name.get(i), desc.get(i), prop.get(i), spinners.get(i), fa, new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String bp = "";
                            if (posHolder <= prop.size())
                                bp = prop.get(posHolder);
                            final String bProp = bp;

                            /* In order to avoid re-applying the current value in onCreate(),
                               I compare the saved spinner position with the current one and only
                               apply the value if they differ. This way root access isn't requested upon launch. */
                            SharedPreferences p = fa.getSharedPreferences(prop.get(posHolder), 0);
                            int curr = p.getInt("CURRENT", 0);
                            final int pos = position;
                            if (pos != curr) {
                                final String item = spinners.get(posHolder).get(pos);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CMDProcessor.runSuCommand(Shell.MOUNT_SYSTEM_RW);
                                        runSuCommand(Shell.SED + bProp + "/d\" " + Shell.BUILD_PROP);
                                        runSuCommand(Shell.ECHO + "\"" + bProp + "=" + item + "\" >> " + Shell.BUILD_PROP);
                                        runSuCommand("setprop " + bProp + " " + item);
                                        SystemPropertiesReflection.set(fa, bProp, item + "");
                                        CMDProcessor.runSuCommand(Shell.MOUNT_SYSTEM_RO);
                                        SharedPreferences prefs = fa.getSharedPreferences(bProp, 0);
                                        prefs.edit().putInt("CURRENT", pos).commit();
                                    }
                                }).start();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    mCardsView.addCard(card, true);
                }


                /************************************************
                 *             Shell Commands Cards             *
                 ************************************************/
            } else if (type.get(i).equalsIgnoreCase("shell")) {

                if (control.get(i).equalsIgnoreCase("button")) {

                    /** Card with Button, which executes a shell command on click
                     **** @see com.androguide.apkreator.cards.CardButton */
                    CardButton card = new CardButton(name.get(i), desc.get(i), buttons.get(i), shellCmds.get(i), fa);
                    mCardsView.addCard(card, true);

                } else if (control.get(i).equalsIgnoreCase("double-button")) {

                    /** Card with 2 Buttons, which executes a shell command on click
                     **** @see com.androguide.apkreator.cards.CardButtonDouble */
                    CardButtonDouble card = new CardButtonDouble(name.get(i), desc.get(i), buttons.get(i), buttons2.get(i),
                            shellCmds.get(i), shellCmds2.get(i), fa);
                    mCardsView.addCard(card, true);
                }

                /************************************************
                 *              Miscellaneous Cards             *
                 ************************************************/
            } else if (type.get(i).equalsIgnoreCase("download")) {

                /** Card for downloading from a URL
                 **** @see com.androguide.apkreator.cards.CardDownload */
                CardDownload card = new CardDownload(name.get(i), desc.get(i), urls.get(i), paths.get(i), buttons.get(i), fa);
                mCardsView.addCard(card, true);

                /************************************************
                 *               Plain Text Cards               *
                 ************************************************/
            } else if (type.get(i).equalsIgnoreCase("text")) {

                /** Plain Text Card
                 **** @see com.androguide.apkreator.cards.CardText */
                SharedPreferences p = fa.getSharedPreferences("CONFIG", 0);
                CardText card = new CardText(name.get(i), desc.get(i), p.getString("APP_COLOR", "#96AA39"), false, false);
                mCardsView.addCard(card, true);

            } else if (type.get(i).equalsIgnoreCase("text-stripe")) {

                /** Plain Text Card with colored stripe
                 **** @see com.androguide.apkreator.cards.CardTextStripe */
                SharedPreferences p = fa.getSharedPreferences("CONFIG", 0);
                CardTextStripe card = new CardTextStripe(name.get(i), desc.get(i), p.getString("APP_COLOR", "#96AA39"), false, false);
                mCardsView.addCard(card, true);
            }
        }

        return ll;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Contextual ActionBar triggered by SeekBar-enabled cards
     * *** @see com.androguide.apkreator.cards.CardSeekBar
     * *** @see com.androguide.apkreator.cards.CardSeekBarCombo
     */
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            assert inflater != null;
            inflater.inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.apply:
                    mActionMode = mode;
                    SharedPreferences p = fa.getSharedPreferences("TO_APPLY", 0);
                    final String prop = p.getString("PROP", "");
                    final int value = p.getInt("VALUE", 0);
                    final String title = p.getString("TO_SAVE", "");
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                runSuCommand(Shell.MOUNT_SYSTEM_RW);
                                runSuCommand(Shell.MOUNT_SYSTEM_RW);
                                runSuCommand(Shell.SED + prop + "/d\" " + Shell.BUILD_PROP);
                                runSuCommand(Shell.ECHO + "\"" + prop + "=" + value + "\" >> " + Shell.BUILD_PROP);
                                runSuCommand("setprop " + prop + " " + value);
                                SystemPropertiesReflection.set(fa, prop, value + "");
                                runSuCommand(Shell.MOUNT_SYSTEM_RO);
                                SharedPreferences pref = fa.getSharedPreferences(title, 0);
                                pref.edit().putInt(title, value).commit();
                            } catch (NullPointerException e) {
                                Log.e("WIFI_SCAN", "NullPointerException: " + e);
                            }
                        }
                    }).start();
                    mActionMode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
}
