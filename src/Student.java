

import java.util.Date;
import java.util.List;

public class Student extends Person {

    private String id;
    private Grade grade;
    private List<Course> courses;
    private Advisor advisor;
    private List<Department> departments;
    private Date startYear;
    private int year;
    private Registration registration;

    public Student() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void prerequisiteFail(List <CourseSection> courseSectionList){
        System.out.println("Prerequisite is fail");
    }

    public void sectionFullFailFail(List <CourseSection> courseSectionList){
        System.out.println("This section is full");
    }

    public void printAdvisorDisapproval(){
        System.out.println("Advisor disapproval");
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Date getStartYear() {
        return startYear;
    }

    public void setStartYear(Date startYear) {
        this.startYear = startYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
