package com.example.teacherportalactivity.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.teacherportalactivity.R;
import com.example.teacherportalactivity.model.ResponseCode;
import com.example.teacherportalactivity.model.ResponseString;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    public static Activity mActivity;
    public String VersionName;
    private int SDK_INT;

    public CommonUtils() {
    }

    public CommonUtils(Activity activity) {
        // TODO Auto-generated constructor stub
        mActivity = activity;
        try {
            VersionName = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void screenCaptureFLAGSECURE() {
        //mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }
    public static void setCompactDrawableToView(Activity activity, EditText editText, int resourceID, int position )
    {    final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        Drawable drawableCompat;

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawableCompat = VectorDrawableCompat.create(activity.getResources(), resourceID, activity.getTheme());
        } else {
            drawableCompat = activity.getResources().getDrawable(resourceID, activity.getTheme());
        }
        // editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableCompat, null);
        switch (position)
        {
            case DRAWABLE_TOP:
                editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawableCompat, null, null);
                break;

            case DRAWABLE_BOTTOM:
                editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawableCompat);
                break;

            case DRAWABLE_LEFT:
                editText.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableCompat, null, null, null);
                break;

            case DRAWABLE_RIGHT:
                editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawableCompat, null);
                break;
        }
    }
    public void checkStrictMode() {
        SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void checkStrictModeCamera() {
        SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(newbuilder.build());
        }
    }

    public boolean belowMarsMallow() {
        boolean condition = false;
        SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.M) {
            condition = true;
        }
        return condition;
    }

    public static long getSystemTime() {
        long returnTime = System.currentTimeMillis();
        Date time = new Date(returnTime);
        return returnTime;
    }
    public static String getDeviceId(Context context) {
        String deviceId = Build.MODEL + mActivity.getString(R.string.underscore);
        deviceId += Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    public static String getDeviceUUID(Context context) {
        String deviceId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    public String getAndroidApiVersion(){
        String deviceOSName = Build.VERSION.RELEASE;
        return deviceOSName;
    }

    public String getAndroidApiVersionName(){
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String fieldName = ResponseString.BLANK;
        for (Field field : fields) {
            fieldName = field.getName();
        }
        return fieldName;
    }
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    public static void loadImageInCircularShape( Context context, String imgUrl,  ImageView imgProfilePicture)
    {

        Glide.with(context).
                load(imgUrl)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .apply(new RequestOptions().circleCrop()).into(imgProfilePicture);

    }

    public static void loadImageUrl(Context mContext, ImageView mImageView,String url)
    {
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false)
                .placeholder(R.drawable.loader)
                .into(mImageView);
    }



    public static String checkStringEmptyOrNull(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        else {

            return s;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    public static String getDateTimeToDate(String inputDate) {
        String inputPattern = ResponseString.APP_DATE_TIME_FORMAT;
        String outputPattern = ResponseString.APP_DATE_FORMAT;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date inputdate;
        Date outputdate;
        String str = null;
        try {
            inputdate = inputFormat.parse(inputDate);
            str = outputFormat.format(inputdate);
            DateFormat formatter = new SimpleDateFormat(outputPattern);
            outputdate = formatter.parse(str);
            str = formatter.format(outputdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    // To calculate the current date
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ResponseString.APP_DATE_FORMAT);
        String currentDate = dateFormat.format(calendar.getTime());
        return currentDate;
    }

    public static String getAutoEndDate(String inputDate) {
        String outputDate = ResponseString.BLANK;
        try {
            DateFormat df = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT);
            Date d = df.parse(inputDate);
            df = new SimpleDateFormat(ResponseString.APP_DATE_FORMAT);
            System.out.println(df.format(d));
            outputDate = df.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    // To calculate the current date
    public static String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT);
        String currentDate = dateFormat.format(calendar.getTime());
        return currentDate;
    }

    public static boolean getDatesIsDifference(String installDate, String updateDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT);
        boolean b = false;
        try {
            //If start date is after the end date
            if (dateFormat.parse(updateDate).before(dateFormat.parse(installDate))) {
                b = true;//If start date is before end date
            } else b = dateFormat.parse(updateDate).equals(dateFormat.parse(installDate));//If two dates are equal
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }
    public static String convertNotificationDate(String givenDateString) {
        long timestampString =  Long.parseLong(givenDateString);
        givenDateString = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT).format(new Date(timestampString * 1000));
        String formattedDate = ResponseString.BLANK;
        SimpleDateFormat sdf = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT);
        try {
            Date mDate = sdf.parse(givenDateString);
            Date date = new Date();
            date.setTime(mDate.getTime());
            formattedDate = new SimpleDateFormat(ResponseString.APP_DATE_FORMAT).format(date);

            if (Integer.parseInt(findNumberOfDay(formattedDate, getCurrentDate())) == ResponseCode.ZERO) {
                formattedDate = "Today " + convertNotificationTime(givenDateString);
            } else if (Integer.parseInt(findNumberOfDay(formattedDate, getCurrentDate())) == ResponseCode.ONE) {
                formattedDate = "Yesterday " + convertNotificationTime(givenDateString);
            } else if (Integer.parseInt(findNumberOfDay(formattedDate, getCurrentDate())) == ResponseCode.TWO) {
                formattedDate = "1 day ago " + convertNotificationTime(givenDateString);
            } else if (Integer.parseInt(findNumberOfDay(formattedDate, getCurrentDate())) == ResponseCode.THREE) {
                formattedDate = "2 day ago " + convertNotificationTime(givenDateString);
            } else {
                formattedDate = formattedDate + " " + convertNotificationTime(givenDateString);
            }
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
            return formattedDate;
        }
    }
    public static String convertNotificationTime(String givenDateString) {
        String formattedTime = ResponseString.BLANK;
        SimpleDateFormat sdf = new SimpleDateFormat(ResponseString.APP_DATE_TIME_FORMAT);
        try {
            Date mDate = sdf.parse(givenDateString);
            Date date = new Date();
            date.setTime(mDate.getTime());
            formattedTime = new SimpleDateFormat(ResponseString.APP_TIME_FORMAT).format(date);
            return formattedTime;
        } catch (Exception e) {
            e.printStackTrace();
            return formattedTime;
        }
    }
    public static String findNumberOfDay(String PreDate, String CurrDate) {
        long diffDays = 0;
        try {
            Date date1;
            Date date2;
            SimpleDateFormat df = new SimpleDateFormat(ResponseString.APP_DATE_FORMAT);
            date1 = df.parse(CurrDate);
            date2 = df.parse(PreDate);
            long diff = Math.abs(date1.getTime() - date2.getTime());
            diffDays = diff / (24 * 60 * 60 * 1000);
            System.out.println(diffDays);
        } catch (Exception e1) {
            System.out.println("exception " + e1);
        }
        return Long.toString(diffDays);
    }
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
