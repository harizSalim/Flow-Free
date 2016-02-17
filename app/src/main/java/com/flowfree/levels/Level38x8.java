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


public class Level38x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level38x8);

        int lightGreen = getApplicationContext().getResources().getColor(R.color.lightGreen);
        int darkRed = getApplicationContext().getResources().getColor(R.color.darkRed);
        int green = getApplicationContext().getResources().getColor(R.color.green);
        int blue = getApplicationContext().getResources().getColor(R.color.blue);
        int lightBlue = getApplicationContext().getResources().getColor(R.color.lightBlue);
        int yellow = getApplicationContext().getResources().getColor(R.color.yellow);
        int orange = getApplicationContext().getResources().getColor(R.color.orange);

        int couleurs[] = {lightGreen, green, Color.RED, yellow, darkRed, blue, lightBlue, orange};
        int corX[] = {4, 5, 0, 3, 2, 4, 3, 5, 2, 3, 1, 2, 0, 4, 4, 3};
        int corY[] = {5, 2, 3, 0, 5, 4, 5, 1, 1, 3, 1, 6, 4, 1, 3, 1};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(3, 8);
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
