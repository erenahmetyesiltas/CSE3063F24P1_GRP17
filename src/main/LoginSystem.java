package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import DataBaseController.AdvisorDBController;
import DataBaseController.DepartmentSchedulerDBController;
import DataBaseController.StudentDBController;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginSystem {
    private static final String USERS_DIRECTORY = "CSE3063F24P1_GRP17/src/database/students"; // Kullanıcı dosyalarının olduğu dizin

    // These Student, Advisor and Department Scheduler objects down below will hold the loaded objects that represents the current user that
    // is successfully logged in to the system

    // Also they have their own DBControllers for loading their information from the database individually
    private Student student;
    private StudentDBController studentDBController;

    private Advisor advisor;
    private AdvisorDBController advisorDBController;

    private DepartmentScheduler departmentScheduler;
    private DepartmentSchedulerDBController departmentSchedulerDBController;

    LoginSystem(StudentDBController studentDBController,AdvisorDBController advisorDBController,DepartmentSchedulerDBController departmentSchedulerDBController) {
        this.studentDBController = studentDBController;
        this.advisorDBController = advisorDBController;
        this.departmentSchedulerDBController = departmentSchedulerDBController;
    }


    /**
     * Authenticates a user (either a student or an advisor) based on their nickname and password.
     *
     * @param studentId The user's nickname (ID).
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    // authenticateUser is responsible of the correctness of the login information given by the student user and matches it's information
    // from the database and loads the necessary objects related to the student user
    public boolean authenticateUser(String studentId, String password) throws IOException {
        // Try to load the student from the json files
        // With the given student ID (also means the nickname), we look for that particular student in our database that has the same file name
        if(studentDBController.loadStudent(studentId)) {
            this.student = studentDBController.getStudent();
        }
        else{
            return false;
        }

        // Student has been loaded and it's time for checking the password
        if (student.getPassword().equals(password)) {
            return true;
        }
        // Password checking has been failed, so delete the student object and return null to them
        // Also return false to indicate the error in login attempt
        else{
            this.student = null;
            studentDBController.setStudent(null);
            return false;
        }
    }
    // authenticateUser is responsible of the correctness of the login information given by the advisor user and matches it's information
    // from the database and loads the necessary objects related to the advisor user
    public boolean authenticateAdvisorUser(String advisorId, String password) throws IOException{
        if(advisorDBController.loadAdvisor(advisorId)){
            this.advisor = advisorDBController.getAdvisor();
        }else{
            return false;
        }

        if(advisor.getPassword().equals(password)){
            return true;
        }else{
            this.advisor = null;
            advisorDBController.setAdvisor(null);
            return false;
        }

    }
    // authenticateUser is responsible of the correctness of the login information given by the department scheduler user and matches it's information
    // from the database and loads the necessary objects related to the department scheduler user
    public boolean authenticateDepartmentSchedulerUser(String departmentSchedulerId, String password) throws IOException{

        if(departmentSchedulerDBController.loadDepartmentScheduler(departmentSchedulerId)){
            this.departmentScheduler = departmentSchedulerDBController.getDepartmentScheduler();
        }else{
            return false;
        }

        if(departmentScheduler.getPassword().equals(password)){
            return true;
        }else{
            this.departmentScheduler = null;
            departmentSchedulerDBController.setDepartmentScheduler(null);
            return false;
        }

    }

    // Getter methodu
    public Student getStudent() {
        return student;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public DepartmentScheduler getDepartmentScheduler() {
        return departmentScheduler;
    }

    public void setDepartmentScheduler(DepartmentScheduler departmentScheduler) {
        this.departmentScheduler = departmentScheduler;
    }

}



