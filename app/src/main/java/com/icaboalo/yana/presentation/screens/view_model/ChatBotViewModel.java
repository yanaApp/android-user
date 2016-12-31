package com.icaboalo.yana.presentation.screens.view_model;

import java.util.ArrayList;

/**
 * Created by icaboalo on 12/12/16.
 */

public class ChatBotViewModel {

    public static final int KEYBOARD = 0, OPTIONS = 1;
    public static final int BOT = 0, USER = 1;

    private int type, answerPositionForSubQuestions, sender;
    private String question, answer;
    private ArrayList<ChatBotAnswerViewModel> answers;
    private ArrayList<ChatBotViewModel> subQuestions;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerPositionForSubQuestions() {
        return answerPositionForSubQuestions;
    }

    public void setAnswerPositionForSubQuestions(int answerPositionForSubQuestions) {
        this.answerPositionForSubQuestions = answerPositionForSubQuestions;
    }

    public ArrayList<ChatBotAnswerViewModel> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<ChatBotAnswerViewModel> answers) {
        this.answers = answers;
    }

    public ArrayList<ChatBotViewModel> getSubQuestions() {
        return subQuestions;
    }

    public void setSubQuestions(ArrayList<ChatBotViewModel> subQuestions) {
        this.subQuestions = subQuestions;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
