package com.icaboalo.yana.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class DayModel extends RealmObject{
    @PrimaryKey
    private int nId;
    private String nDate;

    public RealmList<ActivityModel> getnActivities() {
        return nActivities;
    }

    public void setnActivities(RealmList<ActivityModel> nActivities) {
        this.nActivities = nActivities;
    }

    private RealmList<ActivityModel> nActivities;

    public DayModel(int nId, String nDate){
        this.nId = nId;
        this.nDate = nDate;
    }
}
