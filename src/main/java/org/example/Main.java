package org.example;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager taskManager = new TaskManager();
            TaskManagerView taskManagerView = new TaskManagerView();
            new TaskManagerController(taskManager, taskManagerView);
            taskManagerView.setVisible(true);
        });
    }
}
