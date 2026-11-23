
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private EmployeeManager employeeManager;
    private DepartmentManager departmentManager;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton exitButton;

    public LoginFrame(EmployeeManager employeeManager,
                      DepartmentManager departmentManager) {

        this.employeeManager = employeeManager;
        this.departmentManager = departmentManager;

        setTitle("Employee Management System - Login");
        setSize(480, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 25));
        mainPanel.setBackground(new Color(240, 244, 249));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        );

        // Title
        JLabel title = new JLabel(
                "Employee Management System",
                JLabel.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(31, 58, 95));

        mainPanel.add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 15, 20));
        formPanel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        usernameField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190, 200, 215)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                )
        );

        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190, 200, 215)),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)
                )
        );

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");

        loginButton.setBackground(new Color(43, 91, 145));
        loginButton.setForeground(Color.WHITE);

        exitButton.setBackground(new Color(220, 226, 234));
        exitButton.setForeground(new Color(40, 50, 65));

        loginButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listeners
        loginButton.addActionListener(new LoginHandler());
        exitButton.addActionListener(new ExitHandler());
    }

    private class LoginHandler implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if(employeeManager.login(username, password)) {

                JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Login successful!"
                );

                DashboardFrame dashboard = new DashboardFrame(
                        employeeManager,
                        departmentManager
                );

                dashboard.setVisible(true);

                dispose();
            }
            else {

                JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Incorrect username or password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private class ExitHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
