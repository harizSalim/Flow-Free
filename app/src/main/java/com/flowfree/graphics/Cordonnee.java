/*
+ @author: Joerg Schoba, Peter Tillian, Luuk van Egeraat
* @project: Flow free implementation in course App Development - Android at Rekjavik University 2014
* @version: 1.0
*/

package com.flowfree.graphics;

import java.io.Serializable;

public class Cordonnee implements Serializable {

    private int m_col;
    private int m_row;

    public Cordonnee(int col, int row) {
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
        if (!(other instanceof Cordonnee)) {
            return false;
        }
        Cordonnee otherCo = (Cordonnee) other;
        return otherCo.getCol() == this.getCol() && otherCo.getRow() == this.getRow();
    }
}
