import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        courseRegSystem.restoreSystem();
        CLI cli = new CLI(courseRegSystem);
        cli.run();
    }
}

