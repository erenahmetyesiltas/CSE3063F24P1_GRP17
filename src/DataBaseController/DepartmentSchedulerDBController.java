package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.DepartmentScheduler;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DepartmentSchedulerDBController {

    private DepartmentScheduler departmentScheduler;

    public DepartmentScheduler getDepartmentScheduler() {
        return departmentScheduler;
    }

    public void setDepartmentScheduler(DepartmentScheduler departmentScheduler) {
        this.departmentScheduler = departmentScheduler;
    }

    public boolean loadDepartmentScheduler(String departmentSchedulerId){

        URL departmentSchedulerJsonPath = DepartmentSchedulerDBController.class.getClassLoader().getResource("database/departmentSchedulers/"+departmentSchedulerId+".json");

        if(departmentSchedulerJsonPath == null){
            return false;
        }

        File departmentSchedulerFile = new File(departmentSchedulerJsonPath.getFile());



        if(!departmentSchedulerFile.exists()){
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        DepartmentScheduler departmentSchedulerSelected = null;

        try {
            departmentSchedulerSelected = mapper.readValue(departmentSchedulerFile,DepartmentScheduler.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDepartmentScheduler(departmentSchedulerSelected);

        return true;
    }

}
