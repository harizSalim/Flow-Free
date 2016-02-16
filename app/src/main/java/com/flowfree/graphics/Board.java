package com.flowfree.graphics;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.flowfree.db.Level;
import com.flowfree.db.LevelsDataBase;
import com.flowfree.home.LevelChoice;
import com.flowfree.levels.Level18x8;
import com.flowfree.levels.Level27x7;
import com.flowfree.levels.Level28x8;
import com.flowfree.levels.Level37x7;
import com.flowfree.levels.Level38x8;
import com.game.flowfree.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

public class Board extends View {

    public String currentLevel;
    private int NUM_CELLS;
    private int cellWidth;
    private int cellHeight;
    private Rect rect = new Rect();
    private Paint paintGrid = new Paint();
    private Paint paintPath = new Paint();
    private Paint paintCircles = new Paint();
    private Path path = new Path();
    private Points currentPoint = null;
    private int actualLevelNumber;
    private ArrayList<Points> points;
    private int[] couleur, corX, corY;

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.paintGrid.setStyle(Paint.Style.STROKE);
        this.paintGrid.setColor(Color.WHITE);
        this.paintGrid.setAntiAlias(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
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

    public void setPoints(int[] couleur, int[] corX, int[] corY) {
        this.couleur = couleur;
        this.corX = corX;
        this.corY = corY;
    }

    public void initializeBoard(int level, int numCells) {
        this.actualLevelNumber = level;
        this.NUM_CELLS = numCells;
        this.points = new ArrayList<Points>();
        for (int i = 0; i < couleur.length; i++) {
            Points point;
            point = new Points(new Cordonnee(corX[i * 2], corY[i * 2]), new Cordonnee(corX[i * 2 + 1], corY[i * 2 + 1]), couleur[i]);
            points.add(point);
        }
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);

        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom());

        this.paintPath.setStrokeWidth(cellWidth / 4);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        int sw = Math.max(1, (int) paintGrid.getStrokeWidth());
        cellWidth = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;

        this.paintPath.setStrokeWidth(cellWidth / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //draw the grid
        for (int r = 0; r < NUM_CELLS; ++r) {
            for (int c = 0; c < NUM_CELLS; ++c) {
                int x = colToX(c);
                int y = rowToY(r);
                rect.set(x, y, x + cellWidth, y + cellHeight);
                canvas.drawRect(rect, paintGrid);
            }
        }
        for (int i = 0; i < couleur.length; i++) {
            paintCircles.setColor(couleur[i]);
            canvas.drawCircle(colToX(corX[i * 2]) + cellWidth / 2, rowToY(corY[i * 2]) + cellHeight / 2, cellWidth / 4, paintCircles);
            canvas.drawCircle(colToX(corX[i * 2 + 1]) + cellWidth / 2, rowToY(corY[i * 2 + 1]) + cellHeight / 2, cellWidth / 4, paintCircles);
        }
        //draw all cellpaths
        for (Points point : this.points) {
            path.reset();
            paintPath.setColor(point.getCouleur());
            paintPath.setDither(true);

            paintPath.setStyle(Paint.Style.STROKE);
            paintPath.setStrokeJoin(Paint.Join.ROUND);
            paintPath.setStrokeCap(Paint.Cap.ROUND);
            paintPath.setStrokeWidth(10);
            if (null != point.getCellPath() && point.getCellPath().size() > 0) {
                List<Cordonnee> colist = point.getCellPath().getCordonnees();
                Cordonnee co = colist.get(0);
                path.moveTo(colToX(co.getCol()) + cellWidth / 2, rowToY(co.getRow()) + cellHeight / 2);
                for (int j = 1; j < colist.size(); j++) {
                    co = colist.get(j);
                    path.lineTo(colToX(co.getCol()) + cellWidth / 2, rowToY(co.getRow()) + cellHeight / 2);
                }
            }
            System.out.println(path);
            canvas.drawPath(path, paintPath);
        }

    }

    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        int c = xToCol(x);
        int r = yToRow(y);

        if (c >= NUM_CELLS || r >= NUM_CELLS) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Cordonnee touche = new Cordonnee(c, r);
            Points onPoint = isInFlowPoints(touche);
            Points onPath = isInFlowPaths(touche);

            if (onPath != null) {
                onPath.getCellPath().append(new Cordonnee(c, r));
                currentPoint = onPath;
            } else if (onPoint != null) {
                CellPath newCellPath = new CellPath();
                newCellPath.append(new Cordonnee(c, r));
                onPoint.setCellPath(newCellPath);
                currentPoint = onPoint;
            } else {
                currentPoint = null;
            }
            this.invalidate();

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (currentPoint != null) {
                if (null != currentPoint.getCellPath() && !currentPoint.getCellPath().isEmpty()) {
                    Cordonnee movedTo = new Cordonnee(c, r);
                    List<Cordonnee> coordinateList = currentPoint.getCellPath().getCordonnees();
                    Cordonnee last = coordinateList.get(coordinateList.size() - 1);
                    Points cross = isInFlowPaths(movedTo);
                    Points onPoint = isInFlowPoints(movedTo);
                    if (areNeighbours(last.getCol(), last.getRow(), c, r) &&
                            //(cross == null || cross == currentFlow) && //check if no other path is crossed
                            !(currentPoint.finished() && cross != currentPoint) && //check if not extending current path over the endpoint
                            (onPoint == null || onPoint == currentPoint)) { //check if no other point is crossed
                        currentPoint.getCellPath().append(movedTo);
                        if (cross != null && cross != currentPoint) {
                            cross.getCellPath().removeFrom(movedTo);
                        }
                        //check if all flows have been finished
                        boolean finished = true;
                        if (onPoint == currentPoint) {

                            for (Points point : this.points) {
                                if (!point.finished()) {
                                    finished = false;
                                }
                            }
                            //check if all coordinates have been covered
                            if (finished) {
                                for (int i = 0; i < NUM_CELLS; i++) {
                                    for (int j = 0; j < NUM_CELLS; j++) {
                                        if (null == isInFlowPaths(new Cordonnee(i, j))) {
                                            finished = false;
                                            break;
                                        }
                                    }
                                    this.activateNextLevels();
                                }
                                final Dialog dialog = new Dialog(getContext());
                                dialog.setContentView(R.layout.activity_level_won);
                                dialog.setTitle("Congratulations!!");

                                Button againButton = (Button) dialog.findViewById(R.id.level_again);
                                Button backMenuButton = (Button) dialog.findViewById(R.id.back_to_menu);
                                Button nextLevelButton = (Button) dialog.findViewById(R.id.next_level);
                                if (NUM_CELLS == 8 && actualLevelNumber == 3)
                                    nextLevelButton.setEnabled(false);
                                else {
                                    nextLevelButton.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            dialog.dismiss();
                                            setNextLevel();
                                        }
                                    });
                                }

                                againButton.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        reset();
                                        dialog.dismiss();
                                    }
                                });
                                backMenuButton.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        goToHome();
                                    }
                                });

                            dialog.show();
                            }
                        }
                    }
                    this.invalidate();
                }
            }
        }

        return true;
    }

    public void setColor(int color) {
        paintPath.setColor(color);
        invalidate();
    }

    public Points isInFlowPoints(Cordonnee co) {
        for (Points point : this.points) {
            if (point.getX().equals(co) || point.getY().equals(co)) {
                return point;
            }
        }
        return null;
    }

    public Points isInFlowPaths(Cordonnee co) {
        for (Points point : this.points) {
            if (null != point.getCellPath()) {
                if (point.getCellPath().contains(co)) {
                    return point;
                }
            }
        }
        return null;
    }

    private boolean areNeighbours(int c1, int r1, int c2, int r2) {
        return Math.abs(c1 - c2) + Math.abs(r1 - r2) == 1;
    }

    private void reset() {
        for (Points point : points) {
            if (point.getCellPath() != null) {
                point.getCellPath().reset();
            }
        }
        this.invalidate();
    }

    private void setNextLevel() {

        if (NUM_CELLS == 7) {
            if (actualLevelNumber == 1) {
                Intent intent = new Intent(getContext(), Level27x7.class);
                super.getContext().startActivity(intent);

            }
            if (actualLevelNumber == 2) {
                Intent intent = new Intent(getContext(), Level37x7.class);
                super.getContext().startActivity(intent);
            }
            if (actualLevelNumber == 3) {
                Intent intent = new Intent(getContext(), Level18x8.class);
                super.getContext().startActivity(intent);
            }
        }
        if (NUM_CELLS == 8) {
            if (actualLevelNumber == 1) {
                Intent intent = new Intent(getContext(), Level28x8.class);
                super.getContext().startActivity(intent);
            }
            if (actualLevelNumber == 2) {
                Intent intent = new Intent(getContext(), Level38x8.class);
                super.getContext().startActivity(intent);
            }
        }
        this.invalidate();
    }
private void activateNextLevels(){
    LevelsDataBase levelsDb = new LevelsDataBase(getContext());
    levelsDb.open();
    Level next = new Level();
    if (NUM_CELLS == 7) {
        if (actualLevelNumber == 1) {
            next.setStatus(1);
            next.setId(27);
            levelsDb.updateLevel(27, next);

        }
        if (actualLevelNumber == 2) {
            next.setStatus(1);
            next.setId(37);
            levelsDb.updateLevel(37, next);
        }
    }
    if (NUM_CELLS == 8) {
        if (actualLevelNumber == 1) {
            next.setStatus(1);
            next.setId(28);
            levelsDb.updateLevel(28, next);
        }
        if (actualLevelNumber == 2) {
            next.setStatus(1);
            next.setId(38);
            levelsDb.updateLevel(38, next);
        }
    }
    levelsDb.close();
}
    private  void goToHome(){
        Intent intent = new Intent(getContext(), LevelChoice.class);
        super.getContext().startActivity(intent);
    }
}

