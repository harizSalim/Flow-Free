package com.flowfree.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.flowfree.levels.Level17x7;
import com.flowfree.levels.Level18x8;
import com.flowfree.levels.Level27x7;
import com.flowfree.levels.Level37x7;
import com.game.flowfree.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LevelSelection7 extends AppCompatActivity implements View.OnClickListener {
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
    private Button level1, level2, level3, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.level_selection7);
        level1=(Button)findViewById(R.id.level_1_7);
        level2=(Button)findViewById(R.id.level_2_7);
        level3=(Button)findViewById(R.id.level_3_7);
        back=(Button)findViewById(R.id.back7);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        back.setOnClickListener(this);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        //mContentView = findViewById(R.id.fullscreen_content);



    }
      @Override
    public void onClick(View v) {
        if(v.equals(level1)){
            startActivity(new Intent(this, Level17x7.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if(v.equals(level2)){
            startActivity(new Intent(this, Level27x7.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if(v.equals(level3)){
            startActivity(new Intent(this, Level37x7.class));
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        if(v.equals(back)){
              finish();
             // startActivity(getIntent());
        }
    }
}
