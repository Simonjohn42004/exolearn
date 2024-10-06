package com.example.exolearnapp.learningmodel.model;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
    private String moduleId;
    private String moduleTitle;
    private List<Lesson> lessons;
    private List<QuizQuestion> quizQuestions;
    private boolean quizCompleted;

    // Default constructor (required for Firebase)
    public Module() {}

    public Module(String moduleId, String moduleTitle, List<Lesson> lessons, List<QuizQuestion> quizQuestions, boolean quizCompleted) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.lessons = lessons;
        this.quizQuestions = quizQuestions;
        this.quizCompleted = quizCompleted;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public boolean isQuizCompleted() {
        return quizCompleted;
    }

    public void setQuizCompleted(boolean quizCompleted) {
        this.quizCompleted = quizCompleted;
    }
}
