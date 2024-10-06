package com.example.exolearnapp.learningmodel.model;

import java.io.Serializable;
import java.util.List;

public class QuizQuestion implements Serializable {
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    // Default constructor (required for Firebase)
    public QuizQuestion() {}

    public QuizQuestion(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
