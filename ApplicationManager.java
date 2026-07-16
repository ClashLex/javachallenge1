import java.util.ArrayList;
import java.io.*;

public class ApplicationManager {
    private ArrayList<Application> applications;
    private int nextId;
    private static final String DATA_FILE = "data.csv";

    public ApplicationManager() {
        applications = new ArrayList<>();
        nextId = 1;
        loadFromFile();
    }

    // Adds an application and assigns a unique ID
    public void addApplication(Application app) {
        app.setApplicationId("APP" + (nextId++));
        applications.add(app);
        saveToFile();
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
                saveToFile();
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
                saveToFile();
                return true;
            }
        }
        System.out.println("Application ID not found.");
        return false;
    }

    // ─── FILE PERSISTENCE ───────────────────────────────────────

    // Saves all applications to data.csv
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            pw.println("TYPE,ID,COMPANY,ROLE,STATUS,EXTRA1,EXTRA2");
            for (Application app : applications) {
                if (app instanceof InternshipApplication) {
                    InternshipApplication ia = (InternshipApplication) app;
                    pw.printf("INTERNSHIP,%s,%s,%s,%s,%d,%.2f%n",
                        escape(ia.getApplicationId()), escape(ia.getCompany()),
                        escape(ia.getRole()), ia.getStatus(),
                        ia.getDuration(), ia.getStipend());
                } else {
                    JobApplication ja = (JobApplication) app;
                    pw.printf("JOB,%s,%s,%s,%s,%.2f,%s%n",
                        escape(ja.getApplicationId()), escape(ja.getCompany()),
                        escape(ja.getRole()), ja.getStatus(),
                        ja.getSalary(), escape(ja.getJobType()));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not save data - " + e.getMessage());
        }
    }

    // Loads applications from data.csv on startup
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = splitCsv(line);
                if (parts.length < 7) continue;

                String type   = parts[0];
                String id     = parts[1];
                String company = parts[2];
                String role   = parts[3];
                Status status = Status.valueOf(parts[4]);

                Application app;
                if (type.equals("INTERNSHIP")) {
                    int duration = Integer.parseInt(parts[5]);
                    double stipend = Double.parseDouble(parts[6]);
                    app = new InternshipApplication(id, company, role, status, duration, stipend);
                } else {
                    double salary = Double.parseDouble(parts[5]);
                    String jobType = parts[6];
                    app = new JobApplication(id, company, role, status, salary, jobType);
                }
                app.setApplicationId(id);
                applications.add(app);

                // Keep nextId ahead of existing IDs
                int idNum = Integer.parseInt(id.replace("APP", ""));
                if (idNum >= nextId) {
                    nextId = idNum + 1;
                }
            }
            System.out.println("Loaded " + applications.size() + " application(s) from " + DATA_FILE);
        } catch (Exception e) {
            System.out.println("Warning: Could not load data - " + e.getMessage());
        }
    }

    // Escapes commas in values by wrapping in quotes
    private String escape(String value) {
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // Splits a CSV line respecting quoted fields
    private String[] splitCsv(String line) {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }
}
