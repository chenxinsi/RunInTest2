package com.android.runintest2.items;

import com.android.runintest2.RunInTestActivity;
import com.example.runintest2.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VibrateTest extends Fragment{
	private TextView tv;
	private Button btn;
	private Intent intent;
	private Bundle bundle;
	
	private static final long[] pattern = {1000, 2000, 1000, 2000};
	private Vibrator vibrator;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		vibrator =(Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.runintestfragment, container, false);
		tv= (TextView) view.findViewById(R.id.runintest_tv);
		tv.setText("震动测试");
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		vibrator.vibrate(pattern, -1);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bundle =  getArguments();
        /*btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				intent = (Intent)bundle.getParcelable(RunInTestActivity.BUNDLE_ACTION_INTENT);
				startActivity(intent);
			}
		});*/
	}

}
