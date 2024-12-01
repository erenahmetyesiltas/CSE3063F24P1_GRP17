package main;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        CourseRegistrationSystem courseRegSystem = new CourseRegistrationSystem();
        CourseRegistrationSimulation CLI = new CourseRegistrationSimulation(courseRegSystem);
        CLI.run();
    }
}
