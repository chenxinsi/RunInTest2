package com.android.runintest;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.*;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.runintest.items.reboot.RebootReceiver;
import com.android.runintest.utils.CommonUtils;
import com.android.runintest.utils.LogRuningTest;

/**
 * Created by xinsi on 17-5-17.
 */

/**
 * 监控电量 和 各个运行测试组件的状态
 */
public class TestService  extends Service{
    private static final String TAG = "TestService";
    private static final String  UNLOCK_ACTION = "android.intent.action.USER_PRESENT";
    public static final String ACTION = "android.intent.action.RECEIVER";


    private SystemBroadcastReceiver receiver;

    private TestServiceReceiver serviceReceiver;
    private boolean isRegister = false ;
    private long startTime;

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
        LogRuningTest.printDebug(TAG, "onCreate()", this);
        /**
         * 获取RebootReceiver的状态
         */
        String state = CommonUtils.getComponentState(this, new ComponentName(this, RebootReceiver.class));
        LogRuningTest.printDebug(TAG, "rebootreceiver  state :" + state, this);

        serviceReceiver = new TestServiceReceiver();
        registerBroadcast();
        LogRuningTest.printDebug("111", "onCreate took" +
                (System.currentTimeMillis() - startTime) + "ms" , this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogRuningTest.printDebug(TAG, "onBind()", this);
        return null;
    }

    public void goToNextTest(String action){
        startActivity(new Intent(action));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadcast();
        LogRuningTest.printDebug(TAG, "onDestroy()", this);
    }



    private void registerBroadcast(){
        if(!isRegister) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION);
            registerReceiver(serviceReceiver, filter);
            isRegister = true;
        }
    }

    private void unRegisterBroadcast(){
        if(isRegister) {
            unregisterReceiver(serviceReceiver);
            isRegister = false;
        }
    }

    /**
     * Action 接收广播
     */
    public class TestServiceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            LogRuningTest.printDebug(TAG, "TestServiceReceiver onReceive", context);
            LogRuningTest.printDebug(TAG, "intent.getAction():" + intent.getAction(), context);
             if(intent.getAction().equals(ACTION)){
                 goToNextTest(intent.getExtras().getString("bindaction"));
             }
        }
    }
}
