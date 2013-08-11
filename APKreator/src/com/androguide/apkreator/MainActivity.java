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

package com.androguide.apkreator;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androguide.apkreator.fragments.CPUControl;
import com.androguide.apkreator.fragments.PluginFragment;
import com.androguide.apkreator.pluggable.objects.Config;
import com.androguide.apkreator.pluggable.parsers.PluginParser;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements
        OnPageChangeListener {

    private final Handler handler = new Handler();
    private ArrayList<String> headers = new ArrayList<String>();
    private PagerSlidingTabStrip tabs;
    private Drawable oldBackground = null;
    private int currentColor = 0xFF3F9FE0;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mDrawerHeaders[] = {"Website", "XDA Thread",
            "Follow Me on Twitter", "Follow Me on Google+", "Become a Fan"};
    private Drawable.Callback drawableCallback = new Drawable.Callback() {

        @Override
        public void invalidateDrawable(Drawable who) {
            try {
                getSupportActionBar().setBackgroundDrawable(who);
            } catch (NullPointerException ignored) {
            }
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAppConfigInPrefs();

        headers = getPluginTabs();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        // set up the drawer's list view with items and click listener
        ArrayAdapter<String> pimpAdapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerHeaders);
        mDrawerList.setAdapter(pimpAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        // and if the plugin defines an icon then use it, otherwise use the default APKreator icon
        File file = new File(Environment.getExternalStorageDirectory() + "/.APKreator/icon.png");
        if (file.exists()) {
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                Uri iconUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/.APKreator/icon.png"));
                Bitmap icon = BitmapFactory.decodeFile(iconUri.getPath());
                Drawable ic = new BitmapDrawable(icon);
                getSupportActionBar().setIcon(ic);
            } catch (NullPointerException e) {
            }
        }
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.app_name, /* "open drawer" description for accessibility */
                R.string.app_name /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(this.getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        final int pageMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);

        changeColor(Color.parseColor(getPluginColor()));
        String inputSystem;
        inputSystem = android.os.Build.ID;
        Log.d("hai", inputSystem);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth(); // TODO: deprecated, find the new way
        int height = display.getHeight(); // TODO: deprecated, find the new way
        Log.d("hai", width + "");
        Log.d("hai", height + "");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(width / dm.xdpi, 2);
        double y = Math.pow(height / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d("hai", "Screen inches : " + screenInches + "");
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        return super.onOptionsItemSelected(item);
    }

    public String getPluginColor() {
        List<Config> pluginConfigs = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(Environment.getExternalStorageDirectory() + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String color = "#96AA39";
        for (int i = 0; i < (pluginConfigs != null ? pluginConfigs.size() : 0); i++) {
            String appName = pluginConfigs.get(i).getAppName();
            String appColor = pluginConfigs.get(i).getAppColor();
            getSupportActionBar().setTitle(appName);
            Log.i("APP NAME", appName + "\n" + "APP COLOR = " + appColor);
            color = appColor;
        }
        return color;
    }

    public ArrayList<String> getPluginTabs() {
        List<Config> pluginConfigs = null;
        ArrayList<String> tabs = new ArrayList<String>();
        try {
            PluginParser parser = new PluginParser();
            File file = new File(Environment.getExternalStorageDirectory() + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < (pluginConfigs != null ? pluginConfigs.size() : 0); i++) {
            tabs = pluginConfigs.get(i).getTabs();
        }

        return tabs;
    }

    public void setAppConfigInPrefs() {
        List<Config> pluginConfigs = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(Environment.getExternalStorageDirectory() + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences prefs = getSharedPreferences("CONFIG", 0);
        for (int i = 0; i < (pluginConfigs != null ? pluginConfigs.size() : 0); i++) {
            prefs.edit().putString("APP_NAME", pluginConfigs.get(i).getAppName()).commit();
            prefs.edit().putString("APP_COLOR", pluginConfigs.get(i).getAppColor()).commit();
            prefs.edit().putString("WEBSITE", pluginConfigs.get(i).getWebsite()).commit();
            prefs.edit().putString("XDA", pluginConfigs.get(i).getXda()).commit();
            prefs.edit().putString("TWITTER", pluginConfigs.get(i).getTwitter()).commit();
            prefs.edit().putString("GOOGLE+", pluginConfigs.get(i).getGplus()).commit();
            prefs.edit().putString("FACEBOOK", pluginConfigs.get(i).getFacebook()).commit();
            prefs.edit().putInt("TABS_AMOUNT", pluginConfigs.get(i).getTabsAmount()).commit();
            prefs.edit().putInt("CPU_CONTROL_POS", pluginConfigs.get(i).getCpuControlPos()).commit();
        }
    }

    public void changeColor(int newColor) {
        tabs.setIndicatorColor(newColor);
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = getResources().getDrawable(
                R.drawable.actionbar_bottom);
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable,
                bottomDrawable});

        if (oldBackground == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                ld.setCallback(drawableCallback);
            else
                getSupportActionBar().setBackgroundDrawable(ld);

        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                    oldBackground, ld});

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                td.setCallback(drawableCallback);
            else
                getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }
        oldBackground = ld;
        currentColor = newColor;

        /** The following is a work-around to avoid NPE, see the following thread :
         *  http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler */
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        } catch (NullPointerException ignored) {
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int pos) {
//		switch (pos) {
//		case 0:
//			changeColor(0xFF3F9FE0);
//			return;
//		case 1:
//			changeColor(0xFF96AA39);
//			return;
//		case 2:
//			changeColor(0xFFF4842D);
//			return;
//		case 3:
//			changeColor(0xFFC74B46);
//			return;
//		case 4:
//			changeColor(0xFF666666);
//			return;
//		case 5:
//			changeColor(0xFF5161BC);
//			return;
//		default:
//			return;
//		}
    }

    private void selectItem(int position) {
        //TODO: Remove in RC/stable releases
        Log.v("DEBUG", "Selected item " + position);
        // update selected item and title, then close the drawer
        SharedPreferences prefs = getSharedPreferences("CONFIG", 0);
        switch (position) {
            case 0:
                goToUrl(prefs.getString("WEBSITE", "http://apkreator.com"));
                break;
            case 1:
                goToUrl(prefs.getString("XDA", "http://forum.xda-developers.com/member.php?u=4752917"));
                break;
            case 2:
                goToUrl(prefs.getString("TWITTER", "https://twitter.com/androguidefr"));
                break;
            case 3:
                goToUrl(prefs.getString("GOOGLE+", "https://plus.google.com/u/0/116104837766524942436/posts"));
                break;
            case 4:
                goToUrl(prefs.getString("FACEBOOK", "https://www.facebook.com/andro.guidefr"));
                break;
            default:
                return;
        }
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public void setTitle(CharSequence title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return headers.get(position);
        }

        @Override
        public int getCount() {
            return headers.size();
        }

        @Override
        public Fragment getItem(int position) {
            SharedPreferences prefs = getSharedPreferences("CONFIG", 0);
            if (position == prefs.getInt("CPU_CONTROL_POS", 0) && position != 0)
                return new CPUControl();
            else
                return PluginFragment.newInstance(position);
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            for (int i = 0; i < parent.getCount(); i++)
                view.setBackground(getColouredTouchFeedback());
            selectItem(position);
        }

        private StateListDrawable getColouredTouchFeedback() {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{android.R.attr.state_pressed},
                    new ColorDrawable(Color.parseColor(getPluginColor())));
            states.addState(new int[]{android.R.attr.state_focused},
                    new ColorDrawable(Color.parseColor(getPluginColor())));
            states.addState(new int[]{},
                    getResources().getDrawable(android.R.color.transparent));
            return states;
        }
    }

}