package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Person {

    @JsonProperty("id")
    private String id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("password")
    private String password;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addRegistration (Registration registration) {
        registration.setRegistrationStatus(1);
        registrations.add(registration);
        addRegistrationsIDs(registration.getId());
    };

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


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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
