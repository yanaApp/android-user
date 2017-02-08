package com.icaboalo.yana.data.entities.realm_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 25/01/17.
 */

public class ChatbotMessageRealmModel extends RealmObject{

    @PrimaryKey
    private int id;
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

    @Ignore
    private ArrayList<String> answers;

    @SerializedName("sub_question")
    private int subQuestion;

    @SerializedName("next_question")
    private int nextQuestion;

    @SerializedName("parent_question")
    private int parentQuestion;

    @SerializedName("needs_answer")
    private boolean needsAnswer;

    @SerializedName("needs_save")
    private boolean needsSave;

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
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

    public int getSubQuestion() {
        return subQuestion;
    }

    public int getNextQuestion() {
        return nextQuestion;
    }

    public int getParentQuestion() {
        return parentQuestion;
    }
}
