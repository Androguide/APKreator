package com.androguide.apkreator.helpers.CMDProcessor;
/*
 * Performance Control - An Android CPU Control application Copyright (C) 2012
 * James Roberts
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */


public interface Constants {

    public static final String TAG = "PmR: CPU_CONTROL";
    // CPU settings
    public static final String CUR_CPU_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    public static final String MAX_FREQ_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";
    public static final String TEGRA_MAX_FREQ_PATH = "/sys/module/cpu_tegra/parameters/cpu_user_cap";
    public static final String MIN_FREQ_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
    public static final String STEPS_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_frequencies";
    public static final String GOVERNORS_LIST_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors";
    public static final String GOVERNOR_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public static final String IO_SCHEDULER_PATH = "/sys/block/mmcblk0/queue/scheduler";
    public static final String NUM_OF_CPUS_PATH = "/sys/devices/system/cpu/present";
    public static final String PREF_MAX_CPU = "pref_max_cpu";
    public static final String PREF_MIN_CPU = "pref_min_cpu";
    public static final String PREF_GOV = "pref_gov";
    public static final String PREF_IO = "pref_io";
    public static final String CPU_SOB = "cpu_sob";
    // CPU info
    public static String KERNEL_INFO_PATH = "/proc/version";
    public static String CPU_INFO_PATH = "/proc/cpuinfo";
    public static String MEM_INFO_PATH = "/proc/meminfo";
    // Time in state
    public static final String TIME_IN_STATE_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state";
    public static final String PREF_OFFSETS = "pref_offsets";
    // Other settings
    public static final String MINFREE_PATH = "/sys/module/lowmemorykiller/parameters/minfree";
    public static final String READ_AHEAD_PATH = "/sys/devices/virtual/bdi/179:0/read_ahead_kb";
    public static final String FASTCHARGE_PATH = "/sys/kernel/fast_charge/force_fast_charge";
    public static final String INTENT_ACTION_FASTCHARGE = "com.aokp.romcontrol.FCHARGE_CHANGED";
    public static final String PREF_MINFREE = "pref_minfree";
    public static final String PREF_MINFREE_BOOT = "pref_minfree_boot";
    public static final String PREF_READ_AHEAD = "pref_read_ahead";
    public static final String PREF_READ_AHEAD_BOOT = "pref_read_ahead_boot";
    public static final String PREF_FASTCHARGE = "pref_fast_charge";
    // VM settings
    public static final String PREF_DIRTY_RATIO = "pref_dirty_ratio";
    public static final String PREF_DIRTY_BACKGROUND = "pref_dirty_background";
    public static final String PREF_DIRTY_EXPIRE = "pref_dirty_expire";
    public static final String PREF_DIRTY_WRITEBACK = "pref_dirty_writeback";
    public static final String PREF_MIN_FREE_KB = "pref_min_free_kb";
    public static final String PREF_OVERCOMMIT = "pref_overcommit";
    public static final String PREF_SWAPPINESS = "pref_swappiness";
    public static final String PREF_VFS = "pref_vfs";
    public static final String DIRTY_RATIO_PATH = "/proc/sys/vm/dirty_ratio";
    public static final String DIRTY_BACKGROUND_PATH = "/proc/sys/vm/dirty_background_ratio";
    public static final String DIRTY_EXPIRE_PATH = "/proc/sys/vm/dirty_expire_centisecs";
    public static final String DIRTY_WRITEBACK_PATH = "/proc/sys/vm/dirty_writeback_centisecs";
    public static final String MIN_FREE_PATH = "/proc/sys/vm/min_free_kbytes";
    public static final String OVERCOMMIT_PATH = "/proc/sys/vm/overcommit_ratio";
    public static final String SWAPPINESS_PATH = "/proc/sys/vm/swappiness";
    public static final String VFS_CACHE_PRESSURE_PATH = "/proc/sys/vm/vfs_cache_pressure";
    public static final String VM_SOB = "vm_sob";
    // Voltage control
    public static final String VOLTAGE_SOB = "voltage_sob";
    public static final String UV_MV_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table";
    public static final String VDD_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/vdd_levels";
    public static final String COMMON_VDD_PATH = "/sys/devices/system/cpu/cpufreq/vdd_levels";
    public static final String VDD_SYSFS_PATH = "/sys/devices/system/cpu/cpu0/cpufreq/vdd_sysfs_levels";
    // PC Settings
    public static final String PREF_USE_LIGHT_THEME = "use_light_theme";
    public static final String PREF_WIDGET_BG_COLOR = "widget_bg_color";
    public static final String PREF_WIDGET_TEXT_COLOR = "widget_text_color";
    public static final String VERSION_NUM = "1.0.4";
}