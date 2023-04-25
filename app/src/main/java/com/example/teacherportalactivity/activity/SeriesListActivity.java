package com.example.teacherportalactivity.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.teacherportalactivity.Adapter.SeriesListAdapter;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.dialogs.ShowDialog;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.manager.AppConnectivityManager;
import com.example.teacherportalactivity.manager.AppWeakReferenceManager;
import com.example.teacherportalactivity.manager.AppWindowsManager;
import com.example.teacherportalactivity.model.Data;
import com.example.teacherportalactivity.model.ResponseCode;
import com.example.teacherportalactivity.model.ResponseString;
import com.example.teacherportalactivity.model.SeriesList;
import com.example.teacherportalactivity.retrofit.ApiClient;
import com.example.teacherportalactivity.retrofit.ApiInterface;
import com.example.teacherportalactivity.utils.CommonUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static org.json.JSONObject.wrap;

public class SeriesListActivity extends AppCompatActivity implements InternetRetryListener {
    AlertDialog.Builder builder;
    RecyclerView mRecyclerView;
    SeriesListAdapter serieslistadapter;
    List<Data> list_models;
    private PreferenceHelper pHelper;
    private AppConnectivityManager mAppConnectivityManager;
    private AppWeakReferenceManager mAppWeakReferenceManager;
    private SearchView searchView;
    private ProgressDialog progressDialog;
    private Dialog mPackageDialog;
    String custid="", orderId="", mid="", amount = "";
    SwipeRefreshLayout swipeContainer;
    private CommonUtils mCommonUtils;
    private ShowDialog mShowDialog;
    private AppWindowsManager mAppWindowsManager;
    public static int DisplayHeight, DisplayWidth;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mShowDialog.mExitApp(SeriesListActivity.this);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CommonUtils(SeriesListActivity.this).screenCaptureFLAGSECURE();
        setContentView(R.layout.activity_serieslist);

        //new BookBilling().setBookBillingArrayList();


        Toolbar toolbar = findViewById(R.id.toolbar);
        AppCompatTextView toolbar_title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        toolbar_title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        toolbar_title.setFocusableInTouchMode(true);
        toolbar_title.setFreezesText(true);
        toolbar_title.setSingleLine(true);
        toolbar_title.setMarqueeRepeatLimit(-1);
        toolbar_title.setFocusable(true);
        toolbar_title.setSelected(true);

        mCommonUtils = new CommonUtils(this);
        mShowDialog = new ShowDialog(this);
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setNestedScrollingEnabled(true);
        pHelper = new PreferenceHelper(this);
        mAppConnectivityManager = AppConnectivityManager.getInstance(this);
        mAppWeakReferenceManager = new AppWeakReferenceManager(this);
        mAppWindowsManager = AppWindowsManager.getInstance(this);
        DisplayHeight = mAppWindowsManager.getHeight();
        DisplayWidth = mAppWindowsManager.getWidth();
        mPackageDialog= new Dialog(SeriesListActivity.this);
//        btnPearls.setOnClickListener(this);
        toolbar_title.setText("Grade "+pHelper.getString(ResponseString.CLASS_NAME,ResponseString.BLANK));
        builder=new AlertDialog.Builder(this);
        list_models = new ArrayList<>();
        progressDialog = new ProgressDialog(SeriesListActivity.this);
        progressDialog.setMessage("Loading...");
        loadItem();

        swipeContainer = findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                loadItem();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                R.color.green,
                R.color.grey);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.search, menu);
            final MenuItem searchItem = menu.findItem(R.id.action_search);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            EditText searchEditText = searchView.findViewById(R.id.search_src_text);
            searchEditText.setHintTextColor(getResources().getColor(R.color.white));
            searchEditText.setTextColor(getResources().getColor(R.color.white));

            searchEditText.setCursorVisible(true);
            searchView.setIconified(true);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(list_models.size() != 0) {
                        serieslistadapter.getFilter().filter(newText);
                    }else{
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(list_models.size() != 0) {
                        serieslistadapter.getFilter().filter(query);
                    }else{
                    }
                    return false;
                }
            });
        }catch (Exception ex){
            Log.i("tag",ex.toString());
        }
//        searchView.onActionViewCollapsed();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_class_change) {

            Intent i=new Intent(SeriesListActivity.this, SelectClass.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }
//        if (id == R.id.action_profile) {
//            Intent i=new Intent(SeriesListActivity.this, ProfileActivity.class);
//            startActivity(i);
//            return true;
//        }
//        if (id == R.id.action_about_us) {
//            Intent i=new Intent(SeriesListActivity.this, WebViewHelp.class);
//            pHelper.setInt(ResponseString.ACTION_TYPE, ResponseCode.ZERO);
//            startActivity(i);
//            return true;
//        }
//        if (id == R.id.action_pp) {
//            Intent i=new Intent(SeriesListActivity.this, WebViewHelp.class);
//            pHelper.setInt(ResponseString.ACTION_TYPE, ResponseCode.ONE);
//            startActivity(i);
//            return true;
//        }
//        if (id == R.id.action_credits) {
//            Intent i=new Intent(SeriesListActivity.this, WebViewHelp.class);
//            pHelper.setInt(ResponseString.ACTION_TYPE, ResponseCode.TWO);
//            startActivity(i);
//            return true;
//        }


        if (id == R.id.action_logout) {

            pHelper.setString(ResponseString.LOGIN_STATUS,"FALSE");
            Intent i=new Intent(SeriesListActivity.this,Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finishAffinity();
            return true;
        }

            finish();

        return super.onOptionsItemSelected(item);
    }

    public void loadItem(){

        if (mAppConnectivityManager.isConnected()){
            if (AppWeakReferenceManager.mWeakReference.get() != null){
                try
                {
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

                    hashMap.put(ResponseString.MCM_CODE, pHelper.getString(ResponseString.MCM_CODE,ResponseString.BLANK));
                    hashMap.put(ResponseString.GRADE_NAME, pHelper.getString(ResponseString.CLASS_NAME,ResponseString.BLANK));
                    hashMap.put(ResponseString.REG_SECTION_NAME, pHelper.getString(ResponseString.REG_SECTION_NAME,ResponseString.BLANK));
                    hashMap.put(ResponseString.APP_TYPE,ResponseString.APP_CODE);
                    hashMap.put(ResponseString.USER_MOBILE, pHelper.getString(ResponseString.USER_MOBILE,ResponseString.BLANK));

                    //hashMap.put("class", "LKG");

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
                    Call<SeriesList> call = apiService.getSeries(ResponseString.AUTHERIZATION,hashMap);
                    call.enqueue(new Callback<SeriesList>() {
                        @Override
                        public void onResponse(Call<SeriesList> call, retrofit2.Response<SeriesList> response) {
                            progressDialog.dismiss();
                            if (response.body().getError().equals(false) && response.body().getCode().equals(200)){
                                list_models=response.body().getData();
                                serieslistadapter=new SeriesListAdapter(list_models,SeriesListActivity.this,pHelper);
                                mRecyclerView.setAdapter(serieslistadapter);
                                //new CustomToast().Show_Toast(SignUpActivity.this, response.body().getMessage());
                            }else {
                                ShowDialog.showInternetAlert(SeriesListActivity.this, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<SeriesList> call, Throwable t) {
                            // Log error here since request failed
                            progressDialog.dismiss();
                            Log.i("tag", t.toString());
                            ShowDialog.showInternetAlert(SeriesListActivity.this, t.toString());
                        }
                    });
                }catch (Exception ex){
                    progressDialog.dismiss();
                }
            }
        }else {

            ShowDialog.showInternetAlertWithRetry(this, getResources().getString(R.string.error_internet),this);
            progressDialog.dismiss();
        }
    }




    @Override
    public void onRetry() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

  /*  //Update In app payment status
    class BookBillingAsyntask extends AsyncTask<Void, Void, Void> {

        Context context;
        AlertDialog.Builder alertDialog;
        AlertDialog alertDialogg;
        Intent data;
        public BookBillingAsyntask(Context context, Intent data) {
            this.context = context;
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage(R.string.please_wait);
            alertDialogg = alertDialog.create();
            alertDialogg.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (data != null && data.getStringArrayListExtra("booklist") != null) {
                for (String bookname : data.getStringArrayListExtra("booklist")) {
                    BookBilling.setBookBillingstatus(true, bookname);           // TODO: 04/01/2018  Sets Book payment status true for each book against which payment has been made on PlayStore.
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            alertDialogg.dismiss();
            loadItem();
        }
    }
*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            new BookBillingAsyntask(MainActivity.this, data).execute();
        }
    }*/
}
