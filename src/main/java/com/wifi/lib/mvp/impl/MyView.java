package com.wifi.lib.mvp.impl;

/**
 * Created by Administrator on 2017/3/29.
 */

public abstract class MyView implements BaseImpl.View {
    public void onWifiConnected() {}

    public void onWifiDisConnected() {}

    public void onWifiConnecting() {}

    public void onWifiConnectFailed() {}

    public void onScanResult(String ssid) {}
}
