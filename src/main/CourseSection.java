package main;

import java.util.ArrayList;
import java.util.List;

public class CourseSection {

    private List<Student> enrolledStudents = new ArrayList<Student>();
    private Classroom classroom;
    private String id;
    private List<CourseTime> scheduledTimes = new ArrayList<>();
    private int sectionNumber;
    private Course course;
    private int capacity;
    private String courseId;
    private Lecturer lecturer;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseSection() {

    }

    public List<CourseTime> getScheduledTimes() {
        return scheduledTimes;
    }

    public void setScheduledTimes(List<CourseTime> scheduledTime) {
        this.scheduledTimes =  scheduledTime;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getCapacity() {return capacity;}

    public void setCapacity(int capacity) {this.capacity = capacity;}

}
