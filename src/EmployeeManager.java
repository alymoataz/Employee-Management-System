import java.io.IOException;
import java.util.ArrayList;

public class EmployeeManager {

    private ArrayList<Employee> employees;
    private final String username="admin";
    private final String password="admin123";

    public EmployeeManager() throws Exception {
        employees = EmployeeFileHandler.loadEmployees();

        int maxID = 0;

        for(Employee e : employees) {
            if(e.getEmployeeID() > maxID)
                maxID = e.getEmployeeID();
        }

        Employee.setCount(maxID);
    }
    public boolean login(String username,String password){
        if(this.username.equals(username) && this.password.equals(password)){
            System.out.println("Welcome login successful");
            return true;
        }
        else{
            System.out.println("Incorrect username or password");
            return false;
        }
    }
    public boolean addEmployee(Employee e) throws IOException {
        if(e==null){
            System.out.println("employee cant be null");
            return false;
        }
        employees.add(e);
        EmployeeFileHandler.saveEmployees(employees);
        return true;
    }
    public void viewemployee(){
        if(employees.isEmpty()){
            System.out.println("no employees registered");
            return;
        }
        for(Employee e : employees) {
            System.out.println("Employee ID: " + e.getEmployeeID());
            System.out.println("Name: " + e.getFullName());
            System.out.println("Age: " + e.getAge());
            System.out.println("Position: " + e.getPosition());
            System.out.println("Department: " + e.getDepartment().getDepartmentName());
            System.out.println("Salary: " + e.calculateSalary());

        }

    }

    public boolean deleteEmployee(int id) throws IOException {

        Employee found = null;

        for(Employee e : employees) {
            if(e.getEmployeeID() == id) {
                found = e;
                break; //unsafe to modify arrayList inside loop so save employee and break then mmodify after loop
            }
        }

        if(found == null) {
            System.out.println("Employee not found");
            return false;
        }
        employees.remove(found);
        EmployeeFileHandler.saveEmployees(employees);
        return true;
    }

    public Employee searchEmployeeByID(int id) {

        for(Employee e : employees) {
            if(e.getEmployeeID() == id)
                return e;
        }

        return null;
    }
    public ArrayList<Employee> searchByDepartment(String departmentName) {

        ArrayList<Employee> result = new ArrayList<>();

        for(Employee e : employees) {
            if(e.getDepartment().getDepartmentName()
                    .equalsIgnoreCase(departmentName)) {
                result.add(e);
            }
        }

        return result;
    }
    public boolean updateEmployee(int id, String name, int age,
                                  String position, Department department,
                                  double salary1, double salary2) throws IOException {

        Employee e = searchEmployeeByID(id);

        if(e == null)
            return false;

        e.setFullName(name);
        e.setAge(age);
        e.setPosition(position);
        e.setDepartment(department);

        if(e instanceof FullTimeEmployee) {
            FullTimeEmployee f = (FullTimeEmployee)e;

            f.setMonthlySalary(salary1);
            f.setBonus(salary2);
        }
        else if(e instanceof PartTimeEmployee) {
            PartTimeEmployee p = (PartTimeEmployee)e;

            p.setHourlyRate(salary1);
            p.setHoursWorked((int)salary2);
        }

        EmployeeFileHandler.saveEmployees(employees);
        return true;
    }
    public ArrayList<Employee> getAllEmployees(){
        return new ArrayList<Employee>(employees); //RETURNS COPY SO THAT NO METHOD CHANGES IN ORIGINAL
    }




}