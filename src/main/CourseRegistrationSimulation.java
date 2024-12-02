package main;

import DataBaseController.*;
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
    private final CourseDBController courseDBController;
    private final Logger logger; // Logger instance
    private final Scanner scanner;

    private final RegistrationDBController registrationDBController;

    public CourseRegistrationSimulation(CourseRegistrationSystem courseRegSystem) {
        this.studentDBController = new StudentDBController();
        this.advisorDBController = new AdvisorDBController();
        this.departmentSchedulerDBController = new DepartmentSchedulerDBController();
        this.registrationDBController = new RegistrationDBController();
        this.courseDBController = new CourseDBController();
        this.courseRegSystem = courseRegSystem;
        this.loginSystem = new LoginSystem(this.studentDBController, this.advisorDBController, this.departmentSchedulerDBController);
        this.logger = SingletonLogger.getInstance().getLogger();
        this.scanner = new Scanner(System.in);
    }

    public void run() throws IOException {
        try {
            logger.info("Simulation started.");
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
            logger.severe("Unexpected error: " + e.getMessage());
            e.printStackTrace(); // Beklenmeyen hatalar için genel hata yakalayıcı
        }
        logger.info("Simulation ended.");
    }

    private void loginStudent() {
        try {
            System.out.println();
            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (loginSystem.authenticateUser(nickname, password)) {
                if (loginSystem.getStudent() != null) {
                    System.out.println("Login successful!");
                    logger.info("Student login successful: " + nickname);

                    handleStudentActions(studentDBController.getStudent());
                }
            } else {
                System.out.println("Invalid nickname or password.");
                System.out.println();
                logger.warning("Failed student login attempt: " + nickname);

            }
        } catch (Exception e) {
            logger.severe("Error during student login: " + e.getMessage());
        }
    }

    private void handleStudentActions(Student student) throws IOException {
        try {
            logger.info("Handling student actions for: " + student.getId());
            while (true) {
                System.out.println();
                System.out.println("----------ACTIONS----------");
                System.out.println("1- Create a registration");
                System.out.println("2- Check current registration status");
                System.out.println("3- Print weekly schedule");
                //System.out.println("4- Turn back to the user selection menu");
                System.out.print("Please choose an action: ");

                int choice = scanner.nextInt();

                while(choice < 1 || choice > 3){
                    System.out.print("Please type a invalid choice: ");
                    choice = scanner.nextInt();
                }

                switch (choice) {
                    case 1 -> createRegistration(student);
                    case 2 -> courseRegSystem.getStudentRegistrationStatus(student);
                    case 3 -> student.printWeeklyScheduleAsTable(student);
                    //case 4 -> run();
                    default -> System.out.println("Invalid choice.");
                }

                System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
                String continueChoice = scanner.next();
                if (continueChoice.equalsIgnoreCase("n")) {
                    logout();
                    break;
                }
            }
        } catch (Exception e) {
            logger.severe("Unexpected error during handleStudentactions: " + e.getMessage());
        }
    }

    private void createRegistration(Student student) throws IOException {
        try {
            logger.info("Creating registration for student: " + student.getId());
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
        } catch (Exception e) {
            logger.severe("An error occurred creating registration for student:: " + e.getMessage());
        }
    };

    private void loginAdvisor() {
        try {
            logger.info("Handling advisor login");

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
            logger.severe("Unexpected error during login advisor: " + e.getMessage());
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error during login advisor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleAdvisorActions(Advisor advisor) {
        try {
            logger.info("Handling advisor actions");
            while (true) {
                System.out.println();
                System.out.println("----------ACTIONS----------");
                //System.out.println("1- Check students");
                System.out.println("1- Approve/Reject student registration requests");
                //System.out.println("2- Turn back to the user selection menu");
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
                    //case 2 -> run();
                }

                System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
                String continueChoice = scanner.next();
                if (continueChoice.equalsIgnoreCase("n")) {
                    logout();
                    break;
                }
            }
        } catch (Exception e) {
            logger.severe("Unexpected error during handleAdvisorActions: " + e.getMessage());
        }
    }

    private void handleRegistrationRequests(Advisor advisor) {
        try {
            logger.info("Handling Registration Requests");
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
            logger.severe("Unexpected error during handleRegistrationRequests: " + e.getMessage());
        }
    };


    public void loginDepartmentScheduler(){
        try {
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
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDepartmentSchedulerActions(DepartmentScheduler departmentScheduler) throws IOException {

        while (true) {
            System.out.println();
            System.out.println("----------ACTIONS----------");
            System.out.println("1- Assign time and classroom to the course sections of the department");
            //System.out.println("2- Turn back to the user selection menu");
            System.out.print("Please choose an action: ");

            int choice = scanner.nextInt();

            while(choice != 1){
                System.out.println();
                System.out.print("Please enter a valid option:");
                choice = scanner.nextInt();
            }

            switch (choice) {
                //case 1 -> checkStudents(advisor);
                case 1 -> handleCourseSectionTimesAndClassroom(departmentScheduler);
                //case 2 -> run();
            }

            System.out.print("Do you want to continue? (If not you will logout) (y/n): ");
            String continueChoice = scanner.next();
            if (continueChoice.equalsIgnoreCase("n")) {
                logout();
                break;
            }
        }

    }

    private void handleCourseSectionTimesAndClassroom(DepartmentScheduler departmentScheduler) {
        // CourseSectionList
        courseDBController.loadCourseSectionListOfDepartmentScheduler(departmentScheduler);

        System.out.println();
        System.out.println("------------Course Sections------------");
        for (CourseSection courseSection: departmentScheduler.getCourseSectionList()){
            System.out.println(courseSection.getId());
        }

        System.out.println("Please select a course section to change/assign its time and classroom");

        System.out.print("Print the course name: ");
        String courseName = scanner.next();

        System.out.print("Print the course section id: ");
        String sectionId = scanner.next();

        String courseSectionId = courseName + "-" + sectionId;

        // to define is printed course section exist call this function
        isCourseSectionExistInDepartment(courseSectionId,departmentScheduler.getCourseSectionList());

        // handle the operation about this course section
        handleCourseSectionSettingsMenus(courseSectionId);

        // CourseSectionı seç

        // Yeni Menu ayarla.
    }

    private void handleCourseSectionSettingsMenus(String courseSectionId) {

        CourseSection courseSection;
        courseSection = courseDBController.loadCourseSection(courseSectionId);

        boolean isTimeChange = false;
        boolean isClassroomChange = false;

        boolean isTimeValid = false;
        boolean isClassroomValid = false;

        // Classroom Settings
        if(courseSection.getClassroom() == null){
            System.out.println(courseSection.getId() + "'s classroom is empty. Do you want to add classroom?");
            String yesOrNo = scanner.next();

            boolean isTrueInput = false;

            while(!isTrueInput){
                if(yesOrNo.equalsIgnoreCase("Y")){
                    isTrueInput = true;

                    System.out.print("Please enter the classroom name:");
                    String croomName = scanner.next();
                    isClassroomValid = courseDBController.assignClassroomToCourseSection(courseSection, croomName);

                    if(!isClassroomValid){

                        System.out.print("Do you want to change the classroom of this course section? (y/n): ");
                        yesOrNo = scanner.next();
                        boolean isTrueInputInner = false;

                        while(!isTrueInputInner){

                            if(yesOrNo.equalsIgnoreCase("Y")){
                                isTrueInputInner = true;
                                isTrueInput = false;
                            }else if(yesOrNo.equalsIgnoreCase("N")){
                                isTrueInputInner = true;
                                isTrueInput = true;
                            }else{
                                isTrueInput = false;
                                System.out.print("Do you want to add classroom? Please enter the input correctly (y/n):");
                                yesOrNo = scanner.next();
                            }

                        }
                    }


                }else if(yesOrNo.equalsIgnoreCase("N")){
                    break;
                }else{
                    isTrueInput = false;
                    System.out.print("Do you want to add classroom? Please enter the input correctly (y/n):");
                    yesOrNo = scanner.next();
                }
            }

        }
        else{
            System.out.print(courseSection.getId() + "'s classroom is "+ courseSection.getClassroom().getId() +".\nDo you want to change the classroom? ");
            System.out.print("(y/n): ");
            String yesOrNo = scanner.next();

            boolean isTrueInput = false;

            while(!isTrueInput) {
                if (yesOrNo.equalsIgnoreCase("Y")) {
                    isTrueInput = true;

                    System.out.print("Please enter the classroom name:");
                    String croomName = scanner.next();
                    isClassroomValid = courseDBController.assignClassroomToCourseSection(courseSection, croomName);

                    if(!isClassroomValid){

                        System.out.print("Do you want to change the classroom of this course section? (y/n): ");
                        yesOrNo = scanner.next();
                        boolean isTrueInputInner = false;

                        while(!isTrueInputInner){

                            if(yesOrNo.equalsIgnoreCase("Y")){
                                isTrueInputInner = true;
                                isTrueInput = false;
                            }else if(yesOrNo.equalsIgnoreCase("N")){
                                isTrueInputInner = true;
                                isTrueInput = true;
                            }else{
                                isTrueInput = false;
                                System.out.print("Do you want to add classroom? Please enter the input correctly (y/n):");
                                yesOrNo = scanner.next();
                            }

                        }
                    }

                } else if (yesOrNo.equalsIgnoreCase("N")) {
                    isClassroomValid = true;
                    break;
                } else {
                    isTrueInput = false;
                    System.out.print("Do you want to add classroom? Please enter the input correctly (y/n):");
                    yesOrNo = scanner.next();
                }
            }
            //courseDBController.assignTimesToCourseSection(courseSection);
        }

        // Time Settings
        if(courseSection.getScheduledTimes().isEmpty()){
            System.out.print(courseSection.getId() + "'s time is empty.\nDo you want to add times for this course section?(y/n): ");
            String yesOrNo = scanner.next();

            boolean isTrueInput = false;

            while(!isTrueInput){

                if(yesOrNo.equalsIgnoreCase("Y")){
                    isTrueInput = true;
                    isTimeValid = courseDBController.assignTimesToCourseSection(courseSection,departmentSchedulerDBController.getDepartmentScheduler());

                    if(!isTimeValid){
                        System.out.print("Do you want to change the classroom of this course section? (y/n): ");
                        yesOrNo = scanner.next();
                        boolean isTrueInputInner = false;

                        while(!isTrueInputInner){

                            if(yesOrNo.equalsIgnoreCase("Y")){
                                isTrueInputInner = true;
                                isTrueInput = false;
                            }else if(yesOrNo.equalsIgnoreCase("N")){
                                isTrueInputInner = true;
                                isTrueInput = true;
                            }else{
                                isTrueInput = false;
                                System.out.print("Do you want to add times? Please enter the input correctly (y/n):");
                                yesOrNo = scanner.next();
                            }

                        }
                    }

                }else if(yesOrNo.equalsIgnoreCase("N")){
                    break;
                }else{
                    isTrueInput = false;
                    System.out.print("Do you want to add times? Please enter the input correctly (y/n):");
                    yesOrNo = scanner.next();
                }

            }

        }
        else{
            System.out.println(courseSection.getId() + "'s classroom times are:");

            for (int i = 0; i < courseSection.getScheduledTimes().size(); i++) {
                System.out.println(i+" ==> Day is :" + courseSection.getScheduledTimes().get(i).getCourseDay());
                System.out.println("Start time: " + courseSection.getScheduledTimes().get(i).getStartTime());
                System.out.println("End time: " + courseSection.getScheduledTimes().get(i).getEndTime());
            }

            System.out.print("Do you want to change the times (y/n): ");

            String yesOrNo = scanner.next();

            boolean isTrueInput = false;

            while(!isTrueInput){

                if(yesOrNo.equalsIgnoreCase("Y")){
                    isTrueInput = true;
                    isTimeValid = courseDBController.assignTimesToCourseSection(courseSection,departmentSchedulerDBController.getDepartmentScheduler());

                    if(!isTimeValid){
                        System.out.print("Do you want to change the classroom of this course section? (y/n): ");
                        yesOrNo = scanner.next();
                        boolean isTrueInputInner = false;

                        while(!isTrueInputInner){

                            if(yesOrNo.equalsIgnoreCase("Y")){
                                isTrueInputInner = true;
                                isTrueInput = false;
                            }else if(yesOrNo.equalsIgnoreCase("N")){
                                isTrueInputInner = true;
                                isTrueInput = true;
                            }else{
                                isTrueInput = false;
                                System.out.print("Do you want to add times? Please enter the input correctly (y/n):");
                                yesOrNo = scanner.next();
                            }

                        }
                    }

                }else if(yesOrNo.equalsIgnoreCase("N")){
                    isTimeValid = true;
                    break;
                }else{
                    isTrueInput = false;
                    System.out.print("Do you want to add times? Please enter the input correctly (y/n):");
                    yesOrNo = scanner.next();
                }

            }


        }

        if(isClassroomValid && isTimeValid){
            courseDBController.isClassroomAvailable(courseSection);
        }else{

        }

        //System.out.println(courseSection.getClassroom().getCapacity());

    };

    private boolean isCourseSectionExistInDepartment(String courseSectionId, List<CourseSection> courseSectionList){

        for (CourseSection courseSection : courseSectionList) {
            if(courseSection.getId().equals(courseSectionId)){
                return true;
            }
        }

        System.out.println("Please enter the inputs correctly.");
        System.out.println();
        return false;

    }

    private void checkStudents(Advisor advisor) {
        System.out.println("IN THE NEXT ITERATION IT WILL BE IMPLEMENTED.");
    };




    private void logout() {
        // Save the final state to JSON or database
        logger.info("User logged out.");
        System.out.println("\nLogged out successfully!");
        System.exit(0);
    }
}