package tests;

import DataBaseController.AdvisorDBController;
import DataBaseController.DepartmentSchedulerDBController;
import DataBaseController.StudentDBController;
import main.Advisor;
import main.DepartmentScheduler;
import main.LoginSystem;
import main.Student;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class LoginSystemTest {

    private StudentDBController studentDBController;
    private AdvisorDBController advisorDBController;
    private DepartmentSchedulerDBController departmentSchedulerDBController;
    private LoginSystem loginSystem;

    private Student student;
    private Advisor advisor;
    private DepartmentScheduler departmentScheduler;

    @Before
    public void setUp() {
        // Initialize controllers and objects manually
        studentDBController = new StudentDBController();
        advisorDBController = new AdvisorDBController();
        departmentSchedulerDBController = new DepartmentSchedulerDBController();

        // Initialize Student, Advisor, and DepartmentScheduler with dummy data
        student = new Student();
        student.setId("student123");

        advisor = new Advisor();
        advisor.setId("advisor123");
        advisor.setPassword("advisorPass");

        departmentScheduler = new DepartmentScheduler();
        departmentScheduler.setId("scheduler123");
        departmentScheduler.setPassword("schedulerPass");

        // Initialize LoginSystem
        loginSystem = new LoginSystem(studentDBController, advisorDBController, departmentSchedulerDBController);

        // Add student, advisor, and department scheduler to the respective "databases"
        // Simulate data loading for testing purposes
        studentDBController.setStudent(student);  // Simulate student loading
        advisorDBController.setAdvisor(advisor);  // Simulate advisor loading
        departmentSchedulerDBController.setDepartmentScheduler(departmentScheduler);  // Simulate scheduler loading
    }

    @Test
    public void testAuthenticateStudentFailure() throws IOException {
        // Simulate failed student authentication with incorrect password
        boolean result = loginSystem.authenticateUser("student123", "wrongPass");
        assertFalse("Student authentication should fail with incorrect password", result);
    }

    @Test
    public void testAuthenticateStudentNotFound() throws IOException {
        // Simulate a student who is not in the database
        boolean result = loginSystem.authenticateUser("nonExistingStudent", "studentPass");
        assertFalse("Student authentication should fail for non-existing student", result);
    }

    @Test
    public void testAuthenticateAdvisorFailure() throws IOException {
        // Simulate failed advisor authentication with incorrect password
        boolean result = loginSystem.authenticateAdvisorUser("advisor123", "wrongPass");
        assertFalse("Advisor authentication should fail with incorrect password", result);
    }

    @Test
    public void testAuthenticateAdvisorNotFound() throws IOException {
        // Simulate an advisor who is not in the database
        boolean result = loginSystem.authenticateAdvisorUser("nonExistingAdvisor", "advisorPass");
        assertFalse("Advisor authentication should fail for non-existing advisor", result);
    }

    @Test
    public void testAuthenticateDepartmentSchedulerFailure() throws IOException {
        // Simulate failed department scheduler authentication with incorrect password
        boolean result = loginSystem.authenticateDepartmentSchedulerUser("scheduler123", "wrongPass");
        assertFalse("Department Scheduler authentication should fail with incorrect password", result);
    }

    @Test
    public void testAuthenticateDepartmentSchedulerNotFound() throws IOException {
        // Simulate a department scheduler who is not in the database
        boolean result = loginSystem.authenticateDepartmentSchedulerUser("nonExistingScheduler", "schedulerPass");
        assertFalse("Department Scheduler authentication should fail for non-existing department scheduler", result);
    }
}
