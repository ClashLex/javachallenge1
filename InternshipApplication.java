public class InternshipApplication extends Application {
    private int duration;
    private double stipend;

    public InternshipApplication(String applicationId, String company, String role,
                                  Status status, int duration, double stipend) {
        super(applicationId, company, role, status);
        this.duration = duration;
        this.stipend = stipend;
    }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getStipend() { return stipend; }
    public void setStipend(double stipend) { this.stipend = stipend; }

    public void displayDetails() {
        System.out.println("Type       : Internship");
        System.out.println("ID         : " + getApplicationId());
        System.out.println("Company    : " + getCompany());
        System.out.println("Role       : " + getRole());
        System.out.println("Status     : " + getStatus());
        System.out.println("Duration   : " + duration + " months");
        System.out.println("Stipend    : $" + stipend);
    }
}
