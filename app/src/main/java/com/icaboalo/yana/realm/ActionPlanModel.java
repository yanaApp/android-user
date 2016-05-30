package com.icaboalo.yana.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class ActionPlanModel extends RealmObject{

    public ActionPlanModel() {
    }

    public ActionPlanModel(int nId){
        this.nId = nId;

    }

    @PrimaryKey
    private int nId;
    private RealmList<DayModel> nDays;

    public int getnId() {
        return nId;
    }

    public RealmList<DayModel> getnDays() {
        return nDays;
    }

}
