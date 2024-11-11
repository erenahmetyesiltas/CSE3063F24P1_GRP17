import java.io.IOException;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {

        try {
            CourseRegistrationSystem system = new CourseRegistrationSystem();
            system.restoreSystem();
            Student student;
            Advisor advisor;
            LoginSystem loginSystem = new LoginSystem();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Select user type:");
            System.out.println("1 - Student");
            System.out.println("2 - Advisor");
            System.out.print("Enter your choice: ");
            int userType = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (userType) {
                case 1 -> {
                    System.out.print("Enter your nickname: ");
                    String nickname = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (loginSystem.authenticateUser(nickname, password, system) && loginSystem.getStudent() != null) {
                        student = loginSystem.getStudent();
                        System.out.println("Login successful as Student!");

                        while (true) {
                            System.out.println("----------ACTIONS----------");
                            System.out.println("1- Create a registration");
                            System.out.println("2- Check current registration status");
                            System.out.print("\nPlease choose an action: ");
                            int choice = scanner.nextInt();

                            switch (choice) {
                                case 1 -> {
                                    system.printSuitableCourses();
                                    while (true) {
                                        System.out.print("\nDo you want to add courses? (y/n): ");
                                        String addCourse = scanner.next();
                                        if (addCourse.equalsIgnoreCase("y")) {
                                            system.readCourses(student);
                                        } else if (addCourse.equalsIgnoreCase("n")) {
                                            break;
                                        }
                                    }

                                    System.out.println("The courses inside your registration are:");
                                    for (CourseSection courseSection : student.getRegistration().getCourseSectionList()) {
                                        System.out.println(courseSection.getCourse().getId() + " - " + courseSection.getSectionNumber());
                                    }

                                    System.out.println("\n----------System is checking eligibility----------\n");

                                    // checkEligibility() method to be implemented

                                    System.out.print("Are you sure you want to send the registration request to your advisor? (y/n): ");
                                    String requestChoice = scanner.next();
                                    if (requestChoice.equalsIgnoreCase("y")) {
                                        system.sendRegistrationToAdvisor(student.getRegistration(), student);
                                        System.out.println("SUCCESS: The registration request has been sent to your advisor\n");
                                    } else {
                                        student.setRegistration(null);
                                        System.out.println("WARNING: The registration you created has been deleted. Make a new one\n");
                                    }
                                }
                                case 2 -> {
                                    System.out.println();
                                    system.studentEnrollRequest(student);
                                }
                                default -> System.out.println("Invalid choice. Please try again.");
                            }

                            System.out.print("Do you want to continue? (If not, you will logout) (y/n): ");
                            String continueChoice = scanner.next();
                            if (continueChoice.equalsIgnoreCase("n")) {
                                system.saveLastState();
                                System.out.println("\nLogged out successfully!");
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid nickname or password.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter your nickname: ");
                    String nickname = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (loginSystem.authenticateUser(nickname, password, system) && loginSystem.getAdvisor() != null) {
                        advisor = loginSystem.getAdvisor();
                        System.out.println("Login successful as Advisor!");

                        while (true) {
                            System.out.println("----------ADVISOR ACTIONS----------");
                            System.out.println("1- View students");
                            System.out.println("2- Approve/Reject registration requests");
                            System.out.println("3- Log out");
                            System.out.print("Please choose an action: ");
                            int advisorChoice = scanner.nextInt();

                            switch (advisorChoice) {
                                case 1 -> {
                                    System.out.println("Students under your supervision:");
                                    // Implement the logic for listing students
                                }
                                case 2 -> {
                                    System.out.println("Approve/Reject registration requests - Not implemented yet.Will be in iteration 2");
                                    // Due to JSON problems..
                                    //Will be in iteration 2
                                    //system.answerRegistrationRequests(advisor);
                                }
                                case 3 -> {
                                    system.saveLastState();
                                    System.out.println("Logging out...");
                                    return;
                                }
                                default -> System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid nickname or password.");
                    }
                }
                default -> System.out.println("Invalid user type. Please restart and select a valid option.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
