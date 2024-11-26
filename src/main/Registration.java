package main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Registration {
    private String id;
    private List<CourseSection> courseSections = new ArrayList<>();

    /*
    registrationStatus:
    0, means advisor has not approved.
    1, means advisor has not checked yet.
    2, means advisor has approved.
     */
    private int registrationStatus;

    public Registration() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean addCourseSection(CourseSection coursesection) {
        if (courseSections.size() >= 5) {
            System.out.println("WARNING ! : (Total course number exceeds 5) " + coursesection.getCourseId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
            return false;
        }

        if (coursesection != null) {
            for (CourseSection courseSection : courseSections) {
                if (coursesection.getCourseId().equals(courseSection.getCourseId()) && coursesection.getSectionNumber() == courseSection.getSectionNumber()) {
                    System.out.println("WARNING ! : (Same course and section exists in your registration) " + coursesection.getCourseId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
                    return false;
                }
                if (coursesection.getCourseId().equals(courseSection.getCourseId())) {
                    System.out.println("WARNING ! : (You try to select a second section for the same course) " + coursesection.getCourseId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
                    return false;
                }
            }

            courseSections.add(coursesection);
            System.out.println(coursesection.getCourseId() + " - " + coursesection.getSectionNumber() + " added to registration");
            return true;
        }
       else {
            System.out.println("WARNING ! : " + coursesection.getCourseId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
            return false;
        }

    }

    public List<CourseSection> getCourseSections() {
        return courseSections;
    }

    public void setCourseSections(List<CourseSection> courseSections) {
        this.courseSections = courseSections;
    }

    public void deleteCourseSectionList() {
        courseSections.clear();
    }

    public int getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(int status) {
        registrationStatus = status;
    }


    /*Iteration 2
    public main.Student getStudent() {
        return student;
    }

     */
}
