package com.icaboalo.yana.domain.models.action_plan;

/**
 * @author icaboalo on 12/08/16.
 */
public class Activity {

    private int id;
    private String title, description;
    private int answer;
    private Category category;

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

    public Category getCategory() {
        return category;
    }
}
