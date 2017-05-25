package com.android.runintest.items;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.*;
import com.android.runintest.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.android.runintest.TestService;
import com.android.runintest.drawer.BaseFragment;
import com.android.runintest.itemdata.RunInTestData;
import com.android.runintest.utils.LogRuningTest;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}


	@Override
	public void onDestroy() {
		LogRuningTest.printDebug(TAG, "onDestory()", getActivity());
		super.onDestroy();
		vibrator.cancel();
		handler.removeMessages(1);
		//getActivity().unbindService(mConnection);
		LogRuningTest.printDebug(TAG, "vibrate end", getActivity());
	}

	 Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            //startActivity(new Intent().setAction(getTestItem().action));
			//bindActionService(getTestItem().action);
			getActivity().sendBroadcast(new Intent(TestService.ACTION)
					.putExtra("bindaction", getTestItem().action));
			LogRuningTest.printDebug(TAG, "finish()", getActivity());
			getActivity().finish();
		}
	};
}
