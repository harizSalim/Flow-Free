package com.flowfree.game;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Malek on 2/9/2016.
 */
public class Flow implements Serializable {
    public static int[][] colors = {
            //normal
            {Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.CYAN,
                    Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.MAGENTA, Color.WHITE},
            //gray-blue
            {Color.parseColor("#2C3539"), Color.parseColor("#837E7C"), Color.parseColor("#B6B6B4"),
                    Color.parseColor("#E5E4E2"), Color.parseColor("#616D7E"), Color.parseColor("#151B54"), Color.parseColor("#000080"),
                    Color.parseColor("#342D7E"), Color.parseColor("#1569C7"), Color.parseColor("#488AC7"), Color.parseColor("#38ACEC"), Color.parseColor("#3BB9FF")},
            //green
            {Color.parseColor("#00FF00"), Color.parseColor("#667C26"), Color.parseColor("#6AFB92"), Color.parseColor("#347235"),
                    Color.parseColor("#B5EAAA"), Color.parseColor("#C3FDB8"), Color.parseColor("#4CC417"), Color.parseColor("#99C68E"),
                    Color.parseColor("#306754"), Color.parseColor("#728C00"), Color.parseColor("#6AA121"), Color.parseColor("#254117")},
            //yellow-red
            {Color.parseColor("#FFFF00"), Color.parseColor("#C85A17"), Color.parseColor("#7F5217"), Color.parseColor("#FF0000"),
                    Color.parseColor("#FFDB58"), Color.parseColor("#9F000F"), Color.parseColor("#E8ADAA"), Color.parseColor("#E77471"),
                    Color.parseColor("#C68E17"), Color.parseColor("#800517"), Color.parseColor("#AF7817"), Color.parseColor("#F88158")}

    };
    private Coordinate start;
    private Coordinate end;
    private int color;
    public Flow(Coordinate start, Coordinate end, int color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }
    public Coordinate getStart() {
        return start;
    }
    public void setStart(Coordinate start) {
        this.start = start;
    }
    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
