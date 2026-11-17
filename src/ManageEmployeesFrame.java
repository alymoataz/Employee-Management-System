
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class ManageEmployeesFrame extends JFrame {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private DashboardFrame dashboard;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> searchTypeBox;

    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;

    private JComboBox<String> departmentBox;

    private JTextField salaryField;
    private JTextField bonusField;

    private JLabel salaryLabel;
    private JLabel secondSalaryLabel;

    private JButton searchButton;
    private JButton showAllButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;

    private int selectedEmployeeID = -1;

    public ManageEmployeesFrame(EmployeeManager employeeManager,
                                DepartmentManager departmentManager,
                                DashboardFrame dashboard) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        this.dashboard = dashboard;

        setTitle("Employee Management System - Manage Employees");
        setSize(1250, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(240, 244, 249));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // Title
        JLabel title = new JLabel("Employee Records", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setForeground(new Color(31, 58, 95));

        mainPanel.add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 15));
        centerPanel.setOpaque(false);

        // Search panel
        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );
        searchPanel.setOpaque(false);

        searchTypeBox = new JComboBox<>(
                new String[]{"Employee ID", "Department"}
        );

        searchField = new JTextField(18);

        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All");

        stylePrimaryButton(searchButton);
        styleSecondaryButton(showAllButton);

        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // Employee table
        String[] columns = {
                "ID",
                "Full Name",
                "Age",
                "Position",
                "Department",
                "Employee Type",
                "Basic Salary",
                "Bonus",
                "Hourly Rate",
                "Working Hours",
                "Total Salary"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);
        employeeTable.setRowHeight(25);
        employeeTable.setFont(new Font("Arial", Font.PLAIN, 13));

        employeeTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        employeeTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Update form
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 15));
        bottomPanel.setOpaque(false);

        JLabel formTitle = new JLabel("Employee Information");
        formTitle.setFont(new Font("Arial", Font.BOLD, 17));
        formTitle.setForeground(new Color(31, 58, 95));

        bottomPanel.add(formTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 12));
        formPanel.setOpaque(false);

        nameField = new JTextField();
        ageField = new JTextField();
        positionField = new JTextField();

        departmentBox = new JComboBox<>();

        for (Department d : departmentManager.getAllDepartments()) {
            departmentBox.addItem(d.getDepartmentName());
        }

        salaryLabel = new JLabel("Basic Salary:");
        secondSalaryLabel = new JLabel("Bonus:");

        salaryField = new JTextField();
        bonusField = new JTextField();

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentBox);

        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        formPanel.add(secondSalaryLabel);
        formPanel.add(bonusField);

        bottomPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 3, 12, 0)
        );

        buttonPanel.setOpaque(false);

        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");
        backButton = new JButton("Back");

        stylePrimaryButton(updateButton);
        stylePrimaryButton(deleteButton);
        styleSecondaryButton(backButton);

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners
        employeeTable.getSelectionModel().addListSelectionListener(
                new EmployeeSelectionHandler()
        );

        searchButton.addActionListener(new SearchHandler());
        showAllButton.addActionListener(new ShowAllHandler());
        updateButton.addActionListener(new UpdateHandler());
        deleteButton.addActionListener(new DeleteHandler());
        backButton.addActionListener(new BackHandler());

        refreshTable();
    }

    // Button styling
    private void stylePrimaryButton(JButton button) {

        button.setBackground(new Color(43, 91, 145));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
    }

    private void styleSecondaryButton(JButton button) {

        button.setBackground(new Color(220, 226, 234));
        button.setForeground(new Color(40, 50, 65));
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
    }

    // Display employees in JTable
    private void refreshTable() {

        tableModel.setRowCount(0);

        ArrayList<Employee> employees =
                employeeManager.getAllEmployees();

        for (Employee e : employees) {
            addEmployeeRow(e);
        }
    }

    // Add one employee to the table
    private void addEmployeeRow(Employee e) {

        String type;
        Object basicSalary = "-";
        Object bonus = "-";
        Object hourlyRate = "-";
        Object workingHours = "-";

        if (e instanceof FullTimeEmployee) {

            FullTimeEmployee f = (FullTimeEmployee) e;

            type = "Full-Time";
            basicSalary = f.getMonthlySalary();
            bonus = f.getBonus();
        }
        else {

            PartTimeEmployee p = (PartTimeEmployee) e;

            type = "Part-Time";
            hourlyRate = p.getHourlyRate();
            workingHours = p.getHoursWorked();
        }

        Object[] row = {
                e.getEmployeeID(),
                e.getFullName(),
                e.getAge(),
                e.getPosition(),
                e.getDepartment().getDepartmentName(),
                type,
                basicSalary,
                bonus,
                hourlyRate,
                workingHours,
                e.calculateSalary()
        };

        tableModel.addRow(row);
    }

    // Load selected employee into form
    private void loadSelectedEmployee() {

        int row = employeeTable.getSelectedRow();

        if (row == -1) {
            return;
        }

        selectedEmployeeID = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        Employee e =
                employeeManager.searchEmployeeByID(selectedEmployeeID);

        if (e == null) {
            return;
        }

        nameField.setText(e.getFullName());
        ageField.setText(String.valueOf(e.getAge()));
        positionField.setText(e.getPosition());

        departmentBox.setSelectedItem(
                e.getDepartment().getDepartmentName()
        );

        if (e instanceof FullTimeEmployee) {

            FullTimeEmployee f = (FullTimeEmployee) e;

            salaryLabel.setText("Basic Salary:");
            secondSalaryLabel.setText("Bonus:");

            salaryField.setText(
                    String.valueOf(f.getMonthlySalary())
            );

            bonusField.setText(
                    String.valueOf(f.getBonus())
            );
        }
        else if (e instanceof PartTimeEmployee) {

            PartTimeEmployee p = (PartTimeEmployee) e;

            salaryLabel.setText("Hourly Rate:");
            secondSalaryLabel.setText("Working Hours:");

            salaryField.setText(
                    String.valueOf(p.getHourlyRate())
            );

            bonusField.setText(
                    String.valueOf(p.getHoursWorked())
            );
        }
    }

    private void clearForm() {

        nameField.setText("");
        ageField.setText("");
        positionField.setText("");

        salaryField.setText("");
        bonusField.setText("");

        salaryLabel.setText("Basic Salary:");
        secondSalaryLabel.setText("Bonus:");

        selectedEmployeeID = -1;
        employeeTable.clearSelection();
    }

    // Table selection
    private class EmployeeSelectionHandler
            implements javax.swing.event.ListSelectionListener {

        @Override
        public void valueChanged(
                javax.swing.event.ListSelectionEvent e) {

            if (!e.getValueIsAdjusting()) {
                loadSelectedEmployee();
            }
        }
    }

    // Search
    private class SearchHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String searchValue = searchField.getText().trim();

            if (searchValue.isEmpty()) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        "Please enter something to search."
                );

                return;
            }

            tableModel.setRowCount(0);

            if (searchTypeBox.getSelectedIndex() == 0) {

                try {

                    int id = Integer.parseInt(searchValue);

                    Employee employee =
                            employeeManager.searchEmployeeByID(id);

                    if (employee != null) {
                        addEmployeeRow(employee);
                    }
                    else {
                        showNoResults();
                    }
                }
                catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Employee ID must be a number."
                    );
                }
            }
            else {

                ArrayList<Employee> results =
                        employeeManager.searchByDepartment(searchValue);

                for (Employee emp : results) {
                    addEmployeeRow(emp);
                }

                if (results.isEmpty()) {
                    showNoResults();
                }
            }

            clearForm();
        }
    }

    private void showNoResults() {

        JOptionPane.showMessageDialog(
                ManageEmployeesFrame.this,
                "No matching employees found."
        );
    }

    // Show all
    private class ShowAllHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            searchField.setText("");
            refreshTable();
            clearForm();
        }
    }

    // Update
    private class UpdateHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (selectedEmployeeID == -1) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        "Please select an employee first."
                );

                return;
            }

            try {

                String name = nameField.getText().trim();
                String position = positionField.getText().trim();

                if (name.isEmpty() || position.isEmpty()
                        || ageField.getText().trim().isEmpty()
                        || salaryField.getText().trim().isEmpty()
                        || bonusField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Please complete all fields."
                    );

                    return;
                }

                int age = Integer.parseInt(ageField.getText().trim());

                double salary =
                        Double.parseDouble(salaryField.getText().trim());

                double secondSalary =
                        Double.parseDouble(bonusField.getText().trim());

                Department department =
                        departmentManager.searchByName(
                                (String) departmentBox.getSelectedItem()
                        );

                Employee employee =
                        employeeManager.searchEmployeeByID(selectedEmployeeID);

                if (employee instanceof PartTimeEmployee
                        && secondSalary != Math.floor(secondSalary)) {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Working hours must be a whole number."
                    );

                    return;
                }

                boolean updated = employeeManager.updateEmployee(
                        selectedEmployeeID,
                        name,
                        age,
                        position,
                        department,
                        salary,
                        secondSalary
                );

                if (updated) {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Employee updated successfully!"
                    );

                    refreshTable();
                    clearForm();
                }
                else {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Employee not found."
                    );
                }
            }
            catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        "Please enter valid numeric values."
                );
            }
            catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        ex.getMessage()
                );
            }
            catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        "Error saving employee: " + ex.getMessage()
                );
            }
        }
    }

    // Delete
    private class DeleteHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (selectedEmployeeID == -1) {

                JOptionPane.showMessageDialog(
                        ManageEmployeesFrame.this,
                        "Please select an employee first."
                );

                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    ManageEmployeesFrame.this,
                    "Are you sure you want to delete this employee?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                try {

                    boolean deleted =
                            employeeManager.deleteEmployee(selectedEmployeeID);

                    if (deleted) {

                        JOptionPane.showMessageDialog(
                                ManageEmployeesFrame.this,
                                "Employee deleted successfully!"
                        );

                        refreshTable();
                        clearForm();
                    }
                    else {

                        JOptionPane.showMessageDialog(
                                ManageEmployeesFrame.this,
                                "Employee not found."
                        );
                    }
                }
                catch (IOException ex) {

                    JOptionPane.showMessageDialog(
                            ManageEmployeesFrame.this,
                            "Error saving changes: " + ex.getMessage()
                    );
                }
            }
        }
    }

    // Back
    private class BackHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            dashboard.setVisible(true);
            dispose();
        }
    }
}
