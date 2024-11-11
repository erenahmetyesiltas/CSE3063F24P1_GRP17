

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

    public CourseRegistrationSystem() throws IOException {
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

    public void restoreSystem() throws NoSuchElementException {
        int i = 0;
        List<SystemData> allDatas = dataHandler.getAllDatas();
        ObjectMapper mapper = dataHandler.getObjectMapper();

        while (i != (allDatas.size())) {
            Class objectClass = allDatas.get(i).getObjectClass();
            Object object = allDatas.get(i).getObject();

            if (objectClass == Student.class) {
                Student student = mapper.convertValue(object, Student.class);
                this.getAllStudents().add(student);
            }

            if (objectClass == CourseSection.class) {
                CourseSection course = mapper.convertValue(object, CourseSection.class);
                this.getAllCourseSections().add(course);
            }

            if (objectClass == Advisor.class) {
                Advisor advisor = mapper.convertValue(object, Advisor.class);
                this.getAllAdvisors().add(advisor);
            }

            i++;
        }
    }

    public void updateData(Object object) {
        SystemData data = new SystemData(object, object.getClass(), "object" + dataHandler.getFileNumber() + ".json");
        dataHandler.getAllDatas().add(data);
        dataHandler.removeDuplicateData();
    }

    public void printSuitableCourses() {
        System.out.println("\nThe available courses that you can register\n");
        System.out.println("Course :               Section :");
        for (CourseSection courseSection : this.getAllCourseSections()) {
            Course currentCourse = courseSection.getCourse();

            System.out.printf("%-21s%-10d", currentCourse.getId(), courseSection.getSectionNumber());
            System.out.println();
        }
    }
    // addCourseSection is added to Student, change it in DSD and SSD
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
       System.out.println("Enter the course");
       String course = scanner.next();
       System.out.println("Enter the section");
       String courseSection =scanner.next();

       if (findCourseSection(course,courseSection) != null) {
           return student.getRegistration().addCourseSection((findCourseSection(course,courseSection)));
       }
       else {
           System.out.println("WARNING: Course cannot find in available courses");
           return false;
       }


   }

    public void sendRegistrationToAdvisor(Registration registration, Student student){
        // an advisor is needs
        Advisor advisor = student.getAdvisor();
        advisor.addRegistration(registration);

        for (Advisor advisor1 : this.getAllAdvisors()) {
            if (advisor1.getId().equals(advisor.getId())) {
                advisor1.addRegistration(registration);
            }
        }
    }

    public void studentEnrollRequest(Student student){

        // Regist. and Adv. got from Student
        Registration registration = student.getRegistration();

        switch (registration.getRegistrationStatus()) {
            case 0 :
                System.out.println("WARNING : Your advisor has disapproved your registration, you have to create a new one and send it again\n");
                student.getAdvisor().getRegistrations().remove(student.getRegistration());
                // allAdvisorstan da remove et
                student.setRegistration(null);
                break;
            case 1:
                System.out.println("WARNING : Your advisor has not yet checked your registration\n");
                break;
            case 2 :
                System.out.println("SUCCESS : Your advisor has approved your registration, you will be enrolled to the courses you \n");
                enrollStudent(registration.getCourseSectionList(), student);
                break;
        }

    }

    public void enrollStudent(List<CourseSection> enrollCourseSections, Student student){
        for (CourseSection courseSection : allCourseSections) {
            for (CourseSection enrollCourseSection : enrollCourseSections) {
                if (enrollCourseSection.getCourse().getId().equals(courseSection.getCourse().getId())) {
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



    // public void answerRegistrationRequests(Advisor advisor)
    // Advisor girişi için gelen registration requestlerini istediği zaman konsola basacak bir fonksiyon, sıra sıra requestleri basacak ve advisora soracak onay mı ret mi beklet mi diye
    // Main'de system.answerRegistrationRequests(advisor) olarak call olacak

    /*
    DUE TO A BUG IN REGISTRATIONS, IT WILL BE IMPLEMENTED IN ITERATION 2
    public void answerRegistrationRequests(Advisor advisor) {
        Scanner scanner = new Scanner(System.in);
        List<Registration> pendingRegistrations = advisor.getRegistrations();

        if (pendingRegistrations.isEmpty()) {
            System.out.println("No registration requests to review.");
            return;
        }

        for (Registration registration : pendingRegistrations) {
            System.out.println("Reviewing registration for student: " + registration.getStudent().getFirstName() + registration.getStudent().getFirstName());
            System.out.println("Requested courses:");

            for (CourseSection courseSection : registration.getCourseSectionList()) {
                System.out.println("Course ID: " + courseSection.getCourse().getId() +
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
                    System.out.println("Registration approved.");
                    break;
                case 2:
                    registration.setRegistrationStatus(0); // Rejected
                    System.out.println("Registration rejected.");
                    break;
                case 3:
                    registration.setRegistrationStatus(1); // On hold
                    System.out.println("Registration put on hold.");
                    break;
                default:
                    System.out.println("Invalid input. Registration left as pending.");
                    break;
            }
        }

        System.out.println("All pending registrations have been reviewed.");
    }
*/
}
