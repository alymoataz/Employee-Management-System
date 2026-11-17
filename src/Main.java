public class Main {
    public static void main(String[] args) throws Exception {
        EmployeeManager employeeManager = new EmployeeManager();

        LoginFrame login = new LoginFrame(employeeManager);

        login.setVisible(true);

    }
}
