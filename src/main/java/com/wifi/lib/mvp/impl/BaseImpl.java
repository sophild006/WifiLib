package com.wifi.lib.mvp.impl;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface BaseImpl {

    interface View {
        void onWifiConnected();

        void onWifiDisConnected();

        void onWifiConnecting();

        void onWifiConnectFailed();

        void onScanResult(String ssid);
    }


    interface PresenterImpl {
        void callWifiConnected();

        void callWifiDisConnected();

        void callWifiConnecting();

        void callWifiConnectFailed();

        void callScanResult(String ssid);
    }
}
