package com.android.runintest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.runintest.R;
import com.android.runintest.drawer.BaseActivity;
import com.android.runintest.drawer.CustomDialog;
import com.android.runintest.drawer.TestItem;
import com.android.runintest.drawer.TestItemUtils;
import com.android.runintest.itemdata.RunInTestData;
import com.android.runintest.items.*;
import com.android.runintest.items.reboot.RebootTest;
import com.android.runintest.onlytest.OnlyTest1;
import com.android.runintest.onlytest.OnlyTest2;
import com.android.runintest.utils.CommonUtils;

public class RunInTestActivity extends BaseActivity{
	
    private static final String TAG = "RunInTestActivity";
    
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);


    private boolean isRunInTestActivity;
    
    private String mFragmentclass;
    private Bundle bundle;
    
    final String[] singleChoice = {"开发模式","工厂模式"};
	//默认测试模式 为工厂模式
	private int TestMode = RunInTestData.FACTORYTESTMODE;
    
    public static String[] ACTIVITY_FOR_RUNINTEST = {
			RunInTest.RebootTestActivity.class.getName(),
    		RunInTest.VibrateTestActivity.class.getName(),
    		RunInTest.LcdTestActivity.class.getName(),
    		RunInTest.AudioTestActivity.class.getName(),
    		RunInTest.VedioTestActivity.class.getName(),
    		RunInTest.LightSensorActivity.class.getName(),

			//onlyTest
			RunInTest.OnlyTestActivity1.class.getName(),
			RunInTest.OnlyTestActivity2.class.getName()
    };
    
    private static final String[] ENTRY_FRAGMENT = {
    	RunInTestFragment.class.getName(),
		RebootTest.class.getName(),
    	VibrateTest.class.getName(),
    	LcdTest.class.getName(),
    	AudioTest.class.getName(),
    	VedioTest.class.getName(),
    	LightSenorTest.class.getName(),

    	//onlyTest
		OnlyTest1.class.getName(),
		OnlyTest2.class.getName()

    };
    
    protected boolean isValidFragment(String fragmentName){
    	
    	for(int i = 0; i<ENTRY_FRAGMENT.length; i++){
    		 if(ENTRY_FRAGMENT[i].equals(fragmentName)) return true;
    	}
    	return false;
    }

	/**
	 * onlyTest
	 */
	private TestItem item;

	/**
	 *
	 *
	 *
	**/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long startTime = System.currentTimeMillis();

		isRunInTestActivity = getClass().getName().equals(RunInTestActivity.class.getName());
		if(isRunInTestActivity) {
			CustomDialog.Builder checkBuilder = new CustomDialog.Builder(this);
			CustomDialog dialog = checkBuilder.setTitle("是否需要执行老化测试")
					.setMessage("请选择默认工厂模式, 开发模式仅供开发人员调试, 谢谢！")
					.setSingleSelection(singleChoice, 1, new SingleChoice())
					.setPositiveButton("执行", new CheckStartListener())
					.setNegativeButton("退出", new CancelTestingListener())
					.create();
			dialog.setCancelable(false);
			dialog.show();
		}
/*
		if(false && isRunInTestActivity){
			AlertDialog checkStartTest = new AlertDialog.Builder(RunInTestActivity.this)
            .setTitle("是否需要执行老化测试")
            //.setView(inputRebootCount)
            .setCancelable(false)
            .setSingleChoiceItems(singleChoice, 1,null)
            //.setMessage("老化测试执行后在测试完成前无法退出，请确认是否需要执行")
            .setPositiveButton("执行老化测试", new CheckStartListener())
            .setNegativeButton("退出老化测试", new CancelTestingListener())
            .show();
		}*/
		getMetaData();
		
		switchToFragment(mFragmentclass, true, true, bundle);
		if(DEBUG_TIME) 
			Log.d(TAG,"onCreate took" +(System.currentTimeMillis() - startTime) + "ms");

	}


	private void exitTest(){
		//开发模式
		if(TestMode == RunInTestData.DEVELOPTESTMODE){

		}

		//工厂模式
		if(TestMode == RunInTestData.FACTORYTESTMODE){
			CommonUtils.stopTestService(RunInTestActivity.this,TAG);
			RunInTestActivity.this.finish();
		}

	}

	/**
	 * 开发模式
	 *
	 *
	 *
	 * 工厂模式
	 * 执行老化测试程序 , 启动TestService, 允许接收RebootReceiver广播, 解锁屏
	 */
	private void startTest(){
		//开发模式
		if(TestMode == RunInTestData.DEVELOPTESTMODE){

		}

		//工厂模式
        if(TestMode == RunInTestData.FACTORYTESTMODE){
			initData();

			CommonUtils.startTestService(this, TAG);

			unLock();

			Intent intent =new Intent().setAction(
					item.metaData.getString(TestItemUtils.META_DATA_TARGETACTION));
			startActivity(intent);

			RunInTestActivity.this.finish();
		}

	}

    private void initData(){
		SharedPreferences initdata = getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = initdata.edit();
		//允许接收RebootReceiver广播
		editor.putBoolean(RunInTestData.ISRUNINTEST, true);



		editor.commit();
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
		item = TestItemUtils.getTestItemForAction(
				     this , TestItemUtils.ACTION_TESTITEM);
		mFragmentclass = item.metaData.getString(TestItemUtils.META_DATA_KEY_FRAGMENT_CLASS);
		bundle = new Bundle();
		bundle.putParcelable("testitem",item);
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
			exitTest();
	    }
    }
	
	private class CheckStartListener implements DialogInterface.OnClickListener {
	    @Override
	    public void onClick(DialogInterface arg0, int arg1) {
			startTest();
	    }
	       
    }

	private class SingleChoice implements RadioGroup.OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup radioGroup, int i) {
			if(i == RunInTestData.DEVELOPTESTMODE){
				TestMode = RunInTestData.DEVELOPTESTMODE;
				Toast.makeText(RunInTestActivity.this, singleChoice[0], 0).show();
			}else if(i == RunInTestData.FACTORYTESTMODE){
				TestMode = RunInTestData.FACTORYTESTMODE;
				Toast.makeText(RunInTestActivity.this, singleChoice[1], 0).show();
			}
		}
	}
	
}
