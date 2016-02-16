package com.flowfree.levels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.flowfree.home.LevelSelection8;
import com.game.flowfree.R;


public class Level28x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int lightGreen = getApplicationContext().getResources().getColor(R.color.lightGreen);
        int green = getApplicationContext().getResources().getColor(R.color.green);
        int blue = getApplicationContext().getResources().getColor(R.color.blue);
        int lightBlue = getApplicationContext().getResources().getColor(R.color.lightBlue);
        int yellow = getApplicationContext().getResources().getColor(R.color.yellow);
        int orange = getApplicationContext().getResources().getColor(R.color.orange);

        setContentView(R.layout.activity_level28x8);
        int couleurs[] = {lightGreen, green, Color.RED, yellow, orange , blue, lightBlue};
        int corX[] = {2, 2, 6, 6, 1, 3, 5, 5, 3, 4, 2, 5, 4, 6};
        int corY[] = {2, 4, 1, 3, 6, 4, 0, 3, 6, 1, 6, 5, 0, 0};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(2, 8);
        board.setColor(Color.parseColor("#0099cc"));

    }

    public void backButtonPressed(View view) {
        startActivity(new Intent(this, LevelSelection8.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void resetPressed(View view) {
        finish();
        startActivity(getIntent());
    }

}
