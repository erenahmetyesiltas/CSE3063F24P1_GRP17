package main;

import DataBaseController.StudentDBController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Creating the CourseRegistrationSystem object that will handle the backend processes
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();

        //courseRegSystem.restoreSystem();

        // CLI (command line interface) is the object that handle user interactions and shift the flow of the program accordingly
        CourseRegistrationSimulation CLI = new CourseRegistrationSimulation(courseRegSystem);

        // run() is the main function of the CourseRegistrationSimulation objects that is the start point of user interactions and the all program
        CLI.run();
    }
}
