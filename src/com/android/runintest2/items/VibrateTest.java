package com.android.runintest2.items;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.android.runintest2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import com.android.runintest2.drawer.BaseFragment;
import com.android.runintest2.itemdata.RunInTestData;
import com.android.runintest2.utils.LogRuningTest;

public class VibrateTest extends BaseFragment{

	private static final String TAG = "VibrateTest";

	private long startTime;

	private int longSize;
	private long delayTime;
	private static  long[] pattern;
	private Vibrator vibrator;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogRuningTest.printDebug(TAG,"start vibrate",getActivity());
		vibrator =(Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		longSize = RunInTestData.VIBRATE_LOOP * 2;
		pattern = new long[longSize];
		for(int i = 0; i<pattern.length; i++){
			if(longSize/2 == 0){
				pattern[i] = RunInTestData.VIBRATE_STOP_DURATION;
			}else{
				pattern[i] = RunInTestData.VIBRATE_DURATION;
			}
		}
		delayTime = RunInTestData.DELAY_TIME +
				RunInTestData.VIBRATE_LOOP * ( (RunInTestData.VIBRATE_STOP_DURATION + 500)
						+ RunInTestData.VIBRATE_DURATION);
		if(vibrator != null && vibrator.hasVibrator() != false){
			vibrator.vibrate(pattern, -1);
		}else{
			LogRuningTest.printDebug(TAG, "result:Vibrator test failed", getActivity());
		}
		handler.sendEmptyMessageDelayed(1, delayTime);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		vibrator.cancel();
		handler.removeMessages(1);
		LogRuningTest.printDebug(TAG, "vibrate end", getActivity());
	}

	 Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            startActivity(new Intent().setAction(getTestItem().action));
			getActivity().finish();
		}
	};
}
