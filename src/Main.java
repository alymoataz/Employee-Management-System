public class Main {
    public static void main(String[] args) throws Exception {
        EmployeeManager employeeManager = new EmployeeManager();
        DepartmentManager departmentManager = new DepartmentManager();

        LoginFrame login = new LoginFrame(
                employeeManager,
                departmentManager
        );

        login.setVisible(true);
    }
}
