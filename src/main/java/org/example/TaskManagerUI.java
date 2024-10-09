package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TaskManagerUI extends JFrame {

    private TaskManager taskManager;
    private DefaultTableModel tableModel;

    public TaskManagerUI() {
        taskManager = new TaskManager();
        initUI();
    }

    private void initUI() {
        // Create main frame
        setTitle("Task Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table to display tasks
        String[] columns = {"Task Name", "Due Date", "Priority", "Completed"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Mark as Complete");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(completeButton);

        // Add table and buttons to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button action listeners
        addButton.addActionListener(e -> showAddTaskDialog());
        removeButton.addActionListener(e -> removeSelectedTask(table));
        completeButton.addActionListener(e -> markTaskAsComplete(table));

        // Load existing tasks and update the UI
        loadTasksToTable();
    }

    private void showAddTaskDialog() {
        // Create a new dialog for adding tasks
        JDialog dialog = new JDialog(this, "Add New Task", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));

        // Task input fields
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
        dialog.add(new JLabel()); // Empty cell
        dialog.add(addTaskButton);

        // Add task button listener
        addTaskButton.addActionListener(e -> {
            String name = nameField.getText();
            LocalDate dueDate = LocalDate.parse(dateField.getText());
            int priority = Integer.parseInt(priorityField.getText());

            Task newTask = new Task(name, dueDate, priority);
            taskManager.addTask(newTask); // Add task to TaskManager
            tableModel.addRow(new Object[]{name, dueDate, priority, false});

            dialog.dispose(); // Close the dialog after adding the task
        });

        dialog.setVisible(true);
    }

    private void removeSelectedTask(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String taskName = (String) tableModel.getValueAt(selectedRow, 0);
            Task taskToRemove = taskManager.getTasks().stream()
                    .filter(task -> task.getName().equals(taskName))
                    .findFirst()
                    .orElse(null);
            if (taskToRemove != null) {
                taskManager.removeTask(taskToRemove);
                tableModel.removeRow(selectedRow); // Remove row from UI table
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }

    private void markTaskAsComplete(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String taskName = (String) tableModel.getValueAt(selectedRow, 0);
            taskManager.getTasks().stream()
                    .filter(task -> task.getName().equals(taskName))
                    .findFirst()
                    .ifPresent(task -> {
                        task.setCompleted(true); // Mark task as complete
                        tableModel.setValueAt(true, selectedRow, 3); // Update "Completed" column
                    });
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as complete.");
        }
    }

    private void loadTasksToTable() {
        for (Task task : taskManager.getTasks()) {
            tableModel.addRow(new Object[]{
                    task.getName(), task.getDueDate(), task.getPriority(), task.isCompleted()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagerUI taskManagerUI = new TaskManagerUI();
            taskManagerUI.setVisible(true);
        });
    }
}
