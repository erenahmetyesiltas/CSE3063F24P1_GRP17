


package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.Course;
import main.CourseSection;
import main.DataHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CourseDBController {
    private List<CourseSection> courseSectionList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();

    public void loadAllCourseSectionList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // parent path of course section json files
        URL mainPath = DataHandler.class.getClassLoader().getResource("database/courseSections");
        File file = new File(mainPath.getFile());

        // all the json files of course sections stored in the courseSectionJsons array.
        File[] courseSectionJsons = file.listFiles();

        // Create a course section array list to handle all the course sections from the json files.
        List<CourseSection> courseSectionsList = new ArrayList<>();

        for (File fileCourseSection: courseSectionJsons) {
            CourseSection courseSection = new CourseSection();

            try {
                courseSection = objectMapper.readValue(fileCourseSection, CourseSection.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // add the all course sections one by one
            courseSectionsList.add(courseSection);
        }
        setCourseSectionList(courseSectionsList);

        // Load all courses
        loadCourseList();

        // Associate all courseSections with courses.
        associateCoursesWithSections(getCourseSectionList(), getCourseList());
    }


    public List<Course> loadCourseList(){
        ObjectMapper objectMapper = new ObjectMapper();
        // parent path of course section json files
        URL mainPath = DataHandler.class.getClassLoader().getResource("database/courses");
        File file = new File(mainPath.getFile());

        // all the json files of course sections stored in the courseSectionJsons array.
        File[] courseJsons = file.listFiles();

        // Create a course section array list to handle all the course sections from the json files.
        List<Course> courseList = new ArrayList<>();

        for (File fileCourse: courseJsons) {
            Course course = new Course();

            try {
                course = objectMapper.readValue(fileCourse, Course.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // add the all course sections one by one
            courseList.add(course);
        }
        setCourseList(courseList);
        return courseList;

    }

    public void associateCoursesWithSections(List<CourseSection> courseSections, List<Course> courses) {
        // Course nesnelerini bir Map'e dönüştür (id üzerinden erişim için)
        Map<String, Course> courseMap = courses.stream()
                .collect(Collectors.toMap(Course::getId, course -> course));

        // CourseSection nesnelerindeki courseId ile Course'u eşleştir
        for (CourseSection courseSection : courseSections) {
            String courseId = courseSection.getCourseId();
            if (courseId != null && courseMap.containsKey(courseId)) {
                courseSection.setCourse(courseMap.get(courseId));
            }
        }
    }


    public List<CourseSection> getCourseSectionList() {
        return courseSectionList;
    }

    public void setCourseSectionList(List<CourseSection> courseSectionList) {
        this.courseSectionList = courseSectionList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}



