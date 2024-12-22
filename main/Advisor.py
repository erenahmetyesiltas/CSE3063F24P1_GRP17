import json
from contextlib import nullcontext
from io import StringIO
from pathlib import Path
from typing import List

from main.Registration import Registration
from main.Student import Student

class Advisor:
    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __registrationsIDs : List[str]
    __supervisedStudentsIDs : List[str]
    __supervisedStudents : List[Student]
    __registrations : List[Registration]

    def __init__(self, id : str, firstName : str, lastName : str, password : str, registrationsIDs : List[str]
                 , supervisedStudentsIDs : List[str], supervisedStudents : List[Student], registrations : List[Registration]):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__registrationsIDs = registrationsIDs
        self.__supervisedStudentsIDs = supervisedStudentsIDs
        self.__supervisedStudents = supervisedStudents
        self.__registrations = registrations


    def getId(self):
        return self.__id

    def setId(self,id : str):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self, firstName : str):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self,lastName : str):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self,password : str):
        self.__password = password

    def getRegistrationIDs(self):
        return self.__registrationsIDs

    def setRegistrationIDs(self, registrationsIDs : List[str]):
        self.__registrationsIDs = registrationsIDs

    def getSupervisedStudentsIDs(self):
        return self.__supervisedStudentsIDs

    def setSupervisedStudentsIDs(self, supervisedStudentsIDs : List[str]):
        self.__supervisedStudentsIDs = supervisedStudentsIDs

    def getSupervisedStudents(self):
        return self.__supervisedStudents

    def setSupervisedStudents(self, supervisedStudents : List[Student]):
        self.__supervisedStudents = supervisedStudents

    def getRegistrations(self):
        return self.__registrations

    def setRegistrations(self, registrations : List[Registration]):
        self.__registrations = registrations

    def handleRegistration(self, selectedReg : Registration):

        days = ["Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"]

        hours = ["00:00:00","01:00:00","02:00:00","03:00:00","04:00:00","05:00:00","06:00:00","07:00:00"
                 ,"08:00:00","09:00:00","10:00:00","11:00:00","12:00:00","13:00:00","14:00:00","15:00:00"
                 ,"16:00:00","17:00:00","18:00:00","19:00:00","20:00:00","21:00:00","22:00:00","23:00:00"]

        isAnyConflictedHappened = False

        for day in days:
            for hour in hours:

                courseSectionCounter = 0
                conflictedCourseSections : List[str]
                conflictedCourseSections = []

                for courseSection in selectedReg.getCourseSections():
                    for courseTime in courseSection.getScheduledTimes():

                        if courseTime.getCourseDay() == day and courseTime.getStartTime() == hour:
                            courseSectionCounter = courseSectionCounter + 1

                if courseSectionCounter > 1:
                    isAnyConflictedHappened = True
                    print("There is a conflict at that time:",day,"-",hour)
                    print("Between these course sections: ")

                    for crsSection in conflictedCourseSections:
                        print(crsSection)

                courseSectionCounter = 0
                conflictedCourseSections = []

        if not isAnyConflictedHappened:
            print("There is no any conflict in this registration.")

        self.setStatusOfReg(selectedReg)

    def setStatusOfReg(self, reg: Registration):
        print()
        print("0 => do not approve")
        print("1 => check later again")
        print("2 => approve")
        print("Please set a status for this registration:")
        status = int(input())

        while status > 2 or status < 0:
            print("Please set a valid status for this registration:", end="")
            status = int(input())

        reg.setRegistrationStatus(status)
        # update the json file
        self.updateRegistration(reg)


    def updateRegistration(self, reg : Registration):

        # Find the registration json path
        current_dir = Path(__file__).parent

        registrationJsonPath = "{}{}".format(reg.getid(), ".json")

        relative_path = current_dir / "../database/registrations" / registrationJsonPath

        with open(relative_path, "w") as file:
            # json.dump(courseSectionDictionary, file, indent=4)
            json.dump(reg.toDict(), file, indent=4)