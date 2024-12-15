package main;

import DataBaseController.StudentDBController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        //courseRegSystem.restoreSystem();
        CourseRegistrationSimulation CLI = new CourseRegistrationSimulation(courseRegSystem);
        CLI.run();
    }
}
