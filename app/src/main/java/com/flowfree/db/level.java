package com.flowfree.db;

/**
 * Created by Malek on 2/15/2016.
 */
public class Level {
    private int id;
    private int status;

    public Level(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public Level() {

    }


    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
