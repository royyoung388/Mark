package com.roy.mark.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.roy.mark.activity.clock.ClockActivity;


/**
 * Created by Administrator on 2017/7/21.
 */

public class ClockAlarmReciver extends BroadcastReceiver {

    private MediaPlayer player;
    private Vibrator vibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到广播");
        String title = intent.getStringExtra("title");
        Intent clockIntent = new Intent(context, ClockActivity.class);
        clockIntent.putExtra("title", title);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }
}
