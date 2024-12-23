from pathlib import Path
import json
from typing import List
from CustomEncoder import CustomEncoder

class Lecturer :
    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __departmentIds : List[int]

    def __init__(self):
        pass

    def to_dict(self):
        """
        Convert the object to a dictionary representation that is JSON-serializable.
        """
        return {
            "id": self.__id,
            "firstName": self.__firstName,
            "lastName": self.__lastName,
            "password": self.__password,
            "departmentIds": self.__departmentIds,  # Assumes it's a list
        }

    def getId(self):
        return self.__id

    def setId(self, id):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self, firstName):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self, lastName):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self, password):
        self.__password = password

    def getDepartmentIds(self):
        return self.__departmentIds

    def setDepartmentIds(self, departmentIds):
        self.__departmentIds = departmentIds
