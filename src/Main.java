

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
                        //Şimdilik sadece registration yaptık,başka işlem olursa if else eklenecek
                        system.printSuitableCourses();

                        while (true){
                            // Öğrenci sisteme istediği dersleri girer,ileride bu kısmı sistemden
                            // alınabilecek ders sayısı ile sınırlayacağız

                            System.out.print("Do you want to add courses? (y/n): ");
                            String addCourse = scanner.next();
                            if (addCourse.equalsIgnoreCase("y")) {
                                system.readCourses(student);
                            }
                            else if (addCourse.equalsIgnoreCase("n")) {
                                break;
                            }


                        }


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



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
