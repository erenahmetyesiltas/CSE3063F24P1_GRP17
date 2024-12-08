package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StudentTest {

    private Student student;

    @Before
    public void setUp() {
        student = new Student();
    }

    @Test
    public void testSetAndGetId() {
        student.setId("12345");
        assertEquals("12345", student.getId());
    }

    @Test
    public void testSetAndGetGpa() {
        student.setGpa(3.5f);
        assertEquals(3.5f, student.getGpa(), 0.0f);
    }

    @Test
    public void testSetAndGetCourses() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setId("CSE101");
        courses.add(course);

        student.setCourses(courses);
        assertEquals(1, student.getCourses().size());
        assertEquals("CSE101", student.getCourses().get(0).getId());
    }

    @Test
    public void testSetAndGetAdvisor() {
        Advisor advisor = new Advisor();
        advisor.setId("advisor1");

        student.setAdvisor(advisor);
        assertEquals("advisor1", student.getAdvisor().getId());
    }

    @Test
    public void testSetAndGetDepartments() {
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        department.setId("CS");
        departments.add(department);

        student.setDepartments(departments);
        assertEquals(1, student.getDepartments().size());
        assertEquals("CS", student.getDepartments().get(0).getId());
    }

    @Test
    public void testPrerequisiteFail() {
        List<CourseSection> courseSections = new ArrayList<>();
        CourseSection courseSection = new CourseSection();
        courseSection.setId("CS101-1");
        courseSections.add(courseSection);

        student.prerequisiteFail(courseSections);
        // Simply verify the method executes without errors.
    }

    @Test
    public void testSectionFullFail() {
        List<CourseSection> courseSections = new ArrayList<>();
        CourseSection courseSection = new CourseSection();
        courseSection.setId("CS102-1");
        courseSections.add(courseSection);

        student.sectionFullFail(courseSections);
        // Simply verify the method executes without errors.
    }

    @Test
    public void testPrintAdvisorDisapproval() {
        Registration registration = new Registration();
        registration.setRegistrationStatus(0);
        student.setRegistration(registration);

        student.printAdvisorDisapproval();
        // This is a void method with output, so no direct assertion is made.
    }

    @Test
    public void testPrintWeeklyScheduleAsTable_NoCourses() {
        student.printWeeklyScheduleAsTable(student);
        // This is a void method with output, so no direct assertion is made.
    }

    @Test
    public void testPrintWeeklyScheduleAsTable_WithCourses() {
        // Setup Registration
        Registration registration = new Registration();
        List<CourseSection> courseSections = new ArrayList<>();

        // Create a CourseSection with scheduled times
        CourseSection courseSection = new CourseSection();
        courseSection.setId("CS101-1");
        courseSection.setCourseId("CS101");

        CourseTime courseTime = new CourseTime();
        courseTime.setCourseDay("Monday");
        courseTime.setStartTime(Time.valueOf("08:00:00")); // Set start time
        courseTime.setEndTime(Time.valueOf("10:00:00"));   // Set end time

        List<CourseTime> courseTimes = new ArrayList<>();
        courseTimes.add(courseTime);
        courseSection.setScheduledTimes(courseTimes);

        // Create and assign a Classroom
        Classroom classroom = new Classroom();
        classroom.setId("Room101"); // Set classroom ID
        classroom.setCapacity(50);  // Optional: Set classroom capacity
        courseSection.setClassroom(classroom); // Associate classroom with the course section

        // Add the course section to the registration
        courseSections.add(courseSection);
        registration.setCourseSections(courseSections);

        // Assign the registration to the student
        student.setRegistration(registration);

        // Execute the function to test
        student.printWeeklyScheduleAsTable(student);

        // Verify manually if the console output aligns with the schedule setup
    }

}
