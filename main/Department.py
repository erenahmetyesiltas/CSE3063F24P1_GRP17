from typing import List

from main.CourseSection import CourseSection


class Department:
    __id : str
    __departmentName : str
    __courseSectionIds : List[str]

    def __init__(self, id : str, departmentName : str, courseSectionIds : List[str]):
        self.__id = id
        self.__departmentName = departmentName
        self.__courseSectionIds = courseSectionIds

    def getId(self):
        return self.__id

    def setId(self, id : str):
        self.__id = id

    def getDepartmentName(self):
        return self.__departmentName

    def setDepartmentName(self, departmentName : str):
        self.__departmentName = departmentName

    def getCourseSectionIds(self):
        return self.__courseSectionIds

    def setCourseSectionIdS(self, courseSectionIds : List[str]):
        self.__courseSectionIds = courseSectionIds