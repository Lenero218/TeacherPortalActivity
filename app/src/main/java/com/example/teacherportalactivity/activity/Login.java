package com.example.teacherportalactivity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.Signup.SignUpActivity;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ClassListData;
import com.example.teacherportalactivity.model.OtpResponseData;
import com.example.teacherportalactivity.model.ResponseData;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private PinView pinView;
    private TextView topText, textU, purchase, tvReSendOtp, logintext;
    private EditText userPhone, voucher;
    private ConstraintLayout second;
    LinearLayout first, voucher_lay;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private PreferenceHelper pHelper;
    private CommonUtils mCommonUtils;
    private Dialog dialog;
    private View btnLetsGo, btnVerifyOtp, view;
    private RelativeLayout signup;
    List<ClassListData> list_models;
    String is_voucher, payment_url = "";
    String lat = "0.0";
    String lng = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(Login.this).screenCaptureFLAGSECURE();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        list_models = new ArrayList<>();
        dialog = new Dialog(this);
        pHelper = new PreferenceHelper(this);
        mCommonUtils = new CommonUtils(this);

        /*if (pHelper.getString(ResponseString.LOGIN_STATUS, "FALSE").equals("TRUE")) {
            Intent i = new Intent(Login.this, SelectClass.class);
            startActivity(i);
            finish();
        }*/

        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        topText = findViewById(R.id.topText);
        pinView = findViewById(R.id.pinView);
        logintext = findViewById(R.id.logintext);
        btnLetsGo = findViewById(R.id.btnLetsGo);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        signup = findViewById(R.id.signup);
        userPhone = findViewById(R.id.userPhone);
        view = findViewById(R.id.view);
//        voucher = findViewById(R.id.voucher);
        first = findViewById(R.id.first_step);
        second = findViewById(R.id.secondstep);
        textU = findViewById(R.id.textView_noti);
        tvReSendOtp = findViewById(R.id.tvReSendOtp);
        first.setVisibility(View.VISIBLE);
//        voucher_lay=findViewById(R.id.voucher_lay);
        purchase = findViewById(R.id.purchase);
        btnLetsGo.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);
        signup.setOnClickListener(this);
        purchase.setOnClickListener(this);
        tvReSendOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLetsGo) {
            callLogin();
        } else if (v.getId() == R.id.btnVerifyOtp) {
            final String phone = userPhone.getText().toString();
            String OTP = pinView.getText().toString();
//            String voucher_str = voucher.getText().toString();

            if (OTP.length() != 4) {
                pinView.setLineColor(Color.RED);
                textU.setText("X Incorrect OTP");
                textU.setTextColor(Color.RED);
            } /*else if(voucher_lay.getVisibility()==View.VISIBLE&&voucher_str.equals("")){
                Toast.makeText(this, "Enter Voucher Code", Toast.LENGTH_SHORT).show();
            }*/ else {
                if (mAppConnectivityManager.isConnected()) {
                    if (AppWeakReferenceManager.mWeakReference.get() != null) {
                        try {
                            final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.show();
                            HashMap hashMap = new HashMap<String, String>();

                            //Common files
                            hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                            hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                            hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                            hashMap.put(ResponseString.REG_DEVICE_ID, Utils.getAndroidId(getApplicationContext()));
                            hashMap.put(ResponseString.REG_DEVICE_NAME, Utils.getDeviceName());
                            hashMap.put(ResponseString.REG_APP_ID, ResponseString.REGISTRATION_APP_ID);
                            hashMap.put(ResponseString.REG_PACKAGE_NAME, getPackageName());
                            hashMap.put(ResponseString.REG_DEVICE_TYPE, ResponseString.ANDROID);
                            hashMap.put(ResponseString.LATITUDE, lat);
                            hashMap.put(ResponseString.LONGITUDE, lng);

                            hashMap.put(ResponseString.REG_MOBILE, phone);
                            hashMap.put(ResponseString.OTP, OTP);
//                            hashMap.put(ResponseString.REG_VOUCHER, voucher_str);
                            hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);

                            ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            Call<OtpResponseData> call = apiService.verifyOtp(ResponseString.AUTHERIZATION, hashMap);
                            call.enqueue(new Callback<OtpResponseData>() {
                                @Override
                                public void onResponse(Call<OtpResponseData> call, retrofit2.Response<OtpResponseData> response) {
                                    progressDialog.hide();
                                    if (response.body() != null) {
                                        if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
                                            pinView.setLineColor(Color.GREEN);
                                            pHelper.setString(ResponseString.USER_MOBILE, phone);
//                                        pHelper.setString(ResponseString.CLASS_NAME,response.body().getClassData());
//                                        pHelper.setString(ResponseString.USER_MOBILE,response.body().getUser_mobile());
//                                        pHelper.setString(ResponseString.USER_ID,response.body().getUser_id());
//                                        pHelper.setString(ResponseString.MCM_CODE,response.body().getMcm_code());
                                           // pHelper.setString(ResponseString.LOGIN_STATUS, "TRUE");
                                            pHelper.setBoolean(ResponseString.LOGIN_STATUS, true);
                                            //pHelper.setBoolean(ResponseString.IS_VOUCHER, response.body().getVoucher());
                                            hideKeyboard(Login.this);
                                            //PreferenceHelper.setbookbillinglistBlank();
                                            //new BookBilling().setBookBillingArrayList();
                                            list_models = response.body().getData();
                                            Gson gson = new Gson();
                                            Type type = new TypeToken<ArrayList<ClassListData>>() {
                                            }.getType();
                                            String json = gson.toJson(list_models, type);
                                            pHelper.setString(ResponseString.CLASS_LIST, json);

                                            Intent i = new Intent(Login.this, SelectClass.class);
                                            startActivity(i);
                                            finish();

                                            //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                                        } else {
                                            ShowDialog.showInternetAlert(Login.this, response.body().getMessage());
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<OtpResponseData> call, Throwable t) {
                                    // Log error here since request failed
                                    progressDialog.hide();
                                    Log.i("tag", t.toString());
                                    ShowDialog.showInternetAlert(Login.this, t.toString());
                                }
                            });
                        } catch (Exception ex) {
                        }
                    }
                } else {
                    ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
                }

            }
        } else if (v.getId() == R.id.signup) {
            Intent i = new Intent(Login.this, SignUpActivity.class);
            i.putExtra("registractionFrom", "Login");
            startActivity(i);
        }

        // not using purchase or voucher

//        else if(v.getId()==R.id.purchase){
//            Intent i=new Intent(this, WebViewActivity.class);
//            i.putExtra("link",payment_url);
//            i.putExtra("title","Purchase Voucher");
//            startActivity(i);
//        }
        else if (v.getId() == R.id.tvReSendOtp) {
            callLogin();
        }

    }

    private void callLogin() {
        String phone = userPhone.getText().toString();

        if (Utils.mobileValidator(phone)) {
            if (mAppConnectivityManager.isConnected()) {
                if (AppWeakReferenceManager.mWeakReference.get() != null) {
                    try {
                        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        HashMap hashMap = new HashMap<String, String>();
                        //Common files
                        hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
                        hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
                        hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
                        hashMap.put(ResponseString.REG_DEVICE_ID, Utils.getAndroidId(getApplicationContext()));
                        hashMap.put(ResponseString.REG_DEVICE_NAME, Utils.getDeviceName());
                        hashMap.put(ResponseString.REG_APP_ID, ResponseString.REGISTRATION_APP_ID);
                        hashMap.put(ResponseString.REG_PACKAGE_NAME, getPackageName());
                        hashMap.put(ResponseString.REG_DEVICE_TYPE, ResponseString.ANDROID);
                        hashMap.put(ResponseString.LATITUDE, lat);
                        hashMap.put(ResponseString.LONGITUDE, lng);

                        hashMap.put(ResponseString.REG_MOBILE, phone);
                        hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);

                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        String url = "login";
                        Call<ResponseData> call = apiService.login(ResponseString.AUTHERIZATION, hashMap);
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                                progressDialog.hide();
                                if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
                                    first.setVisibility(View.GONE);
                                    second.setVisibility(View.VISIBLE);
                                    topText.setText("Verify your OTP");
                                    btnLetsGo.setVisibility(View.GONE);
                                    signup.setVisibility(View.GONE);
                                    logintext.setVisibility(View.GONE);

                                    view.setVisibility(View.GONE);
                                    btnVerifyOtp.setVisibility(View.VISIBLE);
                                    hideKeyboard(Login.this);
//                                    payment_url=response.body().getPayment_url();
//                                    is_voucher=response.body().getIs_voucher();
//                                    if(is_voucher.equals("0")){
//                                        //purchase.setText(response.body().getPayment_text());
//                                        voucher_lay.setVisibility(View.GONE);
//                                        purchase.setVisibility(View.GONE);
//                                    }else{
//                                        voucher_lay.setVisibility(View.GONE);
//                                        purchase.setVisibility(View.GONE);
//                                    }
                                    //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                                } else {
                                    ShowDialog.showInternetAlert(Login.this, response.body().getMessage());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                // Log error here since request failed
                                progressDialog.hide();
                                Log.i("tag", t.toString());
                                ShowDialog.showInternetAlert(Login.this, t.toString());
                            }
                        });
                    } catch (Exception ex) {
                    }
                }
            } else {
                ShowDialog.showInternetAlert(this, getResources().getString(R.string.error_internet));
            }
        } else {
            Toast.makeText(this, getString(R.string.mobile_alert), Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideKeyboard(Activity activity) {
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


}
