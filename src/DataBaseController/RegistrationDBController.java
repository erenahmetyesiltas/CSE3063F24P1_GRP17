package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.Advisor;
import main.DataHandler;
import main.Registration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RegistrationDBController {

    private Advisor advisor;
    private ObjectMapper objectMapper = new ObjectMapper();

    // getRegistrationsOfAdvisor is for loading all of the current registrations inside of a particular advisor
    public List<Registration> getRegistrationsOfAdvisor(Advisor advisor){
        String registraionId;
        URL registrationMainPath;
        File registrationFile;

        advisor.getRegistrations().clear();

        // Loading the Registration objects from their json files by using the registration ID'S that were stored inside the Advisor object
        for (int i = 0; i < advisor.getRegistrationsIDs().size(); i++) {
            registraionId = advisor.getRegistrationsIDs().get(i);
            registrationMainPath = DataHandler.class.getClassLoader().getResource("database/registrations/"+registraionId+".json");
            registrationFile = new File(registrationMainPath.getFile());

            Registration registration;

            try {
                registration = objectMapper.readValue(registrationFile, Registration.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            advisor.getRegistrations().add(registration);
        }

        return advisor.getRegistrations();
    }

    // updateRegistrations function is for updating the given registration inside of an advisor's
    // corresponding json file
    public void updateRegistrations(Registration registrationGiven,int status){

        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        URL registrationMainPath = RegistrationDBController.class.getClassLoader().getResource("database/registrations/"+registrationGiven.getId()+".json");
        File willUpdateRegistrationFile;
        Registration registrationWillUpdate;

        willUpdateRegistrationFile = new File(registrationMainPath.getFile());

        try {
            registrationWillUpdate = objectMapper.readValue(willUpdateRegistrationFile, Registration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        registrationWillUpdate.setRegistrationStatus(status);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(willUpdateRegistrationFile, registrationWillUpdate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
