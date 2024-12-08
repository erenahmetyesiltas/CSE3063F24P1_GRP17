package tests;

import main.CourseSection;
import main.DepartmentScheduler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentSchedulerTest {

    private DepartmentScheduler departmentScheduler;

    @Before
    public void setUp() {
        departmentScheduler = new DepartmentScheduler();
    }

    @Test
    public void testSetAndGetId() {
        String id = "DS001";
        departmentScheduler.setId(id);
        assertEquals("ID should match", id, departmentScheduler.getId());
    }

    @Test
    public void testSetAndGetFirstName() {
        String firstName = "John";
        departmentScheduler.setFirstName(firstName);
        assertEquals("First name should match", firstName, departmentScheduler.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String lastName = "Doe";
        departmentScheduler.setLastName(lastName);
        assertEquals("Last name should match", lastName, departmentScheduler.getLastName());
    }

    @Test
    public void testSetAndGetPassword() {
        String password = "secure123";
        departmentScheduler.setPassword(password);
        assertEquals("Password should match", password, departmentScheduler.getPassword());
    }

    @Test
    public void testSetAndGetDepartmentId() {
        String departmentId = "COMP";
        departmentScheduler.setDepartmentId(departmentId);
        assertEquals("Department ID should match", departmentId, departmentScheduler.getDepartmentId());
    }

    @Test
    public void testSetAndGetCourseSectionList() {
        List<CourseSection> courseSections = new ArrayList<>();
        CourseSection section1 = new CourseSection();
        section1.setId("CS101");
        courseSections.add(section1);

        departmentScheduler.setCourseSectionList(courseSections);

        List<CourseSection> retrievedSections = departmentScheduler.getCourseSectionList();
        assertNotNull("Course section list should not be null", retrievedSections);
        assertEquals("Course section list size should match", 1, retrievedSections.size());
        assertEquals("Course section ID should match", "CS101", retrievedSections.get(0).getId());
    }

    @Test
    public void testAddCourseSectionToList() {
        // Test courseSectionList management directly
        CourseSection section1 = new CourseSection();
        section1.setId("CS101");

        CourseSection section2 = new CourseSection();
        section2.setId("CS102");

        departmentScheduler.getCourseSectionList().add(section1);
        departmentScheduler.getCourseSectionList().add(section2);

        List<CourseSection> sections = departmentScheduler.getCourseSectionList();
        assertEquals("Course section list size should be 2", 2, sections.size());
        assertEquals("First course section ID should be 'CS101'", "CS101", sections.get(0).getId());
        assertEquals("Second course section ID should be 'CS102'", "CS102", sections.get(1).getId());
    }
}
