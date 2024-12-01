package main;

import java.util.Date;
import java.util.List;

public class Course {
    private String id;
    private int credit;
    private List<Department> departments;
    private int term;
    private List<Course> prerequisiteCourses;
    private String name;

    public Course() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }



    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }



    public List<Course> getPrerequisiteCourses() {
        return prerequisiteCourses;
    }

    public void setPrerequisiteCourses(List<Course> prerequisiteCourses) {
        this.prerequisiteCourses = prerequisiteCourses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public List<main.CourseSection> getCourseSection() {
        //return courseSections;
    //}

    //public void setCourseSection(List<main.CourseSection> courseSection) {
        //this.courseSections = courseSection;
    //}

}


