package com.appnirman.praxis2k16;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appnirman.praxis2k16.LoginAndRegister.UIClasses.MainLogin;
import com.appnirman.praxis2k16.LoginAndRegister.Utilities.AppnirmanServer.SessionManagement;
import com.appnirman.praxis2k16.Utils.BluetoothConnectivityReceiver;
import com.appnirman.praxis2k16.Utils.ConnectivityReceiver;
import com.appnirman.praxis2k16.Utils.Fab;
import com.appnirman.praxis2k16.adapter.NotificationListAdapter;
import com.appnirman.praxis2k16.model.NotificationItem;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener,BluetoothConnectivityReceiver.BluetoothConnectivityListener{

    private String TAG = MainActivity.class.getSimpleName();
    private SessionManagement session;
    private GoogleApiClient mGoogleApiClient;
    private MaterialSheetFab<Fab> materialSheetFab;
    private Context mContext;
    private String timingWishes;
    private RecyclerView mRecyclerView;
    private NotificationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Session class instance
        session = new SessionManagement(getApplicationContext());

        if (!session.checkLogin()) {
            Intent i = new Intent(this, MainLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_main);

            // Manually checking internet connection
            checkConnection();

            setStatusBarTranslucent(true);
            getCurrentTime();

            mContext = this;
            TextView lblName = (TextView) findViewById(R.id.lblName);
            ImageView imageView = (ImageView) findViewById(R.id.iv_top);
//        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
            // Initialize recycler view
            mRecyclerView = (RecyclerView) findViewById(R.id.ll_mid);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

            Fab fab = (Fab) findViewById(R.id.fab);
            View sheetView = findViewById(R.id.fab_sheet);
            View overlay = findViewById(R.id.overlay);
            overlay.setBackgroundColor(getResources().getColor(R.color.dim_foreground_material_lightt));
            int sheetColor = getResources().getColor(R.color.grey_0);
            int fabColor = getResources().getColor(R.color.colorPrimary);

            ArrayList<String>title = new ArrayList<>();
            title.add("Santosh");
            title.add("Suresh");
            title.add("Gaikar");

            ArrayList<String> url  = new ArrayList<>();

            url.add("http://image.shutterstock.com/z/stock-photo-group-of-indian-asian-college-students-studying-over-the-grass-in-the-campus-115894858.jpg");
            url.add("http://il9.picdn.net/shutterstock/videos/5906375/thumb/1.jpg");
            url.add("http://timesofoman.com/uploads/imported_images/2015/06/19/dtl_26_8_2013_6_34_33.jpg");


            ArrayList<NotificationItem> feedsList = new ArrayList<>();
            for (int i = 0; i < title.size(); i++) {
                NotificationItem item = new NotificationItem();
                item.setTitle(title.get(i));
                item.setThumbnail(url.get(i));

                feedsList.add(item);
            }

            adapter = new NotificationListAdapter(MainActivity.this, feedsList);
            mRecyclerView.setAdapter(adapter);

            // Initialize material sheet FAB
            materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                    sheetColor, fabColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Transition exitTrans = new Explode();
                exitTrans.setDuration(1000);
                getWindow().setExitTransition(exitTrans);

                Transition reenterTrans = new Slide();
                reenterTrans.setDuration(1000);
                getWindow().setReenterTransition(reenterTrans);
            }

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(Plus.API)
                    .addScope(new Scope(Scopes.PLUS_ME))
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(Plus.SCOPE_PLUS_LOGIN).build();



            // get user data from session
            HashMap<String, String> user = session.getUserDetails();

            // name
            String name = user.get(SessionManagement.KEY_NAME);

            // email
            String email = user.get(SessionManagement.KEY_EMAIL);
            String[] splitedName = name.split("\\s+");
            splitedName[0] = String.valueOf(splitedName[0].charAt(0)).toUpperCase() + splitedName[0].substring(1, splitedName[0].length());
            lblName.setText(Html.fromHtml("<b>" + timingWishes + " " + splitedName[0] + "</b>"));
//            lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));

            Glide
                    .with(mContext)
//                    .load("https://monitor-cdn5.icef.com/wp-content/uploads/2015/06/indian-student.jpg")
                    .load("http://www.wallpaperlite.com/images/VedioImages/asian-pakistani-inian-School-Education-girls-boys-talking-college-students-talking.jpg")
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(imageView);


            materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
                @Override
                public void onShowSheet() {
                    // Called when the material sheet's "show" animation starts.
                }

                @Override
                public void onSheetShown() {
                    // Called when the material sheet's "show" animation ends.
                }

                @Override
                public void onHideSheet() {
                    // Called when the material sheet's "hide" animation starts.
                }

                public void onSheetHidden() {
                    // Called when the material sheet's "hide" animation ends.
                }
            });

            // Set material sheet item click listeners
            findViewById(R.id.fab_sheet_item_events).setOnClickListener(MainActivity.this);
            findViewById(R.id.fab_sheet_item_messages).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_map).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_notification).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_sponsors).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_about).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_settings).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_feedback).setOnClickListener(this);
            findViewById(R.id.fab_sheet_item_nearby).setOnClickListener(this);
            findViewById(R.id.btnLogout).setOnClickListener(this);
        }
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
        MyApplication.getInstance().setBluetoothConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }

    @Override
    public void onBluetoothConnectionChanged(boolean isBluetoothConnected) {
        showBluetoothToast(isBluetoothConnected);
    }

    private void showBluetoothToast(boolean isBluetoothConnected) {
        String message;
        if (isBluetoothConnected) {
            message = "Good! Connected to bluetooth";
        } else {
            message = "Sorry! Not connected to bluetooth";
        }
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void showToast(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 4 && timeOfDay < 12) {
            timingWishes = "Good Morning,";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            timingWishes = "Good Afternoon,";
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            timingWishes = "Good Evening,";
        } else if (timeOfDay >= 0 && timeOfDay < 4) {
            timingWishes = "Good Evening,";
        }

    }

    private void logoutUser() {
        session.logoutUser();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
            Intent i = new Intent(MainActivity.this, MainLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(MainActivity.this, MainLogin.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
//            executeCheckLogin();
        }
    }

    private void signOutFromFB() {
        LoginManager.getInstance().logOut();
//        executeCheckLogin();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
//        updateWithToken(AccessToken.getCurrentAccessToken());
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_sheet_item_events:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.events), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_messages:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.messages), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_map:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.map), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_notification:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.notification), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_sponsors:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.sponsors), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_about:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.about_us), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_settings:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.settings), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_feedback:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.feedback), Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_sheet_item_nearby:
                materialSheetFab.hideSheet();
                Toast.makeText(mContext, getResources().getString(R.string.nearby), Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnLogout:
                materialSheetFab.hideSheet();
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("You really want to logout?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                String loginType = null;
                                if (session.checkLogin()) {
                                    // get user data from session
                                    HashMap<String, String> user = session.getLoginType();
                                    // name
                                    loginType = user.get(SessionManagement.KEY_LOGIN_TYPE);
                                }
                                if (loginType.equals(SessionManagement.GoogleLoginType)) {
                                    signOutFromGplus();
                                } else if (loginType.equals(SessionManagement.FacebookLoginType)) {
                                    signOutFromFB();
                                }
                                logoutUser();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();


                break;

        }
    }



}