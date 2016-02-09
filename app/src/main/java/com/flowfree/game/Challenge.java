package com.flowfree.game;

/**
 * Created by Malek on 2/9/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Challenge implements Serializable {

    private int id;
    private String name;
    private Pack pack;
    private ArrayList<Puzzle> puzzles;

    public Challenge() {
    }

    public Challenge(int id, String name, Pack pack) {
        this.id = id;
        this.name = name;
        this.pack = pack;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(ArrayList<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public void addPuzzle(Puzzle puzzle) {
        if (this.puzzles == null) {
            this.puzzles = new ArrayList<Puzzle>();
        }
        this.puzzles.add(puzzle);
    }
}
