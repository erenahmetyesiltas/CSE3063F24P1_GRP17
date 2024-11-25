package main;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student extends Person {

    private String id;
    private float gpa;
    private List<Course> courses;
    private Advisor advisor;
    private int advisorId; // Retrieving advisorId from database.
    private List<Department> departments;
    private int startYear;
    private int year;
    private Registration registration; // Used as composition/aggregation
    private String registrationId; // Easier to connect with Database

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

    public void sectionFullFail(List <CourseSection> courseSectionList){
        System.out.println("This section is full");
    }

    public void printAdvisorDisapproval(){

        if(registration.getRegistrationStatus() == 0){
            System.out.println("main.Advisor disapproval");
        }

    }

    // getter and setter methods.
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

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
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

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public int getAdvisorId() {
        return advisorId;
    }
}
