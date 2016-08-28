package com.appnirman.praxis2k16.LoginAndRegister.UIClasses;

/**
 * Created by santosh on 14-06-2015.
 * this file is used to process login.
 */

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.appnirman.praxis2k16.LoginAndRegister.Utilities.AppnirmanServer.SessionManagement;
import com.appnirman.praxis2k16.MainActivity;
import com.appnirman.praxis2k16.R;
import com.appnirman.praxis2k16.Utils.AppUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainLogin extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnPermissionCallback, ResultCallback<People.LoadPeopleResult> {

    Context mContext;
    Context applicationContext;
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "LoginActivity";
    private boolean mSignInClicked;
    /**
     * Called when the activity is first created.
     */

    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String email;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private SignInButton btnSignIn;
    private AccessTokenTracker accessTokenTracker;
    private SessionManagement session;


    private final static String[] MULTI_PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.GET_ACCOUNTS
    };
    private PermissionHelper permissionHelper;
    private String[] neededPermission;
    private AlertDialog builder;
    private SweetAlertDialog pDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        FacebookSdk.sdkInitialize(mContext);
        setContentView(R.layout.activity_login);

        setStatusBarTranslucent(true);

        applicationContext = getApplicationContext();
        permissionHelper = PermissionHelper.getInstance(this);
        permissionHelper.setForceAccepting(true).request(MULTI_PERMISSIONS);

        // Session class instance
        session = new SessionManagement(getApplicationContext());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition exitTrans = new Explode();
            exitTrans.setDuration(1000);
            getWindow().setExitTransition(exitTrans);

            Transition reenterTrans = new Slide();
            reenterTrans.setDuration(1000);
            getWindow().setReenterTransition(reenterTrans);
        }
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        // Button click listeners
        btnSignIn.setOnClickListener(this);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(new Scope(Scopes.PLUS_ME))
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();


        loginButton.setReadPermissions("public_profile", "email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken() +
                                "Permissions: "
                                + loginResult.getRecentlyGrantedPermissions()
                );
                AccessToken accessToken = loginResult.getAccessToken();

                personPhotoUrl = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                getFBProfileInformation(accessToken);
                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.show();

            }

            @Override
            public void onCancel() {
                System.out.println("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Login attempt failed.");
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                System.out.println("inOnCreate access token" + newAccessToken);
                updateWithToken(newAccessToken);
            }
        };
        updateWithToken(AccessToken.getCurrentAccessToken());
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult peopleResult) {

    }

    /*/---------------------------overrided methods--------------------------------------------------*/

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        callbackManager.onActivityResult(requestCode, responseCode, intent);
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Log.i(TAG, "User is connected");
        // Get user's information
        getProfileInformationGoogle();

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.show();
                int hasFineLocationPermission = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hasFineLocationPermission = checkSelfPermission(Manifest.permission.READ_CONTACTS);
                    if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
                        AppUtils.showNoContactPermissionDialogue(mContext);
                        return;
                    }
                }
                signInWithGplus();
                break;

        }
    }

    /*-------------------------Functions needed to sign in in google and facebook---------------------------------------------*/

    //       SignIn into google
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }


    //         * Method to resolve any signin errors
    private void resolveSignInError() {
        if (mConnectionResult != null) {
            if (mConnectionResult.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
    }


    //Fetching user's information name, email, profile pic
    private void getProfileInformationGoogle() {
        try {
            Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                Log.d("PERSON DATA", "Current person: name="
                        + currentPerson.getDisplayName()
                        + ", has birthday = "
                        + (currentPerson.hasBirthday() ? "yes, it is "
                        + currentPerson.getBirthday() : "no") +
                        ", has gender = "
                        + (currentPerson.hasGender() ? "yes, it is "
                        + currentPerson.getGender() : "no, has gender = "));

//                setUserDetailsToServer(UrlList.GoogleLoginTyp
                RedirectToMainActivity(SessionManagement.GoogleLoginType);

            } else {
                signOutFromGplus();
                revokeGplusAccess();
                Toast.makeText(getApplicationContext(),
                        "Google Sign in is not available, Please try other options!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        signOutFromGplus();
//        revokeGplusAccess();
    }

    private void RedirectToMainActivity(String LoginType) {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
        session.createLoginType(LoginType);
        session.createLoginSession(personName, email);

        // Staring MainActivity
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainLogin.this);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i,options.toBundle());
            finish();
        }else{
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    public void getFBProfileInformation(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.e("GraphResponse", "-------------" + response.toString());
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            try {
                                JSONObject profile = response.getJSONObject();
                                // getting name of the user
                                personName = profile.getString("name");
                                // getting email of the user
                                email = profile.getString("email");
                                RedirectToMainActivity(SessionManagement.FacebookLoginType);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,gender,birthday,email,religion");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
//            getFBProfileInformation(currentAccessToken);
//            updateUI(true,"FB");//Call Server api here and pass user details
        } else {
//            updateUI(false,"FB");
        }
    }

//    private void setUserDetailsToServer(String LoginType) {
//
//        final ProgressDialog pDialog = new ProgressDialog(MainLogin.this);
//        pDialog.setTitle("Contacting Servers");
//        pDialog.setMessage("Logging in ...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//        pDialog.show();
//
//        client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("loginType", LoginType);
//        params.add("name", personName);
//        params.add("email", email);
//        params.add("dob", dob);
//        params.add("gender", gender);
//        params.add("religion", religion);
//        params.add("photoUrl", personPhotoUrl);
//
//        client.post(mContext, UrlList.GoogleLoginUrl, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String responcestr = new String(responseBody);
//                try {
//                    JSONObject json = new JSONObject(responcestr);
//                    if (json.getString(KEY_SUCCESS) != null) {
//
//                        String res = json.getString(KEY_SUCCESS);
//                        System.out.println("Responce in login:");
//                        System.out.println(json.toString());
//
//                        if (Integer.parseInt(res) == 1) {
//                            pDialog.setMessage("Loading User Space");
//                            pDialog.setTitle("Getting Data");
//                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                            JSONObject json_user = json.getJSONObject("user");
//                            /**
//                             * Clear all previous data in SQlite database.
//                             **/
//                            UserFunctions logout = new UserFunctions();
//                            logout.logoutUser(getApplicationContext());
//                            session.createLoginSession(json_user.getString("loginType"));
//                            db.addUser(json_user.getString(KEY_FIRSTNAME),
//                                    json_user.getString(KEY_EMAIL),
//                                    json_user.getString(KEY_MOBILENO),
//                                    json_user.getString(KEY_REFERRAL),
//                                    json_user.getString(KEY_PASSWORD),
//                                    json_user.getString(KEY_DOB),
//                                    json_user.getString(KEY_USERID),
//                                    json_user.getString(KEY_CNOTIFICATION),
//                                    json_user.getString(KEY_ONOTIFICATION), "1");
//
//                            if (!json_user.getString(KEY_WARDID).equals("0")) {
//                                String WardName = db.getWardName(json_user.getString(KEY_WARDID));
//                                System.out.println("WARDNAME:WARDID" + WardName + ":" + json_user.getString(KEY_WARDID));
//                                db.addChosenWard(json_user.getString(KEY_WARDID), WardName);
//                            } else {
//                                //do nothing
//                            }
//                            if (!json_user.getString(KEY_PINCODE).equals("0")) {
//                                db.addPincode(json_user.getString(KEY_PINCODE));
//                            } else {
//                                //do nothing
//                            }
//
//                            RegisterUser(email);
//                            System.out.println("%%%%%%%  " + GcmRegId);
//
//                            /**
//                             *If JSON array details are stored in SQlite it launches the User Panel.
//                             **/
//                            Intent upanel = new Intent(getApplicationContext(), CheckLogin.class);
//                            upanel.putExtra("login", "login");
//                            upanel.putExtra("GcmRegId", GcmRegId);
//                            upanel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            pDialog.dismiss();
//                            startActivity(upanel);
//                            /**
//                             * Close Login Screen
//                             **/
//                            finish();
//                        } else {
//
//                            pDialog.dismiss();
//                            TVloginErrorMsg.setText(json.getString("errormsg"));//"Incorrect username/password");
//                            if (json.getString("errormsg").equals("User inserted but not verified.")) {
//                                pDialog.setMessage("Loading User Space");
//                                pDialog.setTitle("Getting Data");
//                                pDialog.show();
//                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
//                                JSONObject json_user = json.getJSONObject("user");
//                                /**
//                                 * Clear all previous data in SQlite database.
//                                 **/
//                                UserFunctions logout = new UserFunctions();
//                                logout.logoutUser(getApplicationContext());
//                                session.createLoginSession(json_user.getString("loginType"));
//                                db.addUser(json_user.getString(KEY_FIRSTNAME),
//                                        json_user.getString(KEY_EMAIL),
//                                        json_user.getString(KEY_MOBILENO),
//                                        json_user.getString(KEY_REFERRAL),
//                                        json_user.getString(KEY_PASSWORD),
//                                        json_user.getString(KEY_DOB),
//                                        json_user.getString(KEY_USERID),
//                                        json_user.getString(KEY_CNOTIFICATION),
//                                        json_user.getString(KEY_ONOTIFICATION), null);
//
//                                if (!json_user.getString(KEY_WARDID).equals("0")) {
//                                    String WardName = db.getWardName(json_user.getString(KEY_WARDID));
//                                    System.out.println("WARDNAME:WARDID" + WardName + ":" + json_user.getString(KEY_WARDID));
//                                    db.addChosenWard(json_user.getString(KEY_WARDID), WardName);
//                                } else {
//                                    //do nothing
//                                }
//                                if (!json_user.getString(KEY_PINCODE).equals("0")) {
//                                    db.addPincode(json_user.getString(KEY_PINCODE));
//                                } else {
//                                    //do nothing
//                                }
//                                RegisterUser(email);
//                                pDialog.dismiss();
//                                Intent registered = new Intent(getApplicationContext(), OtpActivity.class);
//                                registered.putExtra("GcmRegId", GcmRegId);
//                                registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                registered.putExtra("OTPTIMER", "1");
//                                pDialog.dismiss();
//                                startActivity(registered);
//                                finish();
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                pDialog.dismiss();
//                System.out.println("Responce in login error:" + error.toString());
//                Toast.makeText(mContext, "Server Unavailable", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        updateWithToken(AccessToken.getCurrentAccessToken());
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Revoking access from google
     */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("REVOKETAG", "User access revoked!");
                            mGoogleApiClient.connect();
                        }

                    });
        }
    }

    /**
     * Sign-out from google
     */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

//------------------------------------------------persmissionhelper---------------------------------------

    @Override
    public void onPermissionGranted(String[] permissionName) {
        Log.i("onPermissionGranted", "Permission(s) " + Arrays.toString(permissionName) + " Granted");
    }

    @Override
    public void onPermissionDeclined(String[] permissionName) {
        Log.i("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");
    }

    @Override
    public void onPermissionPreGranted(String permissionsName) {
        Log.i("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");
    }

    @Override
    public void onPermissionNeedExplanation(String permissionName) {
        Log.i("NeedExplanation", "Permission( " + permissionName + " ) needs Explanation");
        neededPermission = PermissionHelper.declinedPermissions(this, MULTI_PERMISSIONS);
        StringBuilder builder = new StringBuilder(neededPermission.length);
        if (neededPermission.length > 0) {
            for (String permission : neededPermission) {
                builder.append(permission).append("\n");
            }
        }
//            result.setText("Permission( " + builder.toString() + " ) needs Explanation");
        AlertDialog alert = getAlertDialog(neededPermission, builder.toString());
        if (!alert.isShowing()) {
            alert.show();
        }

    }

    @Override
    public void onPermissionReallyDeclined(String permissionName) {
        Log.i("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
    }

    @Override
    public void onNoPermissionNeeded() {
        Log.i("onNoPermissionNeeded", "Permission(s) not needed");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public AlertDialog getAlertDialog(final String[] permissions, final String permissionName) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this)
                    .setTitle("Permission Needs Explanation").create();
        }
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Request", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionHelper.requestAfterExplanation(permissions);
            }
        });
        builder.setMessage("To use Google login we need permissions for accessing CONTACTS");
        return builder;
    }

}