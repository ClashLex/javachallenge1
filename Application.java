public abstract class Application {
    private String applicationId;
    private String company;
    private String role;
    private Status status;

    public Application(String applicationId, String company, String role, Status status) {
        this.applicationId = applicationId;
        this.company = company;
        this.role = role;
        this.status = status;
    }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public abstract void displayDetails();
}
