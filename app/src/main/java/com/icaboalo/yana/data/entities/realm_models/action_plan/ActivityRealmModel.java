package com.icaboalo.yana.data.entities.realm_models.action_plan;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 12/08/16.
 */
public class ActivityRealmModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String title, description;
    private int answer;
    private CategoryRealmModel category;

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

    public CategoryRealmModel getCategory() {
        return category;
    }
}
