package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.action_plan.DayRealmModel;
import com.icaboalo.yana.domain.models.action_plan.Activity;
import com.icaboalo.yana.domain.models.action_plan.Day;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author icaboalo on 13/08/16.
 */
public class DayEntityMapper implements EntityMapper<Object, Object> {

    protected Gson gson;

    public DayEntityMapper() {
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
            DayRealmModel dayRealmModel = (DayRealmModel) realmModel;
            return transformToDomainHelper(dayRealmModel);
        }
        return new Day();
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
        return null;
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        return null;
    }

    public static Day transformToDomainHelper(DayRealmModel dayRealmModel){
        Day day = new Day();
        day.setId(dayRealmModel.getId());
        day.setDayNumber(dayRealmModel.getDayNumber());
        day.setDate(dayRealmModel.getDate());

        List<Activity> activityList = new ArrayList<>();

        day.setActivityList(activityList);
        return day;
    }
}
