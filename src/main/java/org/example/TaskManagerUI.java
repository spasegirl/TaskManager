package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TaskManagerUI extends JFrame {

    private TaskManager taskManager;
    private DefaultTableModel tableModel;

    public TaskManagerUI() {
        taskManager = new TaskManager();
        initUI();
    }

    private void initUI() {
        // main frame
        setTitle("Task Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // table to display tasks
        String[] columns = {"Task Name", "Due Date", "Priority", "Completed"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        //  panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Mark as Complete");
        JButton sortButton = new JButton("Sort by Priority");
        JButton filterButton = new JButton("Filter by Priority");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);

        // table and buttons added to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // button action listeners
        addButton.addActionListener(e -> showAddTaskDialog());
        removeButton.addActionListener(e -> removeSelectedTask(table));
        completeButton.addActionListener(e -> markTaskAsComplete(table));
        sortButton.addActionListener(e -> sortTasks());
        filterButton.addActionListener(e -> filterTasks());


        // loading existing tasks and update the UI
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

    private void sortTasks() {
        // Prompt user for sorting order
        String[] options = {"Ascending", "Descending"};
        int choice = JOptionPane.showOptionDialog(this,
                "Choose sorting order:", "Sort by Priority",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        taskManager.sortTasksByPriority(choice == 0);  // true for ascending, false for descending
        updateTable();
    }

    private void filterTasks() {
        String input = JOptionPane.showInputDialog(this, "Enter priority to filter:");
        if (input != null) {
            try {
                int priority = Integer.parseInt(input);
                List<Task> filteredTasks = taskManager.filterTasksByPriority(priority);
                updateTable(filteredTasks);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid priority input.");
            }
        }
    }
    private void updateTable() {
        tableModel.setRowCount(0);  // Clear existing rows
        loadTasksToTable();  // Reload tasks after sorting
    }

    private void updateTable(List<Task> filteredTasks) {
        tableModel.setRowCount(0);  // Clear existing rows
        for (Task task : filteredTasks) {
            tableModel.addRow(new Object[]{task.getName(), task.getDueDate(), task.getPriority(), task.isCompleted()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagerUI taskManagerUI = new TaskManagerUI();
            taskManagerUI.setVisible(true);
        });
    }
}
