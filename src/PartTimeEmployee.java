public class PartTimeEmployee extends Employee{
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String fullName, int age, String position, Department department, double hourlyRate, int hoursWorked) {
        super(fullName, age, position, department);
        if(hourlyRate<=0||hoursWorked<0)
            throw new IllegalArgumentException("Hourly rate or hours worked cant be negative");
        this.hoursWorked=hoursWorked;
        this.hourlyRate=hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHourlyRate(double hourlyRate) {
        if(hourlyRate<=0)
            throw new IllegalArgumentException("Hourly rate cant be negative");
        this.hourlyRate = hourlyRate;
    }

    public void setHoursWorked(int hoursWorked) {
        if(hoursWorked<0)
            throw new IllegalArgumentException("hours worked cant be negative");
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked*hourlyRate;
    }
}
