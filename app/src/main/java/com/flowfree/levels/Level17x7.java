package com.flowfree.levels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.flowfree.home.LevelChoice;
import com.flowfree.home.LevelSelection7;
import com.game.flowfree.R;


public class Level17x7 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Colors
        int green = getApplicationContext().getResources().getColor(R.color.green);
        int blue = getApplicationContext().getResources().getColor(R.color.blue);
        int yelow = getApplicationContext().getResources().getColor(R.color.yellow);
        int orange = getApplicationContext().getResources().getColor(R.color.orange);

        setContentView(R.layout.activity_level17x7);
        int couleurs[] = {blue, green, Color.RED, yelow, orange};
        int corX[] = {0, 0, 0, 5, 2, 4, 2, 4, 1, 4};
        int corY[] = {1, 6, 5, 5, 2, 3, 4, 5, 5, 4};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(1, 7);
        board.setColor(Color.parseColor("#0099cc"));


    }

    public void backButtonPressed(View view) {
        startActivity(new Intent(this, LevelSelection7.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void resetPressed(View view) {
        finish();
        startActivity(getIntent());
    }

}
