package org.example;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate dueDate;
    private int priority;
    private boolean isCompleted;

    public Task(String name, LocalDate dueDate, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return name + " - " + dueDate + " - " + (isCompleted ? "Completed" : "Pending");
    }
}
