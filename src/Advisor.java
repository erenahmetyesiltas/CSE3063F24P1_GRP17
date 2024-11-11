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

        int sizeOfRegistrations = registrations.size();
        registration = new Registration();
        registrations.add(registration);

        if(sizeOfRegistrations + 1 == registrations.size()){
           registration.setRegistrationStatus(2);
        }else if(sizeOfRegistrations == registrations.size()){
           registration.setRegistrationStatus(0);
           // or registration.setRegistrationStatus(1);
        }

    }

}
