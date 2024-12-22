from contextlib import nullcontext
from pathlib import Path
from io import StringIO
import json

from databaseController.AdvisorDBController import AdvisorDBController
from databaseController.DepartmentSchedulerDBController import DepartmentSchedulerDBController
from databaseController.StudentDBController import StudentDBController

class Admin:
    __id : str
    __firstName : str
    __lastName : str 
    __password : str
    __studentDBController : StudentDBController = None
    __advisorDBController : AdvisorDBController = None
    __departmentSchedulerDBController : DepartmentSchedulerDBController = None

    def __init__(self, id = "", firstName = "", lastName = "", password = ""):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password

    # Create new People.
    def createNewStudent(self, student):
        self.__studentDBController = StudentDBController()
        self.__studentDBController.createNewStudent(student)

    def createNewAdvisor(self, advisor):
        self.__advisorDBController = AdvisorDBController()
        self.__advisorDBController.createNewAdvisor(advisor)

    def createNewDepartmentScheduler(self, departmentScheduler):
        self.__departmentSchedulerDBController = DepartmentSchedulerDBController()
        self.__departmentSchedulerDBController.createNewDepartmentScheduler(departmentScheduler)


    def getId(self):
        return self.__id

    def setId(self,id):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self, firstName):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self,lastName):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self,password):
        self.__password = password