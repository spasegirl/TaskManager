package org.example;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.awt.*;

public class TaskManagerController {

    private TaskManager taskManager;
    private TaskManagerView taskManagerView;

    public TaskManagerController(TaskManager taskManager, TaskManagerView taskManagerView) {
        this.taskManager = taskManager;
        this.taskManagerView = taskManagerView;

        initController();
    }

    private void initController() {
        taskManagerView.getAddButton().addActionListener(e -> showAddTaskDialog());
        taskManagerView.getRemoveButton().addActionListener(e -> removeSelectedTask());
        taskManagerView.getCompleteButton().addActionListener(e -> markTaskAsComplete());
        taskManagerView.getSortButton().addActionListener(e -> sortTasks());
        taskManagerView.getFilterButton().addActionListener(e -> filterTasks());

        taskManagerView.getShowCompletedButton().addActionListener(e -> showTasksByCompletionStatus(true));
        taskManagerView.getShowUncompletedButton().addActionListener(e -> showTasksByCompletionStatus(false));
        taskManagerView.getShowAllButton().addActionListener(e -> loadTasksToTable());

        loadTasksToTable();
    }

    private void showAddTaskDialog() {
        JDialog dialog = new JDialog(taskManagerView, "Add New Task", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Task Name:");
        JTextField nameField = new JTextField();
        JLabel dateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel priorityLabel = new JLabel("Priority:");
        JTextField priorityField = new JTextField();
        JButton addTaskButton = new JButton("Add Task");

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(dateLabel);
        dialog.add(dateField);
        dialog.add(priorityLabel);
        dialog.add(priorityField);
        dialog.add(new JLabel());
        dialog.add(addTaskButton);

        addTaskButton.addActionListener(e -> {
            String name = nameField.getText();
            LocalDate dueDate = LocalDate.parse(dateField.getText());
            int priority = Integer.parseInt(priorityField.getText());

            Task newTask = new Task(name, dueDate, priority);
            taskManager.addTask(newTask);
            taskManagerView.getTableModel().addRow(new Object[]{name, dueDate, priority, "❌"});
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private void removeSelectedTask() {
        JTable table = taskManagerView.getTaskTable();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String taskName = (String) taskManagerView.getTableModel().getValueAt(selectedRow, 0);
            taskManager.getTasks().stream()
                    .filter(task -> task.getName().equals(taskName))
                    .findFirst()
                    .ifPresent(task -> {
                        taskManager.removeTask(task);
                        taskManagerView.getTableModel().removeRow(selectedRow);
                    });
        } else {
            JOptionPane.showMessageDialog(taskManagerView, "Please select a task to remove.");
        }
    }

    private void markTaskAsComplete() {
        JTable table = taskManagerView.getTaskTable();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String taskName = (String) taskManagerView.getTableModel().getValueAt(selectedRow, 0);
            taskManager.getTasks().stream()
                    .filter(task -> task.getName().equals(taskName))
                    .findFirst()
                    .ifPresent(task -> {
                        task.setCompleted(true);
                        taskManagerView.getTableModel().setValueAt("✔", selectedRow, 3);
                    });
        } else {
            JOptionPane.showMessageDialog(taskManagerView, "Please select a task to mark as complete.");
        }
    }

    private void sortTasks() {
        String[] options = {"Ascending", "Descending"};
        int choice = JOptionPane.showOptionDialog(taskManagerView,
                "Choose sorting order:", "Sort by Priority",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        taskManager.sortTasksByPriority(choice == 0);
        loadTasksToTable();
    }

    private void filterTasks() {
        String input = JOptionPane.showInputDialog(taskManagerView, "Enter priority to filter:");
        if (input != null) {
            try {
                int priority = Integer.parseInt(input);
                List<Task> filteredTasks = taskManager.filterTasksByPriority(priority);
                updateTable(filteredTasks);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(taskManagerView, "Invalid priority input.");
            }
        }
    }
    private void showTasksByCompletionStatus(boolean completed) {
        List<Task> filteredTasks = taskManager.getTasksByCompletionStatus(completed);
        updateTable(filteredTasks);
    }

    private void loadTasksToTable() {
        taskManagerView.getTableModel().setRowCount(0);
        for (Task task : taskManager.getTasks()) {
            taskManagerView.getTableModel().addRow(new Object[]{
                    task.getName(), task.getDueDate(), task.getPriority(), task.isCompleted()});
        }
    }

    private void updateTable(List<Task> filteredTasks) {
        taskManagerView.getTableModel().setRowCount(0);
        for (Task task : filteredTasks) {
            taskManagerView.getTableModel().addRow(new Object[]{
                    task.getName(), task.getDueDate(), task.getPriority(), task.isCompleted()});
        }
    }
}
