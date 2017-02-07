package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.ChatBotRealmModel;
import com.icaboalo.yana.domain.models.ChatBot;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by icaboalo on 25/01/17.
 */

public class ChatBotEntityMapper extends EntityDataMapper {

    protected Gson gson;

    public ChatBotEntityMapper() {
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
    public Object transformToDomain(Object realmModel, Class domainClass) {
        if (realmModel != null){
            if (realmModel instanceof ChatBotRealmModel){
                ChatBotRealmModel userRealmModel = (ChatBotRealmModel) realmModel;
                return transformToDomainHelper(userRealmModel);
            }
            return domainClass.cast(gson.fromJson(gson.toJson(realmModel), domainClass));
        }

        return new ChatBot();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> list = new ArrayList<>();
        for (Object object : realmModelList){
            list.add(transformToDomain(object, domainClass));
        }
        return list;
    }

    public static ChatBot transformToDomainHelper(ChatBotRealmModel chatBotRealmModel) {
        ChatBot chatBot = new ChatBot();

        chatBot.setAnswer(chatBotRealmModel.getAnswer());
        chatBot.setSupportQuestion(chatBotRealmModel.getSupportQuestion());
        chatBot.setSubQuestions(chatBotRealmModel.getSubQuestions());
        chatBot.setSaveFieldName(chatBotRealmModel.getSaveFieldName());
        chatBot.setQuestionType(chatBotRealmModel.getQuestionType());
        chatBot.setNeedsSave(chatBotRealmModel.isNeedsSave());
        chatBot.setNeedsAnswer(chatBotRealmModel.isNeedsAnswer());
        chatBot.setCategory(chatBotRealmModel.getCategory());
        chatBot.setAnswers(chatBotRealmModel.getAnswers());

        return chatBot;
    }
}
