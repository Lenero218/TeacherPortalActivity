package com.example.teacherportalactivity;


import android.content.Context;
import android.os.StrictMode;

import com.example.teacherportalactivity.helper.PreferenceHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ConvertUrlToString {

    private final Context mContext;
    private final PreferenceHelper pHelper;

    public ConvertUrlToString(Context context, PreferenceHelper pHelper) {

        this.mContext = context;
        this.pHelper = pHelper;
    }
    public void ConvertToText(String content,String URL){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String webPage = URL;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            pHelper.setString(content,result);
            System.out.println("*** BEGIN ***");
            System.out.println(result);
            System.out.println("*** END ***");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
