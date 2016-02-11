package com.flowfree.home;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.game.flowfree.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoadingActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    SeekBar bar;
    TextView txtload;
    MediaPlayer media;
    Activity activity;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);

        activity = this;
        bar = (SeekBar) findViewById(R.id.seekBar1);
        txtload = (TextView) findViewById(R.id.textView2);
        media = MediaPlayer.create(LoadingActivity.this, R.raw.button_pressed);

    }

    protected void onResume() {
        //

        media.start();
        Thread t = new Thread() {

            @Override
            public void run() {
                for (i = 0; i <= 100; i++) {
                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            bar.setProgress(i);
                            txtload.setText(i + " %");

                        }

                    });

                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent ihome = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(ihome);
                super.run();

            }

        };
        t.start();
        super.onResume();

    }

    protected void onPause() {
        media.release();
        finish();
        super.onPause();

    }


}
