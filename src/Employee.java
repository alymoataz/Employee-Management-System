import java.io.Serializable;

public abstract class Employee implements Serializable {
    private int employeeID;
    private String fullName;
    private int age;
    private String position;
    private static int count=0;
    private Department department;// wait for department class
    public boolean isValidString(String s){
        if(s==null||s.isBlank())
            return false;
        return true;
    }
    public abstract double calculateSalary();
    public Employee(String fullName, int age, String position, Department department) {
        if(!isValidString(fullName)||!isValidString(position))
            throw new IllegalArgumentException("String not valid");
        this.fullName=fullName;
        if(age<20)
            throw new IllegalArgumentException("Minimum age for employee is 20");
        this.age=age;
        this.position = position;
        if(department==null)
            throw new IllegalArgumentException("Department cant be null");
        this.department = department;
        count++;
        this.employeeID = count;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getPosition() {
        return position;
    }

    public static int getCount() {
        return count;
    }

    public Department getDepartment() {
        if(department==null)
            throw new IllegalArgumentException("department cant be null");
        return department;
    }


    public void setAge(int age) {
        if(age < 20)
            throw new IllegalArgumentException("Minimum age is 20");

        this.age = age;
    }

    public void setFullName(String fullName) {
        if(!isValidString(fullName))
            throw new IllegalArgumentException("Invalid name");

        this.fullName = fullName;
    }

    public void setPosition(String position) {
        if(!isValidString(position))
            throw new IllegalArgumentException("Invalid position");

        this.position = position;
    }



    public void setDepartment(Department department) {
        this.department = department;
    }
}
