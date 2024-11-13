package tests;

import main.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LoginSystemTest {

    private LoginSystem loginSystem;
    private CourseRegistrationSystem system;
    private DataHandler dataHandler;

    @Before
    public void setUp() throws IOException {
        // Initialize main.LoginSystem and main.CourseRegistrationSystem
        loginSystem = new LoginSystem();
        system = new CourseRegistrationSystem();
        dataHandler = new DataHandler();

        // Retrieve data from main.DataHandler and populate system
        List<SystemData> allData = dataHandler.getAllDatas();

        for (SystemData data : allData) {
            if (data.getObjectClass() == Student.class) {
                Student student = dataHandler.getObjectMapper().convertValue(data.getObject(), Student.class);
                system.getAllStudents().add(student);
            } else if (data.getObjectClass() == Advisor.class) {
                Advisor advisor = dataHandler.getObjectMapper().convertValue(data.getObject(), Advisor.class);
                system.getAllAdvisors().add(advisor);
            }
        }
    }

    @Test
    public void authenticateUser_validStudentCredentials() throws IOException {
        boolean isAuthenticated = loginSystem.authenticateUser("150122011", "melisa123", system);
        assertTrue(isAuthenticated);
        assertNotNull("Expected a student to be authenticated", loginSystem.getStudent());
        assertNull("Expected no advisor to be authenticated", loginSystem.getAdvisor());
    }

    @Test
    public void authenticateUser_validAdvisorCredentials() throws IOException {
        boolean isAuthenticated = loginSystem.authenticateUser("A12345", "StrongPassword@123", system);
        assertTrue(isAuthenticated);
        assertNotNull("Expected an advisor to be authenticated", loginSystem.getAdvisor());
        assertNull("Expected no student to be authenticated", loginSystem.getStudent());
    }

    @Test
    public void authenticateUser_invalidCredentials() throws IOException {
        boolean isAuthenticated = loginSystem.authenticateUser("unknown", "wrongpass", system);
        assertFalse(isAuthenticated);
        assertNull("Expected no student to be authenticated", loginSystem.getStudent());
        assertNull("Expected no advisor to be authenticated", loginSystem.getAdvisor());
    }

    @Test
    public void getStudent_afterSuccessfulStudentLogin() throws IOException {
        loginSystem.authenticateUser("150122011", "melisa123", system);
        assertNotNull("Expected a student to be authenticated", loginSystem.getStudent());
    }

    @Test
    public void getAdvisor_afterSuccessfulAdvisorLogin() throws IOException {
        loginSystem.authenticateUser("A12345", "StrongPassword@123", system);
        assertNotNull("Expected an advisor to be authenticated", loginSystem.getAdvisor());
    }

    @Test
    public void getStudent_withoutLogin() {
        assertNull("Expected no student to be authenticated", loginSystem.getStudent());
    }

    @Test
    public void getAdvisor_withoutLogin() {
        assertNull("Expected no advisor to be authenticated", loginSystem.getAdvisor());
    }
}
