package com.appnirman.praxis2k16.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by santy on 27-08-2016.
 */
public class AppUtils {

    private static final int REQUEST_PERMISSION_SETTING = 962;
    private static SweetAlertDialog contactPermissionDialogue;

    public static void showNoContactPermissionDialogue(final Context mContext) {
        contactPermissionDialogue = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        contactPermissionDialogue.setCancelable(false);
        contactPermissionDialogue.setTitleText("Permission not granted")
                .setContentText("Do you want to go to the settings menu to grant permission?")
                .setCancelText("No")
                .setConfirmText("Settings")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                        intent.setData(uri);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        contactPermissionDialogue.dismiss();
//                        ((Activity)context).finish();
                    }
                })
                .show();
    }
}
