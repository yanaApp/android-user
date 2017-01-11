package com.icaboalo.yana.presentation.screens.view_model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotViewModel {

    public static final int KEYBOARD = 0, OPTIONS = 1, TEXT = 2;
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

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public void setNeedsSave(boolean needsSave) {
        this.needsSave = needsSave;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerForSubQuestions() {
        return answerForSubQuestions;
    }

    public String getSupportQuestion() {
        return supportQuestion;
    }

    public void setSupportQuestion(String supportQuestion) {
        this.supportQuestion = supportQuestion;
    }

    public void setAnswerForSubQuestions(String answerForSubQuestions) {
        this.answerForSubQuestions = answerForSubQuestions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<ChatBotViewModel> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(ArrayList<ChatBotViewModel> subQuestions) {
        this.subQuestions = subQuestions;
    }

    public void setNeedsAnswer(boolean needsAnswer) {
        this.needsAnswer = needsAnswer;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setSaveFieldName(String saveFieldName) {
        this.saveFieldName = saveFieldName;
    }
}
