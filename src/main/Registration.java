package main;

import java.util.ArrayList;
import java.util.List;

public class Registration {
    private String id;
    private List<CourseSection> courseSections = new ArrayList<>();
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
            System.out.println("WARNING ! : (Total course number exceeds 5) " + coursesection.getCourse().getId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
            return false;
        }

        if (coursesection != null) {
            for (CourseSection courseSection : courseSections) {
                if (coursesection.getCourse().getId().equals(courseSection.getCourse().getId()) && coursesection.getSectionNumber() == courseSection.getSectionNumber()) {
                    System.out.println("WARNING ! : (Same course and section exists in your registration) " + coursesection.getCourse().getId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
                    return false;
                }
                if (coursesection.getCourse().getId().equals(courseSection.getCourse().getId())) {
                    System.out.println("WARNING ! : (You try to select a second section for the same course) " + coursesection.getCourse().getId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
                    return false;
                }
            }

            courseSections.add(coursesection);
            System.out.println(coursesection.getCourse().getId() + " - " + coursesection.getSectionNumber() + " added to registration");
            return true;
        }
       else {
            System.out.println("WARNING ! : " + coursesection.getCourse().getId() + " - " + coursesection.getSectionNumber() + " cannot added to registration");
            return false;
        }

    }

    public List<CourseSection> getCourseSectionList() {
        return courseSections;
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
