package com.android.runintest2.drawer;

import com.android.runintest2.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @author xinsi
 * 1. unLock
 * 2. long light
 * 3. forbid MonkeyTest
 * 4. Portrait
 * 5. forbid back key
 */
public class BaseActivity extends Activity{
	private static final String TAG = "BaseActivity";
	protected static final boolean DEBUG_TIME = false;
	private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		//long light
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //protrait
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//forbid mokeyTest
		if(ActivityManager.isUserAMonkey()){
			finish();
			if(DEBUG){
			   Log.d(TAG, "isRunningMonkey" + "\n" + "finish activity");
			}
		}
		//unlock
		
	}
	
	@Override
	public void setContentView(int layoutResID) {
       final ViewGroup parent = (ViewGroup) findViewById(R.id.container);
       LayoutInflater.from(this).inflate(layoutResID, parent);
	}
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	/*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.d(TAG, "User click KEYCODE_BACK. Ignore it.");
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }*/
	
}
