package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.action_plan.ActionPlanRealmModel;
import com.icaboalo.yana.data.entities.realm_models.action_plan.UserRealmModel;
import com.icaboalo.yana.domain.models.action_plan.ActionPlan;
import com.icaboalo.yana.domain.models.action_plan.Contact;
import com.icaboalo.yana.domain.models.action_plan.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * @author icaboalo on 13/08/16.
 */
public class UserEntityMapper implements EntityMapper<Object, Object> {

    protected Gson gson;

    public UserEntityMapper() {
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
            UserRealmModel model = (UserRealmModel) realmModel;
            User user = new User();
            transformToDomainHelper(model, user);
            return user;
        }
        return new User();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList) {
        List<Object> objects = new ArrayList<>();
        for (Object realmObject : realmModelList){
            objects.add(transformToDomain(realmObject));
        }
        return objects;
    }

    @Override
    public Object transformToDomain(Object realmModel, Class domainClass) {
        if (realmModel != null){
            if (realmModel instanceof UserRealmModel){
                UserRealmModel userRealmModel = (UserRealmModel) realmModel;
                User user = new User();
                transformToDomainHelper(userRealmModel, user);
                return user;
            }
            return domainClass.cast(gson.fromJson(gson.toJson(realmModel), domainClass));
        }

        return new User();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> list = new ArrayList<>();
        for (Object object : realmModelList){
            list.add(transformToDomain(object, domainClass));
        }
        return list;
    }


    private void transformToDomainHelper(UserRealmModel userRealmModel, User user){
        user.setFullName(userRealmModel.getFullName());
        user.setEmail(userRealmModel.getEmail());
        user.setGender(userRealmModel.getGender());
        user.setId(userRealmModel.getId());
        user.setPhoneNumber(userRealmModel.getPhoneNumber());
        user.setLocation(userRealmModel.getLocation());
        user.setOccupation(userRealmModel.getOccupation());
        user.setBirthDate(userRealmModel.getBirthDate());

        List<ActionPlan> actionPlanList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();

        for (ActionPlanRealmModel actionPlan : userRealmModel.getActionPlanList())
            actionPlanList.add(ActionPlanEntityMapper.transformToDomainHelper(actionPlan));

        user.setActionPlanList(actionPlanList);
        user.setContactList(contactList);
    }
}
