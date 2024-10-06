package com.example.exolearnapp.learningmodel.model;

import java.io.Serializable;

public class UserProgress implements Serializable {
    private String userId;
    private int currentModule;
    private int currentLesson;
    private boolean moduleUnlocked;

    // Default constructor (required for Firebase)
    public UserProgress() {}

    public UserProgress(String userId, int currentModule, int currentLesson, boolean moduleUnlocked) {
        this.userId = userId;
        this.currentModule = currentModule;
        this.currentLesson = currentLesson;
        this.moduleUnlocked = moduleUnlocked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCurrentModule() {
        return currentModule;
    }

    public void setCurrentModule(int currentModule) {
        this.currentModule = currentModule;
    }

    public int getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(int currentLesson) {
        this.currentLesson = currentLesson;
    }

    public boolean isModuleUnlocked() {
        return moduleUnlocked;
    }

    public void setModuleUnlocked(boolean moduleUnlocked) {
        this.moduleUnlocked = moduleUnlocked;
    }
}
