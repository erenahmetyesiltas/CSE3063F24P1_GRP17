package main;

import java.io.IOException;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "src/login/users";

    private Student student;
    private Advisor advisor;

    //
    /**
     * Authenticates a user (either a student or an advisor) based on their nickname and password.
     *
     * @param nickname The user's nickname (ID).
     * @param password The user's password.
     * @param system   The course registration system containing all users.
     * @return true if authentication is successful, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    public boolean authenticateUser(String nickname, String password, CourseRegistrationSystem system) throws IOException {
        // Iterate over all students in the system and check for matching credentials.
        for (Student student : system.getAllStudents()) {
            if (student.getId().equals(nickname) && student.getPassword().equals(password)) {
                this.student = student; // Assign authenticated student to the instance variable.
                return true; // Credentials matched; return true immediately.
            }
        }

        // Iterate over all advisors in the system and check for matching credentials.
        for (Advisor advisor : system.getAllAdvisors()) {
            if (advisor.getId().equals(nickname) && advisor.getPassword().equals(password)) {
                this.advisor = advisor; // Assign authenticated advisor to the instance variable.
                return true; // Credentials matched; return true immediately.
            }
        }

        // If no match found, return false.
        return false;
    }
   // Getter methods
    public Student getStudent() {
        return student;
    }
    public Advisor getAdvisor() {
        return advisor;
    }
}



