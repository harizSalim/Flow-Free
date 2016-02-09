package com.flowfree.game;

import java.io.Serializable;

/**
 * Created by Malek on 2/9/2016.
 */
public class Pack implements Serializable {

    private int id;
    private String name;
    private String description;
    private String fileName;

    public Pack() {
    }

    public Pack(String name, String description, String fileName) {
        this.name = name;
        this.description = description;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFile() {
        return this.fileName;
    }
}
