import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class StudentTest {

    private DataHandler dataHandler;
    private Student student;

    @Before
    public void setUp() throws IOException {
        // Initialize DataHandler to load data
        dataHandler = new DataHandler();

        // Retrieve all data and find the first Student instance for testing
        List<SystemData> allData = dataHandler.getAllDatas();
        for (SystemData data : allData) {
            if (data.getObjectClass() == Student.class) {
                student = dataHandler.getObjectMapper().convertValue(data.getObject(), Student.class);
                break;
            }
        }

        // Ensure a Student object was loaded for testing
        assertNotNull("No Student object found in data", student);
    }

    @Test
    public void getPassword() {
        // Assuming password is inherited from Person
        // Note: If password is private and inaccessible, you'll need reflection to set it for the test
        assertNotNull("Expected non-null password", student.getPassword());
    }

    @Test
    public void getId() {
        assertNotNull("Expected non-null ID", student.getId());
    }

    @Test
    public void setId() {
        String newId = "654321";
        student.setId(newId);
        assertEquals(newId, student.getId());
    }

    @Test
    public void prerequisiteFail() {
        // Simply test that the method runs without throwing an exception
        student.prerequisiteFail(null);
    }

    @Test
    public void sectionFullFailFail() {
        // Simply test that the method runs without throwing an exception
        student.sectionFullFail(null);
    }

    @Test
    public void printAdvisorDisapproval() {
        student.getRegistration().setRegistrationStatus(0);
        // Test to ensure no exceptions or issues occur when this method is called
        student.printAdvisorDisapproval();
    }

    @Test
    public void setGrade() {
        Grade newGrade = new Grade();
        student.setGrade(newGrade);
        assertEquals(newGrade, student.getGrade());
    }

    @Test
    public void getCourses() {
        assertNotNull("Expected non-null courses list", student.getCourses());
    }

    @Test
    public void setCourses() {
        List<Course> courses = List.of(new Course());
        student.setCourses(courses);
        assertEquals(courses, student.getCourses());
    }

    @Test
    public void getAdvisor() {
        assertNotNull("Expected non-null advisor", student.getAdvisor());
    }

    @Test
    public void setAdvisor() {
        Advisor newAdvisor = new Advisor();
        student.setAdvisor(newAdvisor);
        assertEquals(newAdvisor, student.getAdvisor());
    }


    @Test
    public void setDepartments() {
        List<Department> departments = List.of(new Department());
        student.setDepartments(departments);
        assertEquals(departments, student.getDepartments());
    }

    @Test
    public void setStartYear() {
        int newStartYear = 2020;
        student.setYear(newStartYear);
        assertEquals(newStartYear, student.getYear());
    }


    @Test
    public void getYear() {
        assertTrue("Expected valid year", student.getYear() > 0);
    }

    @Test
    public void setYear() {
        int newYear = 2021;
        student.setYear(newYear);
        assertEquals(newYear, student.getYear());
    }

    @Test
    public void getRegistration() {
        assertNotNull("Expected non-null registration", student.getRegistration());
    }

    @Test
    public void setRegistration() {
        Registration newRegistration = new Registration();
        student.setRegistration(newRegistration);
        assertEquals(newRegistration, student.getRegistration());
    }
}