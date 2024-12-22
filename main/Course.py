
from typing import List


from main.Department import Department

class Course :
    __id: str
    __credit: int
    __departments: []  #
    __term: int
    __prerequisiteCourses:[]
    __name: str
    __weeklyCourseHours: int

    def __init__(self,id="",credit=0,departments=[],term=0,prerequisiteCourses=[],name="",weeklyCourseHours=0):
        self.__id = id
        self.__credit = credit
        self.__term = term
        self.__name = name
        self.__weeklyCourseHours = weeklyCourseHours

        if (len(prerequisiteCourses) != 0 and type(prerequisiteCourses[0]) == dict):
            for i in range(len(prerequisiteCourses)):
                prerequisiteCourses[i] = Course(**prerequisiteCourses[i])

            self.__prerequisiteCourses = prerequisiteCourses
        else:
            self.__prerequisiteCourses = prerequisiteCourses

        if (len(departments) != 0 and type(departments[0]) == dict):
            for i in range(len(departments)):
                departments[i] = Department(**departments[i])

            self.__departments = departments
        else:
            self.__departments = departments


    def getId(self):
        return self.__id

    def setId(self, id):
        self.__id = id

    def getCredit(self):
        return self.__credit

    def setCredit(self, credit):
        self.__credit = credit

    def getDepartments(self):
        return self.__departments

    def setDepartments(self, departments):
        self.__departments = departments

    def getTerm(self):
        return self.__term

    def setTerm(self, term):
        self.__term = term

    def getPrerequisiteCourses(self):
        return self.__prerequisiteCourses

    def setPrerequisiteCourses(self, prerequisiteCourses):
        self.__prerequisiteCourses = prerequisiteCourses

    def getName(self):
        return self.__name

    def setName(self, name):
        self.__name = name

    def getWeeklyCourseHours(self):
        return self.__weeklyCourseHours

    def setWeeklyCourseHours(self, weeklyCourseHours):
        self.__weeklyCourseHours = weeklyCourseHours
