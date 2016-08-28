package com.appnirman.praxis2k16.Utils;

/**
 * Created by santy on 29-08-2016.
 */

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothConnectivityReceiver
        extends BroadcastReceiver {

    public static BluetoothConnectivityListener bluetoothConnectivityListener;

    public BluetoothConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {
            boolean isBluetoothAvailable=false;
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                isBluetoothAvailable = false;
            }else if(mBluetoothAdapter.isEnabled()){
                isBluetoothAvailable = true;
            }

            if( bluetoothConnectivityListener != null){
                bluetoothConnectivityListener.onBluetoothConnectionChanged(isBluetoothAvailable);
            }


        }
    }

    public static boolean isBluetoothConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Boolean isBluetoothAvailableStatus=null;
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                isBluetoothAvailableStatus = false;
            } else if (!mBluetoothAdapter.isEnabled()) {
                isBluetoothAvailableStatus = true;
            }
        }
        return isBluetoothAvailableStatus;
    }

    public interface BluetoothConnectivityListener {
        void onBluetoothConnectionChanged(boolean isBluetoothConnected);
    }
}