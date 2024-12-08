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
        // All these DBControllers are for handling the database loading processes for different object types
        this.studentDBController = new StudentDBController();
        this.advisorDBController = new AdvisorDBController();
        this.departmentSchedulerDBController = new DepartmentSchedulerDBController();
        this.registrationDBController = new RegistrationDBController();
        this.courseDBController = new CourseDBController();
        // courseRegSystem is the CourseRegistrationSystem that was created inside the main function, it will handle the backend processes and
        // it's also used inside CourseRegistrationSimulation to switch the flow to it in neccessary times
        this.courseRegSystem = courseRegSystem;
        // loginSystem is responsible of the handling of login actions
        this.loginSystem = new LoginSystem(this.studentDBController, this.advisorDBController, this.departmentSchedulerDBController);
        this.logger = SingletonLogger.getInstance().getLogger();
        this.scanner = new Scanner(System.in);
    }

    // run() is the main function of the CourseRegistrationSimulation class that will print the first requests from the user and give the responsibility to other functions
    // according to who is logged into the system
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

                // Flow of the program is changed according to who were logged in
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

    // loginStudent is responsible of handling the student CLI actions and prompts the student accordingly
    private void loginStudent() {
        try {
            System.out.println();
            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // If the authentication returns a success student will be logged in and the flow will be switched to handleStudentActions function
            if (loginSystem.authenticateUser(nickname, password)) {
                if (loginSystem.getStudent() != null) {
                    System.out.println("Login successful!");
                    logger.info("Student login successful: " + nickname);

                    // As the login is successfull, we change the flow to handle the student actions
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

    // handleStudentActions function is for switching the flow and prompting according to the student user
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

                // Flow is changed according to what the student user has been selected for the desired action
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

    // createRegistration is a student user specific function that prompts the user for creating a registration and initiates
    // actions related to this process
    private void createRegistration(Student student) throws IOException {
        try {
            logger.info("Creating registration for student: " + student.getId());
            // courseRegSystem will print the suitable courses that the student can take
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

            // Printing the currently selected course and sections by the student user
            System.out.println("The courses inside your registration are:");
            student.getRegistration().getCourseSections().forEach(courseSection ->
                    System.out.println(courseSection.getCourseId() + " - " + courseSection.getSectionNumber())
            );

            // We couldn't implement the eligibility well so we commented it out at the end of second iteration !!!!!
            System.out.println("\n----------System is checking eligibility----------\n");
            // Eligibility check can be implemented here
//           if(!(courseRegSystem.checkEligibility(student))) {
//                student.getAdvisor().getRegistrations().remove(student.getRegistration());
//                student.getAdvisor().getRegistrationsIDs().remove(student.getRegistration().getId());
//              student.setRegistration(new Registration());
//                return;
//           }

            System.out.print("Are you sure you want to send the registration request to your advisor? (y/n): ");
            String requestChoice = scanner.next();
            if (requestChoice.equalsIgnoreCase("y")) {
                // courseRegSystem sends the currently completed registration to the student user's advisor
                courseRegSystem.sendRegistrationToAdvisor(student.getRegistration(), student);
                System.out.println("SUCCESS: The registration request has been sent to your advisor\n");
            } else {
                // Otherwise all of the added things will be discarded
                student.getAdvisor().getRegistrations().remove(student.getRegistration());
                student.getAdvisor().getRegistrationsIDs().remove(student.getRegistration().getId());
                // This should be new Registration() not null, otherwise it causes problems !!!
                student.setRegistration(new Registration());
                System.out.println("WARNING: The registration you have created has been deleted. Make a new one\n");
            }
        } catch (Exception e) {
            logger.severe("An error occurred creating registration for student:: " + e.getMessage());
        }
    };

    // loginAdvisor is responsible of handling the advisor CLI actions and prompts the advisor accordingly
    private void loginAdvisor() {
        try {
            logger.info("Handling advisor login");

            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // If the authentication returns a success advisor will be logged in and the flow will be switched to handleAdvisorActions function
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

    // handleAdvisorActions function is for switching the flow and prompting according to the advisor user
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
            // registrationDBController loads all of the registrations that were associated with this particular advisor
            List<Registration> registrations = registrationDBController.getRegistrationsOfAdvisor(advisor);

            System.out.println();
            System.out.println("Please set the status of the registrations:");
            System.out.println("In order to specify as not approved print O");
            System.out.println("In order to specify as not checked yet 1");
            System.out.println("In order to specify as approved print 2");

            // Registrations come each by each and advisor updates the status of them
            for (int i = 0; i < registrations.size(); i++) {
                System.out.println(registrations.get(i).getId());
                System.out.println(registrations.get(i).getRegistrationStatus());

                System.out.print("Print the status to change the state of the registration listed: ");
                int status = scanner.nextInt();

                while (status < 0 || status > 2) {
                    System.out.print("Please enter valid number: ");
                    status = scanner.nextInt();
                }

                // Here is where a single registration's status is updated in both here and in registrationDBController's side
                registrations.get(i).setRegistrationStatus(status);
                registrationDBController.updateRegistrations(registrations.get(i), status);

            }
        } catch (Exception e) {
            logger.severe("Unexpected error during handleRegistrationRequests: " + e.getMessage());
        }
    };

    // loginDepartmentScheduler is responsible of handling the department scheduler CLI actions and prompts the department scheduler accordingly
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

    // handleDepartmentSchedulerActions function is for switching the flow and prompting according to the department scheduler user
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

        String result = "";

        // Classroom Settings
        if(courseSection.getClassroom() == null){
            System.out.print(courseSection.getId() + "'s classroom is empty.\nDo you want to add classroom?(y/n): ");
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
                                result += "There is no any classroom for this course section.\n";
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
                    result += "There is no any classroom for this course section.\n";
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
                                result += "There is no any saved course section times for this course section.";
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
                    result += "There is no any saved course section times for this course section.";
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
            System.out.println();
            System.out.print("Any course section hour cannot be saved because:\n"+result+"\n");
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