package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        // Data retrieving will be fixed.
        courseRegSystem.restoreSystem();
        CourseRegistrationSimulation CLI = new CourseRegistrationSimulation(courseRegSystem);
        CLI.run();
    }
}
