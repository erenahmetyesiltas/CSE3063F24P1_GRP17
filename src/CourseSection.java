import java.sql.Time;
import java.util.List;

public class CourseSection {
    private Time scheduledTime;
    private int sectionNumber;
    private Course course;

    public CourseSection() {

    }

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Time scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudentEnrolled() {
        return studentEnrolled;
    }

    public void setStudentEnrolled(List<Student> studentEnrolled) {
        this.studentEnrolled = studentEnrolled;
    }

    public String getClassroom() {
        return Classroom;
    }

    public void setClassroom(String classroom) {
        Classroom = classroom;
    }

    private List<Student> studentEnrolled ;
    private String Classroom;
}
