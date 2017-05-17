package com.android.runintest.items.reboot;

import android.content.*;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.android.runintest.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.runintest.RunInTest;
import com.android.runintest.drawer.BaseFragment;
import com.android.runintest.itemdata.RunInTestData;
import com.android.runintest.utils.CommonUtils;
import com.android.runintest.utils.LogRuningTest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xinsi on 17-5-15.
 */
public class RebootTest extends BaseFragment {

    private static final String TAG = "RebootTest";

    public static final String LOOPTIMES = "reboot_loopTimes";
    public static final String ISHASREBOOT = "reboot_ishasreboot";

    private SharedPreferences runintest_preferences;
    private SharedPreferences.Editor reboot_editor;
    private TextView tv;

    private int countdown = RunInTestData.COUNTDOWN;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.runintestfragment, container, false);
        tv = (TextView)view.findViewById(R.id.runintest_tv);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        /**
         * 开启广播组件
         */
        startRebootReceiver();
        rebootLoop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv.setTextSize(50);
                if(countdown >= 0) {
                    tv.setText("countdown: " + Integer.toString(countdown--));
                }else{
                    tv.setText("now shutdown");
                    LogRuningTest.printDebug(TAG, "now shutdown", getActivity());
                    task.cancel();
                    handler.sendEmptyMessageDelayed(2, 500);
                }
            }else if(msg.what == 2){
                startReboot();
            }else if(msg.what == 3){
                gotoVibrateTest();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 重启循环多少次
     * @param
     */
    private void rebootLoop(){
        runintest_preferences = getDefaultSharedPreferences();
        reboot_editor = runintest_preferences.edit();
        int loopTimes = runintest_preferences.getInt(LOOPTIMES, RunInTestData.REBOOT_LOOP);
        if(isReboot(loopTimes)){
            //重启
            timer.schedule(task, 0, 1000);
        }else{
            //跳转振动
            handler.sendEmptyMessageDelayed(3, RunInTestData.DELAY_TIME);
        }
    }

    /**
     *判断是否重启
     */
    private boolean isReboot(int loopTimes){

        boolean isHasReboot = runintest_preferences.getBoolean(ISHASREBOOT, false);

        if(isHasReboot){
            loopTimes -- ;
            reboot_editor.putInt(LOOPTIMES, loopTimes);
            reboot_editor.putBoolean(ISHASREBOOT, false);
            reboot_editor.commit();
        }
        LogRuningTest.printDebug(TAG, "loopTimes:" + loopTimes, getActivity());
        if( loopTimes > 0){
            reboot_editor.putBoolean(ISHASREBOOT, true);
            reboot_editor.commit();
            return true;
        }
        return false;
    }

    private void gotoVibrateTest(){
        /**
         * 重置 重启数据
         */
        initRebootData();
        /**
         * 禁掉 reboot 广播的组件
         */
        stopRebootReceiver();
        startActivity(new Intent().setAction(getTestItem().action));
        getActivity().finish();
    }


    private void stopRebootReceiver(){
        CommonUtils.setComponentEnabledSetting(
                getActivity(),new ComponentName(getActivity(),RebootReceiver.class),false);
    }

    private void startRebootReceiver(){
        CommonUtils.setComponentEnabledSetting(
                getActivity(),new ComponentName(getActivity(),RebootReceiver.class),true);
    }

    private void initRebootData(){
        reboot_editor.putInt(LOOPTIMES, RunInTestData.REBOOT_LOOP);
        reboot_editor.commit();
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    /**
     * 执行重启操作
     */
    private void startReboot(){
        LogRuningTest.printDebug(TAG,"startReboot",getActivity());
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.setAction(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        getActivity().sendBroadcast(intent);
    }

}
