public class PartTimeEmployee extends Employee{
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String fullName, int age, String position, Department department, double hourlyRate, int hoursWorked) {
        super(fullName, age, position, department);
        if(hourlyRate<0||hoursWorked<0)
            throw new IllegalArgumentException("Hourly rate or hours worked cant be negative");
        this.hoursWorked=hoursWorked;
        this.hourlyRate=hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked*hourlyRate;
    }
}
