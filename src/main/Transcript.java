package main;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Add this annotation

public class Transcript {
    private List<CourseRecord> courseRecords = new ArrayList<>();
    private int totalCredit = 0;
    private float GPA = 0.0f;

    // Constructor
    public Transcript() {}

    public Transcript(List<CourseRecord> courseRecords, int totalCredit, float GPA) {
        this.courseRecords = courseRecords;
        this.totalCredit = totalCredit;
        this.GPA = GPA;
    }

    // Nested static class to represent a course record
    public static class CourseRecord {
        private Course course;
        private String grade;
        private int term;

        // Constructors
        public CourseRecord() {}

        public CourseRecord(Course course, String grade, int term) {
            if (course == null || grade == null || term <= 0) {
                throw new IllegalArgumentException("Invalid course, grade, or term.");
            }
            this.course = course;
            this.grade = grade;
            this.term = term;
        }

        // Getters and setters
        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public int getTerm() {
            return term;
        }

        public void setTerm(int term) {
            this.term = term;
        }
    }

    // Add a course record to the transcript
    public void addCourseTaken(Course course, String grade, int term) {
        courseRecords.add(new CourseRecord(course, grade, term));
        calculateGPA();
    }

    // Calculate GPA and total credits
    private void calculateGPA() {
        totalCredit = 0;
        GPA = 0.0f;

        for (CourseRecord record : courseRecords) {
            totalCredit += record.getCourse().getCredit();

            switch (record.getGrade().toUpperCase()) {
                case "AA": GPA += 4.0f * record.getCourse().getCredit(); break;
                case "BA": GPA += 3.5f * record.getCourse().getCredit(); break;
                case "BB": GPA += 3.0f * record.getCourse().getCredit(); break;
                case "CB": GPA += 2.5f * record.getCourse().getCredit(); break;
                case "CC": GPA += 2.0f * record.getCourse().getCredit(); break;
                case "DC": GPA += 1.5f * record.getCourse().getCredit(); break;
                case "DD": GPA += 1.0f * record.getCourse().getCredit(); break;
                case "FD": GPA += 0.5f * record.getCourse().getCredit(); break;
                case "FF": GPA += 0.0f; break;
            }
        }

        if (totalCredit > 0) {
            GPA /= totalCredit;
        }
    }


// Print transcript with proper alignment
    public void printTranscript(String studentName, String studentID) {
        System.out.println("Transcript for " + studentName + " (" + studentID + ")");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-10s %-5s\n", "Term", "Course", "Credit", "Grade");

        for (CourseRecord record : courseRecords) {
            System.out.printf("%-10d %-40s %-10d %-5s\n",
                    record.getTerm(),
                    record.getCourse().getName(),
                    record.getCourse().getCredit(),
                    record.getGrade());
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Credits: " + totalCredit);
        System.out.println("GPA: " + GPA);
    }


    // Getters and setters
    public List<CourseRecord> getCourseRecords() {
        return courseRecords;
    }

    public void setCourseRecords(List<CourseRecord> courseRecords) {
        this.courseRecords = courseRecords;
        calculateGPA();
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public float getGPA() {
        return GPA;
    }

    public void setGPA(float GPA) {
        this.GPA = GPA;
    }
}
