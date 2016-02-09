package com.flowfree.game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Malek on 2/9/2016.
 */
public class Puzzle implements Serializable {
    public ArrayList<Flow> flows;
    private int id;
    private int size;
    private Challenge challenge;
    public Puzzle(int id, int size) {
        this.id = id;
        this.size = size;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Flow> getFlows() {
        return flows;
    }

    public void setFlows(ArrayList<Flow> flows) {
        this.flows = flows;
    }
    public void setFlows(String flowsString, int colorTheme) {
        if (this.flows == null) {
            this.flows = new ArrayList<Flow>();
        }

        int colorCounter = 0;
        String[] withBraces = flowsString.split(",");

        for (String withBrace : withBraces) {
            String startEnd = withBrace.trim().substring(1, withBrace.trim().length() - 1);
            String[] startEndSplit = startEnd.trim().split(" ");

            Coordinate start = new Coordinate(Integer.parseInt(startEndSplit[0]),
                    Integer.parseInt(startEndSplit[1]));
            Coordinate end = new Coordinate(Integer.parseInt(startEndSplit[2]),
                    Integer.parseInt(startEndSplit[3]));

            this.flows.add(new Flow(start, end, Flow.colors[colorTheme][colorCounter++]));
        }
    }
    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
