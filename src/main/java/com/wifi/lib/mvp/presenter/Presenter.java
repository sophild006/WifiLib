package com.wifi.lib.mvp.presenter;

import com.wifi.lib.mvp.impl.BaseImpl;
import com.wifi.lib.mvp.impl.MyView;
import com.wifi.lib.mvp.model.Model;

/**
 * Created by Administrator on 2017/3/29.
 */

public class Presenter implements BaseImpl.PresenterImpl {

    private BaseImpl.View mView;
    private Model mModel;

    public Presenter(BaseImpl.View view) {
        mView = view;
        mModel = new Model();
    }


    @Override
    public void callWifiConnected() {
        if (mView != null) {
            mView.onWifiConnected();
        }
    }

    @Override
    public void callWifiDisConnected() {
        if (mView != null) {
            mView.onWifiDisConnected();
        }
    }

    @Override
    public void callWifiConnecting() {
        if (mView != null) {
            mView.onWifiConnecting();
        }
    }

    @Override
    public void callWifiConnectFailed() {
        if (mView != null) {
            mView.onWifiConnectFailed();
        }
    }

    @Override
    public void callScanResult(String ssid) {
        if (mView != null) {
            mView.onScanResult(ssid);
        }
    }
}
