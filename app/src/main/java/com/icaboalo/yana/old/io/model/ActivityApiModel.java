package com.icaboalo.yana.old.io.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by icaboalo on 26/05/16.
 */
public class ActivityApiModel implements Serializable{

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("answer")
    private int answer = 0;

    @SerializedName("category")
    private CategoryApiModel category;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public CategoryApiModel getCategory() {
        return category;
    }

    public void setCategory(CategoryApiModel category) {
        this.category = category;
    }
}
