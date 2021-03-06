package com.android.runintest.drawer;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.view.*;
import com.android.internal.widget.LockPatternUtils;
import com.android.runintest.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import com.android.runintest.itemdata.RunInTestData;

/**
 * @author xinsi
 * 1. unLock
 * 2. long light
 * 3. forbid MonkeyTest
 * 4. Portrait
 * 5. forbid back key
 * 6. No Title
 */
public class BaseActivity extends Activity{
	private static final String TAG = "BaseActivity";
	protected static final boolean DEBUG_TIME = false;
	private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//no title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
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
		//设置全屏
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
					//| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

		);

	}

	protected void unLock(){
		//unLock 1
		/*KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
		keyguardLock.disableKeyguard();*/

		//unLock 2
		LockPatternUtils mLockPatternUtils = new LockPatternUtils(this);
		mLockPatternUtils.setLockScreenDisabled(true, UserHandle.myUserId());
	}

	protected SharedPreferences getDefaultSharedPreferences(Context context){
		return context.getSharedPreferences(getDefaultSharedPreferencesName(), Context.MODE_PRIVATE);
	}

	protected String getDefaultSharedPreferencesName(){
		return RunInTestData.RUNINTEST_PREFERENCE;
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



	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.d(TAG, "User click KEYCODE_BACK. Ignore it.");
                return false;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
	
}
