package com.flowfree.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.flowfree.db.Level;
import com.flowfree.db.LevelsDataBase;
import com.game.flowfree.R;


public class MainActivity extends AppCompatActivity {
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
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    private MediaPlayer mediaPlayer;
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //Initializing dataBase
        LevelsDataBase levelsDb = new LevelsDataBase(this);
        Level level27 = new Level(27, 0);
        Level level37 = new Level(37, 0);
        Level level28 = new Level(28, 0);
        Level level38 = new Level(38, 0);
        levelsDb.open();

        if (levelsDb.isEmpty()) {
            levelsDb.insertLevel(level27);
            levelsDb.insertLevel(level37);
            levelsDb.insertLevel(level28);
            levelsDb.insertLevel(level38);
        }
        levelsDb.close();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void buttonPressed(View view) {
        startActivity(new Intent(this, LevelChoice.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void buttonAboutPressed(View view) {
        startActivity(new Intent(this, AboutActivity.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void buttonInstructionsPressed(View view) {

        startActivity(new Intent(this, InstructionActivity.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void buttonQuitterPressed(View view) {
          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You Want to Quit");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
