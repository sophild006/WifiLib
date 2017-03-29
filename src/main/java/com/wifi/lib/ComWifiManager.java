package com.wifi.lib;

import android.Manifest;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.wifi.lib.mvp.impl.BaseImpl;
import com.wifi.lib.mvp.presenter.Presenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ComWifiManager {

    private static ComWifiManager mInstance;
    private WifiUtils wifiUtils;
    private Context mContext;
    private Presenter presenter;
    private ScanHandler scanHandler;

    public static ComWifiManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ComWifiManager(context);
        }
        return mInstance;
    }


    public ComWifiManager(Context context) {
        this.mContext = context;
        wifiUtils = new WifiUtils(context);
        scanHandler = new ScanHandler(context);
        registerBroads();

    }


    private List<WeakReference<Presenter>> weakReferences = new ArrayList<>();
    public void setView(BaseImpl.View view) {
        presenter = new Presenter(view);
        weakReferences.add(new WeakReference<>(presenter));
    }

    private void registerBroads() {
        IntentFilter wifiStateFilter = new IntentFilter();
        wifiStateFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiStateFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifiStateFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        wifiStateFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mWifiStateReceiver, wifiStateFilter);
    }


    private final BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {

                handlerWifiStateChanged(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
            } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

            } else if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                Log.d("wwq", "SCAN_RESULTS_AVAILABLE_ACTION");
                handlerScanResults();
            } else if (intent.getAction().equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)) {
                handlerSupplicantChanged(intent);
            }
        }


    };

    private void handleNetworkStateChanged(@NonNull Intent intent) {
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        String ssid = networkInfo.getExtraInfo();
        NetworkInfo.State state = networkInfo.getState();
        NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
    }

    private void handlerSupplicantChanged(Intent intent) {

        SupplicantState supplicantState = intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
        final int errorCode = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1);
        //更新缓存
//        L.d("handleSupplicantStateChanged" + errorCode);
        NetworkInfo.DetailedState detailedState = WifiInfo.getDetailedStateOf(supplicantState);
        Log.d("wwq", "" + detailedState.name().toLowerCase());
        for (WeakReference<Presenter> presenter1 : weakReferences) {
            if (presenter1.get() != null) {
                presenter1.get().callWifiConnecting();
            }
        }
    }

    private void handlerScanResults() {
        if (Build.VERSION.SDK_INT > 22 && (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        List<ScanResult> results = wifiUtils.getScanResults();
        if (results != null && results.size() > 0) {
            Log.d("wwq", "results.size():  " + results.size());
            for (WeakReference<Presenter> presenter1 : weakReferences) {
                if (presenter1.get() != null) {
                    presenter1.get().callScanResult(results.get(0).SSID);
                }
            }
        } else {
            Log.d("wwq", "results is null");
        }

    }

    private void handlerWifiStateChanged(int intExtra) {

        switch (intExtra) {
            case WifiManager.WIFI_STATE_ENABLED:
                scanWifi();
                break;
            case WifiManager.WIFI_STATE_DISABLED:
                break;
            case WifiManager.WIFI_STATE_DISABLING:
            case WifiManager.WIFI_STATE_UNKNOWN:
            case WifiManager.WIFI_STATE_ENABLING:
                break;
        }

    }

    private void scanWifi() {
        if (!wifiUtils.isWifiEnabele()) {
            return;
        }
        scanHandler.startScan();
    }

}
