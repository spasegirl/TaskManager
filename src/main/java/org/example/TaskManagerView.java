package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaskManagerView extends JFrame {

    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JButton addButton, removeButton, completeButton, sortButton, filterButton;
    private JButton showCompletedButton, showUncompletedButton,showAllButton;

    public TaskManagerView() {
        initUI();
    }

    public void initUI() {
        setTitle("Task Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Task Name", "Due Date", "Priority", "Completed"};
        tableModel = new DefaultTableModel(columns, 0);
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");
        completeButton = new JButton("Mark as Complete");
        sortButton = new JButton("Sort by Priority");
        filterButton = new JButton("Filter by Priority");

        // ** New Buttons for filtering tasks by completion status **
        showCompletedButton = new JButton("Show Completed");
        showUncompletedButton = new JButton("Show Uncompleted");
        showAllButton = new JButton("Show All");


        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(showCompletedButton);   // New button
        buttonPanel.add(showUncompletedButton); // New button
        buttonPanel.add(showAllButton);


        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getCompleteButton() {
        return completeButton;
    }

    public JButton getSortButton() {
        return sortButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTaskTable() {
        return taskTable;
    }

    public JButton getShowCompletedButton() {
        return showCompletedButton;
    }

    public JButton getShowUncompletedButton() {
        return showUncompletedButton;
    }

    public JButton getShowAllButton() {
        return showAllButton;
    }
}
