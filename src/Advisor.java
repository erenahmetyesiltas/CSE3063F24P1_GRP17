import java.util.ArrayList;
import java.util.List;

public class Advisor extends Person {
    private String id;
    private List<Student> supervisedStudents;
    private List<Registration> registrations;

    // Getters and Setters
    public Advisor() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addRegistration (Registration registration) {
        registrations.add(registration);

    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setStatus(Registration registration, int status) {
        registration.setRegistrationStatus(status);
        for (Registration reg : registrations) {
            if (reg.getId().equals(registration.getId())) {
                reg.setRegistrationStatus(status);
            }
        }
    }


}
