package com.icaboalo.yana.presentation.screens.view_model;

/**
 * @author icaboalo on 10/08/16.
 */
public class CategoryViewModel {

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

    public boolean isEmpty() {
        return (this.name == null || this.name.isEmpty()) && (this.color == null || this.color.isEmpty());
    }
}
