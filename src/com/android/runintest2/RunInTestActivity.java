package com.android.runintest2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.runintest2.R;

public class RunInTestActivity extends BaseActivity{
	
    private static final String TAG = "RunInTestActivity";
    
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);
    
    
    private TextView text;
    private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long startTime = System.currentTimeMillis();
		initRunInTest();
		if(DEBUG_TIME) 
			Log.d(TAG,"onCreate took" +(System.currentTimeMillis() - startTime) + "ms");
	}
	
	private void initRunInTest(){
		text = (TextView) findViewById(R.id.tv_label);
		btn = (Button) findViewById(R.id.btn_label);
		text.setText("RunInTestActivity");
		btn.setText("跳转RebootTest");
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RunInTestActivity.this, RebootTestActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(DEBUG)
		   Log.d(TAG, "onDestory");
	}
}
