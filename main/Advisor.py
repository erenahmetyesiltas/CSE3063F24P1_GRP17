from contextlib import nullcontext
from io import StringIO
from pathlib import Path
from typing import List
import json
import Student
from main.Registration import Registration




class Advisor:
    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __registrationsIDs = []
    __supervisedStudentsIDs = []
    __supervisedStudents = []
    __registrations = []

    def __init__(self, id = "", firstName = "", lastName = "", password = "", registrationsIDs = [], supervisedStudentsIDs = [], supervisedStudents = [], registrations = []):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__registrationIDs = registrationsIDs
        self.__supervisedStudentsIDs = supervisedStudentsIDs

        if (len(supervisedStudents) != 0 and type(supervisedStudents[0]) == dict) :
            for i in range(len(supervisedStudents)) :
                supervisedStudents[i] = Student(**supervisedStudents[i])

            self.__supervisedStudents = supervisedStudents
        else :
            self.__supervisedStudents = supervisedStudents

        if (len(registrations) != 0 and type(registrations[0]) == dict) :
            for i in range(len(registrations)):
                registrations[i] = Registration(**registrations[i])

            self.__registrations = registrations
        else :
            self.__registrations = registrations

    def to_dict(self):
        return {
            "id": self.__id,
            "firstName": self.__firstName,
            "lastName": self.__lastName,
            "password": self.__password,
            "registrationsIDs": self.__registrationIDs,
            "supervisedStudentsIDs": self.__supervisedStudentsIDs
        }

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

    def getRegistrationIDs(self):
        return self.__registrationsIDs

    def setRegistrationIDs(self, registrationsIDs):
        self.__registrationsIDs = registrationsIDs

    def getSupervisedStudentsIDs(self):
        return self.__supervisedStudentsIDs

    def setSupervisedStudentsIDs(self, supervisedStudentsIDs):
        self.__supervisedStudentsIDs = supervisedStudentsIDs

    def getSupervisedStudents(self):
        return self.__supervisedStudents

    def setSupervisedStudents(self, supervisedStudents):
        self.__supervisedStudents = supervisedStudents

    def getRegistrations(self):
        return self.__registrations

    def setRegistrations(self,registrations):
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