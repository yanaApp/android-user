package com.icaboalo.yana.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by saul on 27/05/16.
 */
public class DayModel extends RealmObject{

    public DayModel() {
    }

    @PrimaryKey
    private int nId;
    private String nDate;
    private RealmList<ActivityModel> nActivities;

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getnDate() {
        return nDate;
    }

    public void setnDate(String nDate) {
        this.nDate = nDate;
    }

    public RealmList<ActivityModel> getnActivities() {
        return nActivities;
    }

    public void setnActivities(RealmList<ActivityModel> nActivities) {
        this.nActivities = nActivities;
    }

    public DayModel(int nId, String nDate){
        this.nId = nId;
        this.nDate = nDate;
    }
}
