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
    private List<String> departmentIds;
    private int startYear;
    private int year;
    private Registration registration = new Registration(); // Used as composition/aggregation
    private String registrationId; // Easier to connect with Database
    private int term;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
    private Transcript transcript; // New Transcript field

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

    public void printWeeklyScheduleAsTable(Student student) {
        if (student.getRegistration() == null || student.getRegistration().getCourseSections().isEmpty()) {
            System.out.println("No courses registered for this student.");
            return;
        }

        // Define table headers and time slots (1-hour intervals)
        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        String[] timeSlots = {
                "08:00 - 09:00", "09:00 - 10:00",
                "10:00 - 11:00", "11:00 - 12:00",
                "12:00 - 13:00", "13:00 - 14:00",
                "14:00 - 15:00", "15:00 - 16:00",
                "16:00 - 17:00", "17:00 - 18:00"
        };

        // Fixed column width
        int columnWidth = 25;

        // Initialize a 2D array for the table
        String[][] scheduleTable = new String[timeSlots.length][days.length];

        // Fill the table with empty strings
        for (int i = 0; i < timeSlots.length; i++) {
            for (int j = 0; j < days.length; j++) {
                scheduleTable[i][j] = ""; // Empty cells
            }
        }

        // Populate the table with course data
        for (CourseSection section : student.getRegistration().getCourseSections()) {
            for (CourseTime time : section.getScheduledTimes()) {
                String courseDay = time.getCourseDay();
                String startTime = time.getStartTime().toString();
                String endTime = time.getEndTime().toString();

                // Find the start and end indexes for the time slots
                int startIndex = -1;
                int endIndex = -1;
                for (int i = 0; i < timeSlots.length; i++) {
                    if (timeSlots[i].startsWith(startTime.substring(0, 5))) {
                        startIndex = i;
                    }
                    if (timeSlots[i].endsWith(endTime.substring(0, 5))) {
                        endIndex = i;
                    }
                }

                // Find the index for the day
                int dayIndex = -1;
                for (int j = 0; j < days.length; j++) {
                    if (days[j].equalsIgnoreCase(courseDay)) {
                        dayIndex = j;
                    }
                }

                // Populate the scheduleTable for all time slots within the range
                if (startIndex != -1 && endIndex != -1 && dayIndex != -1) {
                    for (int i = startIndex; i <= endIndex; i++) {
                        scheduleTable[i][dayIndex] = String.format("%s (%s)", section.getCourseId(), section.getClassroom().getId());
                    }
                }
            }
        }

        // Print table headers
        System.out.println("Weekly Schedule for Student ID: " + student.getId());
        System.out.printf("%-15s", "Time");
        for (String day : days) {
            System.out.printf("| %-"+columnWidth+"s", day);
        }
        System.out.println();
        System.out.println("-".repeat(15 + (columnWidth + 3) * days.length));

        // Print the table content
        for (int i = 0; i < timeSlots.length; i++) {
            System.out.printf("%-15s", timeSlots[i]); // Print time slot
            for (int j = 0; j < days.length; j++) {
                System.out.printf("| %-"+columnWidth+"s", scheduleTable[i][j]); // Print course and classroom in one line
            }
            System.out.println();
            System.out.println("-".repeat(15 + (columnWidth + 3) * days.length));
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

    public List<String> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<String> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public void setAdvisorId(int advisorId) {
        this.advisorId = advisorId;
    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }
}
