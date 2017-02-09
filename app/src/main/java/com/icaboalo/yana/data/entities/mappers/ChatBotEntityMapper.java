package com.icaboalo.yana.data.entities.mappers;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icaboalo.yana.data.entities.realm_models.ChatbotMessageRealmModel;
import com.icaboalo.yana.domain.models.ChatbotMessage;

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
            if (realmModel instanceof ChatbotMessageRealmModel){
                ChatbotMessageRealmModel userRealmModel = (ChatbotMessageRealmModel) realmModel;
                return transformToDomainHelper(userRealmModel);
            }
            return domainClass.cast(gson.fromJson(gson.toJson(realmModel), domainClass));
        }

        return new ChatbotMessage();
    }

    @Override
    public List<Object> transformAllToDomain(List<Object> realmModelList, Class domainClass) {
        List<Object> list = new ArrayList<>();
        for (Object object : realmModelList){
            list.add(transformToDomain(object, domainClass));
        }
        return list;
    }

    public static ChatbotMessage transformToDomainHelper(ChatbotMessageRealmModel chatbotMessageRealmModel) {
        ChatbotMessage chatbotMessage = new ChatbotMessage();

        chatbotMessage.setId(chatbotMessageRealmModel.getId());
        chatbotMessage.setAnswer(chatbotMessageRealmModel.getAnswer());
        chatbotMessage.setSupportQuestion(chatbotMessageRealmModel.getSupportQuestion());
        chatbotMessage.setSubQuestion(chatbotMessageRealmModel.getSubQuestion());
        chatbotMessage.setSaveFieldName(chatbotMessageRealmModel.getSaveFieldName());
        chatbotMessage.setQuestionType(chatbotMessageRealmModel.getQuestionType());
        chatbotMessage.setNeedsSave(chatbotMessageRealmModel.isNeedsSave());
        chatbotMessage.setNeedsAnswer(chatbotMessageRealmModel.isNeedsAnswer());
        chatbotMessage.setCategory(chatbotMessageRealmModel.getCategory());
        chatbotMessage.setAnswers(chatbotMessageRealmModel.getAnswers());
        chatbotMessage.setNextQuestion(chatbotMessageRealmModel.getNextQuestion());
        chatbotMessage.setParentQuestion(chatbotMessageRealmModel.getParentQuestion());
        chatbotMessage.setAnswerForSubQuestions(chatbotMessageRealmModel.getAnswerForSubQuestions());

        return chatbotMessage;
    }
}
