package main;

import DataBaseController.AdvisorDBController;
import DataBaseController.DepartmentSchedulerDBController;
import DataBaseController.RegistrationDBController;
import DataBaseController.StudentDBController;
import log.SingletonLogger;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

class CourseRegistrationSimulation {
    private final CourseRegistrationSystem courseRegSystem;
    private final LoginSystem loginSystem;
    private final StudentDBController studentDBController;
    private final AdvisorDBController advisorDBController;
    private final DepartmentSchedulerDBController departmentSchedulerDBController;
    private final RegistrationDBController registrationDBController;
    private final Logger logger;
    private final Scanner scanner;

    public CourseRegistrationSimulation(CourseRegistrationSystem courseRegSystem) {
        this.studentDBController = new StudentDBController();
        this.advisorDBController = new AdvisorDBController();
        this.departmentSchedulerDBController = new DepartmentSchedulerDBController();
        this.registrationDBController = new RegistrationDBController();
        this.courseRegSystem = courseRegSystem;
        this.loginSystem = new LoginSystem(this.studentDBController, this.advisorDBController, this.departmentSchedulerDBController);
        this.logger = SingletonLogger.getInstance().getLogger();
        this.scanner = new Scanner(System.in);
    }

    public void run() throws IOException {
        try {
            this.logger.info("Course Registration Simulation started.");
            while (true) {
                System.out.println("Please select an option:");
                System.out.println("1- Advisor Login");
                System.out.println("2- Student Login");
                System.out.println("3- Department Scheduler Login ");
                System.out.println("4- Log Out");

                System.out.print("Enter your choice: ");

                // Check whether input is integer or not.
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear the incorrect input
                    continue; // Return back to menu
                }

                int userChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (userChoice) {
                    case 1 -> loginAdvisor();
                    case 2 -> loginStudent();
                    case 3 -> loginDepartmentScheduler();
                    case 4 -> logout();
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            this.logger.severe("An error occurred in CourseRegistrationSimulation.run(): " + e.getMessage());
            e.printStackTrace(); // Beklenmeyen hatalar için genel hata yakalayıcı
        }
    }

    private void loginStudent() {
        try {
            this.logger.info("Student login initiated.");

            System.out.println();
            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (loginSystem.authenticateUser(nickname, password)) {
                if (loginSystem.getStudent() != null) {
                    System.out.println("Login successful!");
                    // Load student to studentDBController.
                    // studentDBController.setStudent(loginSystem.getStudent());
                    // Student actions.
                    handleStudentActions(studentDBController.getStudent());
                }
            } else {
                System.out.println("Invalid nickname or password.");
                System.out.println();
            }
        } catch (Exception e) {
            this.logger.severe("An error occurred in loginStudent: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void handleStudentActions(Student student) throws IOException {
        while (true) {
            try {
                logger.info("handleStudentActions initiated.");

                System.out.println();
                System.out.println("----------ACTIONS----------");
                System.out.println("1- Create a registration");
                System.out.println("2- Check current registration status");
                System.out.println("3- Print weekly schedule");
                System.out.print("Please choose an action: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> createRegistration(student);
                    case 2 -> courseRegSystem.getStudentRegistrationStatus(student);
                    case 3 -> student.printWeeklyScheduleAsTable(student);
                    default -> System.out.println("Invalid choice.");
                }

                System.out.println("Do you want to continue? (If not you will logout) (y/n): ");
                String continueChoice = scanner.next();
                if (continueChoice.equalsIgnoreCase("n")) {
                    logout();
                    break;
                }
            } catch (IOException e) {
                this.logger.severe("An error occurred in handleStudentActions: " + e.getMessage());
            } catch (Exception e) {
                this.logger.severe("An error occurred in handleStudentActions: " + e.getMessage());;
            }
        }
    }

    private void loginAdvisor() {
        try {
            logger.info("Advisor login initiated.");

            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (loginSystem.authenticateAdvisorUser(nickname, password)) {
                if (loginSystem.getAdvisor() != null) {
                    System.out.println("Login successful!");
                    handleAdvisorActions(advisorDBController.getAdvisor());

                }
            } else {
                System.out.println("Invalid nickname or password.");
            }
        } catch (IOException e) {
            logger.severe("An error occurred in loginAdvisor: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("An error occurred in loginAdvisor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleAdvisorActions(Advisor advisor) {
        while (true) {
            try {
                logger.info("handleAdvisorActions initiated.");

                System.out.println();
                System.out.println("----------ACTIONS----------");
                //System.out.println("1- Check students");
                System.out.println("1- Approve/Reject student registration requests");
                System.out.print("Please choose an action: ");

                int choice = scanner.nextInt();

                while (choice != 1) {
                    System.out.println();
                    System.out.print("Please enter a valid option:");
                    choice = scanner.nextInt();
                }

                switch (choice) {
                    //case 1 -> checkStudents(advisor);
                    case 1 -> handleRegistrationRequests(advisor);
                }

                System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
                String continueChoice = scanner.next();
                if (continueChoice.equalsIgnoreCase("n")) {
                    logout();
                    break;
                }
            } catch (Exception e) {
                logger.severe("An error occurred in handleAdvisorActions: " + e.getMessage());
            }

        }
    }

    public void loginDepartmentScheduler(){
        try {
            logger.info("Login department scheduler initiated.");

            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (loginSystem.authenticateDepartmentSchedulerUser(nickname, password)) {
                if (loginSystem.getDepartmentScheduler() != null) {
                    System.out.println("Login successful!");

                    handleDepartmentSchedulerActions(departmentSchedulerDBController.getDepartmentScheduler());
                    //handleAdvisorActions(advisorDBController.getAdvisor());

                }
            } else {
                System.out.println("Invalid nickname or password.");
            }
        } catch (IOException e) {
            logger.severe("An error occurred in loginDepartmentScheduler: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("An error occurred in loginDepartmentScheduler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDepartmentSchedulerActions(DepartmentScheduler departmentScheduler) {
        while (true) {
            try {
                System.out.println();
                System.out.println("----------ACTIONS----------");
                System.out.println("1- Assign time to the course sections of the department");
                //System.out.println("2- Turn back");
                System.out.print("Please choose an action: ");

                int choice = scanner.nextInt();

                while (choice != 1) {
                    System.out.println();
                    System.out.print("Please enter a valid option:");
                    choice = scanner.nextInt();
                }

                switch (choice) {
                    //case 1 -> checkStudents(advisor);
                    case 1 -> handleCourseSectionTimes(departmentScheduler);
                }

                System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
                String continueChoice = scanner.next();
                if (continueChoice.equalsIgnoreCase("n")) {
                    logout();
                    break;
                }
            }   catch (Exception e) {
                logger.severe("An error occurred in handleDepartmentSchedulerActions: " + e.getMessage());
                }
        }

    }

    private void handleCourseSectionTimes(DepartmentScheduler departmentScheduler) {
        System.out.println("CONSTRUCTION");
    }

    private void checkStudents(Advisor advisor) {
        System.out.println("IN THE NEXT ITERATION IT WILL BE IMPLEMENTED.");
    };

    private void handleRegistrationRequests(Advisor advisor) {
        try {
            logger.info("handleRegistrationRequests initiated.");

            List<Registration> registrations = registrationDBController.getRegistrationsOfAdvisor(advisor);

            System.out.println();
            System.out.println("Please set the status of the registrations:");
            System.out.println("In order to specify as not approved print O");
            System.out.println("In order to specify as not checked yet 1");
            System.out.println("In order to specify as approved print 2");


            for (int i = 0; i < registrations.size(); i++) {
                System.out.println(registrations.get(i).getId());
                System.out.println(registrations.get(i).getRegistrationStatus());

                System.out.print("Print the status to change the state of the registration listed: ");
                int status = scanner.nextInt();

                while (status < 0 || status > 2) {
                    System.out.print("Please enter valid number: ");
                    status = scanner.nextInt();
                }

                registrations.get(i).setRegistrationStatus(status);
                registrationDBController.updateRegistrations(registrations.get(i), status);

            }
        } catch (Exception e) {
            logger.severe("An error occurred in handleRegistrationRequests: " + e.getMessage());
        }
    };

    private void createRegistration(Student student) throws IOException {


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
        student.getRegistration().getCourseSections().forEach(courseSection ->
                System.out.println(courseSection.getCourseId() + " - " + courseSection.getSectionNumber())
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
        System.exit(0);
    }
}