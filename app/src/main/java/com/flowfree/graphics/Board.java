/*
+ @author: Joerg Schoba, Peter Tillian, Luuk van Egeraat
* @project: Flow free implementation in course App Development - Android at Rekjavik University 2014
* @version: 1.0
*/

package com.flowfree.graphics;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;

import com.game.flowfree.R;

public class Board extends View {

    private int NUM_CELLS;
    private int cellWidth;
    private int cellHeight;
    private Rect rect = new Rect();
    private Paint paintGrid = new Paint();
    private Paint paintPath = new Paint();
    private Paint paintCircles = new Paint();
    private Path path = new Path();
    //private Flow currentFlow = null;
    private int actualLevelNumber;
    //private Challenge challenge;
    //private Puzzle puzzle;
    //private ArrayList<Flow> flows;
    private MediaPlayer mp;
    private Vibrator vb;
    private boolean vibrate;
    //private int moves;
    private int[] couleur, corX, corY;

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.paintGrid.setStyle(Paint.Style.STROKE);
        this.paintGrid.setColor(Color.WHITE);
        this.paintGrid.setAntiAlias(true);

        this.paintPath.setStrokeCap(Paint.Cap.ROUND);
        this.paintPath.setStrokeJoin(Paint.Join.ROUND);
        this.paintPath.setAntiAlias(true);

        mp = MediaPlayer.create(getContext(), R.raw.button_pressed);

        vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean sound = prefs.getBoolean("sound_onoff", true);
        if (sound) {
            mp.setVolume(1.0f, 1.0f);
        } else {
            mp.setVolume(0, 0);
        }
        vibrate = prefs.getBoolean("vibration_onoff", true);


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

    public void initializeBoard(int level, int numCells) {
        this.actualLevelNumber = level;
        this.NUM_CELLS = numCells;
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

    public void setPoints(int[] couleur, int[] corX, int[] corY) {
        this.couleur = couleur;
        this.corX = corX;
        this.corY = corY;
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

        //draw startPoints
        for (int i = 0; i < couleur.length; i++) {
            paintCircles.setColor(couleur[i]);
            canvas.drawCircle(colToX(corX[i * 2]) + cellWidth / 2, rowToY(corY[i * 2]) + cellHeight / 2, cellWidth / 4, paintCircles);
            canvas.drawCircle(colToX(corX[i * 2 + 1]) + cellWidth / 2, rowToY(corY[i * 2 + 1]) + cellHeight / 2, cellWidth / 4, paintCircles);
        }

    }

    /*public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();         // NOTE: event.getHistorical... might be needed.
        int y = (int) event.getY();
        int c = xToCol(x);
        int r = yToRow(y);

        if (c >= NUM_CELLS || r >= NUM_CELLS) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            Coordinate pressed = new Coordinate(c, r);
            Flow onPoint = isInFlowPoints(pressed);
            Flow onPath = isInFlowPaths(pressed);
            if (onPath != null) {
                onPath.getCellPath().append(new Coordinate(c, r));
                currentFlow = onPath;
                moves++;
            } else if (onPoint != null) {
                mp.start();
                if (vibrate) {
                    vb.vibrate(100);
                }
                CellPath newCellPath = new CellPath();
                newCellPath.append(new Coordinate(c, r));
                onPoint.setCellPath(newCellPath);
                currentFlow = onPoint;
                moves++;
            } else {
                currentFlow = null;
            }
            this.invalidate();

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (currentFlow != null) {
                if (null != currentFlow.getCellPath() && !currentFlow.getCellPath().isEmpty()) {
                    Coordinate movedTo = new Coordinate(c, r);
                    List<Coordinate> coordinateList = currentFlow.getCellPath().getCoordinates();
                    Coordinate last = coordinateList.get(coordinateList.size() - 1);
                    Flow cross = isInFlowPaths(movedTo);
                    Flow onPoint = isInFlowPoints(movedTo);
                    if (areNeighbours(last.getCol(), last.getRow(), c, r) &&
                            //(cross == null || cross == currentFlow) && //check if no other path is crossed
                            !(currentFlow.finished() && cross != currentFlow) && //check if not extending current path over the endpoint
                            (onPoint == null || onPoint == currentFlow)) { //check if no other point is crossed
                        currentFlow.getCellPath().append(movedTo);
                        if (cross != null && cross != currentFlow) {
                            cross.getCellPath().removeFrom(movedTo);
                        }
                        //check if all flows have been finished
                        boolean finished = true;
                        if (onPoint == currentFlow) {
                            mp.start();
                            if (vibrate) {
                                vb.vibrate(100);
                            }
                            for (Flow flow : this.flows) {
                                if (!flow.finished()) {
                                    finished = false;
                                }
                            }
                            //check if all coordinates have been covered
                            if (finished) {
                                for (int i = 0; i < NUM_CELLS; i++) {
                                    for (int j = 0; j < NUM_CELLS; j++) {
                                        if (null == isInFlowPaths(new Coordinate(i, j))) {
                                            finished = false;
                                            break;
                                        }
                                    }

                                }
                            }
                            if (finished) {
                                //int highscore = fa.getLevelHighscore(puzzle.getChallenge().getPack().getName(), puzzle.getChallenge().getName(),
                                //       puzzle.getId());
                                //if (moves < highscore || highscore <= 0) {
                                //  fa.setLevelHighscore(puzzle.getChallenge().getPack().getName(), puzzle.getChallenge().getName(),
                                //        puzzle.getId(), moves);
                            }
                            moves = 0;
                            //long id = fa.setLevelDone(puzzle.getChallenge().getPack().getName(), puzzle.getChallenge().getName(),
                            //       puzzle.getId(), true);

                            // custom dialog
                            final Dialog dialog = new Dialog(getContext());
                            //dialog.setContentView(R.layout.level_won_dialog_layout);
                            dialog.setTitle("Congrats - YOU WON ...");

                                *//*Button againButton = (Button) dialog.findViewById(R.id.level_again);
                                Button backMenuButton = (Button) dialog.findViewById(R.id.back_to_menu);
                                Button nextLevelButton = (Button) dialog.findViewById(R.id.next_level);

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
                                        mp.release();
                                        ((Activity) getContext()).finish();
                                    }
                                });
                                nextLevelButton.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        setNextPuzzle();
                                    }
                                });*//*


                            dialog.show();
                        }
                    }
                    this.invalidate();
                }
            }
        }
        //}

        return true;
    }*/

    public void setColor(int color) {
        paintPath.setColor(color);
        invalidate();
    }


    //Returns a flow if the flow has a point at the given coordinate
    //otherwise returns null
    /*public Flow isInFlowPoints(Coordinate co) {
        for (Flow flow : this.flows) {
            if (flow.getStart().equals(co) || flow.getEnd().equals(co)) {
                return flow;
            }
        }
        return null;
    }

    //Returns a flow if the cellpath of the flow contains this coordinate
    //otherwise returns null
    public Flow isInFlowPaths(Coordinate co) {
        for (Flow flow : this.flows) {
            if (null != flow.getCellPath()) {
                if (flow.getCellPath().contains(co)) {
                    return flow;
                }
            }
        }
        return null;
    }*/
}

