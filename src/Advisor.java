import java.util.List;

public class Advisor {
    private List<Student> supervisedStudents;
    private List<Registration> registrations;

    public Advisor() {

    }

    public void addRegistration (Registration registration) {
        registrations.add(registration);
    }
}