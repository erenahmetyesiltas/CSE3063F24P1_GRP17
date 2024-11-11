import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class RegistrationTest {

    private Registration registration;

    @Before
    public void setUp() {
        registration = new Registration();
    }

    @Test
    public void getId() {
        registration.setId("12345");
        assertEquals("12345", registration.getId());
    }

    @Test
    public void setId() {
        registration.setId("67890");
        assertEquals("67890", registration.getId());
    }

    @Test
    public void addCourseSection() {
        // Creating a Course and CourseSection object using the provided class structure
        Course course = new Course();
        course.setId("MATH101");

        CourseSection section1 = new CourseSection();
        section1.setCourse(course);
        section1.setSectionNumber(1);

        boolean result = registration.addCourseSection(section1);
        assertTrue(result);

        List<CourseSection> courseSections = registration.getCourseSectionList();
        assertEquals(1, courseSections.size());
        assertEquals(section1, courseSections.get(0));

        // Adding more than 5 sections to check limit enforcement
        for (int i = 2; i <= 6; i++) {
            Course additionalCourse = new Course();
            additionalCourse.setId("COURSE" + i);
            CourseSection additionalSection = new CourseSection();
            additionalSection.setCourse(additionalCourse);
            additionalSection.setSectionNumber(i);
            registration.addCourseSection(additionalSection);
        }
        CourseSection section6 = new CourseSection();
        section6.setCourse(course);
        section6.setSectionNumber(6); // Same course ID to test duplicate prevention
        boolean exceedsLimit = registration.addCourseSection(section6);
        assertFalse(exceedsLimit);

        // Adding a duplicate course section
        boolean duplicate = registration.addCourseSection(section1);
        assertFalse(duplicate);
    }

    @Test
    public void getCourseSectionList() {
        Course course = new Course();
        course.setId("CSE2225");
        CourseSection section = new CourseSection();
        section.setCourse(course);
        section.setSectionNumber(2);
        registration.addCourseSection(section);

        List<CourseSection> sections = registration.getCourseSectionList();
        assertEquals(1, sections.size());
        assertEquals(section, sections.get(0));
    }

    @Test
    public void deleteCourseSectionList() {
        Course course = new Course();
        course.setId("CSE2225");
        CourseSection section = new CourseSection();
        section.setCourse(course);
        section.setSectionNumber(3);
        registration.addCourseSection(section);

        registration.deleteCourseSectionList();
        assertTrue(registration.getCourseSectionList().isEmpty());
    }

    @Test
    public void getRegistrationStatus() {
        registration.setRegistrationStatus(1);
        assertEquals(1, registration.getRegistrationStatus());
    }

    @Test
    public void setRegistrationStatus() {
        registration.setRegistrationStatus(2);
        assertEquals(2, registration.getRegistrationStatus());
    }
}