import java.util.Date;
import java.util.List;

public class Course {
    private List<CourseSection> courseSection;
    private int credit;
    private List<Department> departments;
    private Date year;
    private ID id;
    private List<Course> prerequisiteCourses;

    public Course() {

    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }



    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }



    public List<Course> getPrerequisiteCourses() {
        return prerequisiteCourses;
    }

    public void setPrerequisiteCourses(List<Course> prerequisiteCourses) {
        this.prerequisiteCourses = prerequisiteCourses;
    }



    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public List<CourseSection> getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(List<CourseSection> courseSection) {
        this.courseSection = courseSection;
    }

}
