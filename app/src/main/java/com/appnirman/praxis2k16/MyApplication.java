package com.appnirman.praxis2k16;

import android.app.Application;

import com.appnirman.praxis2k16.Utils.BluetoothConnectivityReceiver;
import com.appnirman.praxis2k16.Utils.ConnectivityReceiver;

/**
 * Created by santy on 27-08-2016.
 */
public class MyApplication extends Application {

//    public void onCreate(){
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        printKeyHash();
//    }
//    public void printKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("your package name", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("Hash Key", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//    }
private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
    public void setBluetoothConnectivityListener(BluetoothConnectivityReceiver.BluetoothConnectivityListener listener) {
        BluetoothConnectivityReceiver.bluetoothConnectivityListener = listener;
    }
}
