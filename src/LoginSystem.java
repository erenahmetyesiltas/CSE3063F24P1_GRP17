import java.io.IOException;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "src/login/users";

    private Student student;
    private Advisor advisor;

    // CALL CONSTRUCTOR WITH PERSON. LoginSystem(Person), and create an instance of person to use in Object Mapper.
    public boolean authenticateUser(String nickname, String password, CourseRegistrationSystem system) throws IOException {
        for (Student student : system.getAllStudents()) {
            if (student.getId().equals(nickname) && student.getPassword().equals(password)) {
                this.student = student;
            }
        }

        for (Advisor advisor : system.getAllAdvisors()) {
            if (advisor.getId().equals(nickname) && advisor.getPassword().equals(password)) {
                this.advisor = advisor;
            }
        }

        if (student != null) {
            return student.getPassword().equals(password);
        }
        else {
            return advisor.getPassword().equals(password);
        }


    }

    public Student getStudent() {
        return student;
    }
    public Advisor getAdvisor() {
        return advisor;
    }
}

