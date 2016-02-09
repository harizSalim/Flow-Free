package com.flowfree.home;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.flowfree.db.FlowAdapter;
import com.flowfree.game.Challenge;
import com.flowfree.game.Flow;
import com.flowfree.game.Puzzle;

import java.util.ArrayList;

/**
 * Created by Malek on 2/8/2016.
 */
public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private FlowAdapter fa;
    private Paint blackPaint = new Paint();
    private Paint redPaint = new Paint();
    private Paint paintGrid = new Paint();
    private Paint paintPath = new Paint();
    private Paint paintCircles = new Paint();
    private Path path = new Path();
    private Flow currentFlow = null;
    private int actualLevelNumber;
    private Challenge challenge;
    private Puzzle puzzle;
    private ArrayList<Flow> flows;
    private boolean[][] cellChecked;

    public PixelGridView(Context context) {
        this(context, null);
        this.paintGrid.setStyle(Paint.Style.STROKE);
        this.paintGrid.setColor(Color.GRAY);

        this.paintPath.setStyle(Paint.Style.STROKE);
        this.paintPath.setColor(Color.GREEN);
        //this.paintPath.setStrokeWidth(cellWidth/3); //activated in onMeasure
        this.paintPath.setStrokeCap(Paint.Cap.ROUND);
        this.paintPath.setStrokeJoin(Paint.Join.ROUND);
        this.paintPath.setAntiAlias(true);
        fa = new FlowAdapter(getContext());
      //  this.initializeBoard();
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        redPaint.setColor(Color.red(1));
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }
    private int xToCol(int x) {
        return (x - getPaddingLeft()) / this.cellWidth;
    }

    private int yToRow(int y) {
        return (y - getPaddingTop()) / this.cellHeight;
    }

    private int colToX(int col) {
        return col * this.cellWidth + getPaddingLeft();
    }

    private int rowToY(int row) {
        return row * this.cellHeight + getPaddingTop();
    }
    public void initializeLevel(String Level, Canvas c){


    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
       /* canvas.drawCircle((6), (2) ,30, blackPaint);
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellChecked[i][j]) {
                    //canvas.drawCircle(i * cellWidth, j * cellHeight, 10, blackPaint);
                    canvas.drawCircle((i + 1) * cellWidth, (j + 1) * cellHeight+50,30,
                            blackPaint);
                }
            }
        }
*/
        //draw startPoints
        for (Flow f : flows) {
            paintCircles.setColor(f.getColor());
            canvas.drawCircle(colToX(f.getStart().getCol()) + cellWidth / 2, rowToY(f.getStart().getRow()) + cellHeight / 2, cellWidth / 4, paintCircles);
            canvas.drawCircle(colToX(f.getEnd().getCol()) + cellWidth / 2, rowToY(f.getEnd().getRow()) + cellHeight / 2, cellWidth / 4, paintCircles);
        }
        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
        }

        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
        }
    }
    public void initializeBoard(Challenge challenge, int actualLevelNumber) {
        this.challenge = challenge;
        this.actualLevelNumber = actualLevelNumber;

        this.puzzle = challenge.getPuzzles().get(this.actualLevelNumber - 1);
        this.flows = this.puzzle.getFlows();
        this.numRows = this.puzzle.getSize();

       // moves = 0;

       // this.actualizeTitle();
        //this.actualizeHighscore();
        this.invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);

            cellChecked[column][row] = !cellChecked[column][row];
            invalidate();
        }

        return true;
    }
}