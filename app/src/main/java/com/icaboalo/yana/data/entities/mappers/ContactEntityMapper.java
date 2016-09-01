package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.action_plan.ContactRealmModel;
import com.icaboalo.yana.domain.models.action_plan.Contact;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author icaboalo on 31/08/16.
 */
public class ContactEntityMapper implements EntityMapper<Object, Object> {

    protected Gson gson;

    public ContactEntityMapper() {
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
            ContactRealmModel contactRealmModel = (ContactRealmModel) realmModel;
            return transformToDomainHelper(contactRealmModel);
        }
        return new Contact();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList) {
        List<Object> objects = new ArrayList<>();
        for (Object object : realmModelList){
            objects.add(transformToDomain(object));
        }
        return objects;
    }

    @Override
    public Object transformToDomain(Object realmModel, Class domainClass) {
        if (realmModel != null){
            if (realmModel instanceof ContactRealmModel){
                ContactRealmModel contactRealmModel = (ContactRealmModel) realmModel;
                return transformToDomainHelper(contactRealmModel);
            }
            return gson.fromJson(gson.toJson(realmModel), domainClass);
        }
        return new Contact();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> objects = new ArrayList<>();
        for (Object object : realmModelList){
            objects.add(transformToDomain(object, domainClass));
        }
        return objects;
    }

    public Contact transformToDomainHelper(ContactRealmModel contactRealmModel){
        Contact contact = new Contact();
        contact.setId(contactRealmModel.getId());
        contact.setName(contactRealmModel.getName());
        contact.setPhoneNumber(contactRealmModel.getPhoneNumber());
        contact.setValidated(contactRealmModel.isValidated());
        return contact;
    }
}
