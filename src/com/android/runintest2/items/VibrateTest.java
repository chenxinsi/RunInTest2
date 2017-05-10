package com.android.runintest2.items;

import com.android.runintest2.RunInTestActivity;
import com.example.runintest2.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.runintestfragment, container, false);
		tv= (TextView) view.findViewById(R.id.runintest_tv);
		btn = (Button) view.findViewById(R.id.runintest_btn);
		tv.setText("震动测试");
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		bundle =  getArguments();
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				intent = (Intent)bundle.getParcelable(RunInTestActivity.BUNDLE_ACTION_INTENT);
				startActivity(intent);
			}
		});
	}

}
