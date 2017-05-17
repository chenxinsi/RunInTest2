package com.android.runintest;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.runintest.drawer.BaseBroadCastReceiver;

/**
 * Created by xinsi on 17-5-17.
 */
public class SystemBroadcastReceiver extends BaseBroadCastReceiver{
    private static final String  UNLOCK_ACTION = "android.intent.action.USER_PRESENT";

    @Override
    public void onReceive(Context context, Intent intent) {

        /**
         * 进入用户可操作界面 发送广播 （未锁屏）
         */
        if(intent.getAction().equals(UNLOCK_ACTION)){
            KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
            Log.d("xinsi", "onReceive flag: " + flag);
        }
    }

}
