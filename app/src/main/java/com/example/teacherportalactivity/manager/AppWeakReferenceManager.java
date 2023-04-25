package com.example.teacherportalactivity.manager;


import android.content.Context;

import java.lang.ref.WeakReference;

public class AppWeakReferenceManager {

    public static WeakReference<Context> mWeakReference;

    public AppWeakReferenceManager(Context mActivity) {
        mWeakReference = new WeakReference<>(mActivity);
    }
}
