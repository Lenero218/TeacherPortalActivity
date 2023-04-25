package com.example.teacherportalactivity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.teacherportalactivity.Adapter.SelectClassAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.Signup.SignUpActivity;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ClassListData;
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

public class SelectClass extends AppCompatActivity implements InternetRetryListener {
    private PreferenceHelper pHelper;
    RecyclerView mRecyclerView;
    public DataBaseHelper dbHelper;
    SelectClassAdapter selectClassAdapter;
    List<ClassListData> list_models;
    TextView signup;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeContainer;
    private CommonUtils mCommonUtils;
    private ShowDialog mShowDialog;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mShowDialog.mExitApp(SelectClass.this);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(SelectClass.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.select_class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);

//        setSupportActionBar(toolbar);
        toolbar_title.setText("Select Grade Here");

        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        pHelper = new PreferenceHelper(this);
        mShowDialog = new ShowDialog(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(true);
        list_models = new ArrayList<>();
        dbHelper = new DataBaseHelper(this);
        mCommonUtils = new CommonUtils(this);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectClass.this, SignUpActivity.class);
                i.putExtra("registractionFrom", "SelectClass");
                startActivity(i);
            }
        });

        String list = pHelper.getString(ResponseString.CLASS_LIST, "ClassList");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ClassListData>>() {
        }.getType();

       /* list_models = gson.fromJson(list, type);
         Log.d("classList",list);
        selectClassAdapter=new SelectClassAdapter(list_models, SelectClass.this);
        mRecyclerView.setAdapter(selectClassAdapter);
*/
        progressDialog = new ProgressDialog(SelectClass.this);
        progressDialog.setMessage("Loading...");
        loadItem();
        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                /*(new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                        loadItem();
                    }
                }, 2000);*/
                loadItem();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                R.color.green,
                R.color.grey);
    }

    public void loadItem() {

        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
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
                    hashMap.put(ResponseString.LATITUDE, "0.0");
                    hashMap.put(ResponseString.LONGITUDE, "0.0");

                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseData> call = apiService.getGradeListByUser(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, retrofit2.Response<ResponseData> response) {
                            progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {

                                list_models = response.body().getData();
//                                pHelper.setString(ResponseString.USER_ID, response.body().getData().get().getUser_id());
                                selectClassAdapter = new SelectClassAdapter(list_models, SelectClass.this);
                                mRecyclerView.setHasFixedSize(true);
                                mRecyclerView.setAdapter(selectClassAdapter);

                                mRecyclerView.setLayoutManager(new LinearLayoutManager(SelectClass.this));

                                //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                            } else {
                                ShowDialog.showInternetAlert(SelectClass.this, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(SelectClass.this, t.toString());
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
