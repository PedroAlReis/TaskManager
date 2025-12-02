package com.pedro.taskmanager;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements Serializable {

    private String title;
    private boolean completed;
    private LocalDateTime createdAt;

    public Task(String title) {
        this.title = title;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return (completed ? "✔ " : "• ") + title;
    }
}
