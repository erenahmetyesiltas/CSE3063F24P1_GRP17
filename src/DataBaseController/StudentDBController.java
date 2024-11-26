package DataBaseController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.*;
public class StudentDBController implements DatabaseController {
    private static final String USERS_DIRECTORY = "CSE3063F24P1_GRP17/src/database/"; // Kullanıcı dosyalarının olduğu dizin

    private Student student;


    public boolean loadStudent(String studentId) throws IOException{
        // Belirtilen ID'ye ait dosyanın yolunu oluştur.
        String filePath = USERS_DIRECTORY +"students"+ File.separator + studentId + ".json";
        File studentFile = new File(filePath);

        // Dosya mevcut mu kontrol et.
        if (!studentFile.exists()) {
            return false; // Eğer dosya yoksa doğrulama başarısız.
        }

        // Dosyayı oku ve JSON'dan Student nesnesine dönüştür.
        ObjectMapper objectMapper = new ObjectMapper();
        Student storedStudent = null;

        try {
        storedStudent = objectMapper.readValue(Files.readString(Paths.get(filePath)), Student.class);

        } catch (IOException e) {
           throw new RuntimeException(e);
        }
        setStudent(storedStudent);

        // If registration exist, load it.
        loadStudentRegistration(studentId);
        return true;

    }

    public void loadStudentRegistration(String studentId) throws IOException{
        String filePath = USERS_DIRECTORY +"registrations"+File.separator +"r"+studentId + ".json";
        File studentFile = new File(filePath);

        // Dosya mevcut mu kontrol et.
        if (!studentFile.exists()) {
            return; // Eğer dosya yoksa doğrulama başarısız.
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Registration registration = objectMapper.readValue(Files.readString(Paths.get(filePath)), Registration.class);
        this.student.setRegistration(registration);
    };


    // Login sonrası Student nesnesini ayarlar
    public void setStudent(Student student) {
        this.student = student;
    }

    // Student nesnesini döner
    public Student getStudent() {
        return student;
    }

    // Öğrencinin kayıtlı derslerini döner
    public List<Course> getStudentCourses() {
        return getStudent() != null ? student.getCourses() : null;
    }

    // Öğrencinin GPA'sini döner
    public double getStudentGPA() {
        return getStudent() != null ? getStudent().getGpa() : 0.0;
    }

    // Öğrencinin danışman ID'sini döner
    public int getAdvisorId() {
        return getStudent() != null ? getStudent().getAdvisorId() : -1;
    }

    // Öğrencinin kayıt olduğu yılı döner
    public int getStartYear() {
        return getStudent() != null ? getStudent().getStartYear() : -1;
    }

    // Öğrencinin bulunduğu yılı döner
    public int getYear() {
        return getStudent() != null ? getStudent().getYear() : -1;
    }

    // Öğrencinin bölümlerini döner
    public List<Department> getDepartments() {
        return getStudent() != null ? getStudent().getDepartments() : null;
    }
}

