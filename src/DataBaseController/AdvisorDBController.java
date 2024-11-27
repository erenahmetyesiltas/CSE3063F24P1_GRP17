package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.Advisor;
import main.CourseSection;
import main.DataHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdvisorDBController implements DatabaseController {
    private static final String USERS_DIRECTORY = "CSE3063F24P1_GRP17/src/database/"; // Kullanıcı dosyalarının olduğu dizin

    private Advisor advisor;

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor){
        this.advisor = advisor;
    }

    public boolean loadAdvisor(String advisorId) {
        URL advisorJsonsPath = AdvisorDBController.class.getClassLoader().getResource("database/advisors/"+advisorId+".json");
        File file = new File(advisorJsonsPath.getFile());

        // if the file does not exist, there is no advisor in the system has the advisorId entered.
        if(!file.exists()){
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        Advisor selectedAdvisor = null;

        try {
            selectedAdvisor = mapper.readValue(file, Advisor.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setAdvisor(selectedAdvisor);
        return true;
    }

    public boolean saveAdvisor(String advisorId) {
        if (getAdvisor() == null) {
            System.out.println("No advisor is set to save.");
            return false;
        }

        // Define the output file path for the advisor
        URL advisorJsonsPath = AdvisorDBController.class.getClassLoader().getResource("database/advisors/"+advisorId+".json");
        File file = new File(advisorJsonsPath.getFile());

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Write the advisor object to the file
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, getAdvisor());
            System.out.println("Advisor saved successfully to " + advisorJsonsPath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving advisor: " + e.getMessage());
            return false;
        }
    }
}



