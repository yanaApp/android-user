package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * @author icaboalo on 08/08/16.
 */
public class EntityDataMapper implements EntityMapper<Object, Object> {

    private Gson mGson;

    public EntityDataMapper() {
        mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
    }

    @Override
    public Object transformToRealm(Object item, Class dataClass) {
        return mGson.fromJson(mGson.toJson(item), dataClass);
    }

    @Override
    public List<Object> transformAllToRealm(List<Object> list, Class dataClass) {
        List<Object> objects = new ArrayList<>();
        Object object;
        for (int i = 0; i < list.size(); i++) {
            object = transformToRealm(list.get(i), dataClass);
            if (object != null)
                objects.add(object);
        }
        return objects;
    }

    @Override
    public Object transformToDomain(Object realmModel) {
        return null;
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList) {
        return null;
    }

    @Override
    public Object transformToDomain(Object realmModel, Class domainClass) {
        if (realmModel != null)
            return mGson.fromJson(mGson.toJson(realmModel), domainClass);
        return null;
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> domainObjects = new ArrayList<>();
        for (int i = 0; i < realmModelList.size(); i++) {
            domainObjects.add(transformToDomain(realmModelList.get(i), domainClass));
        }
        return domainObjects;
    }
}
