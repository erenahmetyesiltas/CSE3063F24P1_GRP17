import sys
import os
# Üst dizinin yolunu ekle
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

from databaseController.DepartmentSchedulerDBController import DepartmentSchedulerDBController
from main.DepartmentScheduler import DepartmentScheduler
from databaseController.AdvisorDBController import AdvisorDBController
from databaseController.StudentDBController import StudentDBController
from databaseController.AdminDBController import AdminDBController
from main.Advisor import Advisor
from main.Student import Student
from main.Admin import Admin


class LoginSystem:

    __advisor: Advisor
    __student: Student
    __departmentScheduler: DepartmentScheduler
    __admin : Admin
    __advisorDBController: AdvisorDBController
    __studentDBController: StudentDBController
    __departmentSchedulerDBController: DepartmentSchedulerDBController
    __adminDBController: AdminDBController

    def __init__(self, studentDBController = "", advisorDBController = "", departmentSchedulerDBController = "", adminDBController = ""):
        self.__studentDBController = studentDBController
        self.__advisorDBController = advisorDBController
        self.__departmentSchedulerDBController = departmentSchedulerDBController
        self.__adminDBController = adminDBController

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

    def getDepartmentScheduler(self):
        return self.__departmentScheduler

    def setDepartmentScheduler(self, departmentScheduler):
        self.__departmentScheduler = departmentScheduler

    def getAdmin(self):
        return self.__admin
    
    def setAdmin(self, admin):
        self.__admin = admin

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

    def getAdminDBController(self):
        return self.__adminDBController
    
    def setAdminDBController(self, adminDBController):
        self.__adminDBController = adminDBController

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

    # Authenticate Department Scheduler
    def authenticateDepartmentSchedulerUser(self, departmentSchedulerId, password):
        if self.__departmentSchedulerDBController.loadDepartmentScheduler(departmentSchedulerId):
            self.__departmentScheduler = self.__departmentSchedulerDBController.getDepartmentScheduler()
        else:
            return False

        if self.__departmentScheduler.getPassword() == password:
            return True
        else:
            self.__departmentScheduler = None
            self.__departmentSchedulerDBController.setDepartmentScheduler(None)
            return False
        
    def authenticateAdminUser(self, adminId, password):
        if self.__adminDBController.loadAdmin(adminId):
            self.__admin = self.__adminDBController.getAdmin()
        else:
            return False

        if self.__admin.getPassword() == password:
            return True
        else:
            self.__admin = None
            self.__adminDBController.setAdmin(None)
            return False
        