package com.androguide.apkreator.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.androguide.apkreator.R;
import com.androguide.apkreator.helpers.CMDProcessor.CMDProcessor;
import com.androguide.apkreator.helpers.CPUHelper;
import com.androguide.apkreator.helpers.Helpers;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class CPUControl extends Fragment implements
        SeekBar.OnSeekBarChangeListener {

    private ActionBarActivity fa;

    Button Tweaks;

    private static SharedPreferences preferences;

    public static final String TAG = "CPUSettings";
    public static final String CURRENT_CPU = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    public static final String MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
    public static final String TEGRA_MAX_FREQ = "/sys/module/cpu_tegra/parameters/cpu_user_cap";
    public static final String MIN_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
    public static final String STEPS = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
    public static final String GOVERNORS_LIST = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
    public static final String GOVERNOR = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public static final String IO_SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";
    public static final String NUM_OF_CPUS = "/sys/devices/system/cpu/present";
    public static final String MAX_CPU = "max_cpu";
    public static final String MIN_CPU = "min_cpu";
    public static final String GOV_PREF = "gov";
    public static final String IO_PREF = "io";
    public static final String SOB = "cpu_boot";

    private static LineGraph graph;
    private static Line line;
    private static int currX = 0;
    private static int counter = 0;
    private static TextView mCurFreq;

    private SeekBar mMaxSlider;
    private SeekBar mMinSlider;
    private Spinner mGovernor;
    private Spinner mIo;
    private TextView mMaxSpeedText;
    private TextView mMinSpeedText;
    private String[] availableFrequencies;
    private Activity mActivity;
    private String mMaxFreqSetting;
    private String mMinFreqSetting;
    private CurCPUThread mCurCPUThread;
    private boolean mIsTegra3 = false;
    private int mNumOfCpu = 1;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup root,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        fa = (ActionBarActivity) super.getActivity();
        mActivity = getActivity();
        view = inflater.inflate(R.layout.card_cpu_control, root, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);

        graph = (LineGraph) view.findViewById(R.id.graph);
        line = new Line();
        LinePoint point = new LinePoint();
        point.setX(currX);
        point.setY(1);
        line.addPoint(point);
        line.setColor(Color.parseColor("#FFBB33"));
        graph.addLine(line);
        graph.setLineToFill(0);

        availableFrequencies = new String[0];
        String availableFrequenciesLine = Helpers.readOneLineNotRoot(STEPS);
        if (availableFrequenciesLine != null) {
            availableFrequencies = availableFrequenciesLine.split(" ");
            Arrays.sort(availableFrequencies, new Comparator<String>() {
                @Override
                public int compare(String object1, String object2) {
                    return Integer.valueOf(object1).compareTo(
                            Integer.valueOf(object2));
                }
            });
        }

        int frequenciesNum = availableFrequencies.length - 1;

        String currentGovernor = Helpers.readOneLineNotRoot(GOVERNOR);
        String currentIo = CPUHelper.getIOScheduler();
        String curMaxSpeed = Helpers.readOneLineNotRoot(MAX_FREQ);
        String curMinSpeed = Helpers.readOneLineNotRoot(MIN_FREQ);

        if (mIsTegra3) {
            String curTegraMaxSpeed = Helpers.readOneLineNotRoot(TEGRA_MAX_FREQ);
            int curTegraMax = 0;
            try {
                curTegraMax = Integer.parseInt(curTegraMaxSpeed);
                if (curTegraMax > 0) {
                    curMaxSpeed = Integer.toString(curTegraMax);
                }
            } catch (NumberFormatException ex) {
                curTegraMax = 0;
            }
        }

        String numOfCpus = Helpers.readOneLineNotRoot(NUM_OF_CPUS);
        String[] cpuCount = numOfCpus.split("-");
        if (cpuCount.length > 1) {
            try {
                int cpuStart = Integer.parseInt(cpuCount[0]);
                int cpuEnd = Integer.parseInt(cpuCount[1]);

                mNumOfCpu = cpuEnd - cpuStart + 1;

                if (mNumOfCpu < 0)
                    mNumOfCpu = 1;
            } catch (NumberFormatException ex) {
                mNumOfCpu = 1;
            }
        }

        mCurFreq = (TextView) view.findViewById(R.id.currspeed);

        mMaxSlider = (SeekBar) view.findViewById(R.id.max_slider);
        mMaxSlider.setMax(frequenciesNum);
        mMaxSpeedText = (TextView) view.findViewById(R.id.max_speed_text);
        mMaxSpeedText.setText(toMHz(curMaxSpeed));
        mMaxSlider.setProgress(Arrays.asList(availableFrequencies).indexOf(
                curMaxSpeed));
        mMaxSlider.setOnSeekBarChangeListener(this);

        mMinSlider = (SeekBar) view.findViewById(R.id.min_slider);
        mMinSlider.setMax(frequenciesNum);
        mMinSpeedText = (TextView) view.findViewById(R.id.min_speed_text);
        mMinSpeedText.setText(toMHz(curMinSpeed));
        mMinSlider.setProgress(Arrays.asList(availableFrequencies).indexOf(
                curMinSpeed));
        mMinSlider.setOnSeekBarChangeListener(this);

        mGovernor = (Spinner) view.findViewById(R.id.governor);
        String[] availableGovernors = Helpers.readOneLineNotRoot(GOVERNORS_LIST)
                .split(" ");
        ArrayAdapter<CharSequence> governorAdapter = new ArrayAdapter<CharSequence>(
                mActivity, android.R.layout.simple_spinner_item);
        governorAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < availableGovernors.length; i++) {
            governorAdapter.add(availableGovernors[i]);
        }
        mGovernor.setAdapter(governorAdapter);
        mGovernor.setSelection(Arrays.asList(availableGovernors).indexOf(
                currentGovernor));
        mGovernor.setOnItemSelectedListener(new GovListener());

        mIo = (Spinner) view.findViewById(R.id.io);
        String[] availableIo = CPUHelper.getAvailableIOSchedulers();
        ArrayAdapter<CharSequence> ioAdapter = new ArrayAdapter<CharSequence>(
                mActivity, android.R.layout.simple_spinner_item);
        ioAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < availableIo.length; i++) {
            ioAdapter.add(availableIo[i]);
        }
        mIo.setAdapter(ioAdapter);
        mIo.setSelection(Arrays.asList(availableIo).indexOf(currentIo));
        mIo.setOnItemSelectedListener(new IOListener());

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        if (fromUser) {
            switch (seekBar.getId()) {
                case R.id.max_slider:
                    setMaxSpeed(seekBar, progress);
                    break;
                case R.id.min_slider:
                    setMinSpeed(seekBar, progress);
                    break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // we have a break now, write the values..
        CMDProcessor cmd = new CMDProcessor();

        for (int i = 0; i < mNumOfCpu; i++) {
            cmd.runSuCommand("busybox echo " + mMaxFreqSetting + " > "
                    + MAX_FREQ.replace("cpu0", "cpu" + i));
            cmd.runSuCommand("busybox echo " + mMinFreqSetting + " > "
                    + MIN_FREQ.replace("cpu0", "cpu" + i));
        }

        if (mIsTegra3) {
            cmd.runSuCommand("busybox echo " + mMaxFreqSetting + " > "
                    + TEGRA_MAX_FREQ);
        }
    }

    public class GovListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            String selected = parent.getItemAtPosition(pos).toString();
            CMDProcessor cmd = new CMDProcessor();

            // do this on all cpu's since MSM can have different governors on
            // each cpu
            // and it doesn't hurt other devices to do it
            for (int i = 0; i < mNumOfCpu; i++) {
                cmd.runSuCommand("busybox echo " + selected + " > "
                        + GOVERNOR.replace("cpu0", "cpu" + i));
            }

            final SharedPreferences.Editor editor = preferences.edit();
            editor.putString(GOV_PREF, selected);
            editor.commit();
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    public class IOListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                   long id) {
            String selected = parent.getItemAtPosition(pos).toString();
            CMDProcessor cmd = new CMDProcessor();
            cmd.runSuCommand("busybox echo " + selected + " > " + IO_SCHEDULER);
            final SharedPreferences.Editor editor = preferences.edit();
            editor.putString(IO_PREF, selected);
            editor.commit();
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCurCPUThread == null) {
            mCurCPUThread = new CurCPUThread();
            mCurCPUThread.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCurCPUThread.isAlive()) {
            mCurCPUThread.interrupt();
            try {
                mCurCPUThread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public void setMaxSpeed(SeekBar seekBar, int progress) {
        String current;
        current = availableFrequencies[progress];
        int minSliderProgress = mMinSlider.getProgress();
        if (progress <= minSliderProgress) {
            mMinSlider.setProgress(progress);
            mMinSpeedText.setText(toMHz(current));
            mMinFreqSetting = current;
        }
        mMaxSpeedText.setText(toMHz(current));
        mMaxFreqSetting = current;
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MAX_CPU, current);
        editor.commit();
    }

    public void setMinSpeed(SeekBar seekBar, int progress) {
        String current;
        current = availableFrequencies[progress];
        int maxSliderProgress = mMaxSlider.getProgress();
        if (progress >= maxSliderProgress) {
            mMaxSlider.setProgress(progress);
            mMaxSpeedText.setText(toMHz(current));
            mMaxFreqSetting = current;
        }
        mMinSpeedText.setText(toMHz(current));
        mMinFreqSetting = current;
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MIN_CPU, current);
        editor.commit();
    }

    // Convert raw collected values to formatted MhZ
    private static String toMHz(String mhzString) {
        if (Integer.valueOf(mhzString) != null)
            return String.valueOf(Integer.valueOf(mhzString) / 1000) + " MHz";
        else
            return "NaN";
    }

    // Read current frequency from /sys in a separate thread
    protected class CurCPUThread extends Thread {
        private boolean mInterrupt = false;

        public void interrupt() {
            mInterrupt = true;
        }

        @Override
        public void run() {
            try {
                while (!mInterrupt) {
                    sleep(400);
                    final String curFreq = Helpers.readOneLineNotRoot(CURRENT_CPU);
                    mCurCPUHandler.sendMessage(mCurCPUHandler.obtainMessage(0,
                            curFreq));
                }
            } catch (InterruptedException e) {
                return;
            }
        }
    }


    // Update real-time current frequency & stats in a separate thread
    protected static Handler mCurCPUHandler = new Handler() {
        public void handleMessage(Message msg) {
            mCurFreq.setText(toMHz((String) msg.obj));
            currX += 1;
            final int p = Integer.parseInt((String) msg.obj);

            new Thread(new Runnable() {
                public void run() {
                    counter++;
                    addStatPoint(currX, p, line, graph);
                    ArrayList<LinePoint> array = line.getPoints();
                    if (line.getSize() > 10)
                        array.remove(0);
                    line.setPoints(array);

                    // Reset the line every 50 updates of the current frequency
                    // to make-up for the lack of garbage collection in the
                    // HoloGraph pluggable
                    if (counter == 50) {
                        graph.removeAllLines();
                        line = new Line();
                        LinePoint point = new LinePoint();
                        point.setX(currX);
                        point.setY(1);
                        line.addPoint(point);
                        line.setColor(Color.parseColor("#FFBB33"));
                        graph.addLine(line);
                        counter = 0;
                    }
                }
            }).start();
        }
    };

    // Static method to add new point to the graph
    public static void addStatPoint(int X, int Y, Line line, LineGraph graph) {
        LinePoint point = new LinePoint();
        point.setX(X);
        point.setY(Y);
        line.addPoint(point);
        graph.addLine(line);
    }
}