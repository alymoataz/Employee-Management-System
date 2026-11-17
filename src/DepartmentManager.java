import java.util.ArrayList;

public class DepartmentManager {

    private ArrayList<Department> departments;

    public DepartmentManager() {
        departments = new ArrayList<>();

        departments.add(new Department(1, "IT"));
        departments.add(new Department(2, "Finance"));
        departments.add(new Department(3, "Marketing"));
        departments.add(new Department(4, "Human Resources"));
        departments.add(new Department(5, "Sales"));
    }

    public Department searchByID(int id) {
        for(Department d : departments) {
            if(d.getDepartmentID() == id)
                return d;
        }

        return null;
    }

    public Department searchByName(String name) {
        for(Department d : departments) {
            if(d.getDepartmentName().equalsIgnoreCase(name))
                return d;
        }

        return null;
    }

    public ArrayList<Department> getAllDepartments() {
        return new ArrayList<Department>(departments);
    }
}