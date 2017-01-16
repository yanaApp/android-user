package com.icaboalo.yana.presentation.view_model;

/**
 * @author icaboalo on 10/08/16.
 */
public class ActivityViewModel {

    private int id;
    private String title, description;
    private int answer;
    private CategoryViewModel category;

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

    public CategoryViewModel getCategory() {
        return category;
    }

    public boolean isEmpty() {
        return (this.title == null || this.title.isEmpty()) && (this.description == null || this.description.isEmpty())
                && category.isEmpty();

    }
}
