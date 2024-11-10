import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CourseRegistrationSystem {
    private List<Student> allStudents = new ArrayList<Student>();
    private List<Advisor> allAdvisors = new ArrayList<>();
    private List<CourseSection> allCourseSections = new ArrayList<>();
    private List<Department> allDepartments = new ArrayList<>();
    private final DataHandler dataHandler = new DataHandler();

    public CourseRegistrationSystem() throws IOException {
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

    public List<Advisor> getAllAdvisors() {
        return allAdvisors;
    }

    public void setAllAdvisors(List<Advisor> allAdvisors) {
        this.allAdvisors = allAdvisors;
    }

    public List<CourseSection> getAllCourseSections() {
        return allCourseSections;
    }

    public void setAllCourseSections(List<CourseSection> allCourseSections) {
        this.allCourseSections = allCourseSections;
    }

    public List<Department> getAllDepartments() {
        return allDepartments;
    }

    public void setAllDepartments(List<Department> allDepartments) {
        this.allDepartments = allDepartments;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public void restoreSystem() throws NoSuchElementException {
        int i = 0;
        List<SystemData> allDatas = dataHandler.getAllDatas();
        ObjectMapper mapper = dataHandler.getObjectMapper();

        while (i != (allDatas.size())) {
            Class objectClass = allDatas.get(i).getObjectClass();
            Object object = allDatas.get(i).getObject();

            if (objectClass == Student.class) {
                Student student = mapper.convertValue(object, Student.class);
                this.getAllStudents().add(student);
            }

            if (objectClass == CourseSection.class) {
                CourseSection course = mapper.convertValue(object, CourseSection.class);
                this.getAllCourseSections().add(course);
            }
            if (objectClass == Advisor.class) {
                Advisor advisor = mapper.convertValue(object, Advisor.class);
                this.getAllAdvisors().add(advisor);
            }
            i++;
        }
    }


}
