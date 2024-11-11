import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        courseRegSystem.restoreSystem();
        CLI cli = new CLI(courseRegSystem);
        cli.run();
    }
}

class CLI {
    private final CourseRegistrationSystem courseRegSystem;
    private final LoginSystem loginSystem;
    private final Scanner scanner;

    public CLI(CourseRegistrationSystem courseRegSystem) {
        this.courseRegSystem = courseRegSystem;
        this.loginSystem = new LoginSystem();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        try {
            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (loginSystem.authenticateUser(nickname, password, courseRegSystem)) {
                System.out.println("Login successful!");
                if (loginSystem.getStudent() != null) {
                    handleStudentActions(loginSystem.getStudent());
                }
            }
            else {
                System.out.println("Invalid nickname or password.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAdvisorActions(Advisor advisor) {
        while (true) {
            System.out.println("----------ACTIONS----------");
            System.out.println("1- Check students");
            System.out.println("2- Approve/Reject student registration requests");
            System.out.print("Please choose an action: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> checkStudents(advisor);
                case 2 -> handleRegistrationRequests(advisor);
                default -> System.out.println("Invalid choice.");
            }

            System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
            String continueChoice = scanner.next();
            if (continueChoice.equalsIgnoreCase("n")) {
                logout();
                break;
            }
        }
    }
    private void checkStudents(Advisor advisor){
        System.out.println("IN THE NEXT ITERATION IT WILL BE IMPLEMENTED.");
    };
    private void handleRegistrationRequests(Advisor advisor){
        System.out.println("IN THE NEXT ITERATION IT WILL BE IMPLEMENTED.");
    };

    private void handleStudentActions(Student student) {
        while (true) {
            System.out.println("----------ACTIONS----------");
            System.out.println("1- Create a registration");
            System.out.println("2- Check current registration status");
            System.out.print("Please choose an action: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createRegistration(student);
                case 2 -> courseRegSystem.studentEnrollRequest(student);
                default -> System.out.println("Invalid choice.");
            }

            System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
            String continueChoice = scanner.next();
            if (continueChoice.equalsIgnoreCase("n")) {
                logout();
                break;
            }
        }
    }

    private void createRegistration(Student student) {
        courseRegSystem.printSuitableCourses();
        while (true) {
            System.out.print("\nDo you want to add courses? (y/n): ");
            String addCourse = scanner.next();
            if (addCourse.equalsIgnoreCase("y")) {
                courseRegSystem.readCourses(student);
            } else if (addCourse.equalsIgnoreCase("n")) {
                break;
            }
        }

        System.out.println("The courses inside your registration are:");
        student.getRegistration().getCourseSectionList().forEach(courseSection ->
                System.out.println(courseSection.getCourse().getId() + " - " + courseSection.getSectionNumber())
        );

        System.out.println("\n----------System is checking eligibility----------\n");
        // Eligibility check can be implemented here

        System.out.print("Are you sure you want to send the registration request to your advisor? (y/n): ");
        String requestChoice = scanner.next();
        if (requestChoice.equalsIgnoreCase("y")) {
            courseRegSystem.sendRegistrationToAdvisor(student.getRegistration(), student);
            System.out.println("SUCCESS: The registration request has been sent to your advisor\n");
        } else {
            student.setRegistration(null);
            System.out.println("WARNING: The registration you have created has been deleted. Make a new one\n");
        }
    }

    private void logout() {
        // Save the final state to JSON or database
        System.out.println("\nLogged out successfully!");
    }
}
