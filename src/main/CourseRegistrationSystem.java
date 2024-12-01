package main;

import DataBaseController.AdvisorDBController;
import DataBaseController.CourseDBController;
import DataBaseController.StudentDBController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CourseRegistrationSystem {
    private List<Student> allStudents = new ArrayList<Student>();
    private List<Advisor> allAdvisors = new ArrayList<>();
    private List<CourseSection> allCourseSections = new ArrayList<>();
    private List<Department> allDepartments = new ArrayList<>();
    private final DataHandler dataHandler = new DataHandler();
    private CourseDBController courseDBController = new CourseDBController();

    public CourseRegistrationSystem() throws IOException {
        setAllCourseSections(dataHandler.getCourseSectionList());
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

    public List<Advisor> getAllAdvisors() {
        return allAdvisors;
    }

    public void setAllAdvisors(List<Advisor> allAdvisors) {
        this.allAdvisors = allAdvisors;
    }

    public List<CourseSection> getAllCourseSections() {
        return allCourseSections;
    }

    public void setAllCourseSections(List<CourseSection> allCourseSections) {
        this.allCourseSections = allCourseSections;
    }

    public List<Department> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<Department> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }


//    public void restoreSystem() throws NoSuchElementException {
//        int i = 0;
//        List<SystemData> allDatas = dataHandler.getAllDatas();
//        ObjectMapper mapper = dataHandler.getObjectMapper();
//
//        while (i != (allDatas.size())) {
//            Class objectClass = allDatas.get(i).getObjectClass();
//            Object object = allDatas.get(i).getObject();
//
//            if (objectClass == Student.class) {
//                Student student = mapper.convertValue(object, Student.class);
//                this.getAllStudents().add(student);
//            }
//
//            if (objectClass == CourseSection.class) {
//                CourseSection course = mapper.convertValue(object, CourseSection.class);
//                this.getAllCourseSections().add(course);
//
//            }
//
//            if (objectClass == Advisor.class) {
//                Advisor advisor = mapper.convertValue(object, Advisor.class);
//                this.getAllAdvisors().add(advisor);
//            }
//
//            i++;
//        }
//    }

    public void updateData(Object object) {
        SystemData data = new SystemData(object, object.getClass(), "object" + dataHandler.getFileNumber() + ".json");
        dataHandler.getAllDatas().add(data);
        dataHandler.removeDuplicateData();
    }

    public void printSuitableCourses() {
        System.out.println("\nThe available courses that you can register");
        System.out.println("Course :               Section :");
        //courseDBController.getCourseSectionList(); //Bunu alttakiyle değiştirdim, aynı fonksiyonu içeriyor./ilker
        dataHandler.getCourseSectionList();

        for (CourseSection courseSection : getAllCourseSections()) {
            System.out.printf("%-23s%-1d\n",courseSection.getCourse().getId(),courseSection.getSectionNumber());
            //System.out.println(courseSection.getCourse().getName());
        }
    }

    // addCourseSection is added to main.Student, change it in DSD and SSD
    public CourseSection findCourseSection(String course, String section) {
        int sectionNumber = Integer.parseInt(section);

        for (CourseSection courseSection : this.getAllCourseSections()) {

            if (courseSection.getCourse().getId().equals(course) && courseSection.getSectionNumber() == sectionNumber) {
                return courseSection;
            }
        }
        return null;
    }

    public boolean readCourses(Student student){
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter the course: ");
       String course = scanner.next();
       System.out.print("Enter the section: ");
       String courseSection =scanner.next();

       if (findCourseSection(course,courseSection) != null) {
          if (checkRegistrationTimeConflict(student,findCourseSection(course,courseSection))){
              System.out.println("WARNING: The hours of the course you want to add conflict with the courses you added.");
              return false;
          }
          else {
              return student.getRegistration().addCourseSection((findCourseSection(course,courseSection)));
          }

       }
       else {
           System.out.println("WARNING: The course section entered cannot find in available courses.");
           return false;
       }
   }

   public boolean checkRegistrationTimeConflict(Student student,CourseSection newAddedCourseSection) {
        for (CourseSection eachCourseSection : student.getRegistration().getCourseSections()) {
           for(CourseTime courseTime : eachCourseSection.getScheduledTimes()){
               for(CourseTime newCourseTime : newAddedCourseSection.getScheduledTimes()){
                   if(courseTime.getCourseDay().equals(newCourseTime.getCourseDay())){
                       if((courseTime.getStartTime().before(newCourseTime.getEndTime())) && newCourseTime.getStartTime().before(courseTime.getEndTime())){
                            return true;
                       }
                       else if ((courseTime.getStartTime().equals(newCourseTime.getStartTime())) && (courseTime.getEndTime().equals(newCourseTime.getEndTime()))){
                           return true;
                       }
                   }
               }
           }
        }
        return false;
   }







    public void sendRegistrationToAdvisor(Registration registration, Student student) throws IOException {
        // First, load student's advisor & match it with student.advisor
        AdvisorDBController advisorDBController = new AdvisorDBController();
        advisorDBController.loadAdvisor(Integer.toString(student.getAdvisorId()));
        student.setAdvisor(advisorDBController.getAdvisor());
        for (String eachRegistration : advisorDBController.getAdvisor().getRegistrationsIDs() ){
            if(eachRegistration.equals(student.getRegistrationId())){
                advisorDBController.getAdvisor().getRegistrationsIDs().remove(eachRegistration);
            }
        }

        student.getAdvisor().addRegistration(registration);

        // Save the registration and Advisor's registration.
        StudentDBController studentDBController = new StudentDBController();
        studentDBController.setStudent(student);
        studentDBController.saveStudent(student.getId());
        studentDBController.saveStudentRegistration(student.getId());
        advisorDBController.saveAdvisor(Integer.toString(student.getAdvisorId()));
    };

    public void getStudentRegistrationStatus(Student student){

        // Regist. and Adv. got from main.Student
        Registration registration = student.getRegistration();

        switch (registration.getRegistrationStatus()) {
            case 0 :
                System.out.println(registration.getCourseSections());
                System.out.println("WARNING : Your advisor has disapproved your registration, you have to create a new one and send it again\n");
                //student.getAdvisor().getRegistrations().remove(student.getRegistration());
                // allAdvisorstan da remove et
                //student.setRegistration(null);
                break;
            case 1:
                System.out.println("WARNING : Your advisor has not yet checked your registration\n");
                break;
            case 2 :
                System.out.println("SUCCESS : Your advisor has approved your registration, you will be enrolled to the courses you \n");
                enrollStudent(registration.getCourseSections(), student);
                break;
        }

    }

    public void enrollStudent(List<CourseSection> enrollCourseSections, Student student){
        for (CourseSection courseSection : allCourseSections) {
            for (CourseSection enrollCourseSection : enrollCourseSections) {
                if (enrollCourseSection.getCourseId().equals(courseSection.getCourseId())) {
                    courseSection.getEnrolledStudents().add(student);
                    enrollCourseSection.getEnrolledStudents().add(student);
                }
            }
        }
    }

    public void saveLastState() throws IOException {
        for (Student student : this.getAllStudents()) {
            dataHandler.storeObject(student);
            dataHandler.removeDuplicateData();
        }
        for (CourseSection courseSection : this.getAllCourseSections()) {
            dataHandler.storeObject(courseSection);
            dataHandler.removeDuplicateData();
        }
        for (Advisor advisor : this.getAllAdvisors()) {
            dataHandler.storeObject(advisor);
            dataHandler.removeDuplicateData();
        }
    }

    public boolean checkEligibility(Student student) {
        if (checkPrerequisite(student) && checkCourseSectionFull(student)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkCourseSectionFull(Student student) {
        List<CourseSection> regCourseSections = student.getRegistration().getCourseSections();
        List<Course> prerequisiteCourses = new ArrayList<Course>();

        for (int i = 0; i < regCourseSections.size(); i++) {
            if (regCourseSections.get(i).getCapacity() <= regCourseSections.get(i).getEnrolledStudents().size()) { // checks if there are any full course sections
                // if yes return false
                System.out.println("WARNING : The " + regCourseSections.get(i).getId() + " course section is full, your registration will be discarded\n");
                return false;
            }
        }

        return true;

    }

    public boolean checkPrerequisite(Student student) {
        List<CourseSection> regCourseSections = student.getRegistration().getCourseSections();
        List<Course> prerequisiteCourses = new ArrayList<Course>();

        for (int i = 0; i < regCourseSections.size(); i++) {
            for (int j = 0; j < regCourseSections.get(i).getCourse().getPrerequisiteCourses().size(); j++) {
                prerequisiteCourses.add(regCourseSections.get(i).getCourse().getPrerequisiteCourses().get(j));
            }
        }

        List<Course> takenCourses = student.getCourses();
        boolean canTake = false;

        int n = prerequisiteCourses.size();
        int z = 0;

        for (int i = 0; i < prerequisiteCourses.size(); i++) {
            for (int j = 0; j < takenCourses.size(); j++) {
                if (prerequisiteCourses.get(i).equals(takenCourses.get(j))) {
                    z++;
                    break;
                }
            }
        }
        if (n == z)
            canTake = true;
        System.out.println("WARNING : There is a prerequisite conflict in your registration, your registration will be discarded\n");
        return canTake;
    }

    // public void answerRegistrationRequests(main.Advisor advisor)
    // main.Advisor girişi için gelen registration requestlerini istediği zaman konsola basacak bir fonksiyon, sıra sıra requestleri basacak ve advisora soracak onay mı ret mi beklet mi diye
    // main.Main'de system.answerRegistrationRequests(advisor) olarak call olacak

    /*
    DUE TO A BUG IN REGISTRATIONS, IT WILL BE IMPLEMENTED IN ITERATION 2
    public void answerRegistrationRequests(main.Advisor advisor) {
        Scanner scanner = new Scanner(System.in);
        List<main.Registration> pendingRegistrations = advisor.getRegistrations();

        if (pendingRegistrations.isEmpty()) {
            System.out.println("No registration requests to review.");
            return;
        }

        for (main.Registration registration : pendingRegistrations) {
            System.out.println("Reviewing registration for student: " + registration.getStudent().getFirstName() + registration.getStudent().getFirstName());
            System.out.println("Requested courses:");

            for (main.CourseSection courseSection : registration.getCourseSectionList()) {
                System.out.println("main.Course ID: " + courseSection.getCourse().getId() +
                        ", Section: " + courseSection.getSectionNumber());
            }

            System.out.println("Enter your decision: ");
            System.out.println("1: Approve");
            System.out.println("2: Reject");
            System.out.println("3: Hold");

            int decision = scanner.nextInt();
            switch (decision) {
                case 1:
                    registration.setRegistrationStatus(2); // Approved
                    System.out.println("main.Registration approved.");
                    break;
                case 2:
                    registration.setRegistrationStatus(0); // Rejected
                    System.out.println("main.Registration rejected.");
                    break;
                case 3:
                    registration.setRegistrationStatus(1); // On hold
                    System.out.println("main.Registration put on hold.");
                    break;
                default:
                    System.out.println("Invalid input. main.Registration left as pending.");
                    break;
            }
        }

        System.out.println("All pending registrations have been reviewed.");
    }
*/
}
