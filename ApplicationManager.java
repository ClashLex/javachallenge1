import java.util.ArrayList;

public class ApplicationManager {
    private ArrayList<Application> applications;
    private int nextId;

    public ApplicationManager() {
        applications = new ArrayList<>();
        nextId = 1;
    }

    // Adds an application and assigns a unique ID
    public void addApplication(Application app) {
        app.setApplicationId("APP" + (nextId++));
        applications.add(app);
    }

    // Displays all applications in a table format
    public void viewApplications() {
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        System.out.println("-----------------------------------------------");
        System.out.printf("%-8s %-18s %-20s %-12s %-10s%n",
                          "ID", "Company", "Role", "Type", "Status");
        System.out.println("-----------------------------------------------");
        for (Application app : applications) {
            String type = (app instanceof InternshipApplication) ? "Internship" : "Job";
            System.out.printf("%-8s %-18s %-20s %-12s %-10s%n",
                app.getApplicationId(), app.getCompany(),
                app.getRole(), type, app.getStatus());
        }
        System.out.println("-----------------------------------------------");
    }

    // Searches applications by company name (case-insensitive)
    public void searchByCompany(String company) {
        boolean found = false;
        for (Application app : applications) {
            if (app.getCompany().toLowerCase().contains(company.toLowerCase())) {
                app.displayDetails();
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No applications found for company: " + company);
        }
    }

    // Updates the status of an application by its ID
    public boolean updateStatus(String appId, Status newStatus) {
        for (Application app : applications) {
            if (app.getApplicationId().equals(appId)) {
                app.setStatus(newStatus);
                System.out.println("Status updated to " + newStatus + ".");
                return true;
            }
        }
        System.out.println("Application ID not found.");
        return false;
    }

    // Deletes an application by its ID
    public boolean deleteApplication(String appId) {
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getApplicationId().equals(appId)) {
                applications.remove(i);
                System.out.println("Application deleted.");
                return true;
            }
        }
        System.out.println("Application ID not found.");
        return false;
    }
}
