package com.example.teacherportalactivity.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.teacherportalactivity.Adapter.SemTermAdapter;
import com.example.teacherportalactivity.Adapter.SemTermAdapter2;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ResponseString;


import android.app.ProgressDialog;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.teacherportalactivity.model.SemTermData;
import com.example.teacherportalactivity.model.SemTermData2;
import com.example.teacherportalactivity.model.SemTermModel;
import com.example.teacherportalactivity.model.SemTermModel2;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SemTermActivity extends AppCompatActivity implements InternetRetryListener {
    RecyclerView mRecyclerView, mRecyclerView2;
    SemTermAdapter semTermAdapter;
    SemTermAdapter2 semTermAdapter2;
    List<SemTermData> list_models;
    //    List<SemTermData2> list_models2;
    List<SemTermData> list_models2;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private PreferenceHelper pHelper;
    Intent in;
    String series_id, product_type;
    private ProgressDialog progressDialog;
    SwipeRefreshLayout swipeContainer;
    private CommonUtils mCommonUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(SemTermActivity.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_semster);
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
        progressDialog = new ProgressDialog(this);
        mCommonUtils = new CommonUtils(this);
        try {
            in = getIntent();
            if (in != null && !in.getStringExtra("id").equals("")) {
                series_id = in.getStringExtra("id");
                product_type = in.getStringExtra(ResponseString.PRODUCT_TYPE_KEY);
                toolbar_title.setText(in.getStringExtra("name"));
            }
        } catch (Exception ae) {

        }
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView2 = findViewById(R.id.recycler_view2);


        list_models = new ArrayList<>();

        loadItem();
//        loadItem2();
//        swipeContainer = findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
      /*  swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                        loadItem();
                        loadItem2();
                    }
                }, 3000);

            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                R.color.green,
                R.color.grey);
        progressDialog = new ProgressDialog(SemTermActivity.this);
        progressDialog.setMessage("Loading...");
        loadItem();
        */
        ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadItem() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView2.setHasFixedSize(true);
        list_models2 = new ArrayList<>();
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

                    hashMap.put("series_id", series_id);
                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
//                    hashMap.put(ResponseString.PRODUCT_TYPE, pHelper.getString(ResponseString.PRODUCT_TYPE,""));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<SemTermModel> call = apiService.getTermSem(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<SemTermModel>() {
                        @Override
                        public void onResponse(Call<SemTermModel> call, retrofit2.Response<SemTermModel> response) {
                            progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
//                                list_models = response.body().getData();
                                List<SemTermData> tempList = new ArrayList<>();
                                tempList.addAll(response.body().getData());

                                for (SemTermData d : tempList) {
//                                    if (d.getTermSem().equals("Semester 1") || d.getTermSem().equals("Semester 2") || d.getTermSem().equals("Term 1") || d.getTermSem().equals("Term 2") || d.getTermSem().equals("Term 3")) {
//                                        list_models.add(d);
//                                    } else {
//                                        list_models2.add(d);
//                                    }

                                    if (!d.getTermSem().equalsIgnoreCase("eLive Portal")) {
                                        list_models.add(d);
                                    }
                                }

                                semTermAdapter = new SemTermAdapter(list_models, SemTermActivity.this, in.getStringExtra("name"));
                                mRecyclerView.setAdapter(semTermAdapter);

//                                semTermAdapter2 = new SemTermAdapter2(list_models2, SemTermActivity.this, in.getStringExtra("name"));
//                                mRecyclerView2.setAdapter(semTermAdapter2);
                            } else {
                                ShowDialog.showInternetAlert(SemTermActivity.this, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<SemTermModel> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(SemTermActivity.this, t.toString());
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
    public void onRetry() {
        loadItem();

    }

}
