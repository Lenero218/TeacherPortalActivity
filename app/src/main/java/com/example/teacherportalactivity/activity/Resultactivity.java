package com.example.teacherportalactivity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.Adapter.ResultActivityAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.model.ActivityData;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.Result;
import com.example.teacherportalactivity.model.ResultDisplay;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Resultactivity extends AppCompatActivity implements InternetRetryListener {


    private RecyclerView recyclerView, m1recycler_view;
    private ResultActivityAdapter adapter;
    private AppConnectivityManager mAppConnectivityManager;
    private TextView teacher_name, grade, section_registered;
    private PreferenceHelper preferenceHelper;
    Intent in;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultpage);
        recyclerView = findViewById(R.id.recycler_view);
        preferenceHelper = new PreferenceHelper(this);
        grade = findViewById(R.id.grade);
        section_registered = findViewById(R.id.section_registered);
        teacher_name = findViewById(R.id.teacher_name);

        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        progressDialog = new ProgressDialog(this);

        in = getIntent();
        in.getStringExtra("LESSONNAME");
        init();

        grade.setText(preferenceHelper.getString(ResponseString.CLASS_NAME, ResponseString.BLANK));
        section_registered.setText(preferenceHelper.getString(ResponseString.REG_SECTION_NAME, ResponseString.BLANK));
        teacher_name.setText(preferenceHelper.getString(ResponseString.USER_NAME, ResponseString.BLANK));

        teacher_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        teacher_name.setSelected(true);
        teacher_name.setSingleLine(true);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        if (mAppConnectivityManager.isConnected()) {
            if (AppWeakReferenceManager.mWeakReference.get() != null) {
                try {
                    progressDialog.show();
                    HashMap hashMap = new HashMap<String, String>();
                    hashMap.put(ResponseString.USER_ID, preferenceHelper.getString(ResponseString.USER_ID, ResponseString.BLANK));
                    hashMap.put("period_id", preferenceHelper.getString(ResponseString.PERIOD_ID, ResponseString.BLANK));
                    hashMap.put("lesson_id", preferenceHelper.getString(ResponseString.LESSON_ID, ResponseString.BLANK));
                    hashMap.put("grade", preferenceHelper.getString(ResponseString.CLASS_NAME, ResponseString.BLANK));
                    hashMap.put("subject_id", preferenceHelper.getString(ResponseString.SUBJECT_ID, ResponseString.BLANK));
                    Log.e("rew", hashMap.toString());
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<ResultDisplay> call = apiService.getActivityResultList(ResponseString.AUTHERIZATION, hashMap);
                    call.enqueue(new Callback<ResultDisplay>() {
                        @Override
                        public void onResponse(Call<ResultDisplay> call, retrofit2.Response<ResultDisplay> response) {
                            progressDialog.dismiss();
                            Log.e("ff", String.valueOf(response.body().getData().size()));
                            adapter = new ResultActivityAdapter(Resultactivity.this, response.body().getData());
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Resultactivity.this));
                            recyclerView.setHasFixedSize(true);
                        }

                        @Override
                        public void onFailure(Call<ResultDisplay> call, Throwable t) {
                            progressDialog.dismiss();
                            ShowDialog.showInternetAlert(Resultactivity.this, t.toString());
                        }
                    });
                } catch (Exception ex) {
                    progressDialog.dismiss();
                }
            }
        } else {
            ShowDialog.showInternetAlertWithRetry(Resultactivity.this, getResources().getString(R.string.error_internet), this);
            progressDialog.dismiss();
        }
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
}