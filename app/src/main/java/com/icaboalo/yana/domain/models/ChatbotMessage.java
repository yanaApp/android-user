package com.icaboalo.yana.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;

/**
 * Created by icaboalo on 25/01/17.
 */

public class ChatbotMessage {

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

    public void setId(int id) {
        this.id = id;
    }

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

    public String getAnswerForSubQuestions() {
        return answerForSubQuestions;
    }

    public String getSupportQuestion() {
        return supportQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public void setNeedsAnswer(boolean needsAnswer) {
        this.needsAnswer = needsAnswer;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setSaveFieldName(String saveFieldName) {
        this.saveFieldName = saveFieldName;
    }

    public int getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(int subQuestion) {
        this.subQuestion = subQuestion;
    }

    public int getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(int nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public int getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(int parentQuestion) {
        this.parentQuestion = parentQuestion;
    }
}
