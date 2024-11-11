import java.sql.Time;
import java.util.List;

public class CourseSection {
    private String id;
    private Time scheduledTime;
    private int sectionNumber;
    private Course course;
    private int capacity;
    private int currentParticipantNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseSection() {}

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

    public int getCurrentParticipantNumber() {return currentParticipantNumber;}

    public void setCurrentParticipantNumber(int currentParticipantNumber) {this.currentParticipantNumber = currentParticipantNumber;}

    public int getCapacity() {return capacity;}

    public void setCapacity(int capacity) {this.capacity = capacity;}

    private List<Student> studentEnrolled ;
    private String Classroom;
}
