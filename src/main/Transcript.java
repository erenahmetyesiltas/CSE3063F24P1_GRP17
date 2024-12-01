package main;

import java.util.List;

public class Transcript {

    private String studentID;
    private String studentName;
    private float GPA;
    private int totalCredit;
    private List<Course> coursesTaken;
    private List<String> grade;
    private List<Integer> termOfCourse; // Holds the information on which term the corresponding course was taken
    private List<Integer> yearOfCourse; // Holds the information on which year the corresponding course was taken

    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public float getGPA() {
        return GPA;
    }
    public void setGPA(float GPA) {
        this.GPA = GPA;
    }
    public int getTotalCredit() {
        return totalCredit;
    }
    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }
    public List<Course> getCoursesTaken() {
        return coursesTaken;
    }
    public void setCoursesTaken(List<Course> coursesTaken) {
        this.coursesTaken = coursesTaken;
    }
    public List<String> getGrade() {
        return grade;
    }
    public void setGrade(List<String> grade) {
        this.grade = grade;
    }
    public List<Integer> getTermOfCourse() {
        return termOfCourse;
    }
    public void setTermOfCourse(List<Integer> termOfCourse) {
        this.termOfCourse = termOfCourse;
    }
    public List<Integer> getYearOfCourse() {
        return yearOfCourse;
    }
    public void setYearOfCourse(List<Integer> yearOfCourse) {
        this.yearOfCourse = yearOfCourse;
    }
}
