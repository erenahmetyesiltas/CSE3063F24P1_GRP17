


package DataBaseController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class CourseDBController {
    private List<CourseSection> courseSectionList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

    public void loadAllCourseSectionList() throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
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

    public List<CourseSection> getAllCourseSectionList(){

        List<CourseSection> courseSections = new ArrayList<>();

        URL courseSectionMainPath = CourseDBController.class.getClassLoader().getResource("database/courseSections");

        File courseSectionDirectory = new File(courseSectionMainPath.getPath());


        CourseSection courseSection;
        if(courseSectionDirectory.isDirectory()){
            for (File file : courseSectionDirectory.listFiles()) {

                try {
                    courseSection = mapper.readValue(file, CourseSection.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                courseSections.add(courseSection);

            }
        }

        return courseSections;

    }


    public List<Course> loadCourseList(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

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

    public void loadCourseSectionListOfDepartmentScheduler(DepartmentScheduler departmentScheduler){

        URL departmentPath = CourseDBController.class.getClassLoader().getResource("database/departments/"+departmentScheduler.getDepartmentId()+".json");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(departmentPath == null){
            System.out.println("There is no such a department called " + departmentScheduler.getDepartmentId());
            return;
        }

        File departmentFile = new File(departmentPath.getFile());

        Department department;

        try {
            department = mapper.readValue(departmentFile, Department.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Getting courseSections one by one
        String courseSectionId = "";
        URL courseSectionPath;
        File courseSectionFile;

        departmentScheduler.getCourseSectionList().clear();

        for (int i = 0; i < department.getCourseSectionIds().size(); i++) {

            courseSectionId = department.getCourseSectionIds().get(i);
            courseSectionPath = CourseDBController.class.getClassLoader().getResource("database/courseSections/"+courseSectionId+".json");
            courseSectionFile = new File(courseSectionPath.getFile());

            CourseSection courseSection;

            try {
                courseSection = mapper.readValue(courseSectionFile,CourseSection.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            departmentScheduler.getCourseSectionList().add(courseSection);

        }
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

    public CourseSection loadCourseSection(String courseSectionId) {

        URL courseSectionPath = CourseDBController.class.getClassLoader().getResource("database/courseSections/"+courseSectionId+".json");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File courseSectionFile = new File(courseSectionPath.getFile());

        CourseSection crs;
        try {
            crs = mapper.readValue(courseSectionFile, CourseSection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return crs;

    }

    public boolean assignClassroomToCourseSection(CourseSection courseSection, String classroomName) {

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        URL classroomPath = CourseDBController.class.getClassLoader().getResource("database/classrooms/"+classroomName+".json");

        File classroomFile = new File(classroomPath.getFile());

        Classroom classroom;

        try {
            classroom = mapper.readValue(classroomFile, Classroom.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(classroom.getCapacity() < courseSection.getCapacity()){
            System.out.println("The classroom capacity is not enough for this course section.");
            return false;
        }else{
            courseSection.setClassroom(classroom);

            URL courseSectionPath = CourseDBController.class.getClassLoader().getResource("database/courseSections/"+courseSection.getId()+".json");

            File selectedCourseSectionFile = new File(courseSectionPath.getFile());

//            try {
//                mapper.writeValue(selectedCourseSectionFile,courseSection);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            return true;
        }

    }

    public void assignClassroomToCourseSectionWithNoDoubt(File file, CourseSection courseSection){
        try {
            mapper.writeValue(file,courseSection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean assignTimesToCourseSection(CourseSection courseSection, DepartmentScheduler departmentScheduler) {

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Scanner scanner = new Scanner(System.in);

        URL courseSectionPath = CourseDBController.class.getClassLoader().getResource("database/courseSections/"+courseSection.getId()+".json");
        File courseSectionFile = new File(courseSectionPath.getFile());

        if(!courseSection.getScheduledTimes().isEmpty()){
//            System.out.println("Current times for the course section");
//            for (int i = 0; i < courseSection.getScheduledTimes().size(); i++) {
//                System.out.println("i ==> "+ courseSection.getScheduledTimes().get(i).getCourseDay());
//                System.out.println("  ==> "+ courseSection.getScheduledTimes().get(i).getStartTime());
//                System.out.println("  ==> "+ courseSection.getScheduledTimes().get(i).getEndTime());
//            }

            System.out.println("WARNING!!! If you want to change not all the courses please enter the " +
                    "course section hours you want to keep it same and new time for what you want to change course section hours");

        }


        courseSection.getScheduledTimes().clear();


        System.out.println("There are "+courseSection.getCourse().getWeeklyCourseHours()+" lesson hourses for this course.");

        // Course Section Hours enrolling attempt
        for (int i = 0; i < courseSection.getCourse().getWeeklyCourseHours(); i++) {

            int nthCourseSectionHour = i+1;
            CourseTime courseTime = new CourseTime();


            System.out.print("Enter a day for the course hour "+nthCourseSectionHour+": ");
            String day = scanner.next();
            //courseSection.getScheduledTimes().get(i).setCourseDay(day);
            courseTime.setCourseDay(day);

            System.out.print("Enter a start time for the course hour "+nthCourseSectionHour+": ");
            String startTime = scanner.next();
            //courseSection.getScheduledTimes().get(i).setStartTime(Time.valueOf(startTime));
            courseTime.setStartTime(Time.valueOf(startTime));

            String endTime = startTime.substring(0,3) + "50:00";
            //courseSection.getScheduledTimes().get(i).setEndTime(Time.valueOf(endTime));
            courseTime.setEndTime(Time.valueOf(endTime));

            courseSection.getScheduledTimes().add(courseTime);
        }

        //System.out.println(courseSection.getScheduledTimes().size());

        int enteredValidCourseSectionTime = courseSection.getScheduledTimes().size();

        // Kendi bölümleri ile zaman çakışıyor ise patla
        for (int i = 0; i < courseSection.getScheduledTimes().size(); i++) {

            for (int j = 0; j < departmentScheduler.getCourseSectionList().size(); j++) {

                // If terms of them are not the same, then they can conflict.
                if(departmentScheduler.getCourseSectionList().get(j).getCourse().getTerm() != courseSection.getCourse().getTerm()){
                    continue;
                }
                // If there is no scheduled times, then ignore it.
                else if(departmentScheduler.getCourseSectionList().get(j).getScheduledTimes() == null){
                    continue;
                }else{

                    for (int k = 0; k < departmentScheduler.getCourseSectionList().get(j).getScheduledTimes().size(); k++) {

                        if(courseSection.getScheduledTimes().get(i).getCourseDay().equalsIgnoreCase(departmentScheduler.getCourseSectionList().get(j).getScheduledTimes().get(k).getCourseDay())
                                && courseSection.getScheduledTimes().get(i).getStartTime() == departmentScheduler.getCourseSectionList().get(j).getScheduledTimes().get(k).getStartTime()
                                && courseSection.getScheduledTimes().get(i).getEndTime() == departmentScheduler.getCourseSectionList().get(j).getScheduledTimes().get(k).getEndTime()){

                            System.out.println("There is a time conflict with another course section hours, for this reason the time removed.");
                            System.out.println("Day: "+courseSection.getScheduledTimes().get(i).getCourseDay());
                            System.out.println("Start Time: "+courseSection.getScheduledTimes().get(i).getStartTime());
                            System.out.println("End Time: "+courseSection.getScheduledTimes().get(i).getEndTime());
                            courseSection.getScheduledTimes().remove(i);
                            enteredValidCourseSectionTime--;
                        }

                    }

                }

            }

        }


        if(enteredValidCourseSectionTime <= courseSection.getCourse().getWeeklyCourseHours() &&
                enteredValidCourseSectionTime > 0){

            //System.out.println(enteredValidCourseSectionTime);

//            try {
//                mapper.writeValue(courseSectionFile,courseSection);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            return true;

        }else{

            System.out.println("Any course section time is not added.");
            return false;

        }

    }

    public void assignTimesToCourseSectionWithNoDoubt(File file, CourseSection courseSection){
        try {
            mapper.writeValue(file,courseSection);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void isClassroomAvailable(CourseSection courseSection) {

        List<CourseSection> allCourseSections = getAllCourseSectionList();

        // Discard itself from the allCourseSections
        for (int i = 0; i < allCourseSections.size(); i++) {
            if(courseSection.getId().equals(allCourseSections.get(i).getId())){
                allCourseSections.remove(allCourseSections.get(i));
            }
        }

//        for (int i = 0; i < courseSection.getScheduledTimes().size(); i++) {
//            for (int j = 0; j < allCourseSections.size(); j++) {
//
//                if(!courseSection.getClassroom().getId().equalsIgnoreCase(allCourseSections.get(j).getClassroom().getId())){
//                    continue;
//                }else if(allCourseSections.get(j).getScheduledTimes().size() == 0){
//                    System.out.println(allCourseSections.get(j).getId() + " is empty");
//                    continue;
//                }
//
//                for (int k = 0; k < allCourseSections.get(j).getScheduledTimes().size(); k++) {
//
//                    if(courseSection.getScheduledTimes().get(i).getCourseDay().equalsIgnoreCase(allCourseSections.get(j).getScheduledTimes().get(k).getCourseDay()) &&
//                    courseSection.getScheduledTimes().get(i).getStartTime().equals(allCourseSections.get(j).getScheduledTimes().get(k).getStartTime()) &&
//                    courseSection.getScheduledTimes().get(i).getEndTime().equals(allCourseSections.get(j).getScheduledTimes().get(k).getEndTime())){
//
//                        System.out.println("The course section was removed since the classroom was already used for another course section hours at the time.");
//                        System.out.println("Day: "+courseSection.getScheduledTimes().get(i).getCourseDay());
//                        System.out.println("Start Time: "+courseSection.getScheduledTimes().get(i).getStartTime());
//                        System.out.println("End Time: "+courseSection.getScheduledTimes().get(i).getEndTime());
//                        System.out.println("Classroom:" + courseSection.getClassroom().getId());
//                        System.out.println();
//
//                        courseSection.getScheduledTimes().remove(i);
//
//                    }
//
//                }
//            }
//        }

        Iterator<CourseTime> iterator = courseSection.getScheduledTimes().iterator();
        while (iterator.hasNext()) {
            CourseTime scheduledTime = iterator.next();
            for (int j = 0; j < allCourseSections.size(); j++) {
                if (!courseSection.getClassroom().getId().equalsIgnoreCase(allCourseSections.get(j).getClassroom().getId())) {
                    continue;
                } else if (allCourseSections.get(j).getScheduledTimes().size() == 0) {
                    System.out.println(allCourseSections.get(j).getId() + " is empty");
                    continue;
                }

                for (CourseTime otherScheduledTime : allCourseSections.get(j).getScheduledTimes()) {
                    if (scheduledTime.getCourseDay().equalsIgnoreCase(otherScheduledTime.getCourseDay()) &&
                            scheduledTime.getStartTime().equals(otherScheduledTime.getStartTime()) &&
                            scheduledTime.getEndTime().equals(otherScheduledTime.getEndTime())) {

                        System.out.println("The course section was removed since the classroom was already used for another course section hours at the time.");
                        System.out.println("Day: " + scheduledTime.getCourseDay());
                        System.out.println("Start Time: " + scheduledTime.getStartTime());
                        System.out.println("End Time: " + scheduledTime.getEndTime());
                        System.out.println("Classroom:" + courseSection.getClassroom().getId());
                        System.out.println();
                        iterator.remove(); // Güvenli kaldırma
                        break;
                    }
                }
            }
        }


        if(courseSection.getScheduledTimes().isEmpty()){
            System.out.println("Any course section hours cannot be saved.");
        }else{

            URL courseSectionPath = CourseDBController.class.getClassLoader().getResource("database/courseSections/"+courseSection.getId()+".json");
            File courseSectionFile = new File(courseSectionPath.getFile());

            assignTimesToCourseSectionWithNoDoubt(courseSectionFile, courseSection);
            assignClassroomToCourseSectionWithNoDoubt(courseSectionFile, courseSection);

            System.out.println();
            System.out.println("The course section hours "+ courseSection.getScheduledTimes().size()+"/"+courseSection.getCourse().getWeeklyCourseHours()+" are saved.");
            System.out.println("The saved course sections are:");

            System.out.println("Classroom: "+courseSection.getClassroom().getId());
            for (int i = 0; i < courseSection.getScheduledTimes().size(); i++) {
                System.out.println(i+"==> Day: "+courseSection.getScheduledTimes().get(i).getCourseDay());
                System.out.println("Start Time: "+courseSection.getScheduledTimes().get(i).getStartTime());
                System.out.println("End Time: "+courseSection.getScheduledTimes().get(i).getEndTime());
            }

            System.out.println("Course section hours are saved successfully.");

        }

    }
}