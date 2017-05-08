package com.android.runintest2;

import com.example.runintest2.R;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RebootTestActivity extends BaseActivity{

	private TextView tv;
	private Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initReboot();
		
	}
	
	private void initReboot(){
		tv = (TextView) findViewById(R.id.tv_label);
		btn = (Button) findViewById(R.id.btn_label);
		tv.setText("RebootTestActivity");
		btn.setVisibility(View.INVISIBLE);
		
	}
}
