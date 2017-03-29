package com.wifi.lib;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ScanHandler extends Handler {

    public WifiManager wifiManager;

    public ScanHandler(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public void startScan() {
        removeMessages(0);
        sendEmptyMessage(0);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Log.d("wwq","handlerMessage");
        wifiManager.startScan();
        sendEmptyMessageDelayed(1, 10000);
    }
}
