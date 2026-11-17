import java.io.Serializable;

public class Department implements Serializable {

    private int departmentID;
    private String departmentName;

    public Department(int departmentID, String departmentName) {

        if(departmentID <= 0)
            throw new IllegalArgumentException("Invalid department ID");

        if(departmentName == null || departmentName.isBlank())
            throw new IllegalArgumentException("Invalid department name");

        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}