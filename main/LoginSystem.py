import sys
import os

# Üst dizinin yolunu ekle
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

from databaseController.AdvisorDBController import AdvisorDBController
from databaseController.StudentDBController import StudentDBController
from main.Advisor import Advisor
from main.Student import Student


class LoginSystem:

    __advisor: Advisor
    __student: Student
    __advisorDBController: AdvisorDBController
    __studentDBController: StudentDBController

    def __init__(self, studentDBController, advisorDBController):
        self.__studentDBController = studentDBController
        self.__advisorDBController = advisorDBController

    # Getter and setter for Advisor
    def getAdvisor(self):
        return self.__advisor

    def setAdvisor(self, advisor):
        self.__advisor = advisor

    # Getter and setter for Student
    def getStudent(self):
        return self.__student

    def setStudent(self, student):
        self.__student = student

    # Getter and setter for AdvisorDBController
    def getAdvisorDBController(self):
        return self.__advisorDBController

    def setAdvisorDBController(self, advisorDBController):
        self.__advisorDBController = advisorDBController

    # Getter and setter for StudentDBController
    def getStudentDBController(self):
        return self.__studentDBController

    def setStudentDBController(self, studentDBController):
        self.__studentDBController = studentDBController

    # Authenticate Advisor
    def authenticateAdvisorUser(self, advisorId, password):
        if self.__advisorDBController.loadAdvisor(advisorId):
            self.__advisor = self.__advisorDBController.getAdvisor()
        else:
            return False

        if self.__advisor.getPassword() == password:
            return True
        else:
            self.__advisor = None
            self.__advisorDBController.setAdvisor(None)
            return False

    # Authenticate Student
    def authenticateStudentUser(self, studentId, password):
        if self.__studentDBController.loadStudent(studentId):
            self.__student = self.__studentDBController.getStudent()
        else:
            return False

        if self.__student.getPassword() == password:
            return True
        else:
            self.__student = None
            self.__studentDBController.setStudent(None)
            return False
