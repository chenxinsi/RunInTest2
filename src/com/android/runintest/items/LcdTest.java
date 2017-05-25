package com.android.runintest.items;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import com.android.runintest.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.runintest.TestService;
import com.android.runintest.drawer.BaseFragment;
import com.android.runintest.itemdata.RunInTestData;

public class LcdTest extends BaseFragment {


    private ImageView image;
    private AnimationDrawable drawable;
    private long startTime;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        startTime = System.currentTimeMillis();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lcd_test, container, false);
        image = (ImageView)view.findViewById(R.id.color_image);
        image.setBackgroundResource(R.anim.lcd_color_animation);
        drawable = (AnimationDrawable)image.getBackground();
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mhandler.sendEmptyMessageDelayed(1, RunInTestData.LCD_DURATION);
    }

    private void startColorAnimation(){
        if(drawable != null && !drawable.isRunning()){
            drawable.start();
        }
    }

    private void stopColorAnimation(){
        if(drawable != null && drawable.isRunning()){
            drawable.stop();
            drawable = null;
            image.setVisibility(View.GONE);
        }
        Log.d("xinsi", "took " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    public void onResume() {
        super.onResume();
        startColorAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mhandler.removeMessages(1);
    }

    private void gotoVedioTest(){
        //startActivity(new Intent().setAction(getTestItem().action));
        getActivity().sendBroadcast(new Intent(TestService.ACTION)
        .putExtra("bindaction", getTestItem().action));
        getActivity().finish();
    }

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopColorAnimation();
            gotoVedioTest();
        }
    };
}
