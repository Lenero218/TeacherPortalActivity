package com.example.teacherportalactivity.manager;

import android.content.Context;
import android.net.ConnectivityManager;


public class AppConnectivityManager {

	private final ConnectivityManager mConnectivityManager;
	private static AppConnectivityManager mAppConnectivityManager = null;

	private AppConnectivityManager(Context ctx) {
		mConnectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public static AppConnectivityManager getInstance(Context ctx) {
		if (mAppConnectivityManager == null)
			mAppConnectivityManager = new AppConnectivityManager(ctx);
		return mAppConnectivityManager;
	}

	public boolean isConnected() {
		return mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isConnected();
	}

	public boolean isAvailable() {
		return mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isAvailable();
	}

	public boolean isConnectedOrConnecting() {
		return mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public boolean isFailover() {
		return mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isFailover();
	}

	public boolean isRoaming() {
		return mConnectivityManager.getActiveNetworkInfo() != null && mConnectivityManager.getActiveNetworkInfo().isRoaming();
	}
	
	public boolean isMobileAvailable(){
		try{
			android.net.NetworkInfo mobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			return mobile.isAvailable();
		}catch(Exception e){
		    return false;	
		}	
	}
	
	public boolean isMobileConnected(){
		try{
			android.net.NetworkInfo mobile =mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			return mobile.isConnected();
		}catch(Exception e){
		    return false;	
		}
	}
	
	
	public boolean isWifiAvailable(){
		android.net.NetworkInfo wifi =mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isAvailable();
	}
	
	public boolean isWifiConnected(){
		android.net.NetworkInfo wifi =mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}
}
