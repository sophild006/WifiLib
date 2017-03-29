package com.wifi.lib;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class WifiUtils {

    private WifiManager wifiManager;

    public WifiUtils(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

    }

    public boolean isWifiEnabele() {
        return wifiManager.isWifiEnabled();
    }

    public int getWifiState() {
        return wifiManager.getWifiState();
    }

    public List<ScanResult> getScanResults() {
        return wifiManager.getScanResults();
    }
}
