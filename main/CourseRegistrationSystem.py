import string
from typing import List

from databaseController import AdvisorDBController, StudentDBController
from databaseController.CourseDBController import CourseDBController
from main import Course, Student, Registration,CourseSection,DataHandler



class CourseRegistrationSystem:
    __allStudents: list
    __allAdvisors: list
    __allCourseSections: list
    __allDepartments: list
    __dataHandler: DataHandler
    __courseDBController: CourseDBController
    def __init__(self):
        # Initialize all_course_sections with data from data_handler
        self.setAllCourseSections(self.__dataHandler.getCourseSectionList())

    def getAllStudents(self):
        return self.__allStudents

    def setAllStudents(self, allStudents):
        self.__allStudents = allStudents

    def getAllAdvisors(self):
        return self.__allAdvisors

    def setAllAdvisors(self, allAdvisors):
        self.__allAdvisors = allAdvisors

    def getAllCourseSections(self):
        return self.__allCourseSections

    def setAllCourseSections(self, allCourseSections):
        self.__allCourseSections = allCourseSections

    def getAllDepartments(self):
        return self.__allDepartments

    def setAllDepartments(self, allDepartments):
        self.__allDepartments = allDepartments

    def getDataHandler(self):
        return self.__dataHandler


    #---------------------------------UPDATEDATA KULLANILMIYOR -----------------------------------------------
   # def updateData(self, object):
   #   data = SystemData(object, object.getClass(), f"object{dataHandler.get_file_number()}.json")
   #     dataHandler.getAllDatas().add(data)
   #     dataHandler.removeDuplicateData()

    def printSuitableCourses(self, student: Student):
        print("\nThe available courses that you can register")
        print("Course:                Section:                Lecturer:")

        appropriateCourseSections: list[CourseSection] = self.__courseDBController.getCourseSectionListAppropriate(student)

        for courseSection in appropriateCourseSections:
            fullNameOfLecturer = courseSection.getLecturer().getFirstName() + " " + courseSection.getLecturer().getLastName()
            print("%-23s%-24d%-23s\n", courseSection.getCourse().getId(), courseSection.getSectionNumber(), fullNameOfLecturer)


    def findCourseSection(self,course: str ,section: str):
        try:
             sectionNumber: int = int(section)
        except ValueError:
            print("section parameter is different than int")

        for courseSection in self.getAllCourseSections():
            if courseSection.getCourse().getId() == course and courseSection.getSectionNumber() == sectionNumber:
                return courseSection

        return None

    def readCourses(self,student: Student):
        course : str = input("Enter the course: ")
        courseSection : str= input("Enter the course section: ")

        if self.findCourseSection(course,courseSection) != None:
            if self.checkRegistrationTimeConflict(student,self.findCourseSection(course,courseSection)):
                print("WARNING: The hours of the course you want to add conflict with the courses you added.")
                return False

            else:
                return student.getRegistration().addCourseSection((self.findCourseSection(course, courseSection)))

        else:
            print("WARNING: The course section entered cannot find in available courses.")
            return False


    def checkRegistrationTimeConflict(self,student: Student, newAddedCourseSection: CourseSection):
        for eachCourseSection in student.getRegistration().getCourseSections():
            for courseTime in eachCourseSection.getScheduledTimes():
                for newCourseTime in newAddedCourseSection.getScheduledTimes():
                    if courseTime.getCourseDay() == newCourseTime.getCourseDay():
                        if courseTime.getStartTime() < (newCourseTime.getEndTime()) and newCourseTime.getStartTime()< (courseTime.getEndTime()):
                            return True
                        elif courseTime.getStartTime() == newCourseTime.getStartTime() and courseTime.getEndTime() == newCourseTime.getEndTime() :
                            return True

        return False


    def sendRegistrationToAdvisor(self, registration : Registration, student: Student): # throws IOException
        try:
            # First, loadstudent's advisor & match it with student.advisor
            advisorDBController : AdvisorDBController
            advisorDBController.loadAdvisor(str(student.getAdvisorId()))
            student.setAdvisor(advisorDBController.getAdvisor())
            student.getAdvisor().addRegistration(registration)

            #Save the registration and Advisor's registration.
            studentDBController : StudentDBController
            studentDBController.setStudent(student)
            studentDBController.saveStudent(student.getId())
            studentDBController.saveStudentRegistration(student.getId())
            advisorDBController.saveAdvisor(str(student.getAdvisorId()))

        except Exception as e:
            # Input hatası olabilir
            print(f"Bir hata oluştu: {e}")
            raise

    def getStudentRegistrationStatus(self,student : Student):
        registration : Registration = student.getRegistration()
        registrationStatus : int = registration.getRegistrationStatus()

        if registrationStatus == 0:
            print(registration.getCourseSections())
            print("WARNING : Your advisor has disapproved your registration, you have to create a new one and send it again\n")

        elif registrationStatus == 1:
            print("WARNING : Your advisor has not yet checked your registration\n")

        elif registrationStatus == 2:
            print("SUCCESS : Your advisor has approved your registration, you will be enrolled to the courses you \n")
            self.enrollStudent(registration.getCourseSections(), student)



    def enrollStudent(self, enrollCourseSections: List[CourseSection], student: Student):
        for courseSection in self.__allCourseSections:
            for enrollCourseSection in enrollCourseSections:
                if enrollCourseSection.getCourseId() == courseSection.getCourseId():
                    courseSection.getEnrolledStudents().add(student)
                    enrollCourseSection.getEnrolledStudents().add(student)


    def saveLastState(self): #throws IOException
        try:
            for student in self.getAllStudents():
                self.__dataHandler.storeObject(student)
                self.__dataHandler.removeDuplicateData()

            for courseSection in self.getAllCourseSections():
                self.__dataHandler.storeObject(courseSection)
                self.__dataHandler.removeDuplicateData()

            for advisor in self.getAllAdvisors():
                self.__dataHandler.storeObject(advisor)
                self.__dataHandler.removeDuplicateData()
                
        except Exception as e:
            # Input hatası olabilir
            print(f"Bir hata oluştu: {e}")
            raise

    def checkEligibility(self, student: Student):
        if self.checkPrerequisite(student) and self.checkCourseSectionFull(student):
            return True
        else:
            return False


    def checkCourseSectionFull(self, student: Student):
        regCourseSections: List[CourseSection] = student.getRegistration().getCourseSections()
        prerequisiteCourses: List[Course]

        for i in range(len(regCourseSections)):
            if regCourseSections[i].getCapacity() <= len(regCourseSections[i].getEnrolledStudents()):
                print("WARNING : The " + regCourseSections[i].getId() + " course section is full, your registration will be discarded\n")
                return False

        return True

    def checkPrerequisite(self, student: Student):
        regCourseSections: List[CourseSection] = student.getRegistration().getCourseSections()
        prerequisiteCourses: List[Course]

        for i in range(len(regCourseSections)):
            for j in range(len(regCourseSections[i].getCourse().getPrerequisiteCourses())):
                prerequisiteCourses.append(regCourseSections[i].getCourse().getPrerequisiteCourses()[j])

        takenCourses: List[Course] = student.getCourses()
        canTake : bool = False

        n: int = len(prerequisiteCourses)
        z: int =0

        for i in range(len(prerequisiteCourses)):
            for j in range(len(takenCourses)):
                if prerequisiteCourses[i] == takenCourses[j]:
                    z+=1
        if n == z:
            cantake = True
            print("WARNING : There is a prerequisite conflict in your registration, your registration will be discarded\n")
            return canTake


