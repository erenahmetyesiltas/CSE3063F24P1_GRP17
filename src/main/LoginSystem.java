package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import DataBaseController.StudentDBController;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "CSE3063F24P1_GRP17/src/database/students"; // Kullanıcı dosyalarının olduğu dizin

    private Student student;
    private StudentDBController studentDBController;

    LoginSystem(StudentDBController studentDBController) {
        this.studentDBController = studentDBController;
    }

    /**
     * Authenticates a user (either a student or an advisor) based on their nickname and password.
     *
     * @param studentId The user's nickname (ID).
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    public boolean authenticateUser(String studentId, String password) throws IOException {
        if(studentDBController.loadStudent(studentId)) {
            this.student = studentDBController.getStudent();
        }
        else{
            return false;
        }

        // Student yüklendi, şifreyi karşılaştır.
        if (student.getPassword().equals(password)) {
            return true;
        }
        // Şifre karşılaştırma başarısız, Student Database'ini sıfırla ve false döndür.
        else{
            this.student = null;
            studentDBController.setStudent(null);
            return false;
        }
    }

    // Getter methodu
    public Student getStudent() {
        return student;
    }
}



