package com.example.teacherportalactivity.activity;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.Telephony.ThreadsColumns.ERROR;

public class Utils {
    private final static int MAX_REDIRECTION = 10;
    public static String FileFolderName = "FileFolderName";
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    private Utils() {
        // no instance
    }

    public static String loadJSONFromAsset(String filePath, Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String loadJSONFromStorage(String filePath, Context context) {
        String json = null;
        try {
            /* InputStream is = context.getResources().getAssets().open(filePath);*/
            File file = new File(context.getFilesDir(), filePath);
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static void freeMemory(){
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
    public static String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }
    public static Drawable getBitmapFromAsset(String strName, Context context) {
        try {
            File file = new File(context.getFilesDir(), strName);
            FileInputStream inputStream = new FileInputStream(file);
            Drawable d = Drawable.createFromStream(inputStream, null);
            return d;
        } catch (IOException ex) {
            return null;
        }
    }
    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean mobileValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[0-9]{10}";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static String getDeviceName() {
        String DeviceName = "";
        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                DeviceName = capitalize(model);
                return DeviceName;
            }
            DeviceName = capitalize(manufacturer) + " " + model;
            return DeviceName;
        } catch (Exception e) {
            e.printStackTrace();
            return DeviceName;
        }

    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public static String getAndroidId(Context applicationContext) {
        String AndroidId = "";
        try {
            AndroidId = Settings.Secure.getString(applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            return AndroidId;
        } catch (Exception e) {
            e.printStackTrace();
            return AndroidId;
        }
    }

    public static long getSystemTime() {
        long returnTime = System.currentTimeMillis();
        Date time = new Date(returnTime);
        return returnTime;
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
//                    null)[0];
            File file = context.getFilesDir();
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }


    /*check  internal & external memory is exist or not */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.
                getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED);
    }

    /*get Total Internal memory*/
    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long BlockSize = stat.getBlockSize();
        long TotalBlocks = stat.getBlockCount();
        return formatSize(TotalBlocks * BlockSize);
    }

    /*get Available Internal memory*/
    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return formatSize(availableBlocks * blockSize);
    }

    /*get Total External memory*/
    public static String getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.
                    getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long BlockSize = stat.getBlockSize();
            long TotalBlocks = stat.getBlockCount();
            return formatSize(TotalBlocks * BlockSize);
        } else {
            return ERROR;
        }
    }
    /*get Available External memory*/
    public static String getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return formatSize(availableBlocks * blockSize);
        } else {
            return ERROR;
        }
    }
    /*Convert Memory  size MB & KB Format*/
    public static String formatSize(long size) {
        String suffixSize = null;

        if (size >= 1024) {
            suffixSize = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffixSize = "MB";
                size /= 1024;
            }
        }
        StringBuilder BufferSize = new StringBuilder(
                Long.toString(size));
        int commaOffset = BufferSize.length() - 3;
        while (commaOffset > 0) {
            BufferSize.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffixSize != null) BufferSize.append(suffixSize);
        return BufferSize.toString();
    }
    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     * */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress
     *            -
     * @param totalDuration
     *            returns current duration in milliseconds
     * */
    public static int  progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }
    public static void mSetTextImage(final TextView mTextView, final Drawable mDrawableImage) {
        mTextView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mDrawableImage.setBounds(0, 0,
                                (int) (mDrawableImage.getIntrinsicHeight()/1.2), (int) (mDrawableImage.getIntrinsicHeight()/1.2));
                        mTextView.setCompoundDrawables(mDrawableImage, null, null, null);
                        mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    public static byte[] downloadUrl(URL toDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }

}
