package login;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "src/login/users";

    // CALL CONSTRUCTOR WITH PERSON. LoginSystem(Person), and create an instance of person to use in Object Mapper.
    public boolean authenticateUser(String nickname, String password) throws IOException {
        File userFile = new File(USERS_DIRECTORY + "/" + nickname + ".json");
        if (!userFile.exists()) {
            System.out.println("ASOPJDPSA");
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(userFile, Person.class);
        System.out.println("Name: " + person.getFirstName());
        System.out.println("Age: " + person.getId());

        return person.getPassword().equals(password);
    }
}

