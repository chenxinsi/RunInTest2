package com.android.runintest2;


import com.example.runintest2.R;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;


public class RunInTestFragment extends Fragment{

	private Button btn;
	private Intent intent;
	private Bundle bundle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.runintestfragment, container, false);
		btn = (Button) view.findViewById(R.id.runintest_btn);
        
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
}