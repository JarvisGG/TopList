package com.topList.theme.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * @author yyf
 * @since 03-11-2020
 */
public class SystemUtils {

    public static final boolean SDK_VERSION_JELLY_BEAN_OR_LATER = true;

    public static final boolean SDK_VERSION_JELLY_BEAN_MR1_OR_LATER = true;

    public static final boolean SDK_VERSION_JELLY_BEAN_MR2_OR_LATER = true;

    public static final boolean SDK_VERSION_KITKAT_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static final boolean SDK_VERSION_KITKAT_WATCH_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;

    public static final boolean SDK_VERSION_LOLLIPOP_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

    public static final boolean SDK_VERSION_LOLLIPOP_MR1_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;

    public static final boolean SDK_VERSION_M_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

    public static final boolean SDK_VERSION_N_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    public static final boolean SDK_VERSION_N_MR1_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;

    public static final boolean SDK_VERSION_O_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;

    public static final boolean SDK_VERSION_O_MR1_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;

    public static final boolean SDK_VERSION_P_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;

    public static final boolean SDK_VERSION_Q_OR_LATER = Build.VERSION.SDK_INT >= 29;

    private static final String MEMTOTAL_PATTERN = "MemTotal[\\s]*:[\\s]*(\\d+)[\\s]*kB\n";

    private static final String MEMFREE_PATTERN = "MemFree[\\s]*:[\\s]*(\\d+)[\\s]*kB\n";

    public static int getPackageVersionCode(final Context pContext) throws SystemUtilsException {
        return SystemUtils.getPackageInfo(pContext).versionCode;
    }

    public static String getPackageVersionName(final Context pContext) throws SystemUtilsException {
        return SystemUtils.getPackageInfo(pContext).versionName;
    }

    public static String getPackageName(final Context pContext) {
        return pContext.getPackageName();
    }

    synchronized private static PackageInfo getPackageInfo(final Context pContext) throws SystemUtilsException {
        final PackageManager packageManager = pContext.getPackageManager();
        try {
            return packageManager.getPackageInfo(pContext.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            throw new SystemUtilsException(e);
        }
    }

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isCharging(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent == null) {
            return false;
        }
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return plugged != 0;
    }

    public static class SystemUtilsException extends Exception {

        public SystemUtilsException() {

        }

        public SystemUtilsException(final Throwable pThrowable) {
            super(pThrowable);
        }
    }

    //region 判断手机的设备型号

    /**
     * 简单通过机型判断是不是 MIUI
     */
    public static boolean isMIUI() {
        return isCurrentPhone(Phone.XIAOMI);
    }

    /**
     * 简单通过机型判断是不是 EMUI
     */
    public static boolean isHuaweiEMUI() {
        return isCurrentPhone(Phone.HUAWEI);
    }

    /**
     * @return 是否是三星折叠屏
     */
    public static boolean isSamSungFold() {
        return isSamsung() && ("SM-W2020".equalsIgnoreCase(Build.MODEL)
                || "SM-F9000".equalsIgnoreCase(Build.MODEL));
    }

    private static Phone sCurrentPhone = null;

    /**
     * 是否是小米手机，包括小米与 Redmi
     */
    public static boolean isXiaomi() {
        return isCurrentPhone(Phone.XIAOMI);
    }

    /**
     * 是否是 Oppo 手机
     */
    public static boolean isOppo() {
        return isCurrentPhone(Phone.OPPO);
    }

    /**
     * 是否是 Vivo 手机
     */
    public static boolean isVivo() {
        return isCurrentPhone(Phone.VIVO);
    }

    /**
     * 是否是索尼手机
     */
    public static boolean isSony() {
        return isCurrentPhone(Phone.SONY);
    }

    /**
     * 是否是华为生产的手机，包括华为与荣耀，甚至包括 Nexus 6P
     */
    public static boolean isHuawei() {
        return isCurrentPhone(Phone.HUAWEI);
    }

    /**
     * 是否是三星生产的手机
     */
    public static boolean isSamsung() {
        return isCurrentPhone(Phone.SAMSUNG);
    }

    private static boolean isCurrentPhone(Phone phone) {
        if (sCurrentPhone != null) {
            return sCurrentPhone == phone;
        }
        if (phone.isCurrent()) {
            sCurrentPhone = phone;
        }
        return sCurrentPhone == phone;
    }

    /**
     * 重置当前的 sCurrentPhone 值，用于测试
     */
    @RestrictTo(RestrictTo.Scope.TESTS)
    static void resetCurrentPhone() {
        sCurrentPhone = null;
    }

    //endregion

}
