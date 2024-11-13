package main;

import java.io.IOException;

public class Main2 {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        courseRegSystem.restoreSystem();
        CLI cli = new CLI(courseRegSystem);
        cli.run();
    }
}