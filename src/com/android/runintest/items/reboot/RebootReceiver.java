package com.android.runintest.items.reboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.runintest.SystemBroadcastReceiver;
import com.android.runintest.drawer.BaseBroadCastReceiver;
import com.android.runintest.itemdata.RunInTestData;
import com.android.runintest.utils.LogRuningTest;

/**
 * Created by xinsi on 17-5-16.
 */
public class RebootReceiver extends BaseBroadCastReceiver {
    private static final String TAG = "RebootReceiver";

    public static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    /**
     * 开机广播
     */
        @Override
        public void onReceive(Context context, Intent intent) {
            SharedPreferences runintestPreferences = getDefaultSharedPreferences(context);
            Boolean isRunInTest = runintestPreferences.getBoolean(RunInTestData.ISRUNINTEST, false);
            if(intent.getAction().equals(ACTION_BOOT) && isRunInTest) {
                LogRuningTest.printDebug(TAG, "isRunInTest:" + isRunInTest, context);
                context.startActivity(new Intent().setAction("com.android.runintest.action.RebootTestActivity")
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
}
