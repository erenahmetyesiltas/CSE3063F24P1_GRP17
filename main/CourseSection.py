from main.Classroom import Classroom
from main.Course import Course
from main.Lecturer import Lecturer

class CourseSection :
    __enrolledStudents : []
    __classroom : Classroom
    __id : str
    __scheduledTimes : []
    __sectionNumber : int
    __course : Course
    __capacity : int
    __courseId : str
    __lecturer : Lecturer

    def __init__(self):
        pass

    def getEnrolledStudents(self) :
        return self.__enrolledStudents

    def setEnrolledStudents(self, enrolledStudents) :
        self.__enrolledStudents = enrolledStudents

    def getClassroom(self) :
        return self.__classroom

    def setClassroom(self, classroom) :
        self.__classroom = classroom

    def getId(self) :
        return self.__id

    def setId(self, id) :
        self.__id = id

    def getScheduledTimes(self) :
        return self.__scheduledTimes

    def setScheduledTimes(self, scheduledTimes) :
        self.__scheduledTimes = scheduledTimes

    def getSectionNumber(self) :
        return self.__sectionNumber

    def setSectionNumber(self, sectionNumber) :
        self.__sectionNumber = sectionNumber

    def getCourse(self) :
        return self.__course

    def setCourse(self, course) :
        self.__course = course

    def getCapacity(self) :
        return self.__capacity

    def setCapacity(self, capacity) :
        self.__capacity = capacity

    def getCourseId(self) :
        return self.__courseId

    def setCourseId(self, courseId) :
        self.__courseId = courseId

    def getLecturer(self) :
        return self.__lecturer

    def setLecturer(self, lecturer) :
        self.__lecturer = lecturer


