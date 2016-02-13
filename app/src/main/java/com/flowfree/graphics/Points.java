package com.flowfree.graphics;


import java.io.Serializable;

public class Points implements Serializable {
    private Cordonnee X;
    private Cordonnee Y;
    private int couleur;
    private CellPath cellPath;

    Points(Cordonnee X, Cordonnee Y, int couleur) {
        this.setX(X);
        this.setY(Y);
        this.setCouleur(couleur);
    }

    public Cordonnee getX() {
        return X;
    }

    public void setX(Cordonnee x) {
        X = x;
    }

    public Cordonnee getY() {
        return Y;
    }

    public void setY(Cordonnee y) {
        Y = y;
    }

    public int getCouleur() {
        return couleur;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public CellPath getCellPath() {
        return cellPath;
    }

    public void setCellPath(CellPath cellPath) {
        this.cellPath = cellPath;
    }

    public boolean finished() {
        if (null != this.cellPath) {
            return this.cellPath.contains(this.X) && this.cellPath.contains(this.Y);
        }
        return false;
    }
}
