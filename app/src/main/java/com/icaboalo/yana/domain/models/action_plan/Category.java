package com.icaboalo.yana.domain.models.action_plan;

/**
 * @author icaboalo on 12/08/16.
 */
public class Category {

    private int id;
    private String name, color;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
