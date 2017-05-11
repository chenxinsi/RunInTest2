package com.android.runintest2.drawer;

import java.util.HashMap;
import java.util.List;

import junit.framework.Test;

import com.android.runintest2.RunInTestActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

public class TestItemUtils {

	private static final String TAG = "TestItemUtils";
	
	private static final String ACTION_TESTITEM = 
			"com.android.runintest2.action.RunInTest";
	private static final String METADATA_TESTITEM_TITLE =
			    "com.android.runintest2.title";
	public static final String META_DATA_KEY_FRAGMENT_CLASS =
            "com.android.runintest2.FRAGMENT_CLASS";
	
	private static final String RUNINTEST_PACKAGE = "com.android.runintest2";
	
	public static HashMap<String, TestItem> caches;
	
	private static boolean requireRunInTest = true;

	/***
	 * xinsi
	 * for Only Test
	 */
	public static final String ACTION_ONLYTEST =
			"com.android.action.onlytest";
	
	public static List<TestItem> getTestItems(Context context){
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent();
		intent.setAction(ACTION_ONLYTEST);
		if(requireRunInTest){
			Log.d("xinsi", "context.getPackageName()" + context.getPackageName());
			//intent.setPackage(RUNINTEST_PACKAGE);
		}
		List<ResolveInfo> resolveInfo = pm.queryIntentActivities(
				   intent, PackageManager.GET_META_DATA);
		Log.d("xinsi", "resolveInfo:" + resolveInfo);
		for(ResolveInfo info : resolveInfo){
			ActivityInfo activityInfo = info.activityInfo;
            Bundle metaData = activityInfo.metaData;
            Log.d("xinsi",metaData.getString(META_DATA_KEY_FRAGMENT_CLASS)
            		+ "\n" + "activityInfo.name:"+ activityInfo.name
            		+ "\n" + "activityInfo.packageName" + activityInfo.packageName);
		}
		return null;
	}
	
	
	
	public static TestItem getTestItem(Context context){
		TestItem test = new TestItem();
		Intent intent = new Intent();
		
		try {
			ActivityInfo ai = context.getPackageManager().getActivityInfo(
					((Activity)context).getComponentName(), PackageManager.GET_META_DATA);
			 test.metaDate = ai.metaData;
			 test.title = ai.metaData.getString(METADATA_TESTITEM_TITLE);
			 test.intent = intent.setAction(ai.metaData.getString(ACTION_TESTITEM));
		} catch (NameNotFoundException e) {
			Log.d(TAG, "Cannot get Metadata for: " + ((Activity)context).getComponentName().toString());
		}

		return test;
	}
	
	
	
	
	
}
