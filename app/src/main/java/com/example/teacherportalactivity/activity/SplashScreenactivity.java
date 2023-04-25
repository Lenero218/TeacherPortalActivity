
package com.example.teacherportalactivity.activity;


import static com.example.teacherportalactivity.model.ResponseString.LOGIN_STATUS;
import static com.example.teacherportalactivity.model.ResponseString.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PermissionHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ActivityData;
import com.example.teacherportalactivity.model.CountData;
import com.example.teacherportalactivity.model.ResponseCode;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.utils.CommonUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashScreenactivity extends AppCompatActivity {
    private static final long SPLASH_MILLIS = 1000;
    private CommonUtils mCommonUtils;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private AppConnectivityManager mAppConnectivityManager;
    private PreferenceHelper pHelper;
    private Dialog dialog;
    public static String[] RequiredPermission;
    private PermissionHelper permissionHelper;
    private ShowDialog mShowDialog;
    private String currentVersion, latestVersion;
    private TextView tvVersion;
    private DataBaseHelper dbHelper;
    private AppUpdateManager appUpdateManager;
    private String mLastUpdateTime;
    public static double latitude, longitude;
    int REQUEST_CHECK_SETTINGS = 100;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates = false;
    private static final int REQUEST_UPDATE_CODE = 22;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(SplashScreenactivity.this).screenCaptureFLAGSECURE();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        pHelper = new PreferenceHelper(this);
        dbHelper = new DataBaseHelper(this);

        mCommonUtils = new CommonUtils(this);
        tvVersion = findViewById(R.id.tvVersion);
        mShowDialog = new ShowDialog(SplashScreenactivity.this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        String AndroidVersion = mCommonUtils.getAndroidApiVersion();
        String AndroidVersionName = mCommonUtils.getDeviceName();
        pHelper.setString(ResponseString.APP_VERSION, mCommonUtils.VersionName);
        pHelper.setString(ResponseString.ANDROID_API, AndroidVersion);
        pHelper.setString(ResponseString.ANDROID_API_NAME, AndroidVersionName);
        pHelper.setString(ResponseString.APP_TYPE, ResponseString.APP_CODE);
        pHelper.setString(ResponseString.DEVICE_ID, CommonUtils.getDeviceUUID(this));
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
     /*   pHelper.setString("eng_sam_p1_coulddo", "5");
        pHelper.setString("eng_sam_p2_mustdo", "2");
        pHelper.setString("eng_sam_p2_shouldo", "1");
        pHelper.setString("eng_sam_p3_shoulddo", "2");
        pHelper.setString("eng_sam_p3_mustdo", "1");
        pHelper.setString("eng_sam_p4_shoulddo", "2");
        pHelper.setString("eng_sam_p4_coulddo", "1");
        pHelper.setString("eng_sam_p4_mustdo", "2");
        pHelper.setString("eng_sam_p5_mustdo", "5");
        pHelper.setString("eng_sam_p6_mustdo", "3");
        pHelper.setString("eng_sam_p6_coulddo", "1");
        pHelper.setString("eng_sam_p7_mustdo", "2");
        pHelper.setString("eng_sam_p7_coulddo", "1");
        pHelper.setString("eng_sam_p8_mustdo", "2");
        pHelper.setString("eng_sam_p9_mustdo", "2");
        pHelper.setString("eng_sam_p7_shoulddo", "1");
        pHelper.setString("eng_sam_p10_mustdo", "1");
        pHelper.setString("eng_sam_p10_shoulddo", "1");
        pHelper.setString("eng_sam_p11_mustdo", "2");
        pHelper.setString("eng_sam_p11_shoulddo", "2");*/
        RequiredPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (pHelper.getString(ResponseString.FCM_TOKEN, ResponseString.BLANK).equals(ResponseString.BLANK)) {
            try {
                PreferenceHelper pHelper = new PreferenceHelper(this);
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.i("fcm token id", refreshedToken);
                pHelper.setString(ResponseString.FCM_TOKEN, refreshedToken);
            } catch (Exception ex) {
            }
        }
        Log.i("fcm token id", pHelper.getString(ResponseString.FCM_TOKEN, ""));
        tvVersion.setText("V:" + pHelper.getString(ResponseString.APP_VERSION, ResponseString.BLANK));
        //getCurrentVersion();
    }

//    private void launchHomeScreen() {
//        pHelper.setFirstTimeLaunch(false);
//        startActivity(new Intent(SplashScreenactivity.this, SelectClass.class));
//        finish();
//    }

    @Override
    protected void onResume() {
        super.onResume();

        checkUpdate();
        mResumeWork();

    }

    private void checkUpdate() {

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                startUpdateFlow(appUpdateInfo);
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startUpdateFlow(appUpdateInfo);
            } else {
                mResumeWork();
            }
        });
    }

    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, REQUEST_UPDATE_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    //@anurag This method is define for Marshmallow and above required permission
    private void mCheckPermission() {
        permissionHelper = new PermissionHelper(this, RequiredPermission, ResponseCode.PERMISSION_REQUEST_CODE);
        permissionHelper.request(new PermissionHelper.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(SplashScreenactivity.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();
            }

            @Override
            public void onPermissionDenied() {
                mShowDialog.PermissionDenied(permissionHelper.shouldShowRational(RequiredPermission));
            }

            @Override
            public void onPermissionDeniedBySystem() {
                mShowDialog.PermissionDenied(permissionHelper.shouldShowRational(RequiredPermission));
            }
        });

    }

    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(SplashScreenactivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(SplashScreenactivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    private void mResumeWork() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!pHelper.getBoolean(ResponseString.LOGIN_STATUS, false)) {
                    Intent intent = new Intent(SplashScreenactivity.this, Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreenactivity.this, SelectClass.class);
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_MILLIS);
    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(getString(R.string.check_update_link) + "&hl=en").timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) {
                        double current = Double.parseDouble(currentVersion);
                        double latest = Double.parseDouble(latestVersion);
                        if (latest > current) {
                            showUpdateDialog();
                        } else {
                            mResumeWork();
                        }
                    }
                } else {
                    mResumeWork();
                }
            } else {
                mResumeWork();
            }
            super.onPostExecute(jsonObject);
        }
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_message));
        builder.setMessage(getString(R.string.update_message));
        builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.market_link))));
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog = builder.show();
    }

    public void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        //startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();

    }


    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }
        }
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();

            // location last updated time
//            latlong.setText("Last updated on: " + mLastUpdateTime);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }
}
