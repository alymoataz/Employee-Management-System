import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees;

    public EmployeeManager() throws Exception {
        employees = EmployeeFileHandler.loadEmployees();

        int maxID = 0;

        for(Employee e : employees) {
            if(e.getEmployeeID() > maxID)
                maxID = e.getEmployeeID();
        }

        Employee.setCount(maxID);
    }
}