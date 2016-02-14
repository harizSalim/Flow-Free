package com.flowfree.levels;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.game.flowfree.R;


public class Level18x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level18x8);
        int couleurs[] = {Color.DKGRAY, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.GRAY, Color.BLACK, Color.CYAN};
        int corX[] = {0, 0, 0, 2, 4, 4, 0, 6, 2, 3, 5, 7, 7, 7, 5, 6, 2, 5};
        int corY[] = {0, 2, 1, 2, 0, 5, 3, 3, 5, 4, 1, 1, 2, 7, 2, 1, 4, 3};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(1, 8);
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
