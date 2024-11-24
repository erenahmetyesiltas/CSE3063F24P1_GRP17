package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        courseRegSystem.restoreSystem();
        CourseRegistrationSimulation courseRegistrationSimulation = new CourseRegistrationSimulation(courseRegSystem);
        courseRegistrationSimulation.run();
    }
}
