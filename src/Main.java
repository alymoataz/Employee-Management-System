import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            EmployeeManager employeeManager = new EmployeeManager();
            DepartmentManager departmentManager = new DepartmentManager();

            LoginFrame login = new LoginFrame(employeeManager, departmentManager);
            login.setVisible(true);
        }
        catch(Exception ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Could not load data files: " + ex.getMessage()
                            + "\nThe file may be corrupted.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );

            System.exit(1);
        }
    }
}