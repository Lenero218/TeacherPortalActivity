package com.example.teacherportalactivity.manager;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.util.Objects;


public class AppWindowsManager {


    private static AppWindowsManager mAppWindowsManager = null;

    private final WindowManager mWindowManager;
    private final int width;
    private final int height;

    public static AppWindowsManager getInstance(Context ctx) {
        if (mAppWindowsManager == null) {
            mAppWindowsManager = new AppWindowsManager(ctx);
        }
        return mAppWindowsManager;
    }

    private AppWindowsManager(Context ctx) {
        mWindowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = Objects.requireNonNull(mWindowManager).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
