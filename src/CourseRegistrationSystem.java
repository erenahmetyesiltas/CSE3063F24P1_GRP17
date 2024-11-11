

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
       System.out.println("Enter the course");
       String courseSection =scanner.next();

       if (findCourseSection(course,courseSection) != null) {
           return student.getRegistration().addCourseSection((findCourseSection(course,courseSection)));
       }
       else {
           System.out.println("WARNING: Course cannot find in available courses");
           return false;
       }


   }



}
