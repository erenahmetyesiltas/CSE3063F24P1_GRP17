package main;

import java.util.List;

public class Transcript {

    private String studentID;
    private String studentName;
    private float GPA;
    private int totalCredit;
    private List<Course> coursesTaken;
    private List<String> grades;
    private List<Integer> termOfCourse; // Holds the information on which term the corresponding course was taken

    public Transcript() {
        calculateThings();
    }

    public void calculateThings() {
        totalCredit = 0;
        GPA = 0.0f;

        for (int i = 0; i < grades.size(); i++) {
            totalCredit += coursesTaken.get(i).getCredit(); // calculates total credit

            if (grades.get(i).equalsIgnoreCase("aa"))
                GPA += 4.0f;
            else if (grades.get(i).equalsIgnoreCase("ba"))
                GPA += 3.5f;
            else if (grades.get(i).equalsIgnoreCase("bb"))
                GPA += 3.0f;
            else if (grades.get(i).equalsIgnoreCase("cb"))
                GPA += 2.5f;
            else if (grades.get(i).equalsIgnoreCase("cc"))
                GPA += 2.0f;
            else if (grades.get(i).equalsIgnoreCase("dc"))
                GPA += 1.5f;
            else if (grades.get(i).equalsIgnoreCase("dd"))
                GPA += 1.0f;
            else if (grades.get(i).equalsIgnoreCase("fd"))
                GPA += 0.5f;
            else if (grades.get(i).equalsIgnoreCase("ff"))
                GPA += 0.0f;
        }
        GPA /= grades.size();

    }

    public void addCourseTaken(Course course, String grade, Integer term) {

        coursesTaken.add(course);
        grades.add(grade);
        termOfCourse.add(term);

        calculateThings();


    }

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
        return grades;
    }
    public void setGrade(List<String> grade) {
        this.grades = grade;
    }
    public List<Integer> getTermOfCourse() {
        return termOfCourse;
    }
    public void setTermOfCourse(List<Integer> termOfCourse) {
        this.termOfCourse = termOfCourse;
    }
}
