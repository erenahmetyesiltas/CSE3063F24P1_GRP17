package tests;

import main.Advisor;
import main.Registration;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AdvisorTest {

    private Advisor advisor;

    @Before
    public void setUp() {
        advisor = new Advisor();
    }

    @Test
    public void testSetAndGetId() {
        String id = "123";
        advisor.setId(id);
        assertEquals("ID should match", id, advisor.getId());
    }

    @Test
    public void testAddRegistration() {
        Registration registration = new Registration();
        advisor.addRegistration(registration);

        // Verify that the registration is added and status is set to 1
        List<Registration> registrations = advisor.getRegistrations();
        assertEquals(1, registrations.size());
        assertEquals(1, registrations.get(0).getRegistrationStatus());
    }

    @Test
    public void testSetAndGetFirstName() {
        String firstName = "John";
        advisor.setFirstName(firstName);
        assertEquals("First name should match", firstName, advisor.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        String lastName = "Doe";
        advisor.setLastName(lastName);
        assertEquals("Last name should match", lastName, advisor.getLastName());
    }

    @Test
    public void testSetAndGetPassword() {
        String password = "securepassword";
        advisor.setPassword(password);
        assertEquals("Password should match", password, advisor.getPassword());
    }
}
