
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class AddEmployeeFrame extends JFrame {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;
    private DashboardFrame dashboard;

    private JTextField nameField;
    private JTextField ageField;
    private JTextField positionField;
    private JTextField salaryField;
    private JTextField bonusField;

    private JComboBox<String> departmentBox;
    private JComboBox<String> employeeTypeBox;

    private JLabel salaryLabel;
    private JLabel bonusLabel;

    private JButton addButton;
    private JButton backButton;

    public AddEmployeeFrame(EmployeeManager employeeManager,
                            DepartmentManager departmentManager,
                            DashboardFrame dashboard) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        this.dashboard = dashboard;

        setTitle("Employee Management System - Add Employee");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new BorderLayout(15, 20));

        getContentPane().setBackground(new Color(240, 244, 249));

        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 244, 249));
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(25, 20, 10, 20)
        );

        JLabel title = new JLabel(
                "Add New Employee",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(31, 58, 95));

        titlePanel.add(title, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 12, 15));

        formPanel.setBackground(new Color(240, 244, 249));
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 35, 30, 35)
        );

        // Text fields
        nameField = new JTextField();
        ageField = new JTextField();
        positionField = new JTextField();
        salaryField = new JTextField();
        bonusField = new JTextField();

        // Department combo box
        departmentBox = new JComboBox<>();

        for (Department d : departmentManager.getAllDepartments()) {
            departmentBox.addItem(d.getDepartmentName());
        }

        // Employee type combo box
        employeeTypeBox = new JComboBox<>(
                new String[]{"Full-Time", "Part-Time"}
        );

        // Salary labels
        salaryLabel = new JLabel("Basic Salary:");
        bonusLabel = new JLabel("Bonus:");

        // Buttons
        addButton = new JButton("Add Employee");
        backButton = new JButton("Back");

        addButton.setBackground(new Color(43, 91, 145));
        addButton.setForeground(Color.WHITE);

        backButton.setBackground(new Color(220, 226, 234));
        backButton.setForeground(new Color(40, 50, 65));

        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        addButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        // Add components to form
        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentBox);

        formPanel.add(new JLabel("Employee Type:"));
        formPanel.add(employeeTypeBox);

        formPanel.add(salaryLabel);
        formPanel.add(salaryField);

        formPanel.add(bonusLabel);
        formPanel.add(bonusField);

        formPanel.add(addButton);
        formPanel.add(backButton);

        // Add panels to frame
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Action listeners
        employeeTypeBox.addActionListener(new TypeHandler());
        addButton.addActionListener(new AddHandler());
        backButton.addActionListener(new BackHandler());
    }

    // Update salary labels
    private void updateSalaryFields() {

        String type = (String) employeeTypeBox.getSelectedItem();

        if (type.equals("Full-Time")) {

            salaryLabel.setText("Basic Salary:");
            bonusLabel.setText("Bonus:");

        } else {

            salaryLabel.setText("Hourly Rate:");
            bonusLabel.setText("Working Hours:");
        }
    }

    private class TypeHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateSalaryFields();
        }
    }

    private class AddHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                String name = nameField.getText().trim();
                String position = positionField.getText().trim();

                if (name.isEmpty() || position.isEmpty()
                        || ageField.getText().trim().isEmpty()
                        || salaryField.getText().trim().isEmpty()
                        || bonusField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            AddEmployeeFrame.this,
                            "Please complete all required fields."
                    );

                    return;
                }

                int age = Integer.parseInt(ageField.getText().trim());

                if (age < 20) {

                    JOptionPane.showMessageDialog(
                            AddEmployeeFrame.this,
                            "Employee age must be at least 20."
                    );

                    return;
                }

                String departmentName =
                        (String) departmentBox.getSelectedItem();

                Department department =
                        departmentManager.searchByName(departmentName);

                String type =
                        (String) employeeTypeBox.getSelectedItem();

                Employee employee;

                if (type.equals("Full-Time")) {

                    double salary = Double.parseDouble(
                            salaryField.getText().trim()
                    );

                    double bonus = Double.parseDouble(
                            bonusField.getText().trim()
                    );

                    employee = new FullTimeEmployee(
                            name, age, position, department, salary, bonus
                    );

                } else {

                    double hourlyRate = Double.parseDouble(
                            salaryField.getText().trim()
                    );

                    int hours = Integer.parseInt(
                            bonusField.getText().trim()
                    );

                    employee = new PartTimeEmployee(
                            name, age, position, department,
                            hourlyRate, hours
                    );
                }

                if (employeeManager.addEmployee(employee)) {

                    JOptionPane.showMessageDialog(
                            AddEmployeeFrame.this,
                            "Employee added successfully!\nEmployee ID: "
                                    + employee.getEmployeeID()
                    );

                    clearFields();
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        AddEmployeeFrame.this,
                        "Please enter valid numeric values.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        AddEmployeeFrame.this,
                        ex.getMessage(),
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                        AddEmployeeFrame.this,
                        "Error saving employee: " + ex.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class BackHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            dashboard.setVisible(true);
            dispose();
        }
    }

    private void clearFields() {

        nameField.setText("");
        ageField.setText("");
        positionField.setText("");

        salaryField.setText("");
        bonusField.setText("");

        employeeTypeBox.setSelectedIndex(0);
    }
}
