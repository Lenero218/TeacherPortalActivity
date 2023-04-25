package com.example.teacherportalactivity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.Adapter.LessonPlanAdapter;
import com.example.teacherportalactivity.Adapter.PeriodClassListAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.PeriodData;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.PlanDetail;
import com.example.teacherportalactivity.model.lessonsplanandperioddata.Token;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class PeriodClasslist extends AppCompatActivity implements InternetRetryListener {
    PeriodClassListAdapter lessonPlanAdapter;
    List<PeriodData> periodlist;
    Intent ints;
    RecyclerView recyclerView;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private PreferenceHelper pHelper;
    private CommonUtils mCommonUtils;


    private ProgressDialog progressDialog;
    String lesson_id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(PeriodClasslist.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_periodlistclass);


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
            if (ints != null && !ints.getStringExtra("lessonid").equals("")) {
                lesson_id = ints.getStringExtra("lessonid");
//                product_type = ints.getStringExtra(ResponseString.PRODUCT_TYPE_KEY);
                toolbar_title.setText(ints.getStringExtra("lessonname"));
            }
        } catch (Exception ae) {

        }

        recyclerView = findViewById(R.id.mrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        periodlist = new ArrayList<>();
        loadplanDetails();
    }

    private void loadplanDetails() {
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

                    hashMap.put("lesson_id", lesson_id);
                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
//         hashMap.put(ResponseString.PRODUCT_TYPE, pHelper.getString(ResponseString.PRODUCT_TYPE,""));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<Token> call = apiService.getlessonid(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, retrofit2.Response<Token> response) {
                            progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {

                                periodlist = response.body().getData().getPeriodList();
                                lessonPlanAdapter = new PeriodClassListAdapter((List<PeriodData>) periodlist, PeriodClasslist.this, ints.getStringExtra("lesson_id"));
                                recyclerView.setAdapter(lessonPlanAdapter);
                            } else {
                                ShowDialog.showInternetAlert(PeriodClasslist.this, response.body().getMessage());
                            }
                        }


                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(PeriodClasslist.this, t.toString());
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
        loadplanDetails();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
