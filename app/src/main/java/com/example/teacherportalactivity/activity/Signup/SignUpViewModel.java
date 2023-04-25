package com.example.teacherportalactivity.activity.Signup;


import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SignUpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SignUp Page");
    }

    public LiveData<String> getText() {
        return mText;
    }

}