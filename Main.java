import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ApplicationManager manager = new ApplicationManager();

    public static void main(String[] args) {
        System.out.println("===============================");
        System.out.println("  Internship & Job Application");
        System.out.println("          Tracker");
        System.out.println("===============================");

        int choice;
        do {
            printMenu();
            choice = getIntInput("Choose option: ");
            switch (choice) {
                case 1 -> addInternship();
                case 2 -> addJob();
                case 3 -> manager.viewApplications();
                case 4 -> searchApplication();
                case 5 -> updateStatus();
                case 6 -> deleteApplication();
                case 7 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 7);
    }

    private static void printMenu() {
        System.out.println("\n===============================");
        System.out.println("  Internship & Job Application");
        System.out.println("          Tracker");
        System.out.println("===============================");
        System.out.println("1. Add Internship Application");
        System.out.println("2. Add Job Application");
        System.out.println("3. View Applications");
        System.out.println("4. Search by Company");
        System.out.println("5. Update Status");
        System.out.println("6. Delete Application");
        System.out.println("7. Exit");
        System.out.println("===============================");
    }

    private static void addInternship() {
        System.out.println("--- Add Internship ---");
        String company = getInput("Company: ");
        String role = getInput("Role: ");
        int duration = getIntInput("Duration (months): ");
        double stipend = getDoubleInput("Stipend: $");

        InternshipApplication app = new InternshipApplication(
                null, company, role, Status.APPLIED, duration, stipend);
        manager.addApplication(app);
        System.out.println("Added! ID: " + app.getApplicationId());
    }

    private static void addJob() {
        System.out.println("--- Add Job ---");
        String company = getInput("Company: ");
        String role = getInput("Role: ");
        double salary = getDoubleInput("Salary: $");
        String jobType = getInput("Job Type (Full-time/Part-time/Contract): ");

        JobApplication app = new JobApplication(
                null, company, role, Status.APPLIED, salary, jobType);
        manager.addApplication(app);
        System.out.println("Added! ID: " + app.getApplicationId());
    }

    private static void searchApplication() {
        System.out.println("--- Search by Company ---");
        String company = getInput("Enter company name: ");
        manager.searchByCompany(company);
    }

    private static void updateStatus() {
        System.out.println("--- Update Status ---");
        String appId = getInput("Enter Application ID: ");
        System.out.println("Available: APPLIED, INTERVIEW, SELECTED, REJECTED");
        String input = getInput("New status: ").toUpperCase();
        try {
            Status newStatus = Status.valueOf(input);
            manager.updateStatus(appId, newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status.");
        }
    }

    private static void deleteApplication() {
        System.out.println("--- Delete Application ---");
        String appId = getInput("Enter Application ID: ");
        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            manager.deleteApplication(appId);
        } else {
            System.out.println("Cancelled.");
        }
    }

    private static String getInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Cannot be empty.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Cannot be empty.");
                continue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
