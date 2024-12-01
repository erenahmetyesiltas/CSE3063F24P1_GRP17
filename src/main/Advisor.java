package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Advisor extends Lecturer {

//    @JsonProperty("id")
//    private String id;
//
//    @JsonProperty("firstName")
//    private String firstName;
//
//    @JsonProperty("lastName")
//    private String lastName;
//
//    @JsonProperty("password")
//    private String password;

    @JsonProperty
    private List<String> registrationsIDs = new ArrayList<>();

    @JsonProperty
    private List<Integer> supervisedStudentsIDs = new ArrayList<>();

    @JsonIgnore
    private List<Student> supervisedStudents = new ArrayList<>();

    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    // Getters and Setters
    public Advisor() {}

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    // Add-Send a new student registration to advisor. Advisor will check the registration and set status.
    public void addRegistration (Registration registration) {
        // Check If there is a recorded registration of the same student.
        // If there is, remove ex-registration before adding new one.
        checkAndRemoveDuplicateRegistration(registration.getId());

        // Add new registration
        // Every new registration added to the advisor's registration means it's sent for approval, status 1.
        registration.setRegistrationStatus(1);
        this.registrations.add(registration);
        addRegistrationsIDs(registration.getId());
    };

    // If there is a recorded registration of the same student.
    // First remove the ex-registration before uploading new registration.
    public void checkAndRemoveDuplicateRegistration(String registrationID) {
        if (this.registrationsIDs != null) {
            Iterator<String> iterator = this.registrationsIDs.iterator();
            while (iterator.hasNext()) {
                String regID = iterator.next();
                if (regID.equals(registrationID)) {
                    iterator.remove();
                }
            }
        } else {
            System.out.println("registrationsIDs is null!");
        }
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


//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    @Override
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    public List<Student> getSupervisedStudents() {
        return supervisedStudents;
    }

    public void setSupervisedStudents(List<Student> supervisedStudents) {
        this.supervisedStudents = supervisedStudents;
    }

    public List<Integer> getSupervisedStudentsIDs() {
        return supervisedStudentsIDs;
    }

    public List<String> getRegistrationsIDs() {
        return registrationsIDs;
    }
    public void addRegistrationsIDs(String registrationID) {
        this.registrationsIDs.add(registrationID);
    }
}
