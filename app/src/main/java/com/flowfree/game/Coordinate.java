package com.flowfree.game;

import java.io.Serializable;

/**
 * Created by Malek on 2/9/2016.
 */
public class Coordinate  implements Serializable {
    private int m_col;
    private int m_row;
    public Coordinate(int col, int row) {
        m_col = col;
        m_row = row;
    }
    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinate)) {
            return false;
        }
        Coordinate otherCo = (Coordinate) other;
        return otherCo.getCol() == this.getCol() && otherCo.getRow() == this.getRow();
    }
}
