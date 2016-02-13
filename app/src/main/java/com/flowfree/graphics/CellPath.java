package com.flowfree.graphics;

import java.util.ArrayList;
import java.util.List;

public class CellPath {

    private ArrayList<Cordonnee> path = new ArrayList<Cordonnee>();

    public void append(Cordonnee co) {
        int idx = path.indexOf(co);
        if (idx >= 0) {
            for (int i = path.size() - 1; i > idx; i--) {
                path.remove(i);
            }
        } else {
            path.add(co);
        }
    }

    public void removeFrom(Cordonnee co) {
        int idx = path.indexOf(co);
        if (idx >= 0) {
            for (int i = path.size() - 1; i >= idx; i--) {
                path.remove(i);
            }
        }
    }

    public boolean contains(Cordonnee co) {
        return this.path.indexOf(co) >= 0;
    }

    public List<Cordonnee> getCordonnees() {
        return path;
    }

    public void reset() {
        if (path != null) {
            path.clear();
        }
    }

    public int size() {
        return path.size();
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }
}

