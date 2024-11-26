import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            CourseRegistrationSystem system = new CourseRegistrationSystem();
            system.restoreSystem();
            Student student;
            Advisor advisor;
            LoginSystem loginSystem = new LoginSystem();
            Scanner scanner = new Scanner(System.in);



            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try {
                //Authenticate the Student
                if (loginSystem.authenticateUser(nickname, password, system)) {
                    System.out.println("Login successful!");
                    if (loginSystem.getStudent() != null) {
                        student = loginSystem.getStudent();

                        while (true) {
                            System.out.println("----------ACTIONS----------");
                            System.out.println("1- Create a registration");
                            System.out.println("2- Check current registration status");
                            System.out.println("\nPlease choose an action :");

                            int choice = scanner.nextInt();

                            switch (choice) {
                                case 1:

                                    system.printSuitableCourses();

                                    while (true){

                                        System.out.println("\nDo you want to add courses? (y/n): ");
                                        String addCourse = scanner.next();
                                        if (addCourse.equalsIgnoreCase("y")) {
                                            system.readCourses(student);
                                        }
                                        else if (addCourse.equalsIgnoreCase("n")) {
                                            break;
                                        }
                                    }

                                    System.out.println("The courses inside your registration are :");
                                    for (CourseSection courseSection : student.getRegistration().getCourseSectionList()) {
                                        System.out.println(courseSection.getCourse().getId() + " - " + courseSection.getSectionNumber());
                                    }

                                    System.out.println("\n----------System is checking eligibility----------\n");

                                    if (!system.checkEligibility(student)) {
                                        student.setRegistration(new Registration());
                                        break;
                                    }

                                    System.out.println("Are you sure want to send the registration request to your advisor? (y/n): ");

                                    String requestChoice = scanner.next();
                                    if (requestChoice.equalsIgnoreCase("y")) {
                                        system.sendRegistrationToAdvisor(student.getRegistration(), student);
                                        System.out.println("SUCCESS : The registration request have sent to your advisor\n");
                                        System.out.println();
                                        break;
                                    }
                                    else if (requestChoice.equalsIgnoreCase("n")) {
                                        student.setRegistration(new Registration());
                                        System.out.println("WARNING : The registration you have been created is deleted. Make a new one\n");
                                        break;
                                    }

                                case 2 :
                                    System.out.println();
                                    system.studentEnrollRequest(student);
                            }

                            System.out.println("Do you want to continue ? (If not you will logout) (y/n): ");
                            String continueChoice = scanner.next();
                            if (continueChoice.equalsIgnoreCase("y")) {
                                continue;
                            }
                            else if (continueChoice.equalsIgnoreCase("n")) {
                                system.saveLastState();
                                System.out.println("\nLogged out successfully! ");
                                break;
                            }


                        }


                    }
                    else {
                        // Advisor işlemleri ve UI burada
                        advisor = loginSystem.getAdvisor();
                        while (true) {
                            System.out.println("----------ADVISOR ACTIONS----------");
                            System.out.println("1- View students");
                            System.out.println("2- Approve/Reject registration requests");
                            System.out.println("3- Log out");
                            System.out.print("Please choose an action: ");

                            int advisorChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (advisorChoice) {
                                case 1 -> {
                                    System.out.println("Students under your supervision:");
                                    /*
                                    for (Student supervisedStudent : advisor.getStudents()) {
                                        System.out.println("ID: " + supervisedStudent.getId() + " | Name: " + supervisedStudent.getName());
                                    }

                                     */
                                }
                                case 2 -> {
                                    System.out.println("IT'S NOT WORKING");
                                    //Will be in iteration 2
                                    //system.answerRegistrationRequests(advisor);
                                }
                                case 3 -> {
                                    system.saveLastState();
                                    System.out.println("Logging out...");
                                    return; // Exit the advisor actions loop
                                }
                                default -> System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid nickname or password.");
                }


            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (Exception e) {
            e.printStackTrace();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
