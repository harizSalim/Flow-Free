package com.flowfree.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.flowfree.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    private MediaPlayer mediaPlayer;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);

        //MediaPlayer for button-sound
        mediaPlayer = MediaPlayer.create(this, R.raw.button_pressed);

        setContentView(R.layout.activity_main);
    }

    protected void onResume(){
        super.onResume();

        //Sound settings
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sound = prefs.getBoolean("sound_onoff", true);
        if (sound) {
            mediaPlayer.setVolume(1.0f, 1.0f);
        } else {
            mediaPlayer.setVolume(0, 0);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
    }

    public void buttonPressed(View view){
        mediaPlayer.start();
        startActivity(new Intent(this, LevelChoice.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
