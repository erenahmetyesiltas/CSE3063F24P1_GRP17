package login;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Student student;
        Advisor advisor;
        LoginSystem loginSystem = new LoginSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your nickname: ");
        String nickname = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // If it's id starts with 1, its means he/she is a student
        if(nickname.charAt(0) == '1'){
            try {
                //Authenticate the Student
                if (loginSystem.authenticateStudent(nickname, password)) {
                    System.out.println("Login successful!");
                    student = loginSystem.getStudent();
                    System.out.println(student.getAdvisor());
                    System.out.println(student.getId());
                } else {
                    System.out.println("Invalid nickname or password.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        // If he/she isn't student then he/she is an advisor.
        else{
            System.out.println("ADVISOR WILL BE IMPLEMENTED.");
        }
    }
}
