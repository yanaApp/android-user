package com.icaboalo.yana.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaboalo on 20/06/16.
 */
public class CategoryApiModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
