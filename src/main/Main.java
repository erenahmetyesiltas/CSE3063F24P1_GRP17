package main;

import DataBaseController.StudentDBController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StudentDBController studentDBController = new StudentDBController();
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem(studentDBController);
        courseRegSystem.restoreSystem();
        CourseRegistrationSimulation CLI = new CourseRegistrationSimulation(courseRegSystem, studentDBController);
        CLI.run();
    }
}
