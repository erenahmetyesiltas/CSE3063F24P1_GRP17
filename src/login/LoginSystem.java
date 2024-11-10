package login;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "src/login/users";
    Student student;

    public boolean authenticateStudent(String nickname, String password) throws IOException {
        File userFile = new File(USERS_DIRECTORY + "/" + nickname + ".json");
        if (!userFile.exists()) {
            System.out.println("FILE DOESN'T EXIST");
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        Student jsonStudent = mapper.readValue(userFile, Student.class);

        if(jsonStudent.getPassword().equals(password)){
            this.student = jsonStudent;
            return true;
        }
        return false;
    }
    public Student getStudent(){
    return this.student;
    }
}

