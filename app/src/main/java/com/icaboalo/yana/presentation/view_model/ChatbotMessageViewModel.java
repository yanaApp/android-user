package com.icaboalo.yana.presentation.view_model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatbotMessageViewModel {

    public static final int KEYBOARD = 0, OPTIONS = 1, TEXT = 2, HOUR = 3, WEEK_DAYS = 4, DATE = 5, FINISH=6;
    public static final int CATEGORY_PROFILE = 0, CATEGORY_SCHEDULE = 1;

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
    private ArrayList<String> answers;

    @SerializedName("sub_question")
    private Integer subQuestion;

    @SerializedName("next_question")
    private Integer nextQuestion;

    @SerializedName("parent_question")
    private Integer parentQuestion;

    @SerializedName("needs_answer")
    private boolean needsAnswer;

    @SerializedName("needs_save")
    private boolean needsSave;

    public int getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public int getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
    }

    public String getSaveFieldName() {
        return saveFieldName;
    }

    public String getSupportQuestion() {
        return supportQuestion;
    }

    public String getAnswerForSubQuestions() {
        return answerForSubQuestions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public Integer getSubQuestion() {
        return subQuestion;
    }

    public Integer getNextQuestion() {
        return nextQuestion;
    }

    public Integer getParentQuestion() {
        return parentQuestion;
    }

    public boolean isNeedsAnswer() {
        return needsAnswer;
    }

    public boolean isNeedsSave() {
        return needsSave;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return getQuestion();
    }
}
