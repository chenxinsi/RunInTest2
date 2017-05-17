package com.android.runintest.drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
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
	
	public static final String ACTION_TESTITEM =
			"com.android.runintest.action.RunInTest";
	private static final String METADATA_TESTITEM_TITLE =
			    "com.android.runintest.title";
	public static final String META_DATA_KEY_FRAGMENT_CLASS =
            "com.android.runintest.FRAGMENT_CLASS";
	public static final String META_DATA_TITLE = "com.android.runintest.title";

	public static final String META_DATA_TARGETACTION =
			"com.android.runintest.targetAction";

	private static final String RUNINTEST_PACKAGE = "com.android.runintest";
	
	public static HashMap<String, TestItem> caches;
	
	private static boolean requireRunInTest = true;

	/***
	 * xinsi
	 * for Only Test
	 */
	public static final String ACTION_ONLYTEST =
			"com.android.action.onlytest";
	
	public static List<TestItem> getTestItems(Context context, String action){
		PackageManager pm = context.getPackageManager();
		Intent intent = new Intent();
		intent.setAction(action);
		if(requireRunInTest){
			Log.d("xinsi", "context.getPackageName()" + context.getPackageName());
			intent.setPackage(RUNINTEST_PACKAGE);
		}
		List<ResolveInfo> resolveInfo = pm.queryIntentActivities(
				   intent, PackageManager.GET_META_DATA);
		Log.d("xinsi", "resolveInfo:" + resolveInfo);
		ArrayList<TestItem> items = new ArrayList<TestItem>();
		for(ResolveInfo info : resolveInfo){
			ActivityInfo activityInfo = info.activityInfo;
            Bundle metaData = activityInfo.metaData;
            TestItem item = new  TestItem();
			intent = new Intent();
			item.intent = intent.setClassName(activityInfo.packageName,activityInfo.name)
					.setAction(action);
			item.metaData = metaData;
			items.add(item);
			Log.d("xinsi", "item action :" + item.intent.getAction().toString() +"\n"
			            + "META_DATA_KEY_FRAGMENT_CLASS:"+item.metaData.getString(META_DATA_KEY_FRAGMENT_CLASS)
			            + "\n" + "com.android.runintest.title :" + item.metaData.getString(META_DATA_TITLE));
		}
		Log.d("xinsi","items:"+items);
		return items;
	}
	
	
	
	public static TestItem getTestItemForAction(Context context,String action){
		TestItem test = new TestItem();
		Intent intent = new Intent();
		
		try {
			ActivityInfo ai = context.getPackageManager().getActivityInfo(
					((Activity)context).getComponentName(), PackageManager.GET_META_DATA);
			 test.metaData = ai.metaData;
			 test.title = ai.metaData.getString(METADATA_TESTITEM_TITLE);
			 test.action = ai.metaData.getString(META_DATA_TARGETACTION);
			 test.intent = intent.setAction(ai.metaData.getString(action));
			Log.d("xinsi","test.title :" + test.title);
		} catch (NameNotFoundException e) {
			Log.d(TAG, "Cannot get Metadata for: " + ((Activity)context).getComponentName().toString());
		}

		return test;
	}
	
	
	
	
	
}
