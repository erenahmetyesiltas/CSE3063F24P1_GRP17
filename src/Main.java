

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            CourseRegistrationSystem system = new CourseRegistrationSystem();
            system.restoreSystem();
            Student student;
            Advisor advisor;
            LoginSystem loginSystem = new LoginSystem();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your nickname: ");
            String nickname = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try {
                //Authenticate the Student
                if (loginSystem.authenticateUser(nickname, password, system)) {
                    System.out.println("Login successful!");
                    if (loginSystem.getStudent() != null) {
                        student = loginSystem.getStudent();
                    }
                    else {
                        advisor = loginSystem.getAdvisor();
                    }
                } else {
                    System.out.println("Invalid nickname or password.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            } catch (Exception e) {
            e.printStackTrace();
            }

            system.printSuitableCourses();

            //  Buraya yazacağın kodun sıra sıra course alacak şekilde olması lazım readCourse kullanarak

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
