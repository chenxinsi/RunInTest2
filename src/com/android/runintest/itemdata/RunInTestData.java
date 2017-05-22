package com.android.runintest.itemdata;

/**
 * Created by xinsi on 17-5-15.
 */
public class RunInTestData {

    public static final long DELAY_TIME = 5 * 1000;


    public static final String RUNINTEST_PREFERENCE = "runintest_preferences";

    /**
     * RunInTest
     */
    public static final int DEVELOPTESTMODE = 0;
    public static final int FACTORYTESTMODE = 1;
    public static final String  ISRUNINTEST = "isrunintest"; //允许接收RebootReceiver广播

    /**
     * reboot
     */
    public static final int COUNTDOWN = 5;
    public static final int REBOOT_LOOP = 1 ;

    /**
     * Vibrate
     */
    public static final int VIBRATE_LOOP = 5;
    public static final long VIBRATE_DURATION = 15 * 100;
    public static final long VIBRATE_STOP_DURATION = 1 * 1000;

    /**
     * LcdTest
     */
    public static final long LCD_DURATION = 30 * 1000;
}
