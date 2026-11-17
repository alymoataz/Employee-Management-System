
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

    private JComboBox<String> departmentBox;
    private JComboBox<String> employeeTypeBox;

    private JTextField salaryField;
    private JTextField bonusField;
    private JTextField hourlyRateField;
    private JTextField hoursWorkedField;

    private JPanel salaryPanel;

    private JButton addButton;
    private JButton backButton;

    public AddEmployeeFrame(EmployeeManager employeeManager,
                            DepartmentManager departmentManager,
                            DashboardFrame dashboard) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;
        this.dashboard = dashboard;

        setTitle("Employee Management System - Add Employee");
        setSize(550, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 20));
        mainPanel.setBackground(new Color(240, 244, 249));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Title
        JLabel title = new JLabel("Add New Employee", JLabel.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(31, 58, 95));

        mainPanel.add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 12, 15));
        formPanel.setOpaque(false);

        nameField = new JTextField();
        ageField = new JTextField();
        positionField = new JTextField();

        departmentBox = new JComboBox<>();

        for(Department d : departmentManager.getAllDepartments()) {
            departmentBox.addItem(d.getDepartmentName());
        }

        employeeTypeBox = new JComboBox<>(
                new String[]{"Full-Time", "Part-Time"}
        );

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

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Salary panel
        salaryPanel = new JPanel(new GridLayout(2, 2, 12, 15));
        salaryPanel.setOpaque(false);

        salaryField = new JTextField();
        bonusField = new JTextField();

        hourlyRateField = new JTextField();
        hoursWorkedField = new JTextField();

        updateSalaryFields();

        JPanel bottomPanel = new JPanel(new BorderLayout(15, 20));
        bottomPanel.setOpaque(false);

        bottomPanel.add(salaryPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

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

        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listeners
        employeeTypeBox.addActionListener(new TypeHandler());
        addButton.addActionListener(new AddHandler());
        backButton.addActionListener(new BackHandler());
    }

    private void updateSalaryFields() {

        salaryPanel.removeAll();

        String type = (String) employeeTypeBox.getSelectedItem();

        if(type.equals("Full-Time")) {

            salaryPanel.add(new JLabel("Basic Salary:"));
            salaryPanel.add(salaryField);

            salaryPanel.add(new JLabel("Bonus:"));
            salaryPanel.add(bonusField);
        }
        else {

            salaryPanel.add(new JLabel("Hourly Rate:"));
            salaryPanel.add(hourlyRateField);

            salaryPanel.add(new JLabel("Working Hours:"));
            salaryPanel.add(hoursWorkedField);
        }

        salaryPanel.revalidate();
        salaryPanel.repaint();
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

                if(name.isEmpty() || position.isEmpty()
                        || ageField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            AddEmployeeFrame.this,
                            "Please complete all required fields."
                    );

                    return;
                }

                int age = Integer.parseInt(ageField.getText().trim());

                if(age < 20) {
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

                if(type.equals("Full-Time")) {

                    double salary =
                            Double.parseDouble(salaryField.getText().trim());

                    double bonus =
                            Double.parseDouble(bonusField.getText().trim());

                    employee = new FullTimeEmployee(
                            name, age, position, department, salary, bonus
                    );
                }
                else {

                    double hourlyRate =
                            Double.parseDouble(hourlyRateField.getText().trim());

                    int hours =
                            Integer.parseInt(hoursWorkedField.getText().trim());

                    employee = new PartTimeEmployee(
                            name, age, position, department,
                            hourlyRate, hours
                    );
                }

                if(employeeManager.addEmployee(employee)) {

                    JOptionPane.showMessageDialog(
                            AddEmployeeFrame.this,
                            "Employee added successfully!\nEmployee ID: "
                                    + employee.getEmployeeID()
                    );

                    clearFields();
                }

            }
            catch(NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        AddEmployeeFrame.this,
                        "Please enter valid numeric values.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            catch(IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        AddEmployeeFrame.this,
                        ex.getMessage(),
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            catch(IOException ex) {

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

        hourlyRateField.setText("");
        hoursWorkedField.setText("");

        employeeTypeBox.setSelectedIndex(0);
    }
}

