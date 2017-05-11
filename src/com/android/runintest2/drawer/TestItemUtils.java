package com.android.runintest2.drawer;

import java.util.HashMap;
import java.util.List;

import com.android.runintest2.RunInTestActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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
	
	/*public static HashMap<String, TestItem> getTestItems(){
		caches = new HashMap<String, TestItem>();
		
		 for(int i = 0 ; i<RunInTestActivity.ACTIVITY_FOR_RUNINTEST.length; i++){
			 caches.put(RunInTestActivity.ACTIVITY_FOR_RUNINTEST[i], getTestItem);
		 }
		return caches;
	}
	*/
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
