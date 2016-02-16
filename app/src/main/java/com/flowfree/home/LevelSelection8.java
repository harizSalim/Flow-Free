package com.flowfree.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.flowfree.db.LevelsDataBase;
import com.flowfree.levels.Level18x8;
import com.flowfree.levels.Level28x8;
import com.flowfree.levels.Level38x8;
import com.game.flowfree.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LevelSelection8 extends AppCompatActivity implements View.OnClickListener {
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
    private Button level1, level2, level3, back;
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_selection8);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);

        level1 = (Button) findViewById(R.id.level_1_8);
        level2 = (Button) findViewById(R.id.level_2_8);
        level3 = (Button) findViewById(R.id.level_3_8);
        back = (Button) findViewById(R.id.back8);
        //Disabling Buttons
        setUpLevels();
        //Setting buttons Listener
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(level1)) {
            startActivity(new Intent(this, Level18x8.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if (v.equals(level2)) {
            startActivity(new Intent(this, Level28x8.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if (v.equals(level3)) {
            startActivity(new Intent(this, Level38x8.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if (v.equals(back)) {
            startActivity(new Intent(this, LevelChoice.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
    private void setUpLevels(){
        LevelsDataBase levelsDb= new LevelsDataBase(this);
        levelsDb.open();
        if(levelsDb.getLevelStatus(28)==0 ){
            level2.setEnabled(false);
            level2.setBackgroundColor(Color.LTGRAY);
        }else{
            level2.setEnabled(true);
            level2.setBackgroundColor(Color.WHITE);
        }
        if(levelsDb.getLevelStatus(38)==0 ){
            level3.setEnabled(false);
            level3.setBackgroundColor(Color.LTGRAY);
        }else{
            level3.setEnabled(true);
            level3.setBackgroundColor(Color.WHITE);
        }
        levelsDb.close();
    }
}
