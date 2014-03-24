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

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androguide.apkreator.fragments.CPUControl;
import com.androguide.apkreator.fragments.PluginFragment;
import com.androguide.apkreator.helpers.FileHelper;
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

    protected static final String DEV_KEY = "AIzaSyCaIJoWM1Ft-8_9NMXTcno2jtNxLl64XHk";
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

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        /**
         * Before anything we need to check if the config files exist to avoid
         * FC is they don't
         *
         * @see #checkIfConfigExists()
         */
        checkIfConfigExists();

        /**
         * Now it's all good because if no configuration was found we have
         * copied a default one over.
         *
         * @see #checkIfConfigExists()
         */
        setAppConfigInPrefs();

        headers = getPluginTabs();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

		/*
         * set a custom shadow that overlays the main content when the drawer
		 * opens
		 */
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

		/* set up the drawer's list view with items and click listener */
        ArrayAdapter<String> pimpAdapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerHeaders);
        mDrawerList.setAdapter(pimpAdapter);
        Log.e("FIRST POS", mDrawerList.getFirstVisiblePosition() + "");
        Log.e("LAST POS", mDrawerList.getLastVisiblePosition() + "");
        View child = mDrawerList.getChildAt(mDrawerList
                .getFirstVisiblePosition());
        if (child != null
                && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            child.setBackground(getColouredTouchFeedback());
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        /** Set the user-defined ActionBar icon */
        File file = new File(getFilesDir()
                + "/.APKreator/icon.png");
        if (file.exists()) {
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                Uri iconUri = Uri
                        .fromFile(new File(getFilesDir() + "/.APKreator/icon.png"));
                Bitmap icon = BitmapFactory.decodeFile(iconUri.getPath());
                Drawable ic = new BitmapDrawable(icon);
                getSupportActionBar().setIcon(ic);
            } catch (NullPointerException e) {
                Log.e("NPE", e.getMessage());
            }
        }
        /*
         * ActionBarDrawerToggle ties together the proper interactions between
		 * the sliding drawer and the action bar app icon
		 */
        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.app_name, /* "open drawer" description for accessibility */
                R.string.app_name /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); /*
                                         * creates call to
										 * onPrepareOptionsMenu()
										 */

            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); /*
                                         * creates call to
										 * onPrepareOptionsMenu()
										 */
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /** Tabs adapter using the PagerSlidingStrip library */
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(
                this.getSupportFragmentManager());
        pager.setAdapter(adapter);
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
        pager.setOffscreenPageLimit(5);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * The action bar home/up action should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which checks if the configuration files exist in
     * /sdcard/.APKreator If they don't, it copies a default configuration over
     * from the assets
     */
    public void checkIfConfigExists() {
        File config = new File(getFilesDir() + "/.APKreator/config.xml");
//        if (!config.exists()) {
            final String[] tabNames = {"tab0.xml", "tab1.xml", "tab2.xml"};
            final String[] configNames = {"config.xml", "icon.png"};
            FileHelper.createFolder(getFilesDir()
                    + "/.APKreator");
            FileHelper.createFolder(getFilesDir()
                    + "/.APKreator/tabs");
            FileHelper.copyFromAssets(getBaseContext(), configNames,
                    getFilesDir() + "/.APKreator");
            FileHelper.copyFromAssets(getBaseContext(), tabNames,
                    getFilesDir()
                            + "/.APKreator/tabs");
//        }
    }

    /**
     * Method to parse the desired color scheme from the config.xml file
     *
     * @return The Color parsed from the XML, in String form
     */
    public String getPluginColor() {
        List<Config> pluginConfigs = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(getFilesDir()
                    + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            Log.e("getPluginColor()", e.getMessage());
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

    /**
     * Method to parse the config.xml to get the amount of tabs and their
     * headers
     *
     * @return The tabs headers in an ordered ArrayList of Strings
     */
    public ArrayList<String> getPluginTabs() {
        List<Config> pluginConfigs = null;
        ArrayList<String> tabs = new ArrayList<String>();

        try {
            PluginParser parser = new PluginParser();
            File file = new File(getFilesDir()
                    + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            Log.e("getPluginTabs()", e.getMessage());
        }

        for (int i = 0; i < (pluginConfigs != null ? pluginConfigs.size() : 0); i++)
            tabs = pluginConfigs.get(i).getTabs();
        return tabs;
    }

    /**
     * Convenience method to save the configuration into SharedPreferences for
     * later re-use Please note that this is triggered again at each #onCreate()
     * call in order to keep the configuration updated if it changes
     */
    public void setAppConfigInPrefs() {
        List<Config> pluginConfigs = null;
        try {
            PluginParser parser = new PluginParser();
            File file = new File(getFilesDir()
                    + "/.APKreator/config.xml");
            FileInputStream fis = new FileInputStream(file);
            pluginConfigs = parser.parseConfig(fis);
        } catch (IOException e) {
            Log.e("setAppConfigInPrefs", e.getMessage());
        }
        SharedPreferences prefs = getSharedPreferences("CONFIG", 0);
        for (int i = 0; i < (pluginConfigs != null ? pluginConfigs.size() : 0); i++) {
            prefs.edit()
                    .putString("APP_NAME", pluginConfigs.get(i).getAppName())
                    .putString("APP_COLOR", pluginConfigs.get(i).getAppColor())
                    .putString("WEBSITE", pluginConfigs.get(i).getWebsite())
                    .putString("XDA", pluginConfigs.get(i).getXda())
                    .putString("TWITTER", pluginConfigs.get(i).getTwitter())
                    .putString("GOOGLE+", pluginConfigs.get(i).getGplus())
                    .putString("FACEBOOK", pluginConfigs.get(i).getFacebook())
                    .putInt("TABS_AMOUNT", pluginConfigs.get(i).getTabsAmount())
                    .putInt("CPU_CONTROL_POS", pluginConfigs.get(i).getCpuControlPos())
                    .commit();
        }
    }

    /**
     * Method to set the color scheme according to the color defined in
     * config.xml
     *
     * @param newColor : the color retrieved from config.xml
     */
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

        /**
         * The following is a work-around to avoid NPE, see the following
         * thread:
         *
         * @see http://stackoverflow.com/questions/11002691/actionbar-
         *      setbackgrounddrawable-nulling-background-from-thread-handler
         */
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        } catch (NullPointerException e) {
            Log.e("NPE", e.getMessage());
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* Save current color scheme value to the #Bundle */
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
		/*
		 * Retrieve the color from the #Bundle and set the color scheme to its
		 * value
		 * 
		 * @see #changeColor()
		 */
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
        /** Keep this for a future multi-color config implementation */
        // switch (pos) {
        // case 0:
        // changeColor(0xFF3F9FE0);
        // return;
        // case 1:
        // changeColor(0xFF96AA39);
        // return;
        // case 2:
        // changeColor(0xFFF4842D);
        // return;
        // case 3:
        // changeColor(0xFFC74B46);
        // return;
        // case 4:
        // changeColor(0xFF666666);
        // return;
        // case 5:
        // changeColor(0xFF5161BC);
        // return;
        // default:
        // return;
        // }
    }

    private void selectItem(int position) {
        // TODO: Remove in RC/stable releases
        Log.v("DEBUG", "Selected item " + position);
        SharedPreferences prefs = getSharedPreferences("CONFIG", 0);
        switch (position) {
            case 0:
                goToUrl(prefs.getString("WEBSITE", "http://apkreator.com"));
                break;
            case 1:
                goToUrl(prefs.getString("XDA",
                        "http://forum.xda-developers.com/member.php?u=4752917"));
                break;
            case 2:
                goToUrl(prefs.getString("TWITTER",
                        "https://twitter.com/androguidefr"));
                break;
            case 3:
                goToUrl(prefs.getString("GOOGLE+",
                        "https://plus.google.com/u/0/116104837766524942436/posts"));
                break;
            case 4:
                goToUrl(prefs.getString("FACEBOOK",
                        "https://www.facebook.com/andro.guidefr"));
                break;
            default:
                return;
        }
		/* Update the selected item and automatically close the drawer */
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * Convenience method for triggering an #Intent.ACTION_VIEW event to an url
     * passed as a parameter
     *
     * @param url : the url to launch the intent with
     */
    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public void setTitle(CharSequence title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            Log.e("NPE", e.getMessage());
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * #onPostCreate() and #onConfigurationChanged()
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
		/* Sync the toggle state after onRestoreInstanceState has occurred. */
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
		/* Pass any configuration change to the drawer toggle */
        mDrawerToggle.onConfigurationChanged(newConfig);
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

    /**
     * Adapter for the ViewPager
     */
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

//            else if (position == 0)
//                return new WelcomeFragment();
//
//            else if (position == 1)
//                return new YouTubeFragment();
//
//            else if (position == 2)
//                return new GplusFragment();
//
//            else if (position == 4)
//                return new SupportMapFragment();

            else
                return PluginFragment.newInstance(position);
        }
    }

    /**
     * Handle the drawer items click
     */
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {

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