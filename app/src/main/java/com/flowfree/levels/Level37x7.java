package com.flowfree.levels;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.game.flowfree.R;


public class Level37x7 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level37x7);
        int couleurs[] = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.GRAY};
        int corX[] = {0, 3, 1, 4, 3, 6, 2, 4, 1, 4, 1, 5};
        int corY[] = {5, 4, 3, 4, 5, 6, 2, 2, 5, 5, 2, 4};

        this.board = (Board) findViewById(R.id.board);
        board.setPoints(couleurs, corX, corY);
        board.initializeBoard(1, 7);
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
