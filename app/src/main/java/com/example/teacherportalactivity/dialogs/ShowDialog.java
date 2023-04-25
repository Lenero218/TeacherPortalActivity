package com.example.teacherportalactivity.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherportalactivity.Adapter.SearchableListAdapter;
import com.example.teacherportalactivity.Adapter.SearchableSchoolListAdapter;
import com.example.teacherportalactivity.CustomView.SimpleDividerItemDecoration;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.activity.SubjectActivity;
import com.example.teacherportalactivity.helper.DataBaseHelper;
import com.example.teacherportalactivity.helper.PreferenceHelper;
import com.example.teacherportalactivity.interfaces.InternetRetryListener;
import com.example.teacherportalactivity.utils.CommonUtils;
import java.util.ArrayList;
import java.util.Objects;

public class ShowDialog {

    private Activity mActivity;
    private Dialog mDialog;
    private LayoutInflater mLayoutInflater;
    private View mView;
    private static TextView txtHeading;
    private static Button btnDialogOK;
    private String bookTitle,bookCode, Assets_URL, Assets_FILE;
    private int downloadBookId;
    private String mDownloadDir;
    private static View layout;
    private static TextView mTextViewAnswer;
    private static Button mButtonDialogYes;
    private static Button mButtonDialogNo;
    private final PreferenceHelper pHelper;
    private final CommonUtils mCommonUtils;
    ArrayList<String> schoolData = new ArrayList<>();
    public ShowDialog(Activity mActivity) {
        this.mActivity = mActivity;
        this.pHelper = new PreferenceHelper(mActivity);
        this.mCommonUtils = new CommonUtils();
    }

    public void checkOpenDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
        if (mLayoutInflater != null) {
            mLayoutInflater = null;
        }
        if (mView != null) {
            mView = null;
        }
    }

    public void mFillSimpleSpinner(Activity activity, final EditText editText, ArrayList<String> mList, String title) {

        checkOpenDialog();
        mActivity = activity;
        mDialog = new Dialog(mActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.dialog_simple_list, null);

        final AutoCompleteTextView editSearch = mView.findViewById(R.id.search);
        editSearch.setHint(activity.getString(R.string.select) + title);
        RecyclerView mRecyclerView = mView.findViewById(R.id.data_list);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mActivity.getResources()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TextView txtTitle = mView.findViewById(R.id.txtTitle);
        TextView txtNoRecord = mView.findViewById(R.id.txtNoRecord);
        txtTitle.setText(activity.getString(R.string.select) + title);
        if (mList.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            txtNoRecord.setVisibility(View.GONE);
            final SearchableListAdapter mSearchableCityListAdapter = new SearchableListAdapter(mActivity, editText, mList, mDialog);
            mRecyclerView.setAdapter(mSearchableCityListAdapter);
            mSearchableCityListAdapter.notifyDataSetChanged();
            editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    mSearchableCityListAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            mRecyclerView.setVisibility(View.GONE);
            txtNoRecord.setVisibility(View.VISIBLE);
        }
        mDialog.setContentView(mView);
        mView.findViewById(R.id.textClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        });
        mDialog.show();
    }

    public void mFillSimpleSpinnerSchool(Activity activity, final EditText editText, ArrayList<String> mList, String title, DataBaseHelper dbHelper) {

        checkOpenDialog();
        mActivity = activity;
        mDialog = new Dialog(mActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.dialog_simple_list, null);

        final AutoCompleteTextView editSearch = mView.findViewById(R.id.search);
        editSearch.setHint(activity.getString(R.string.search) + title);
        RecyclerView mRecyclerView = mView.findViewById(R.id.data_list);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mActivity.getResources()));
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        TextView txtTitle = mView.findViewById(R.id.txtTitle);
        TextView txtNoRecord = mView.findViewById(R.id.txtNoRecord);
        txtTitle.setText(activity.getString(R.string.select) + title);
        schoolData.clear();
        schoolData = dbHelper.getSchoolList("");
        if (schoolData.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            txtNoRecord.setVisibility(View.GONE);
            final SearchableSchoolListAdapter mSearchableCityListAdapter = new SearchableSchoolListAdapter(mActivity, editText, schoolData, mDialog);
            mRecyclerView.setAdapter(mSearchableCityListAdapter);
            mSearchableCityListAdapter.notifyDataSetChanged();
            editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    schoolData.clear();
                    schoolData = dbHelper.getSchoolList(s.toString());
                    mSearchableCityListAdapter.setItems(schoolData);
                    mSearchableCityListAdapter.notifyDataSetChanged();
                    //mSearchableCityListAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            mRecyclerView.setVisibility(View.GONE);
            txtNoRecord.setVisibility(View.VISIBLE);
        }
        mDialog.setContentView(mView);
        mView.findViewById(R.id.textClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                    mDialog = null;
                }
            }
        });
        mDialog.show();
    }

    public static void showInternetAlert(final Context mActivity, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.AlertDialogTheme);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.alert_outline);
        builder.setPositiveButton(mActivity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(
                    DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                builder.show().cancel();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public static void showInternetAlertWithRetry(final Context mActivity, String message, final InternetRetryListener retryListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.AlertDialogTheme);
        builder.setTitle(mActivity.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.alert_outline);
        builder.setPositiveButton(mActivity.getResources().getString(R.string.re_try), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(
                    DialogInterface dialogInterface, int i) {
                if (retryListener!=null)
                    retryListener.onRetry();

                dialogInterface.dismiss();
                builder.show().cancel();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageView = dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void PermissionDenied(boolean should) {
        if (should) {
            try {
                mDialog = new Dialog(mActivity);
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setCancelable(false);
                Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
                mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.dialog_permission_denied, null);

                mDialog.setContentView(mView);
                mView.findViewById(R.id.btnDialogOK).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                            mDialog = null;
                            mActivity.startActivity(mActivity.getIntent());
                            mActivity.finish();
                        }
                    }
                });
                mView.findViewById(R.id.btnDialogQuit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                            mDialog = null;
                            mActivity.finish();
                        }
                    }
                });
                mDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            promptSettings();
        }
    }

    public void promptSettings() {
        try {
            mDialog = new Dialog(mActivity);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
            mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.dialog_go_permission_setting, null);
            mDialog.setContentView(mView);
            mView.findViewById(R.id.btnDialogGoSetting).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                        goToSettings();
                    }
                }
            });
            mView.findViewById(R.id.btnDialogCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                        mDialog = null;
                        mActivity.finish();
                    }
                }
            });
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToSettings() {
        try {
            Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
            myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(myAppSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(final Activity mActivity, String message, final boolean condition) {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.AlertDialogTheme);
            builder.setTitle(mActivity.getResources().getString(R.string.app_name));
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setIcon(R.drawable.alert_outline);
            builder.setPositiveButton(mActivity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                        DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if (condition) {
                        builder.show().cancel();
                        mActivity.finish();
                    } else {
                        builder.show().cancel();
                    }
                }
            });
            AlertDialog dialog = builder.show();
            TextView messageView = dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Dialog to show when starting timer-based exercise*/
    public void showDialogStartTimerExercise() {
        DisplayMetrics metrics = mActivity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        try {
            mDialog = new Dialog(mActivity);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(false);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
            mDialog.getWindow().setLayout((16 * width)/7, (14 * height)/5);
            mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = mLayoutInflater.inflate(R.layout.dialog_answer_exercise, null);
            mDialog.setContentView(layout);
            txtHeading = (TextView) layout.findViewById(R.id.tvAnswer);
            btnDialogOK = (Button) layout.findViewById(R.id.btnDialogOK);
            txtHeading.setText("Test is ready. You will get 60 seconds to attempt each question.");
            mDialog.show();
            btnDialogOK.setVisibility(View.VISIBLE);
//
//            btnDialogOK.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mDialog.dismiss();
//                    Intent i=new Intent(mActivity, QuestionsActivity.class);
//                    //i.putParcelableArrayListExtra("typinArrayList", (ArrayList<? extends Parcelable>) questionData);
//                    mActivity.startActivity(i);
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mExitApp(Activity activity) {

        checkOpenDialog();
        mActivity = activity;
        mDialog = new Dialog(mActivity);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(mActivity.getResources().getColor(android.R.color.transparent)));
        mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.custom_exit_dialog, null);
        mView.setMinimumWidth((int) (SubjectActivity.DisplayWidth * 0.9f));
        mView.setMinimumHeight(SubjectActivity.DisplayHeight / 4);
        mView.findViewById(R.id.mImageViewNo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mDialog.dismiss();
            }
        });
        mView.findViewById(R.id.mImageViewYes).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
//                if (VideoActivity.Companion.getMVideoActivity()!=null){
//                    VideoActivity.Companion.getMVideoActivity().finish();
//                }
                mDialog.dismiss();
                mActivity.finish();
            }
        });
        mView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mDialog.dismiss();
            }
        });
        mView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
//                if (VideoActivity.Companion.getMVideoActivity()!=null){
//                    VideoActivity.Companion.getMVideoActivity().finish();
//                }
                mDialog.dismiss();
                mActivity.finish();
            }
        });
        mView.findViewById(R.id.mImageViewRateUs).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
//                if (VideoActivity.Companion.getMVideoActivity()!=null){
//                    VideoActivity.Companion.getMVideoActivity().finish();
//                }
                mDialog.dismiss();
                final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        mView.findViewById(R.id.mImageViewShare).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
//                if (VideoActivity.Companion.getMVideoActivity()!=null){
//                    VideoActivity.Companion.getMVideoActivity().finish();
//                }
                mDialog.dismiss();
                try {
                    String shareBody = mActivity.getResources().getString(R.string.share_app_link);
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, mActivity.getResources().getString(R.string.app_name));
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    mActivity.startActivity(Intent.createChooser(sharingIntent, mActivity.getResources().getString(R.string.share_app)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mDialog.setContentView(mView);
        mDialog.show();
    }
}
