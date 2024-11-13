package DataBaseController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import main.*;

public class StudentDBController {

        public static void main(String[] args) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                // Read JSON file and map to Student object
                Student student = objectMapper.readValue(new File("src/database/students/150123823.json"), Student.class);

                // Print student details to verify
                System.out.println("Student ID: " + student.getId());
                System.out.println("First Name: " + student.getFirstName());
                System.out.println("Last Name: " + student.getLastName());
                System.out.println("GPA: " + student.getGpa());
                System.out.println("Advisor ID: " + student.getAdvisorId());
                System.out.println("Start year : " + student.getStartYear());
                System.out.println("Year : " + student.getYear());
                System.out.println("Registration ID : " + student.getRegistrationId());

                // List departments
                for (Department department : student.getDepartments()) {
                    System.out.println("Department: " + department.getDepartmentName());
                }

                // List courses
                for (Course course : student.getCourses()) {
                    System.out.println("Course: " + course.getName() + " (" + course.getCredit() + " credits)");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
