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
}
