from dataclasses import dataclass
from tkinter import Listbox
from typing import List

import Classroom
import Course
import Lecturer
from main.CourseTime import CourseTime
from main.Student import Student

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

    def __init__(self, enrolledStudents : List[Student], classroom : Classroom, id : str, scheduledTimes : List[CourseTime],
                 sectionNumber : int, course : Course, capacity : int, courseId : str, lecturer : Lecturer):
        self.__enrolledStudents = enrolledStudents
        self.__classroom = classroom
        self.__id = id
        self.__scheduledTimes = scheduledTimes
        self.__sectionNumber = sectionNumber
        self.__course = course
        self.__capacity = capacity
        self.__courseId = courseId
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


    