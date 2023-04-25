package com.example.teacherportalactivity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.teacherportalactivity.Adapter.ThemeListAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SubjectionSectionData;
import com.example.teacherportalactivity.model.SubjectionSectionModel;
import com.example.teacherportalactivity.model.Theme.ThemeData;
import com.example.teacherportalactivity.model.Theme.ThemeModel;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ThemeActivity extends AppCompatActivity implements InternetRetryListener {


    Intent ints;
    ThemeListAdapter themeListAdapter;
    List<ThemeData> list_themelist;
    RecyclerView recyclerView;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private PreferenceHelper pHelper;
    private CommonUtils mCommonUtils;

    private ProgressDialog progressDialog;
    String subject_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(ThemeActivity.this).screenCaptureFLAGSECURE();

        setContentView(R.layout.activity_theme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.setFreezesText(true);
        toolbar_title.setSingleLine(true);
        toolbar_title.setMarqueeRepeatLimit(-1);
        toolbar_title.setFocusable(true);
        toolbar_title.setSelected(true);



        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        pHelper = new PreferenceHelper(this);
        mCommonUtils = new CommonUtils(this);
        progressDialog = new ProgressDialog(this);
        try {
            ints = getIntent();
            if (ints != null && !ints.getStringExtra("subject_id").equals("")) {
                subject_id = ints.getStringExtra("subject_id");
//                product_type = ints.getStringExtra(ResponseString.PRODUCT_TYPE_KEY);
            toolbar_title.setText("Themes");
            }
        } catch (Exception ae) {

        }
        recyclerView = findViewById(R.id.mrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        list_themelist = new ArrayList<>();
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                 progressDialog.show();
                    HashMap hashMap = new HashMap<String, String>();
                    //Common files
//                    hashMap.put(ResponseString.ANDROID_API, pHelper.getString(ResponseString.ANDROID_API, ResponseString.BLANK));
//                    hashMap.put(ResponseString.ANDROID_API_NAME, pHelper.getString(ResponseString.ANDROID_API_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.APP_VERSION, mCommonUtils.VersionName);
//                    hashMap.put(ResponseString.REG_DEVICE_ID, Utils.getAndroidId(getApplicationContext()));
//                    hashMap.put(ResponseString.REG_DEVICE_NAME, Utils.getDeviceName());
//                    hashMap.put(ResponseString.REG_APP_ID, ResponseString.REGISTRATION_APP_ID);
//                    hashMap.put(ResponseString.REG_PACKAGE_NAME, getPackageName());
//                    hashMap.put(ResponseString.REG_DEVICE_TYPE, ResponseString.ANDROID);
//                    hashMap.put(ResponseString.LATITUDE, "0.0");
//                    hashMap.put(ResponseString.LONGITUDE, "0.0");
//
//                    hashMap.put(ResponseString.MCM_CODE, pHelper.getString(ResponseString.MCM_CODE, ResponseString.BLANK));
//                    hashMap.put(ResponseString.GRADE_NAME, pHelper.getString(ResponseString.CLASS_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.REG_SECTION_NAME, pHelper.getString(ResponseString.REG_SECTION_NAME, ResponseString.BLANK));
//                    hashMap.put(ResponseString.BOOK_SELECTION_ID, pHelper.getString(ResponseString.BOOK_SELECTION_ID, ResponseString.BLANK));
//                    hashMap.put(ResponseString.BOOK_SELECTION_CODE, pHelper.getString(ResponseString.BOOK_SELECTION_CODE, ResponseString.BLANK));
//                    hashMap.put(ResponseString.BOOK_SELECTION_NAME, pHelper.getString(ResponseString.BOOK_SELECTION_NAME, ResponseString.BLANK));

                    hashMap.put("subject_id", subject_id);
                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
//         hashMap.put(ResponseString.PRODUCT_TYPE, pHelper.getString(ResponseString.PRODUCT_TYPE,""));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ThemeModel> call = apiService.gettheme(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<ThemeModel>() {
                        @Override
                        public void onResponse(Call<ThemeModel> call, retrofit2.Response<ThemeModel> response) {
                             progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
                                list_themelist = response.body().getData();

                                themeListAdapter = new ThemeListAdapter(list_themelist, ThemeActivity.this, ints.getStringExtra("subject_id"));
                                recyclerView.setAdapter(themeListAdapter);
//                                loadItem2();
                                //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                            } else {
                                ShowDialog.showInternetAlert(ThemeActivity.this, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ThemeModel> call, Throwable t) {
                            // Log error here since request failed
                           progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(ThemeActivity.this, t.toString());
                        }
                    });
                } catch (Exception ex) {
                     progressDialog.dismiss();
                }
            }
        } else {
            ShowDialog.showInternetAlertWithRetry(this, getResources().getString(R.string.error_internet), this);
             progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}