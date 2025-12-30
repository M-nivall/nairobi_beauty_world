package com.example.Varsani.Clients.Models;

public class ExamQuizModel {
    private Integer quizId;   // Use Integer here
    private String question;
    private String correct_answer;
    private String multiple_1;
    private String multiple_2;

    public ExamQuizModel(Integer quizId, String question, String correct_answer, String multiple_1, String multiple_2) {
        this.quizId = quizId;
        this.question = question;
        this.multiple_1 = multiple_1;
        this.multiple_2 = multiple_2;
        this.correct_answer = correct_answer;
    }

    public Integer getQuizId() {  // Return an Integer here
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
    public String getCorrect_answer() {
        return correct_answer;
    }
}
