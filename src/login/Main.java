package login;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your nickname: ");
        String nickname = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            if (loginSystem.authenticateUser(nickname, password)) {
                System.out.println("Login successful!");
                // Kullanıcı bilgilerini yükle ve işle.
            } else {
                System.out.println("Invalid nickname or password.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
