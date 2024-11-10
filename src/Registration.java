import java.util.ArrayList;
import java.util.List;

public class Registration {
    private List<CourseSection> courseSections;
    private int registrationStatus;

    public Registration() {

    }

    public void addCourseSection(CourseSection coursesection) {
        courseSections.add(coursesection);
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
