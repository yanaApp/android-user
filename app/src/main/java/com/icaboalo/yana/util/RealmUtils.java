package com.icaboalo.yana.util;

import android.support.annotation.Nullable;
import android.util.Log;

import com.icaboalo.yana.realm.ActionPlanModel;
import com.icaboalo.yana.realm.ActivityModel;
import com.icaboalo.yana.realm.ContactModel;
import com.icaboalo.yana.realm.DayModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by icaboalo on 13/06/16.
 */
public class RealmUtils {

    public static int getCompletedActivitiesFromRealm(@Nullable DayModel day){
        Realm realm = Realm.getDefaultInstance();
        if (day == null){
            return realm.where(ActivityModel.class).greaterThan("answer", 0).findAll().size();
        } else {
            return realm.where(ActivityModel.class).greaterThan("answer", 0).equalTo("day.id", day.getId()).findAll().size();
        }
    }

    public static int getIncompleteActivitiesFromRealm(@Nullable DayModel day){
        Realm realm = Realm.getDefaultInstance();
        if (day == null){
            return realm.where(ActivityModel.class).equalTo("answer", 0).findAll().size();
        } else {
            return realm.where(ActivityModel.class).equalTo("answer", 0).equalTo("day.id", day.getId()).findAll().size();
        }
    }

    public static int getEmotionAverageFromRealm(@Nullable DayModel day){
        Realm realm = Realm.getDefaultInstance();
        if (day == null){
            double count = realm.where(ActivityModel.class).average("answer");
            return (int) Math.round(count);
        } else {
            double count = realm.where(ActivityModel.class).equalTo("day.id", day.getId()).average("answer");
            return (int) Math.round(count);
        }

    }

    public static ArrayList<DayModel> getDaysFromRealm(@Nullable ActionPlanModel actionPlan){
        Realm realm = Realm.getDefaultInstance();
        if (actionPlan == null){
            RealmResults<DayModel> results = realm.where(DayModel.class).findAll();
            Log.d("REALM_RESULTS", results.toString());
            ArrayList<DayModel> dayList = new ArrayList<>();
            for (DayModel day: results){
                dayList.add(day);
            }
            return dayList;
        } else {
            RealmResults<DayModel> results = realm.where(DayModel.class).equalTo("actionPlan.id", actionPlan.getId()).findAll();
            Log.d("REALM_RESULTS", results.toString());
            ArrayList<DayModel> dayList = new ArrayList<>();
            for (DayModel day: results){
                dayList.add(day);
            }
            return dayList;
        }
    }

    public static ArrayList<ActionPlanModel> getActionPlansFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ActionPlanModel> results = realm.where(ActionPlanModel.class).findAll();
        Log.d("REALM_RESULTS", results.toString());
        ArrayList<ActionPlanModel> actionPlanList = new ArrayList<>();
        for (ActionPlanModel actionPlan: results){
            actionPlanList.add(actionPlan);
        }
        return actionPlanList;
    }

    public static ArrayList<ContactModel> getContactsFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ContactModel> query = realm.where(ContactModel.class);
        RealmResults<ContactModel> results =  query.findAll();

        Log.d("REALM_RESULTS", results.toString());

        ArrayList<ContactModel> contactList = new ArrayList<>();
        for (ContactModel contact: results){
            contactList.add(contact);
        }

        return contactList;
    }

    public static void removeContactFromRealm(int contactId){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ContactModel contact = realm.where(ContactModel.class).equalTo("id", contactId).findFirst();
        contact.deleteFromRealm();
        realm.commitTransaction();
    }

}
