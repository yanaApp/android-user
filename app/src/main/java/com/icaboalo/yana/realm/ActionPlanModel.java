package com.icaboalo.yana.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class ActionPlanModel extends RealmObject{
    @PrimaryKey
    private int id;
    private RealmList<DayModel> days;

    public ActionPlanModel(int id){
        this.id = id;

    }

}
