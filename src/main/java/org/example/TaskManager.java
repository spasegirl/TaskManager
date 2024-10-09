package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 2. Class: TaskManager (Using collections and methods abstraction)
public class TaskManager {
    List<Task> tasks = new ArrayList<>();
    private Set<String> categories = new HashSet<>();


    public void addTask(Task task) {
        tasks.add(task);
    }

    public void displayTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void filterTasksByPriority(int priority) {
        tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .forEach(System.out::println);
    }

    // Get the list of tasks
    public List<Task> getTasks() {
        return tasks;
    }


    // Remove a task
    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
