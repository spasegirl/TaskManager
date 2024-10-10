package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    List<Task> tasks = new ArrayList<>();
    private Set<String> categories = new HashSet<>();


    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> filterTasksByPriority(int priority) {
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public void sortTasksByPriority(boolean ascending){
        if (ascending) {
            tasks.sort(Comparator.comparingInt(Task::getPriority));

        } else {
            tasks.sort((task1, task2) -> Integer.compare(task2.getPriority(),task1.getPriority()));
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasksByCompletionStatus(boolean completed) {
        return tasks.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

}
