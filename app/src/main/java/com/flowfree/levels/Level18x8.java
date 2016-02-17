package com.flowfree.levels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.flowfree.graphics.Board;
import com.flowfree.home.LevelSelection8;
import com.game.flowfree.R;


public class Level18x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level18x8);

        int lightGreen = getApplicationContext().getResources().getColor(R.color.lightGreen);
        int darkRed = getApplicationContext().getResources().getColor(R.color.darkRed);
        int green = getApplicationContext().getResources().getColor(R.color.green);
        int blue = getApplicationContext().getResources().getColor(R.color.blue);
        int lightBlue = getApplicationContext().getResources().getColor(R.color.lightBlue);
        int yellow = getApplicationContext().getResources().getColor(R.color.yellow);
        int orange = getApplicationContext().getResources().getColor(R.color.orange);
        int gray = getApplicationContext().getResources().getColor(R.color.gray);

        int couleurs[] = {darkRed, green, Color.RED, yellow, orange, blue, lightBlue, gray, lightGreen};
        int corX[] = {0, 0, 0, 2, 4, 4, 0, 6, 2, 3, 5, 7, 7, 7, 5, 6, 2, 5};
        int corY[] = {0, 2, 1, 2, 0, 5, 3, 3, 5, 4, 1, 1, 2, 7, 2, 1, 4, 3};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(1, 8);
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

    public void setMovesTextView(String text) {
        ((TextView) findViewById(R.id.nbmove)).setText(text);
    }

}
