package com.flowfree.levels;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flowfree.graphics.Board;
import com.game.flowfree.R;


public class Level28x8 extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level28x8);
        int couleurs[] = {Color.DKGRAY, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.GRAY};
        int corX[] = {2, 2, 6, 6, 1, 3, 5, 5, 3, 4, 2, 5, 4, 6};
        int corY[] = {2, 4, 1, 3, 6, 4, 0, 3, 6, 1, 6, 5, 0, 0};

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
