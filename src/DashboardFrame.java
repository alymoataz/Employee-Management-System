
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;

    private JButton addButton;
    private JButton manageButton;
    private JButton exitButton;

    public DashboardFrame(EmployeeManager employeeManager,
                          DepartmentManager departmentManager) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;

        setTitle("Employee Management System - Dashboard");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 25));
        mainPanel.setBackground(new Color(240, 244, 249));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        );

        JLabel title = new JLabel(
                "Employee Management System",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(31, 58, 95));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 18));
        buttonPanel.setOpaque(false);

        addButton = new JButton("Add Employee");
        manageButton = new JButton("Manage Employees");
        exitButton = new JButton("Exit");

        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        manageButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        addButton.setBackground(new Color(43, 91, 145));
        addButton.setForeground(Color.WHITE);

        manageButton.setBackground(new Color(43, 91, 145));
        manageButton.setForeground(Color.WHITE);

        exitButton.setBackground(new Color(220, 226, 234));
        exitButton.setForeground(new Color(40, 50, 65));

        addButton.setFocusPainted(false);
        manageButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        addButton.addActionListener(new AddHandler());
        manageButton.addActionListener(new ManageHandler());
        exitButton.addActionListener(new ExitHandler());
    }

    private class AddHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AddEmployeeFrame frame = new AddEmployeeFrame(
                    employeeManager,
                    departmentManager,
                    DashboardFrame.this
            );

            frame.setVisible(true);
            setVisible(false);
        }
    }

    private class ManageHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ManageEmployeesFrame frame = new ManageEmployeesFrame(
                    employeeManager,
                    departmentManager,
                    DashboardFrame.this
            );

            frame.setVisible(true);
            setVisible(false);
        }
    }

    private class ExitHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
