package com.example.teacherportalactivity.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.Adapter.ActivityClassAdapter;
import com.example.teacherportalactivity.Adapter.LessonListAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.interfaces.onClickCheckWithPosition;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.Activity.ActivityData_new;
import com.example.teacherportalactivity.model.Activity.ActivityModel;
import com.example.teacherportalactivity.model.PostResult;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.ResultModel;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDataClass extends AppCompatActivity implements InternetRetryListener, onClickCheckWithPosition, Html.ImageGetter {

    RecyclerView recyclerView;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private PreferenceHelper pHelper;
    private CommonUtils mCommonUtils;
    Intent ints;
    String period_id;
    JSONArray postJson = new JSONArray();
    List<ActivityData_new> activity_data;
    private ActivityClassAdapter activityClassAdapter;
    Button submitbtn;
    ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(ActivityDataClass.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_activitydata);


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
            ints = getIntent();
            if (ints != null && !ints.getStringExtra("period_id").equals("")) {
                period_id = ints.getStringExtra("period_id");
//                product_type = ints.getStringExtra(ResponseString.PRODUCT_TYPE_KEY);
                toolbar_title.setText(ints.getStringExtra("period_name"));
            }
        } catch (Exception ae) {

        }

        recyclerView = findViewById(R.id.mrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        activity_data = new ArrayList<>();
        submitbtn = findViewById(R.id.submitbtn);

        getPeriodActivityDetails();
    }

    void getPeriodActivityDetails() {
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

                    hashMap.put(ResponseString.USER_ID, pHelper.getString(ResponseString.USER_ID, ResponseString.BLANK));
                    hashMap.put("period_id", period_id);
                    hashMap.put(ResponseString.LESSON_ID, pHelper.getString(ResponseString.LESSON_ID, ResponseString.BLANK));
                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE, ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE, ResponseString.APP_CODE);
                    Log.e("rew", hashMap.toString());

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ActivityModel> call = apiService.getactivityid(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<ActivityModel>() {
                        @Override
                        public void onResponse(Call<ActivityModel> call, retrofit2.Response<ActivityModel> response) {
                            progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)) {
                                activity_data = response.body().getData();

                                DisplayMetrics displayMetrics = new DisplayMetrics();
                                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                int height = displayMetrics.heightPixels;
                                int width = displayMetrics.widthPixels;

                                activityClassAdapter = new ActivityClassAdapter(activity_data, ActivityDataClass.this, ints.getStringExtra("period_id"), width);
                                recyclerView.setAdapter(activityClassAdapter);
                                activityClassAdapter.notifyDataSetChanged();
//                                loadItem2();
                                //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                            } else {
                                ShowDialog.showInternetAlert(ActivityDataClass.this, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ActivityModel> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(ActivityDataClass.this, t.toString());
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

    public void confirmation(final String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle1);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.ic_confused);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                if (mAppConnectivityManager.isConnected()) {
                    if (AppWeakReferenceManager.mWeakReference.get() != null) {
                        try {
                            progressDialog.show();
                            HashMap hashMap = new HashMap<String, String>();
                            hashMap.put(ResponseString.USER_ID, pHelper.getString(ResponseString.USER_ID, ResponseString.BLANK));
                            hashMap.put("period_id", pHelper.getString(ResponseString.PERIOD_ID, ResponseString.BLANK));
                            hashMap.put("activity_data", postJson.toString());
                            hashMap.put(ResponseString.USER_ID, pHelper.getString(ResponseString.USER_ID, ResponseString.BLANK));
                            hashMap.put("period_id", pHelper.getString(ResponseString.PERIOD_ID, ResponseString.BLANK));
                            hashMap.put("lesson_id", pHelper.getString(ResponseString.LESSON_ID, ResponseString.BLANK));
                            hashMap.put("grade", pHelper.getString(ResponseString.CLASS_NAME, ResponseString.BLANK));
                            hashMap.put("subject_id",pHelper.getString(ResponseString.SUBJECT_ID, ResponseString.BLANK));
                            Log.e("dfde", hashMap.toString());
                            ApiInterface apiService =
                                    ApiClient.getClient().create(ApiInterface.class);
                            Call<String> call = apiService.postDataActivityid(ResponseString.AUTHERIZATION, hashMap);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                    Log.e("dfd", response.body().toString());
                                    progressDialog.dismiss();
                                    Intent i = new Intent(ActivityDataClass.this, Resultactivity.class);
                                    alertDialogBuilder.setCancelable(true);
                                    i.putExtra("gradename", pHelper.getString(ResponseString.GRADE_NAME, ""));
                                    i.putExtra("lessonname", pHelper.getString(ResponseString.LESSON_NAME, ""));
                                    i.putExtra("periodname", pHelper.getString(ResponseString.PERIOD_NAME, ""));
                                    i.putExtra("json", postJson.toString());
                                    startActivity(i);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    progressDialog.dismiss();
                                    ShowDialog.showInternetAlert(ActivityDataClass.this, t.toString());
                                }
                            });
                        } catch (Exception ex) {
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    ShowDialog.showInternetAlertWithRetry(ActivityDataClass.this, getResources().getString(R.string.error_internet), ActivityDataClass.this);
                    progressDialog.dismiss();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

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
    protected void onRestart() {
        super.onRestart();
        getPeriodActivityDetails();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void showcount(String s2) {
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postJson.length() > 0)
                    confirmation("Confirm!", "Are you sure, you want to proceed?");
            }
        });
    }

    public void ShowCountValues(int total, int Present, int Absent) {
//        String valP = String.valueOf(Present);
//        String valA = String.valueOf(Absent);
//        present_students.setText(valP);
//        absent_students.setText(valA);
    }

    @Override
    public void onClick(JSONArray jsonArray) {
        postJson = jsonArray;
        Log.e("json", postJson.toString());
    }

    @Override
    public Drawable getDrawable(String source) {
        int resourceId = getResources().getIdentifier(source, "drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(resourceId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;

    }
}
