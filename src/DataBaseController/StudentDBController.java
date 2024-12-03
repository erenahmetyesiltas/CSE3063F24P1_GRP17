package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.Registration;
import main.Student;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class StudentDBController implements DatabaseController {
    private static final String USERS_DIRECTORY = "out/production/project/database/";
    private Student student;

    public StudentDBController() {
    }

    public boolean loadStudent(String studentId) throws IOException {
        URL studentJsonPath = StudentDBController.class.getClassLoader().getResource("database/students/" + studentId + ".json");

        if (studentJsonPath == null) {
            return false;
        }

        File studentFile = new File(studentJsonPath.getFile());

        if (!studentFile.exists()) {
            return false;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Student storedStudent;

        try {
            storedStudent = objectMapper.readValue(studentFile, Student.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setStudent(storedStudent);

        // If registration exists, load it
        loadStudentRegistration(studentId);
        return true;
    }

    public void saveStudent(String studentId) throws IOException {
        URL studentJsonPath = StudentDBController.class.getClassLoader().getResource("database/students/" + studentId + ".json");

        if (studentJsonPath == null) {
            throw new IOException("Student JSON path not found");
        }

        File studentFile = new File(studentJsonPath.getFile());

        if (this.student == null) {
            throw new IllegalStateException("Student is null, cannot save.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(studentFile, student);
    }

    public void loadStudentRegistration(String studentId) throws IOException {
        URL registrationJsonPath = StudentDBController.class.getClassLoader().getResource("database/registrations/r" + studentId + ".json");

        if (registrationJsonPath == null) {
            return;
        }

        File registrationFile = new File(registrationJsonPath.getFile());

        if (!registrationFile.exists()) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Registration registration = objectMapper.readValue(registrationFile, Registration.class);
        this.student.setRegistration(registration);
    }

    public void saveStudentRegistration(String studentId) throws IOException {
        // Locate the registration JSON path
        URL registrationJsonPath = StudentDBController.class.getClassLoader().getResource("database/registrations/r" + studentId + ".json");

        if (registrationJsonPath == null) {
            throw new IOException("Registration JSON path not found");
        }

        File registrationFile = new File(registrationJsonPath.getFile());

        Registration registration = this.student.getRegistration();
        if (registration == null) {
            throw new IllegalStateException("Student registration is null, cannot save.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(registrationFile, registration);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
