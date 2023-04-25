package com.example.teacherportalactivity.CustomView;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.manager.AppWindowsManager;
import java.util.Objects;


public class CustomToast {
    private LayoutInflater mLayoutInflater;
    private View mView;
    private TextView mTextError;
    private Toast mToast;
    private AppWindowsManager mAppWindowsManager;
    CountDownTimer mCountDownTimer;
    int toastDurationInMilliSeconds = 5000;
    // Custom Toast Method
    public void Show_Toast(Context context, String error) {
        mAppWindowsManager = AppWindowsManager.getInstance(context);
        try {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = Objects.requireNonNull(mLayoutInflater).inflate(R.layout.custom_toast, null);
            mView.setLayoutParams(new ViewGroup.LayoutParams(mAppWindowsManager.getHeight()/4, mAppWindowsManager.getWidth()));
            mTextError = mView.findViewById(R.id.mTextError);
            mTextError.setText(error);
            mToast = new Toast(context);// Get Toast Context
            mToast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
            mToast.setView(mView); // Set Custom View over toast
            mCountDownTimer = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
                public void onTick(long millisUntilFinished) {
                    mToast.show();
                }
                public void onFinish() {
                    mToast.cancel();
                }
            };
            // Show the toast and starts the countdown
            mToast.show();// Finally show toast
            mCountDownTimer.start();
//            mAppAudioManager.playSound(ResponseString.ERROR_TONE1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
