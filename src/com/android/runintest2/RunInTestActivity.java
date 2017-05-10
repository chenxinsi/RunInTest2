package com.android.runintest2;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.android.runintest2.drawer.BaseActivity;
import com.android.runintest2.items.AudioTest;
import com.android.runintest2.items.LcdTest;
import com.android.runintest2.items.LightSenorTest;
import com.android.runintest2.items.VedioTest;
import com.android.runintest2.items.VibrateTest;
import com.example.runintest2.R;

public class RunInTestActivity extends BaseActivity{
	
    private static final String TAG = "RunInTestActivity";
    
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
    
    public static final String META_DATA_KEY_FRAGMENT_CLASS =
            "com.android.runintest2.FRAGMENT_CLASS";
    public static final String RUNINTEST_ACTION = 
    		"com.android.runintest2.action.RunInTest";
    public static final String META_DATA_ACTION = 
    		"com.android.runintest2.ACTION";
    public static final String ADD_BACK_STACK = "add_back_stack";
    
    public static final String BUNDLE_ACTION_INTENT = "actionIntent";
    
    public static int GOTOATIVITY = 0;
    private boolean isRunInTestActivity;
    
    private String mFragmentclass; 
    private String mActionName;
    private Intent actionIntent;
    private Bundle bundle;
    
    
    private String[] ACTIVITY_FOR_RUNINTEST = {
    		RunInTest.VibrateTestActivity.class.getName(),
    		RunInTest.LcdTestActivity.class.getName(),
    		RunInTest.AudioTestActivity.class.getName(),
    		RunInTest.VedioTestActivity.class.getName(),
    		RunInTest.LightSensorActivity.class.getName()
    };
    
    private static final String[] ENTRY_FRAGMENT = {
    	RunInTestFragment.class.getName(),
    	VibrateTest.class.getName(),
    	LcdTest.class.getName(),
    	AudioTest.class.getName(),
    	VedioTest.class.getName(),
    	LightSenorTest.class.getName(),
    };
    
    protected boolean isValidFragment(String fragmentName){
    	
    	for(int i = 0; i<ENTRY_FRAGMENT.length; i++){
    		 if(ENTRY_FRAGMENT[i].equals(fragmentName)) return true;
    	}
    	return false;
    }
    
    protected SharedPreferences getDefaultSharedPreferences(){
    	return getSharedPreferences(getDefaultSharedPreferencesName(), Context.MODE_PRIVATE);
    }
    
    protected String getDefaultSharedPreferencesName(){
    	return getPackageName() + "_preferences";
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long startTime = System.currentTimeMillis();
		isRunInTestActivity = getClass().getName().equals(RunInTestActivity.class.getName());
		if(isRunInTestActivity){
			AlertDialog checkStartTest = new AlertDialog.Builder(RunInTestActivity.this)
            .setTitle("是否需要执行老化测试")
            //.setView(inputRebootCount)
            .setMessage("老化测试执行后在测试完成前无法退出，请确认是否需要执行")
            .setPositiveButton("退出老化测试", new CheckStartListener())
            .setNegativeButton("执行老化测试", new CancelTestingListener())
            .show();
		}
		getMetaData();
		
		switchToFragment(mFragmentclass, true, true, bundle);
		if(DEBUG_TIME) 
			Log.d(TAG,"onCreate took" +(System.currentTimeMillis() - startTime) + "ms");
		
		
	}
	
	
	private Fragment switchToFragment(String fragmentName, boolean addToBackStack,
			boolean validate,Bundle args){
		
		if (validate && !isValidFragment(fragmentName)) {
            throw new IllegalArgumentException("Invalid fragment for this activity: "
                    + fragmentName);
        }
		
		
		Fragment f = Fragment.instantiate(this, fragmentName);
		f.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, f);
        
        /**
         * xinsi ?????????????????????????
         */
        if(addToBackStack){
        	//transaction.addToBackStack(ADD_BACK_STACK);
        }
        
        transaction.commit();
        getFragmentManager().executePendingTransactions();
		return f;
	}
	
	private void getMetaData(){
		try {
			ActivityInfo ai = getPackageManager().getActivityInfo(getComponentName(), 
					PackageManager.GET_META_DATA);
			if(ai == null || ai.metaData == null) return;
			mFragmentclass = ai.metaData.getString(META_DATA_KEY_FRAGMENT_CLASS);
			//mActionName = ai.metaData.getString(RunInTestActivity.META_DATA_ACTION);
			actionIntent = new Intent();
			if(GOTOATIVITY < ACTIVITY_FOR_RUNINTEST.length ){
			 actionIntent.setClassName(ai.packageName, ACTIVITY_FOR_RUNINTEST[GOTOATIVITY]);
			 GOTOATIVITY++;
			}else{
				return;
			}
			
			if(bundle == null){
				bundle = new Bundle();
			}else{
				bundle.clear();
			}
			bundle.putParcelable(BUNDLE_ACTION_INTENT, actionIntent);
		} catch (NameNotFoundException e) {
			Log.d(TAG, "Cannot get Metadata for: " + getComponentName().toString());
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(DEBUG)
		   Log.d(TAG, "onDestory");
	}
	
	private class CancelTestingListener implements DialogInterface.OnClickListener {
	    @Override
	    public void onClick(DialogInterface arg0, int arg1) {
	    	// TODO Auto-generated method stub
	    	
	    }
    }
	
	private class CheckStartListener implements DialogInterface.OnClickListener {
	    @Override
	    public void onClick(DialogInterface arg0, int arg1) {
	    	// TODO Auto-generated method stub
	    	
	    }    
    }
	
}
