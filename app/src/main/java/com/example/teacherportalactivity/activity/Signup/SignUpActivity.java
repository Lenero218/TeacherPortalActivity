package com.example.teacherportalactivity.activity.Signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.teacherportalactivity.CustomView.CustomLoadingButton;
import com.example.teacherportalactivity.CustomView.CustomToast;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.Login;
import com.example.teacherportalactivity.activity.SelectClass;
import com.example.teacherportalactivity.activity.SubjectActivity;
import com.example.teacherportalactivity.activity.Utils;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.SpinnerSelectionListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.CityDetails;
import com.example.teacherportalactivity.model.ResponseData;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SchoolData;
import com.example.teacherportalactivity.model.StateDetails;
import com.example.teacherportalactivity.model.city;
import com.example.teacherportalactivity.model.classes;
import com.example.teacherportalactivity.model.school;
import com.example.teacherportalactivity.model.state;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SpinnerSelectionListener {


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private SignUpViewModel signUpViewModel;
    private CustomLoadingButton btnRegister;
    private PreferenceHelper pHelper;
    private CommonUtils mCommonUtils;
    private static ArrayList<String> mStateArrayList = new ArrayList<>();
    private static ArrayList<String> mCityArrayList = new ArrayList<>();
    private static ArrayList<String> mSchoolArrayList = new ArrayList<>();
    private static ArrayList<String> mClassArrayList = new ArrayList<>();
    private static ArrayList<String> mSectionArrayList = new ArrayList<>();
    private static List<StateDetails> mStateDetails = new ArrayList<>();
    private static List<CityDetails> mCityDetails = new ArrayList<>();
    private static List<SchoolData> mSchoolDetails = new ArrayList<>();
    private EditText etName, etClass, etCity, etSchool, etSchoolName, etSection, etState, etMobile, etEmail, etRollNo;
    private ShowDialog mShowDialog;
    private static String mSelectedStateCode, mSelectedCity, mSelectedSchoolName, mSelectedSchoolCode;
    private CustomLoadingButton mStateLoadingButton, mCityLoadingButton, mSchoolLoadingButton, mClassLoadingButton;
    private ImageView mImageState, mImageCity, mImageSchool, mImageClass;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private TextInputLayout NameWrapper, ClassWrapper, StateWrapper, CityWrapper, MobileWrapper, EmailWrapper;
    private LinearLayout layoutRegistration;
    private Animation shakeAnimation;
    private ProgressDialog progressDialog;
    private Button btnRegister2;
    private LinearLayout tvAlreadyUser;
    static final int REQUEST_LOCATION = 15;
    public static double latitude, longitude;
    private static SignUpActivity instance;

    private static final String TAG = SubjectActivity.class.getSimpleName();
    int REQUEST_CHECK_SETTINGS = 100;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;
    private String registractionFrom = "";
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(SignUpActivity.this).screenCaptureFLAGSECURE();
        signUpViewModel =
                ViewModelProviders.of(this).get(SignUpViewModel.class);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addSection();
        instance = this;
        if (getIntent() != null) {
            registractionFrom = getIntent().getStringExtra("registractionFrom");
        }
        init();
        latitude = 0.0;
        longitude = 0.0;
        pHelper = new PreferenceHelper(this);
        mCommonUtils = new CommonUtils(this);
        mShowDialog = new ShowDialog(this);
        dbHelper = new DataBaseHelper(this);
        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        layoutRegistration = findViewById(R.id.layoutRegistration);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister2 = findViewById(R.id.btnRegister2);
        tvAlreadyUser = findViewById(R.id.tvAlreadyUser);
        etName = findViewById(R.id.etName);
        etState = findViewById(R.id.etState);
        etCity = findViewById(R.id.etCity);
        etSchool = findViewById(R.id.etSchool);
        etSchoolName = findViewById(R.id.etSchoolName);
        etClass = findViewById(R.id.etClass);
        etSection = findViewById(R.id.etSection);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etRollNo = findViewById(R.id.etRollNo);
        mStateLoadingButton = findViewById(R.id.mStateLoadingButton);
        mCityLoadingButton = findViewById(R.id.mCityLoadingButton);
        mSchoolLoadingButton = findViewById(R.id.mSchoolLoadingButton);
        mClassLoadingButton = findViewById(R.id.mClassLoadingButton);
        mImageState = findViewById(R.id.mImageState);
        mImageCity = findViewById(R.id.mImageCity);
        mImageSchool = findViewById(R.id.mImageSchool);
        mImageClass = findViewById(R.id.mImageClass);
        NameWrapper = findViewById(R.id.NameWrapper);
        ClassWrapper = findViewById(R.id.ClassWrapper);
        StateWrapper = findViewById(R.id.StateWrapper);
        CityWrapper = findViewById(R.id.CityWrapper);
        MobileWrapper = findViewById(R.id.MobileWrapper);
        EmailWrapper = findViewById(R.id.EmailWrapper);

        setUpDrawableToEditText();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Loading...");

        signUpViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //text.setText(s);
            }
        });
        btnRegister2.setOnClickListener(this);
        tvAlreadyUser.setOnClickListener(this);
        getStateList();
        mSetTouch(etState);
        mSetTouch(etCity);
        mSetTouch(etClass);
        mSetTouch(etSchool);
        mSetTouch(etSection);
    }

    private void setUpDrawableToEditText() {

        CommonUtils.setCompactDrawableToView(this, etState, R.drawable.up_and_down, 2);
        CommonUtils.setCompactDrawableToView(this, etCity, R.drawable.up_and_down, 2);
        CommonUtils.setCompactDrawableToView(this, etSchool, R.drawable.up_and_down, 2);
        CommonUtils.setCompactDrawableToView(this, etClass, R.drawable.up_and_down, 2);
        CommonUtils.setCompactDrawableToView(this, etSection, R.drawable.up_and_down, 2);
    }

    public static SignUpActivity getInstance() {
        return instance;
    }

    private void addSection() {
        mSectionArrayList = new ArrayList<>();
        mSectionArrayList.add("A");
        mSectionArrayList.add("B");
        mSectionArrayList.add("C");
        mSectionArrayList.add("D");
        mSectionArrayList.add("E");
        mSectionArrayList.add("F");
        mSectionArrayList.add("G");
        mSectionArrayList.add("H");
        mSectionArrayList.add("I");
        mSectionArrayList.add("J");
        mSectionArrayList.add("K");
        mSectionArrayList.add("L");
        mSectionArrayList.add("M");
        mSectionArrayList.add("N");
        mSectionArrayList.add("O");
        mSectionArrayList.add("P");
        mSectionArrayList.add("Q");
        mSectionArrayList.add("R");
        mSectionArrayList.add("S");
        mSectionArrayList.add("T");
        mSectionArrayList.add("U");
        mSectionArrayList.add("V");
        mSectionArrayList.add("W");
        mSectionArrayList.add("X");
        mSectionArrayList.add("Y");
        mSectionArrayList.add("Z");
    }

    private void getStateList() {
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                    progressDialog.show();
                    mStateArrayList.clear();
                    mImageState.setVisibility(View.GONE);
                    mStateLoadingButton.startLoading();
                    mStateLoadingButton.setVisibility(View.VISIBLE);
                    etState.setText(R.string.please_wait);
                    etState.setEnabled(false);

                    JSONObject JSON_REQUEST = new JSONObject();
                    JSON_REQUEST.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                    JSON_REQUEST.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                    JSON_REQUEST.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                    //JSON_REQUEST.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    String url = "login";
                    Call<state> call = apiService.getState(ResponseString.AUTHERIZATION, ApiClient.BASE_URL + "getStates");
                    call.enqueue(new Callback<state>() {
                        @Override
                        public void onResponse(Call<state> call, retrofit2.Response<state> response) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mStateArrayList.add(response.body().getData().get(i).getStateName());
                                mStateDetails.add(new StateDetails(response.body().getData().get(i).getCode(), response.body().getData().get(i).getStateName()));
                            }
                            progressDialog.dismiss();
                            etState.setText(ResponseString.BLANK);
                            etState.setEnabled(true);
                            mStateLoadingButton.reset();
                            mStateLoadingButton.setVisibility(View.GONE);
                            mImageState.setVisibility(View.VISIBLE);
                            //Log.i("tag",response.toString());
                        }

                        @Override
                        public void onFailure(Call<state> call, Throwable t) {
                            // Log error here since request failed
                            Log.i("tag", t.toString());
                            progressDialog.dismiss();
                        }
                    });
                } catch (Exception ex) {
                    progressDialog.dismiss();
                }
            }
        } else {
            progressDialog.dismiss();
            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister2) {
            if (etName.getText().toString().trim().isEmpty()) {
                NameWrapper.setError(getString(R.string.name_alert));
                requestFocus(etName);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.name_alert));
                return;
            } else if (etEmail.getText().toString().trim().isEmpty() || !Utils.emailValidator(etEmail.getText().toString())) {
                EmailWrapper.setError(getString(R.string.email_alert));
                requestFocus(etEmail);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.email_alert));
                return;
            } else if (etMobile.getText().toString().trim().isEmpty() || etMobile.getText().toString().length() < 10) {
                MobileWrapper.setError(getString(R.string.mobile_alert));
                requestFocus(etMobile);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.mobile_alert));
                return;
            } else if (etState.getText().toString().trim().isEmpty()) {
                StateWrapper.setError(getString(R.string.state_alert));
                requestFocus(etState);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.state_alert));
                return;
            } else if (etCity.getText().toString().trim().isEmpty()) {
                CityWrapper.setError(getString(R.string.city_alert));
                requestFocus(etCity);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.city_alert));
                return;
            } else if (etSchool.getText().toString().trim().isEmpty()) {
                CityWrapper.setError(getString(R.string.school_alert));
                requestFocus(etSchool);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.school_alert));
                return;
            } else if (etSchool.getText().toString().equals("Others") && etSchoolName.getText().toString().trim().isEmpty()) {
                CityWrapper.setError(getString(R.string.school_alert));
                requestFocus(etSchoolName);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.school_alert));
                return;
            } else if (etClass.getText().toString().trim().isEmpty()) {
                ClassWrapper.setError(getString(R.string.class_alert));
                requestFocus(etClass);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.class_alert));
                return;
            } else if (etSection.getText().toString().trim().isEmpty()) {
                ClassWrapper.setError(getString(R.string.section_alert));
                requestFocus(etSection);
                layoutRegistration.startAnimation(shakeAnimation);
                new CustomToast().Show_Toast(this, getString(R.string.section_alert));
                return;
            } else {
                if (mAppConnectivityManager.isConnected()) {
                    if (AppWeakReferenceManager.mWeakReference.get() != null) {
                        btnRegister2.setVisibility(View.GONE);
                        //btnRegister.setVisibility(View.VISIBLE);
                        //btnRegister.startLoading();
                        progressDialog.show();
                        try {
                            HashMap hashMap = new HashMap<String, String>();
                            hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                            hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                            hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                            hashMap.put(ResponseString.REG_USER_NAME, etName.getText().toString().trim());
                            hashMap.put(ResponseString.REG_STATE, etState.getText().toString().trim());
                            hashMap.put(ResponseString.REG_CITY, etCity.getText().toString().trim());
                            hashMap.put(ResponseString.REG_SCHOOL_CODE, mSelectedSchoolCode);
                            if (mSelectedSchoolName.equals("Others")) {
                                hashMap.put(ResponseString.REG_SCHOOL_NAME, etSchoolName.getText().toString().trim());
                            } else {
                                hashMap.put(ResponseString.REG_SCHOOL_NAME, mSelectedSchoolName);
                            }
                            hashMap.put(ResponseString.REG_SECTION_NAME, etSection.getText().toString().trim());
                            hashMap.put(ResponseString.SERIES_GRADE, etClass.getText().toString().trim());
                            hashMap.put(ResponseString.ROLL_NO, etRollNo.getText().toString().trim());
                            hashMap.put(ResponseString.REG_MOBILE, etMobile.getText().toString().trim());
                            hashMap.put(ResponseString.REG_EMAIL, etEmail.getText().toString().trim());
                            hashMap.put(ResponseString.REG_DEVICE_ID, Utils.getAndroidId(getApplicationContext()));
                            hashMap.put(ResponseString.REG_DEVICE_NAME, Utils.getDeviceName());
                            hashMap.put(ResponseString.REG_APP_ID, ResponseString.REGISTRATION_APP_ID);
                            hashMap.put(ResponseString.REG_PACKAGE_NAME, getPackageName());
                            hashMap.put(ResponseString.REG_DEVICE_TYPE, ResponseString.ANDROID);
                            hashMap.put(ResponseString.LATITUDE, String.valueOf(latitude));
                            hashMap.put(ResponseString.LONGITUDE, String.valueOf(longitude));
                            //hashMap.put(ResponseString.REG_APP_FCM_TOKEN, pHelper.getString(ResponseString.FCM_TOKEN, ResponseString.BLANK));
                            hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
                            ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            String url = "login";
                            Call<ResponseData> call = apiService.registration(ResponseString.AUTHERIZATION, hashMap);
                            call.enqueue(new Callback<ResponseData>() {
                                @Override
                                public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                                    if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
                                        btnRegister.loadingSuccessful();
                                        progressDialog.dismiss();
                                        if (registractionFrom.equals("SelectClass") && etMobile.getText().toString().trim().equals(pHelper.getString(ResponseString.REG_MOBILE, ResponseString.BLANK))) {
                                            Intent intent = new Intent(SignUpActivity.this, SelectClass.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finishAffinity();
                                        } else {
                                            pHelper.setString(ResponseString.LOGIN_STATUS, "FALSE");
                                            Intent intent = new Intent(SignUpActivity.this, Login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finishAffinity();
                                        }
                                        //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                                    } else {
                                        btnRegister2.setVisibility(View.VISIBLE);
                                        progressDialog.dismiss();
                                        btnRegister.loadingFailed();
                                        btnRegister.reset();
                                        ShowDialog.showInternetAlert(SignUpActivity.this, response.body().getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseData> call, Throwable t) {
                                    btnRegister.loadingFailed();
                                    btnRegister.reset();
                                    btnRegister.setText("Re-Try");
                                    Log.i("tag", t.toString());
                                    progressDialog.dismiss();
                                    btnRegister2.setVisibility(View.VISIBLE);
                                    ShowDialog.showInternetAlert(SignUpActivity.this, t.toString());
                                }
                            });

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            btnRegister2.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                } else {
                    progressDialog.dismiss();
                    btnRegister2.setVisibility(View.VISIBLE);
                    ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
                }
            }
        } else if (v.getId() == R.id.tvAlreadyUser) {
            pHelper.setString(ResponseString.LOGIN_STATUS, "FALSE");
            Intent intent = new Intent(SignUpActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        }
    }

    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void mSetTouch(final EditText editText) {
        editText.setFocusable(false);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    switch (editText.getId()) {
                        case R.id.etState:
                            if (TextUtils.isEmpty(etName.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_name_first));
                            } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_email_first));
                            } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_mobile_first));
                            } else {
                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mStateArrayList, getResources().getString(R.string.state));
                            }
                            break;
                        case R.id.etCity:
                            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etState.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_state_first));
                            } else {
                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mCityArrayList, getResources().getString(R.string.city));
                            }
                            break;
                        case R.id.etSchool:
                            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etState.getText().toString())
                                    || TextUtils.isEmpty(etCity.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_city_first));
                            } else {
                                mShowDialog.mFillSimpleSpinnerSchool(SignUpActivity.this, editText, mSchoolArrayList, getResources().getString(R.string.school), dbHelper);
                            }
                            break;
                        case R.id.etClass:
                            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etState.getText().toString())
                                    || TextUtils.isEmpty(etCity.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_city_first));
                            } else {
                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mClassArrayList, getResources().getString(R.string._class));
                            }
                            break;
                        case R.id.etSection:
                            if (TextUtils.isEmpty(etClass.getText().toString())) {
                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_class_first));
                            } else {
                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mSectionArrayList, getResources().getString(R.string.section));
                            }
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onSectionChanged(EditText editText, String Value, int Position) {
        switch (editText.getId()) {
            case R.id.etState:
                mCheckKeyPad(SignUpActivity.this);
                etSchoolName.setVisibility(View.GONE);
                etSchoolName.setText(ResponseString.BLANK);
                etCity.setText(ResponseString.BLANK);
                etSchool.setText(ResponseString.BLANK);
                etClass.setText(ResponseString.BLANK);
                etSection.setText(ResponseString.BLANK);
                etState.setText(Value);
                String State = etState.getText().toString().trim();
                for (int i = 0; i < mStateDetails.size(); i++) {
                    if (mStateDetails.get(i).getStateName().equals(State)) {
                        mSelectedStateCode = mStateDetails.get(i).getStateCode();
                        break;
                    }
                }

                mFindCity(mSelectedStateCode);
                break;
            case R.id.etCity:
                mCheckKeyPad(SignUpActivity.this);
                etSchoolName.setVisibility(View.GONE);
                etSchoolName.setText(ResponseString.BLANK);
                etSchool.setText(ResponseString.BLANK);
                etClass.setText(ResponseString.BLANK);
                etSection.setText(ResponseString.BLANK);
                mSelectedCity = Value;
                etCity.setText(mSelectedCity);
                mFindSchool(mSelectedStateCode, mSelectedCity);
                break;
            case R.id.etSchool:

                mCheckKeyPad(SignUpActivity.this);
                etClass.setText(ResponseString.BLANK);
                etSection.setText(ResponseString.BLANK);
                String[] parts = Value.split("\nAddress:- ");
                mSelectedSchoolName = parts[0];

                if (mSelectedSchoolName.equals("Preschool")) {
                    mSelectedSchoolCode = "MCMPre000";
                    etSchoolName.setVisibility(View.GONE);
                } else if (mSelectedSchoolName.equals("Others")) {
                    mSelectedSchoolCode = "MCMOthers";
                    etSchoolName.setVisibility(View.VISIBLE);
                    etSchoolName.setText(ResponseString.BLANK);
                } else {
                    etSchoolName.setVisibility(View.GONE);
                    String mSchoolAddress = parts[1];
                    mSelectedSchoolCode = dbHelper.getSchoolCode(mSelectedStateCode, mSelectedCity, mSelectedSchoolName, mSchoolAddress);
                }

                etSchool.setText(mSelectedSchoolName);
                mFindClass();
                break;

            case R.id.etClass:
                mCheckKeyPad(SignUpActivity.this);
                etSection.setText(ResponseString.BLANK);
                etClass.setText(Value);
                break;
            case R.id.etSection:
                mCheckKeyPad(SignUpActivity.this);
                etSection.setText(Value);
                break;
        }
    }

    private void mFindCity(String mSelectedStateCode) {
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                    progressDialog.show();
                    mCityArrayList.clear();
                    mImageCity.setVisibility(View.GONE);
                    mCityLoadingButton.startLoading();
                    mCityLoadingButton.setVisibility(View.VISIBLE);
                    etCity.setText(R.string.please_wait);
                    etCity.setEnabled(false);

                    HashMap hashMap = new HashMap<String, String>();
                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
                    hashMap.put(ResponseString.CITY_STATE_CODE, mSelectedStateCode);

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    String url = "login";
                    Call<city> call = apiService.getCity(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<city>() {
                        @Override
                        public void onResponse(Call<city> call, retrofit2.Response<city> response) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mCityArrayList.add(response.body().getData().get(i).getCityName());
                                mCityDetails.add(new CityDetails(response.body().getData().get(i).getStateCode(), response.body().getData().get(i).getCityName()));
                            }
                            progressDialog.dismiss();
                            etCity.setText(ResponseString.BLANK);
                            etCity.setEnabled(true);
                            mCityLoadingButton.reset();
                            mCityLoadingButton.setVisibility(View.GONE);
                            mImageCity.setVisibility(View.VISIBLE);
                            //Log.i("tag",response.toString());
                        }

                        @Override
                        public void onFailure(Call<city> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                        }
                    });
                } catch (Exception ex) {
                    progressDialog.dismiss();
                }
            }
        } else {
            progressDialog.dismiss();
            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
        }
    }

    private void mFindSchool(String mSelectedStateCode, String mSelectedCity) {
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                    progressDialog.show();
                    mSchoolArrayList.clear();
                    mImageSchool.setVisibility(View.GONE);
                    mSchoolLoadingButton.startLoading();
                    mSchoolLoadingButton.setVisibility(View.VISIBLE);
                    etSchool.setText(R.string.please_wait);
                    etSchool.setEnabled(false);

                    HashMap hashMap = new HashMap<String, String>();
                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
                    hashMap.put(ResponseString.CITY_STATE_CODE, mSelectedStateCode);
                    hashMap.put(ResponseString.CITY_NAME, mSelectedCity);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    Call<school> call = apiService.getSchool(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<school>() {
                        @Override
                        public void onResponse(Call<school> call, retrofit2.Response<school> response) {
                            dbHelper.deleteAllSchool();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                //mSchoolArrayList.add(response.body().getData().get(i).getName());
                                mSchoolArrayList.add(response.body().getData().get(i).getName() +
                                        "\nAddress:- " + response.body().getData().get(i).getAddress());
                                String schoolCode = response.body().getData().get(i).getMcmNo();
                                String schoolName = response.body().getData().get(i).getName();
                                String schoolCity = response.body().getData().get(i).getCity();
                                String schoolStateCode = response.body().getData().get(i).getStateCode();
                                String schoolAddress = response.body().getData().get(i).getAddress();

                                dbHelper.insertSchoolListData(schoolCode, schoolName, schoolCity, schoolStateCode, schoolAddress);

                            }
                            mSchoolDetails = response.body().getData();
                            progressDialog.dismiss();
                            etSchool.setText(ResponseString.BLANK);
                            etSchool.setEnabled(true);
                            mSchoolLoadingButton.reset();
                            mSchoolLoadingButton.setVisibility(View.GONE);
                            mImageSchool.setVisibility(View.VISIBLE);
                            //Log.i("tag",response.toString());
                        }
                        @Override
                        public void onFailure(Call<school> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                        }
                    });
                } catch (Exception ex) {
                    progressDialog.dismiss();
                }
            }
        } else {
            progressDialog.dismiss();
            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
        }
    }

    private void mFindClass() {
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                    progressDialog.show();
                    mClassArrayList.clear();
                    mImageClass.setVisibility(View.GONE);
                    mClassLoadingButton.startLoading();
                    mClassLoadingButton.setVisibility(View.VISIBLE);
                    etClass.setText(R.string.please_wait);
                    etClass.setEnabled(false);

                    HashMap hashMap = new HashMap<String, String>();
                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    String url = "login";
                    Call<classes> call = apiService.getClass(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<classes>() {
                        @Override
                        public void onResponse(Call<classes> call, retrofit2.Response<classes> response) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mClassArrayList.add(response.body().getData().get(i).getSclass());
                            }
                            progressDialog.dismiss();
                            etClass.setText(ResponseString.BLANK);
                            etClass.setEnabled(true);
                            mClassLoadingButton.reset();
                            mClassLoadingButton.setVisibility(View.GONE);
                            mImageClass.setVisibility(View.VISIBLE);
                            //Log.i("tag",response.toString());
                        }

                        @Override
                        public void onFailure(Call<classes> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                        }
                    });
                } catch (Exception ex) {
                    progressDialog.dismiss();
                }
            }
        } else {
            progressDialog.dismiss();
            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
        }
    }

    private void mCheckKeyPad(Activity activity) {
        try {
            View view = activity.findViewById(android.R.id.content);
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSectionChanged(EditText editText, StringBuilder Value, int Position) {

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
                        startLocationUpdates();
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
    /**
     * Restoring values from saved instance state
     */
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


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
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

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
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
                                    rae.startResolutionForResult(SignUpActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {
                Log.e(TAG, "User agreed to make required location settings changes.");
                // Nothing to do. startLocationupdates() gets called in onResume again.
            } else {

                Log.e(TAG, "User chose not to make required location settings changes.");
                mRequestingLocationUpdates = false;
            }
        }
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

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;

    }

}


//import android.Manifest;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.example.teacherportalactivity.BuildConfig;
//import com.example.teacherportalactivity.CustomView.CustomLoadingButton;
//import com.example.teacherportalactivity.CustomView.CustomToast;
//import com.example.teacherportalactivity.R;
//import com.example.teacherportalactivity.activity.Login;
//import com.example.teacherportalactivity.activity.SubjectActivity;
//import com.example.teacherportalactivity.activity.Utils;
//import com.example.teacherportalactivity.dialogs.ShowDialog;
//import com.example.teacherportalactivity.helper.DataBaseHelper;
//import com.example.teacherportalactivity.helper.PreferenceHelper;
//import com.example.teacherportalactivity.interfaces.SpinnerSelectionListener;
//import com.example.teacherportalactivity.manager.AppConnectivityManager;
//import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
//import com.example.teacherportalactivity.model.CityDetails;
//import com.example.teacherportalactivity.model.ResponseData;
//import com.example.teacherportalactivity.model.ResponseString;
//import com.example.teacherportalactivity.model.SchoolData;
//import com.example.teacherportalactivity.model.StateDetails;
//import com.example.teacherportalactivity.model.city;
//import com.example.teacherportalactivity.model.classes;
//import com.example.teacherportalactivity.model.school;
//import com.example.teacherportalactivity.model.state;
//import com.example.teacherportalactivity.retrofit.ApiClient;
//import com.example.teacherportalactivity.retrofit.ApiInterface;
//import com.example.teacherportalactivity.utils.CommonUtils;
//import com.google.android.material.textfield.TextInputLayout;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;

//public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SpinnerSelectionListener {
//    private SignUpViewModel signUpViewModel;
//    private CustomLoadingButton btnRegister;
//    private PreferenceHelper pHelper;
//    private CommonUtils mCommonUtils;
//    private static final ArrayList<String> mStateArrayList = new ArrayList<>();
//    private static final ArrayList<String> mCityArrayList = new ArrayList<>();
//    private static final ArrayList<String> mSchoolArrayList = new ArrayList<>();
//    private static final ArrayList<String> mClassArrayList = new ArrayList<>();
//    private static ArrayList<String> mSectionArrayList = new ArrayList<>();
//    private static final List<StateDetails> mStateDetails = new ArrayList<>();
//    private static final List<CityDetails> mCityDetails = new ArrayList<>();
//    private static List<SchoolData> mSchoolDetails = new ArrayList<>();
//    private EditText etName, etClass, etCity, etSchool, etSection, etState, etMobile, etEmail;
//    private ShowDialog mShowDialog;
//    private static String mSelectedStateCode,mSelectedCity,mSelectedSchoolName,mSelectedSchoolCode;
//    private CustomLoadingButton mStateLoadingButton,mCityLoadingButton,mSchoolLoadingButton,mClassLoadingButton;
//    private ImageView mImageState,mImageCity,mImageSchool,mImageClass;
//    private AppConnectivityManager mAppConnectivityManager;
//    private AppWeakReferenceManager mAppWeakReferenceManager;
//    private TextInputLayout NameWrapper, ClassWrapper, StateWrapper, CityWrapper, MobileWrapper, EmailWrapper;
//    private LinearLayout layoutRegistration;
//    private Animation shakeAnimation;
//    private ProgressDialog progressDialog;
//    private Button btnRegister2;
//    private LinearLayout tvAlreadyUser;
//    static final int REQUEST_LOCATION = 15;
//    public static double latitude, longitude;
//    private static final String TAG = SubjectActivity.class.getSimpleName();
//    int REQUEST_CHECK_SETTINGS = 100;
//    private DataBaseHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        new CommonUtils(SignUpActivity.this).screenCaptureFLAGSECURE();
//        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_sign_up);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        addSection();
//
//        latitude=0.0;
//        longitude=0.0;
//        pHelper = new PreferenceHelper(this);
//        dbHelper = new DataBaseHelper(this);
//        mCommonUtils = new CommonUtils(this);
//        mShowDialog = new ShowDialog(this);
//        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
//        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
//        layoutRegistration = findViewById(R.id.layoutRegistration);
//        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
//        btnRegister = findViewById(R.id.btnRegister);
//        btnRegister2 = findViewById(R.id.btnRegister2);
//        tvAlreadyUser = findViewById(R.id.tvAlreadyUser);
//        etName = findViewById(R.id.etName);
//        etState = findViewById(R.id.etState);
//        etCity = findViewById(R.id.etCity);
//        etSchool = findViewById(R.id.etSchool);
//        etClass = findViewById(R.id.etClass);
//        etSection = findViewById(R.id.etSection);
//        etMobile = findViewById(R.id.etMobile);
//        etEmail = findViewById(R.id.etEmail);
//        mStateLoadingButton = findViewById(R.id.mStateLoadingButton);
//        mCityLoadingButton = findViewById(R.id.mCityLoadingButton);
//        mSchoolLoadingButton = findViewById(R.id.mSchoolLoadingButton);
//        mClassLoadingButton = findViewById(R.id.mClassLoadingButton);
//        mImageState = findViewById(R.id.mImageState);
//        mImageCity = findViewById(R.id.mImageCity);
//        mImageSchool = findViewById(R.id.mImageSchool);
//        mImageClass = findViewById(R.id.mImageClass);
//        NameWrapper = findViewById(R.id.NameWrapper);
//        ClassWrapper = findViewById(R.id.ClassWrapper);
//        StateWrapper = findViewById(R.id.StateWrapper);
//        CityWrapper = findViewById(R.id.CityWrapper);
//        MobileWrapper = findViewById(R.id.MobileWrapper);
//        EmailWrapper = findViewById(R.id.EmailWrapper);
//
//        progressDialog = new ProgressDialog(SignUpActivity.this);
//        progressDialog.setMessage("Loading...");
//
//        signUpViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                //text.setText(s);
//            }
//        });
//        btnRegister2.setOnClickListener(this);
//        tvAlreadyUser.setOnClickListener(this);
//        getStateList();
//        mSetTouch(etState);
//        mSetTouch(etCity);
//        mSetTouch(etClass);
//        mSetTouch(etSchool);
//        mSetTouch(etSection);
//    }
//
//    private void addSection() {
//        mSectionArrayList = new ArrayList<>();
//        mSectionArrayList.add("A");
//        mSectionArrayList.add("B");
//        mSectionArrayList.add("C");
//        mSectionArrayList.add("D");
//        mSectionArrayList.add("E");
//        mSectionArrayList.add("F");
//        mSectionArrayList.add("G");
//        mSectionArrayList.add("H");
//        mSectionArrayList.add("I");
//        mSectionArrayList.add("J");
//        mSectionArrayList.add("K");
//        mSectionArrayList.add("L");
//        mSectionArrayList.add("M");
//        mSectionArrayList.add("N");
//        mSectionArrayList.add("O");
//        mSectionArrayList.add("P");
//        mSectionArrayList.add("Q");
//        mSectionArrayList.add("R");
//        mSectionArrayList.add("S");
//        mSectionArrayList.add("T");
//        mSectionArrayList.add("U");
//        mSectionArrayList.add("V");
//        mSectionArrayList.add("W");
//        mSectionArrayList.add("X");
//        mSectionArrayList.add("Y");
//        mSectionArrayList.add("Z");
//    }
//
//    private void getStateList() {
//        if (mAppConnectivityManager.isConnected()){
//            if (AppWeakReferenceManager.mWeakReference.get() != null){
//                try
//                {
//                    progressDialog.show();
//                    mStateArrayList.clear();
//                    mImageState.setVisibility(View.GONE);
//                    mStateLoadingButton.startLoading();
//                    mStateLoadingButton.setVisibility(View.VISIBLE);
//                    etState.setText(R.string.please_wait);
//                    etState.setEnabled(false);
//
//                    JSONObject JSON_REQUEST = new JSONObject();
//                    JSON_REQUEST.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                    JSON_REQUEST.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                    JSON_REQUEST.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                    //JSON_REQUEST.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
//
//                    ApiInterface apiService =
//                            ApiClient.getClient().create(ApiInterface.class);
//                    String url = "login";
//                    Call<state> call = apiService.getState(ResponseString.AUTHERIZATION,ApiClient.BASE_URL+"getStates");
//                    call.enqueue(new Callback<state>() {
//                        @Override
//                        public void onResponse(Call<state> call, retrofit2.Response<state> response) {
//                            for (int i=0;i<response.body().getData().size();i++){
//                                mStateArrayList.add(response.body().getData().get(i).getStateName());
//                                mStateDetails.add(new StateDetails(response.body().getData().get(i).getCode(),response.body().getData().get(i).getStateName()));
//                            }
//                            progressDialog.dismiss();
//                            etState.setText(ResponseString.BLANK);
//                            etState.setEnabled(true);
//                            mStateLoadingButton.reset();
//                            mStateLoadingButton.setVisibility(View.GONE);
//                            mImageState.setVisibility(View.VISIBLE);
//                            //Log.i("tag",response.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Call<state> call, Throwable t) {
//                            // Log error here since request failed
//                            Log.i("tag", t.toString());
//                            progressDialog.dismiss();
//                        }
//                    });
//                }catch (Exception ex){
//                    progressDialog.dismiss();
//                }
//            }
//        }else {
//            progressDialog.dismiss();
//            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btnRegister2){
//            if (etName.getText().toString().trim().isEmpty()) {
//                NameWrapper.setError(getString(R.string.name_alert));
//                requestFocus(etName);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.name_alert));
//                return;
//            } else if (etEmail.getText().toString().trim().isEmpty() || !Utils.emailValidator(etEmail.getText().toString())) {
//                EmailWrapper.setError(getString(R.string.email_alert));
//                requestFocus(etEmail);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.email_alert));
//                return;
//            }else if (etMobile.getText().toString().trim().isEmpty() || etMobile.getText().toString().length() < 10) {
//                MobileWrapper.setError(getString(R.string.mobile_alert));
//                requestFocus(etMobile);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.mobile_alert));
//                return;
//            }else if (etState.getText().toString().trim().isEmpty()) {
//                StateWrapper.setError(getString(R.string.state_alert));
//                requestFocus(etState);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.state_alert));
//                return;
//            } else if (etCity.getText().toString().trim().isEmpty()) {
//                CityWrapper.setError(getString(R.string.city_alert));
//                requestFocus(etCity);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.city_alert));
//                return;
//            }else if (etSchool.getText().toString().trim().isEmpty()) {
//                CityWrapper.setError(getString(R.string.city_alert));
//                requestFocus(etSchool);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.school_alert));
//                return;
//            } else if (etClass.getText().toString().trim().isEmpty()) {
//                ClassWrapper.setError(getString(R.string.class_alert));
//                requestFocus(etClass);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.class_alert));
//                return;
//            }else if (etSection.getText().toString().trim().isEmpty()) {
//                ClassWrapper.setError(getString(R.string.section_alert));
//                requestFocus(etSection);
//                layoutRegistration.startAnimation(shakeAnimation);
//                new CustomToast().Show_Toast(this, getString(R.string.section_alert));
//                return;
//            } else {
//                if (mAppConnectivityManager.isConnected()) {
//                    if (AppWeakReferenceManager.mWeakReference.get() != null) {
////                        btnRegister2.setVisibility(View.GONE);
////                        btnRegister.setVisibility(View.VISIBLE);
//                        //btnRegister.startLoading();
//                        progressDialog.show();
//                        try {
//                            HashMap hashMap = new HashMap<String, String>();
//                            hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                            hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                            hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                            hashMap.put(ResponseString.REG_USER_NAME, etName.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_STATE, etState.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_CITY, etCity.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_SCHOOL_CODE, mSelectedSchoolCode);
//                            hashMap.put(ResponseString.REG_SECTION_NAME, etSection.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_CLASS_GRADE, etClass.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_MOBILE, etMobile.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_EMAIL, etEmail.getText().toString().trim());
//                            hashMap.put(ResponseString.REG_DEVICE_ID, Utils.getAndroidId(getApplicationContext()));
//                            hashMap.put(ResponseString.REG_DEVICE_NAME, Utils.getDeviceName());
//                            hashMap.put(ResponseString.REG_APP_ID, ResponseString.REGISTRATION_APP_ID);
//                            hashMap.put(ResponseString.REG_PACKAGE_NAME, getPackageName());
//                            hashMap.put(ResponseString.REG_DEVICE_TYPE, ResponseString.ANDROID);
//                            hashMap.put(ResponseString.LATITUDE, String.valueOf(latitude));
//                            hashMap.put(ResponseString.LONGITUDE, String.valueOf(longitude));
//                            //hashMap.put(ResponseString.REG_APP_FCM_TOKEN, pHelper.getString(ResponseString.FCM_TOKEN, ResponseString.BLANK));
//                            hashMap.put(ResponseString.APP_TYPE,ResponseString.APP_CODE);
//                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
//                            Call<ResponseData> call = apiService.registration(ResponseString.AUTHERIZATION,hashMap);
//                            call.enqueue(new Callback<ResponseData>() {
//                                @Override
//                                public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
//                                    if (response.body().getError().equals(false) && response.body().getCode().equals(200)){
//                                        btnRegister.loadingSuccessful();
//                                        progressDialog.dismiss();
//                                        pHelper.setString(ResponseString.LOGIN_STATUS,"FALSE");
//                                        pHelper.setString(ResponseString.CLASS_NAME,"FALSE");
//                                        Intent intent = new Intent(SignUpActivity.this, SubjectActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finishAffinity();
//                                        //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
//                                    }else {
//                                        progressDialog.dismiss();
//                                        btnRegister.loadingFailed();
//                                        btnRegister.reset();
//                                        ShowDialog.showInternetAlert(SignUpActivity.this, response.body().getMessage());
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseData> call, Throwable t) {
//                                    btnRegister.loadingFailed();
//                                    btnRegister.reset();
//                                    btnRegister.setText("Re-Try");
//                                    Log.i("tag", t.toString());
//                                    progressDialog.dismiss();
//                                    btnRegister2.setVisibility(View.VISIBLE);
//                                    ShowDialog.showInternetAlert(SignUpActivity.this, t.toString());
//                                }
//                            });
//
//                        } catch (Exception e) {
//                            progressDialog.dismiss();
//                            btnRegister2.setVisibility(View.VISIBLE);
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    progressDialog.dismiss();
//                    btnRegister2.setVisibility(View.VISIBLE);
//                    ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
//                }
//            }
//        }
//        else if (v.getId() == R.id.tvAlreadyUser){
//            pHelper.setString(ResponseString.LOGIN_STATUS,"FALSE");
//            Intent intent = new Intent(SignUpActivity.this, Login.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finishAffinity();
//        }
//    }
//
//    public void requestFocus(View view) {
//        if (view.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
//    }
//
//    private void mSetTouch(final EditText editText) {
//        editText.setFocusable(false);
//        editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    switch (editText.getId()) {
//                        case R.id.etState:
//                            if (TextUtils.isEmpty(etName.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_name_first));
//                            }else if (TextUtils.isEmpty(etEmail.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_email_first));
//                            }else if (TextUtils.isEmpty(etEmail.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_mobile_first));
//                            } else {
//                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mStateArrayList, getResources().getString(R.string.state));
//                            }
//                            break;
//                        case R.id.etCity:
//                            if (TextUtils.isEmpty(etName.getText().toString())||TextUtils.isEmpty(etState.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_state_first));
//                            } else {
//                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mCityArrayList, getResources().getString(R.string.city));
//                            }
//                            break;
//                        case R.id.etSchool:
//                            if (TextUtils.isEmpty(etName.getText().toString())||TextUtils.isEmpty(etState.getText().toString())
//                                    ||TextUtils.isEmpty(etCity.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_city_first));
//                            } else {
//                                mShowDialog.mFillSimpleSpinnerSchool(SignUpActivity.this, editText, mSchoolArrayList, getResources().getString(R.string.school),dbHelper);
//                            }
//                            break;
//                        case R.id.etClass:
//                            if (TextUtils.isEmpty(etName.getText().toString())||TextUtils.isEmpty(etState.getText().toString())
//                                    ||TextUtils.isEmpty(etCity.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_city_first));
//                            } else {
//                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mClassArrayList, getResources().getString(R.string._class));
//                            }
//                            break;
//                        case R.id.etSection:
//                            if (TextUtils.isEmpty(etClass.getText().toString())) {
//                                new CustomToast().Show_Toast(SignUpActivity.this, getString(R.string.select_class_first));
//                            } else {
//                                mShowDialog.mFillSimpleSpinner(SignUpActivity.this, editText, mSectionArrayList, getResources().getString(R.string.section));
//                            }
//                            break;
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//    public void onSectionChanged(EditText editText, String Value, int Position) {
//        switch (editText.getId()) {
//            case R.id.etState:
//                mCheckKeyPad(SignUpActivity.this);
//                etCity.setText(ResponseString.BLANK);
//                etSchool.setText(ResponseString.BLANK);
//                etClass.setText(ResponseString.BLANK);
//                etSection.setText(ResponseString.BLANK);
//                etState.setText(Value);
//                String State = etState.getText().toString().trim();
//                for (int i=0;i<mStateDetails.size();i++){
//                    if (mStateDetails.get(i).getStateName().equals(State)){
//                        mSelectedStateCode = mStateDetails.get(i).getStateCode();
//                        break;
//                    }
//                }
//
//                mFindCity(mSelectedStateCode);
//                break;
//            case R.id.etCity:
//                mCheckKeyPad(SignUpActivity.this);
//                etSchool.setText(ResponseString.BLANK);
//                etClass.setText(ResponseString.BLANK);
//                etSection.setText(ResponseString.BLANK);
//                mSelectedCity = Value;
//                etCity.setText(mSelectedCity);
//                mFindSchool(mSelectedStateCode, mSelectedCity);
//                break;
//            case R.id.etSchool:
//                mCheckKeyPad(SignUpActivity.this);
//                etClass.setText(ResponseString.BLANK);
//                etSection.setText(ResponseString.BLANK);
//                String[] parts = Value.split("\nAddress:- ");
//                mSelectedSchoolName = parts[0];
//                String mSchoolAddress = "";
//                try {
//                    mSchoolAddress = parts[1];
//                }catch (Exception ex){
//
//                }
//
//                if (mSelectedSchoolName.equals("Preschool")){
//                    mSelectedSchoolCode = "MCMPre000";
//                }else if (mSelectedSchoolName.equals("Others")){
//                    mSelectedSchoolCode = "MCMOthers";
//                }else {
//                    for (int i=0;i<mSchoolDetails.size();i++){
//                        if (mSchoolDetails.get(i).getStateCode().equals(mSelectedStateCode) && mSchoolDetails.get(i).getCity().equals(mSelectedCity) && mSchoolDetails.get(i).getName().equals(mSelectedSchoolName) && mSchoolDetails.get(i).getAddress().equals(mSchoolAddress)){
//                            mSelectedSchoolCode = mSchoolDetails.get(i).getMcmNo();
//                            break;
//                        }
//                    }
//                }
//
//                etSchool.setText(mSelectedSchoolName);
//                mFindClass();
//                break;
//            case R.id.etClass:
//                mCheckKeyPad(SignUpActivity.this);
//                etSection.setText(ResponseString.BLANK);
//                etClass.setText(Value);
//                break;
//            case R.id.etSection:
//                mCheckKeyPad(SignUpActivity.this);
//                etSection.setText(Value);
//                break;
//        }
//    }
//
//    @Override
//    public void onSectionChanged(EditText editText, StringBuilder Value, int Position) {
//
//    }
//
//
//    private void mFindCity(String mSelectedStateCode) {
//        if (mAppConnectivityManager.isConnected()){
//            if (AppWeakReferenceManager.mWeakReference.get() != null){
//                try
//                {
//                    progressDialog.show();
//                    mCityArrayList.clear();
//                    mImageCity.setVisibility(View.GONE);
//                    mCityLoadingButton.startLoading();
//                    mCityLoadingButton.setVisibility(View.VISIBLE);
//                    etCity.setText(R.string.please_wait);
//                    etCity.setEnabled(false);
//
//                    HashMap hashMap = new HashMap<String, String>();
//                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_TYPE,ResponseString.APP_CODE);
//                    hashMap.put(ResponseString.CITY_STATE_CODE, mSelectedStateCode);
//
//                    ApiInterface apiService =
//                            ApiClient.getClient().create(ApiInterface.class);
//                    String url = "login";
//                    Call<city> call = apiService.getCity(ResponseString.AUTHERIZATION,hashMap);
//                    call.enqueue(new Callback<city>() {
//                        @Override
//                        public void onResponse(Call<city> call, retrofit2.Response<city> response) {
//                            for (int i=0;i<response.body().getData().size();i++){
//                                mCityArrayList.add(response.body().getData().get(i).getCityName());
//                                mCityDetails.add(new CityDetails(response.body().getData().get(i).getStateCode(),response.body().getData().get(i).getCityName()));
//                            }
//                            progressDialog.dismiss();
//                            etCity.setText(ResponseString.BLANK);
//                            etCity.setEnabled(true);
//                            mCityLoadingButton.reset();
//                            mCityLoadingButton.setVisibility(View.GONE);
//                            mImageCity.setVisibility(View.VISIBLE);
//                            //Log.i("tag",response.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Call<city> call, Throwable t) {
//                            // Log error here since request failed
//                            progressDialog.dismiss();
//                            Log.i("tag", t.toString());
//                        }
//                    });
//                }catch (Exception ex){
//                    progressDialog.dismiss();
//                }
//            }
//        }else {
//            progressDialog.dismiss();
//            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
//        }
//    }
//
//    private void mFindSchool(String mSelectedStateCode, String mSelectedCity) {
//        if (mAppConnectivityManager.isConnected()){
//            if (AppWeakReferenceManager.mWeakReference.get() != null){
//                try
//                {
//                    progressDialog.show();
//                    mSchoolArrayList.clear();
//                    mImageSchool.setVisibility(View.GONE);
//                    mSchoolLoadingButton.startLoading();
//                    mSchoolLoadingButton.setVisibility(View.VISIBLE);
//                    etSchool.setText(R.string.please_wait);
//                    etSchool.setEnabled(false);
//
//                    HashMap hashMap = new HashMap<String, String>();
//                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_TYPE,ResponseString.APP_CODE);
//                    hashMap.put(ResponseString.CITY_STATE_CODE, mSelectedStateCode);
//                    hashMap.put(ResponseString.CITY_NAME, mSelectedCity);
//                    ApiInterface apiService =
//                            ApiClient.getClient().create(ApiInterface.class);
//                    String url = "login";
//                    Call<school> call = apiService.getSchool(ResponseString.AUTHERIZATION,hashMap);
//                    call.enqueue(new Callback<school>() {
//                        @Override
//                        public void onResponse(Call<school> call, retrofit2.Response<school> response) {
//                            dbHelper.deleteAllSchool();
//                            for (int i=0;i<response.body().getData().size();i++){
//                                //mSchoolArrayList.add(response.body().getData().get(i).getName());
//                                mSchoolArrayList.add(response.body().getData().get(i).getName() +
//                                        "\nAddress:- " + response.body().getData().get(i).getAddress());
//                                dbHelper.insertSchoolListData(response.body().getData().get(i).getMcmNo(),response.body().getData().get(i).getName(),response.body().getData().get(i).getCity(),response.body().getData().get(i).getStateCode(),response.body().getData().get(i).getAddress());
//                            }
//                            mSchoolDetails = response.body().getData();
//                            progressDialog.dismiss();
//                            etSchool.setText(ResponseString.BLANK);
//                            etSchool.setEnabled(true);
//                            mSchoolLoadingButton.reset();
//                            mSchoolLoadingButton.setVisibility(View.GONE);
//                            mImageSchool.setVisibility(View.VISIBLE);
//                            //Log.i("tag",response.toString());
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<school> call, Throwable t) {
//                            // Log error here since request failed
//                            progressDialog.dismiss();
//                            Log.i("tag", t.toString());
//                        }
//                    });
//                }catch (Exception ex){
//                    progressDialog.dismiss();
//                }
//            }
//        }else {
//            progressDialog.dismiss();
//            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
//        }
//    }
//
//    private void mFindClass() {
//        if (mAppConnectivityManager.isConnected()){
//            if (AppWeakReferenceManager.mWeakReference.get() != null){
//                try
//                {
//                    progressDialog.show();
//                    mClassArrayList.clear();
//                    mImageClass.setVisibility(View.GONE);
//                    mClassLoadingButton.startLoading();
//                    mClassLoadingButton.setVisibility(View.VISIBLE);
//                    etClass.setText(R.string.please_wait);
//                    etClass.setEnabled(false);
//
//                    HashMap hashMap = new HashMap<String, String>();
//                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                    //hashMap.put(ResponseString.APP_TYPE, pHelper.getString(ResponseString.APP_TYPE, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_TYPE,ResponseString.APP_CODE);
//                    ApiInterface apiService =
//                            ApiClient.getClient().create(ApiInterface.class);
//                    String url = "login";
//                    //Call<classes> call = apiService.getClass(ResponseString.AUTHERIZATION,ApiClient.BASE_URL+"getGradeList");
//                    Call<classes> call = apiService.getClassList(ResponseString.AUTHERIZATION,hashMap);
//                    call.enqueue(new Callback<classes>() {
//                        @Override
//                        public void onResponse(Call<classes> call, retrofit2.Response<classes> response) {
//                            for (int i=0;i<response.body().getData().size();i++){
//                                mClassArrayList.add(response.body().getData().get(i).getSclass());
//                            }
//                            progressDialog.dismiss();
//                            etClass.setText(ResponseString.BLANK);
//                            etClass.setEnabled(true);
//                            mClassLoadingButton.reset();
//                            mClassLoadingButton.setVisibility(View.GONE);
//                            mImageClass.setVisibility(View.VISIBLE);
//                            //Log.i("tag",response.toString());
//                        }
//
//                        @Override
//                        public void onFailure(Call<classes> call, Throwable t) {
//                            // Log error here since request failed
//                            progressDialog.dismiss();
//                            Log.i("tag", t.toString());
//                        }
//                    });
//                }catch (Exception ex){
//                    progressDialog.dismiss();
//                }
//            }
//        }else {
//            progressDialog.dismiss();
//            ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
//        }
//    }
//
//    private void mCheckKeyPad(Activity activity) {
//        try {
//            View view = activity.findViewById(android.R.id.content);
//            if (view != null) {
//                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CHECK_SETTINGS) {
//
//            if (resultCode == RESULT_OK) {
//                Log.e(TAG, "User agreed to make required location settings changes.");
//                // Nothing to do. startLocationupdates() gets called in onResume again.
//            } else {
//                Log.e(TAG, "User chose not to make required location settings changes.");
//            }
//        }
//    }
//
//    private void openSettings() {
//        Intent intent = new Intent();
//        intent.setAction(
//                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package",
//                BuildConfig.APPLICATION_ID, null);
//        intent.setData(uri);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture)
//    {
//        super.onPointerCaptureChanged(hasCapture);
//    }
//}
