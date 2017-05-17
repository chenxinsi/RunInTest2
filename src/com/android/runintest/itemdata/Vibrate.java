package com.android.runintest.itemdata;

/**
 * Created by xinsi on 17-5-15.
 */
public class Vibrate {

        //Vibrate loop times
        private int loop;

        //Vibration duration
        private long vibrate_duration;

        //stop Vibration duration
        private long stop_duration;

        public int getLoop(){
            return loop;
        }

        public void setLoop(int loop){
            this.loop = loop;
        }

        public long getVibrate_duration(){
            return vibrate_duration;
        }

        public void setVibrate_duration(long vibrate_duration){
            this.vibrate_duration = vibrate_duration;
        }

        public long getStop_duration(){
            return stop_duration;
        }

        public void setStop_duration(long stop_duration){
            this.vibrate_duration = stop_duration;
        }




}
