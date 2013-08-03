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

package com.androguide.apkreator.helpers.CMDProcessor;

public class Shell {

    // Mount /system as Read-Write or Read-Only
    public static final String MOUNT_SYSTEM_RW = "busybox mount -o rw,remount -t auto /system";
    public static final String MOUNT_SYSTEM_RO = "busybox mount -o ro,remount -t auto /system";
    public static final String ECHO = "busybox echo ";
    public static final String BUILD_PROP = "/system/build.prop";
    public static final String SED = "busybox sed -i /\"";

    // Wifi Scan Interval
    public static final String WIFI_SCAN_PROP1 = "wifi.supplicant_scan_interval";
    public static final String WIFI_SCAN_PROP2 = "persist.wifi_scan_interval";

    // TCP Congestion Algorithms
    public static final String SYSCTL_ALGORITHMS = "sysctl -w net.ipv4.tcp_congestion_control=";
    public static final String AVAILABLE_ALGORITHMS = "sysctl net.ipv4.tcp_available_congestion_control";

    // Google DNS
    public static final String PMR_DNS = "ro.pimp.my.rom.google_dns";
    public static final String SED_PMR_DNS = "busybox sed -i \'/" + PMR_DNS + "/d\' " + BUILD_PROP;
    public static final String SED_DNS_1 = "busybox sed -i \'/net.dns1=8.8.8.8/d\' /system/build.prop";
    public static final String SED_DNS_2 = "busybox sed -i \'/net.dns2=8.8.4.4/d\' /system/build.prop";
    public static final String ECHO_DNS_1 = "busybox echo \"net.dns1=8.8.8.8\" >> /system/build.prop";
    public static final String ECHO_DNS_2 = "busybox echo \"net.dns2=8.8.4.4\" >> /system/build.prop";
    public static final String SETPROP_DNS1 = "setprop net.dns1 8.8.8.8";
    public static final String SETPROP_DNS2 = "setprop net.dns2 8.8.4.4";

    // Media Streaming Tweaks (StageFright)
    public static final String PMR_STREAMING = "ro.pimp.my.rom.streaming_tweaks";
    public static final String SED_PMR_STREAMING = "busybox sed -i \'/" + PMR_STREAMING + "/d\' " + BUILD_PROP;
    public static final String SED_STREAMING = "busybox sed -i \'/media.stagefright.enable-/d\' /system/build.prop";
    public static final String[] STREAMING_TWEAKS = {
            "busybox echo \"media.stagefright.enable-player=true\" >> /system/build.prop",
            "busybox echo \"media.stagefright.enable-meta=true\" >> /system/build.prop",
            "busybox echo \"media.stagefright.enable-scan=true\" >> /system/build.prop",
            "busybox echo \"media.stagefright.enable-http=false\" >> /system/build.prop",
            "setprop media.stagefright.enable-player true", "setprop media.stagefright.enable-meta true",
            "setprop media.stagefright.enable-scan true", "setprop media.stagefright.enable-http false"
    };

    // HSUPA
    public static final String PMR_HSUPA = "ro.pimp.my.rom.enable_hsupa";
    public static final String SED_HSUPA = "busybox sed -i \'/ro.ril.hsxpa=/d\' /system/build.prop";
    public static final String ECHO_HSUPA = "busybox echo \"ro.ril.hsxpa=3\" >> /system/build.prop";
    public static final String SETPROP_HSUPA = "setprop ro.ril.hsxpa 3";

    public static void su(String command) {
        CMDProcessor.runSuCommand(command);
    }

    public static void sh(String command) {
        CMDProcessor.runShellCommand(command);
    }
}
