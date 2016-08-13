package com.icaboalo.yana.data.entities.realm_models.action_plan;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author icaboalo on 12/08/16.
 */
public class CategoryRealmModel extends RealmObject{

    @PrimaryKey
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
}
