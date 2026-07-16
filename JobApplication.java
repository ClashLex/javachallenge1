public class JobApplication extends Application {
    private double salary;
    private String jobType;

    public JobApplication(String applicationId, String company, String role,
                           Status status, double salary, String jobType) {
        super(applicationId, company, role, status);
        this.salary = salary;
        this.jobType = jobType;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public void displayDetails() {
        System.out.println("Type       : Job");
        System.out.println("ID         : " + getApplicationId());
        System.out.println("Company    : " + getCompany());
        System.out.println("Role       : " + getRole());
        System.out.println("Status     : " + getStatus());
        System.out.println("Salary     : $" + salary);
        System.out.println("Job Type   : " + jobType);
    }
}
