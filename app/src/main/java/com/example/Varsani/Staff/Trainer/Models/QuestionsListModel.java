package com.example.Varsani.Staff.Trainer.Models;

public class QuestionsListModel {
    private Integer quizId;
    private String question;
    private String multiple_1;
    private String multiple_2;
    private String correctAnswer;

    public QuestionsListModel(Integer quizId, String question, String multiple_1, String multiple_2, String correctAnswer) {
        this.quizId = quizId;
        this.question = question;
        this.multiple_1 = multiple_1;
        this.multiple_2 = multiple_2;
        this.correctAnswer = correctAnswer;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public String getQuestion() {
        return question;
    }

    public String getMultiple_1() {
        return multiple_1;
    }

    public String getMultiple_2() {
        return multiple_2;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
