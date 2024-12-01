package main;

import java.sql.Time;

public class CourseTime {
    private Time startTime;
    private Time endTime;
    private String courseDay;
    public CourseTime() {
    }

    public CourseTime(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CourseTime(){

    }

    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getCourseDay() {
        return courseDay;
    }
    public void setCourseDay(String courseDay) {
        this.courseDay = courseDay;
    }
}
