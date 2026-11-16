public class FullTimeEmployee extends Employee{
    private double monthlySalary;
    private double bonus;

    public FullTimeEmployee(String fullName, int age, String position, Department department, double monthlySalary, double bonus) {
        super(fullName, age, position, department);
         if(monthlySalary<=0||bonus<0)
            throw new IllegalArgumentException("Invalid salary or bonus");
        this.monthlySalary=monthlySalary;
        this.bonus=bonus;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary+bonus;
    }
}
