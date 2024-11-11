import java.util.ArrayList;
import java.util.List;

public class Registration {
    private List<CourseSection> courseSections;
    private int registrationStatus;

    public Registration() {

    }

    public boolean addCourseSection(CourseSection coursesection) {
        if (coursesection != null) {
            courseSections.add(coursesection);
            System.out.println(coursesection.getCourse().getId() + " - " + coursesection.getId() + " added to registration");
            return true;
        }
       else {
            System.out.println("WARNING ! : " + coursesection.getCourse().getId() + " - " + coursesection.getId() + " cannot added to registration");
            return false;
        }
    }

    public List<CourseSection> getCourseSectionList() {
        return courseSections;
    }

    public void deleteCourseSectionList() {
        courseSections.clear();
    }

    public int getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(int status) {
        registrationStatus = status;
    }

}
