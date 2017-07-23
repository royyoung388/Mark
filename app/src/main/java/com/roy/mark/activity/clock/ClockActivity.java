package com.roy.mark.activity.clock;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.roy.mark.R;
import com.roy.mark.ui.ClockDialog;

/**
 * Created by Administrator on 2017/7/21.
 */

public class ClockActivity extends AppCompatActivity {

    private MediaPlayer player;
    private Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        init();
        start();
        showDialog();
    }

    private void init() {
        player = new MediaPlayer();
        vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
    }

    // 播放铃声和震动
    private void start() {
        if (player.isPlaying() || player.isLooping()) {
            return;
        }

        try {
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            player.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
                player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                player.setLooping(true);
                player.prepare();
                player.start();
            }

            vibrator.vibrate(new long[]{1000, 2000}, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {
        final ClockDialog dialog = new ClockDialog(getIntent().getStringExtra("title"));
        dialog.setDialogClickListener(new ClockDialog.DialogClickListener() {
            @Override
            public void DialogClick() {
                player.stop();
                player.release();

                vibrator.cancel();

                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "clock_dialog");
    }
}
