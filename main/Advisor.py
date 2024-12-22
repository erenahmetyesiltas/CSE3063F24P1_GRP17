from contextlib import nullcontext
from io import StringIO

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