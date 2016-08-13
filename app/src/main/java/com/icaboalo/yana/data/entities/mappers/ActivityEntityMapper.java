package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.action_plan.ActivityRealmModel;
import com.icaboalo.yana.domain.models.action_plan.Activity;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author icaboalo on 13/08/16.
 */
public class ActivityEntityMapper implements EntityMapper<Object, Object> {

    protected Gson gson;

    public ActivityEntityMapper() {
        gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class)
                        && f.getDeclaredClass().equals(RealmModel.class)
                        && f.getDeclaringClass().equals(RealmList.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
    }


    @Override
    public Object transformToRealm(Object item, Class dataClass) {
        if (item != null)
            return dataClass.cast(gson.fromJson(gson.toJson(item), dataClass));
        return null;
    }

    @Override
    public List<Object> transformAllToRealm(List<Object> list, Class dataClass) {
        List<Object> objects = new ArrayList<>();
        Object o;
        for (Object object : list){
            o = transformToRealm(object, dataClass);
            if (o != null)
                objects.add(o);
        }
        return objects;
    }

    @Override
    public Object transformToDomain(Object realmModel) {
        if (realmModel != null){
            ActivityRealmModel activityRealmModel = (ActivityRealmModel) realmModel;
            return transformToDomainHelper(activityRealmModel);
        }
        return new Activity();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList) {
        List<Object> list = new ArrayList<>();
        for (Object object : realmModelList){
            list.add(transformToDomain(object));
        }
        return list;
    }

    @Override
    public Object transformToDomain(Object realmModel, Class domainClass) {
        if (realmModel != null){
            if (realmModel instanceof ActivityRealmModel){
                ActivityRealmModel activityRealmModel = (ActivityRealmModel) realmModel;
                return transformToDomain(activityRealmModel);
            }
            return domainClass.cast(gson.fromJson(gson.toJson(realmModel), domainClass));
        }
        return new Activity();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> list = new ArrayList<>();
        for (Object object : realmModelList){
            list.add(transformToDomain(object, domainClass));
        }
        return list;
    }

    //TODO add category
    public static Activity transformToDomainHelper(ActivityRealmModel activityRealmModel){
        Activity activity = new Activity();
        activity.setId(activityRealmModel.getId());
        activity.setAnswer(activityRealmModel.getAnswer());
        activity.setTitle(activityRealmModel.getTitle());
        activity.setDescription(activityRealmModel.getDescription());


        return activity;
    }
}
