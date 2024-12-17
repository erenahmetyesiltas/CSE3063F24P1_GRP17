from main.CourseSection import CourseSection
from main.Staff import Staff
from typing import List

class DepartmentScheduler(Staff):

    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __departmentId : str
    __courseSectionList : List[CourseSection]

    def __init__(self, id: str, firstName: str, lastName: str, password: str, departmentId: str, courseSectionList: List[CourseSection]):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__departmentId = departmentId
        self.__courseSectionList = courseSectionList

    def getId(self):
        return self.__id

    def setId(self, id : str):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self,firstName : str):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self, lastName: str):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self, password : str):
        self.__password = password

    def getDepartmentId(self):
        return self.__departmentId

    def setDepartmentId(self, departmentId : str):
        self.__departmentId = departmentId

    def getCourseSectionList(self):
        return self.__courseSectionList

    def setCourseSectionList(self, courseSectionList : List[CourseSection]):
        self.__courseSectionList = courseSectionList