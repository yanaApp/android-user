package com.icaboalo.yana.data.entities.realm_models;

import com.google.gson.annotations.SerializedName;
import com.icaboalo.yana.presentation.view_model.ChatBotViewModel;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by icaboalo on 25/01/17.
 */

public class ChatBotRealmModel {
    public static final int KEYBOARD = 0, OPTIONS = 1, TEXT = 2, HOUR = 3, WEEK_DAYS = 4;
    public static final int CATEGORY_PROFILE = 0, CATEGORY_SCHEDULE = 1;

    private int category;

    @SerializedName("question_type")
    private int questionType;
    private String question, answer;

    @SerializedName("save_field_name")
    private String saveFieldName;

    @SerializedName("support_question")
    private String supportQuestion;

    @SerializedName("answer_for_sub_questions")
    private String answerForSubQuestions;
    private ArrayList<String> answers;

    @SerializedName("sub_questions")
    private ArrayList<ChatBotViewModel> subQuestions;

    @SerializedName("needs_answer")
    private boolean needsAnswer;

    @SerializedName("needs_save")
    private boolean needsSave;

    public int getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isNeedsAnswer() {
        return needsAnswer;
    }

    public int getCategory() {
        return category;
    }

    public String getSaveFieldName() {
        return saveFieldName;
    }

    public boolean isNeedsSave() {
        return needsSave;
    }

    public String getAnswerForSubQuestions() {
        return answerForSubQuestions;
    }

    public String getSupportQuestion() {
        return supportQuestion;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public ArrayList<ChatBotViewModel> getSubQuestions() {
        return subQuestions;
    }

}
