from dataclasses import dataclass
from tkinter import Listbox
from typing import List

from main.Classroom import Classroom
from main.Course import Course
from main.Lecturer import Lecturer
from main.CourseTime import CourseTime
import Student

class CourseSection :
    __enrolledStudents : List[Student]
    __classroom : Classroom
    __id : str
    __scheduledTimes : List[CourseTime]
    __sectionNumber : int
    __course : Course
    __capacity : int
    __courseId : str
    __lecturer : Lecturer

    def __init__(self, enrolledStudents : List[Student] = [], classroom : Classroom = "", id : str = "", scheduledTimes : List[CourseTime] = [],
                 sectionNumber : int = 0, course : Course = "", capacity : int = 0, courseId : str = "", lecturer : Lecturer = ""):

        if (len(enrolledStudents) != 0 and type(enrolledStudents[0]) == dict) :
            for i in range(len(enrolledStudents)) :
                enrolledStudents[i] = Student(**enrolledStudents[i])

            self.__enrolledStudents = enrolledStudents
        else :
            self.__enrolledStudents = enrolledStudents

        if (type(classroom) == dict) :
            self.__classroom = Classroom(**classroom)
        else :
            self.__classroom = classroom

        self.__id = id

        if (len(scheduledTimes) != 0 and type(scheduledTimes[0]) == dict) :
            for i in range(len(scheduledTimes)):
                scheduledTimes[i] = CourseTime(**scheduledTimes[i])

            self.__scheduledTimes = scheduledTimes
        else :
            self.__scheduledTimes = scheduledTimes

        self.__sectionNumber = sectionNumber

        if (type(course) == dict) :
            self.__course = Course(**course)
        else :
            self.__course = course

        self.__capacity = capacity
        self.__courseId = courseId

        if (type(lecturer) == dict) :
            self.__lecturer = Lecturer(**lecturer)
        else :
            self.__lecturer = lecturer



    def toDict(self):
        return {
            "enrolledStudents": self.__enrolledStudents,
            "classroom": self.__classroom,
            "id": self.__id,
            #"scheduledTimes": self.__scheduledTimes,
            "scheduledTimes": [courseTime.toDict() for courseTime in self.__scheduledTimes],
            "sectionNumber": self.__sectionNumber,
            "course": self.__course,
            "capacity": self.__capacity,
            "courseId": self.__courseId,
            "lecturer": self.__lecturer
        }

    def getEnrolledStudents(self) :
        return self.__enrolledStudents

    def setEnrolledStudents(self, enrolledStudents : List[Student]) :
        self.__enrolledStudents = enrolledStudents

    def getClassroom(self) :
        return self.__classroom

    def setClassroom(self, classroom : Classroom) :
        self.__classroom = classroom

    def getId(self) :
        return self.__id

    def setId(self, id) :
        self.__id = id

    def getScheduledTimes(self) :
        return self.__scheduledTimes

    def setScheduledTimes(self, scheduledTimes : List[CourseTime]) :
        self.__scheduledTimes = scheduledTimes

    def getSectionNumber(self) :
        return self.__sectionNumber

    def setSectionNumber(self, sectionNumber : str) :
        self.__sectionNumber = sectionNumber

    def getCourse(self) :
        return self.__course

    def setCourse(self, course : Course) :
        self.__course = course

    def getCapacity(self) :
        return self.__capacity

    def setCapacity(self, capacity : int) :
        self.__capacity = capacity

    def getCourseId(self) :
        return self.__courseId

    def setCourseId(self, courseId : str) :
        self.__courseId = courseId

    def getLecturer(self) :
        return self.__lecturer

    def setLecturer(self, lecturer : Lecturer) :
        self.__lecturer = lecturer


    