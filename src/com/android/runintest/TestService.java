package com.android.runintest;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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


    private SystemBroadcastReceiver receiver;
    private Boolean isRegister = false;


    @Override
    public void onCreate() {
        super.onCreate();
        LogRuningTest.printDebug(TAG, "onCreate()", this);
        /**
         * 获取RebootReceiver的状态
         */
        String state = CommonUtils.getComponentState(this, new ComponentName(this, RebootReceiver.class));
        LogRuningTest.printDebug(TAG, "rebootreceiver  state :" + state, this);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogRuningTest.printDebug(TAG, "onDestroy()", this);
    }

}
