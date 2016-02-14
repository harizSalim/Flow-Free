package com.flowfree.levels;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.game.flowfree.R;


public class Level38x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level38x8);
        int couleurs[] = {Color.DKGRAY, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.GRAY, Color.BLACK};
        int corX[] = {4, 5, 0, 3, 2, 4, 3, 5, 2, 3, 1, 2, 0, 4, 4, 3};
        int corY[] = {5, 2, 3, 0, 5, 4, 5, 1, 1, 3, 1, 6, 4, 1, 3, 1};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(3, 8);
        board.setColor(Color.parseColor("#0099cc"));

    }

    public void backButtonPressed(View view) {
        finish();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void resetPressed(View view) {
        finish();
        startActivity(getIntent());
    }

}
