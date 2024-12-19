from contextlib import nullcontext
from io import StringIO

class Admin:
    __id : str
    __firstName : str
    __lastName : str 
    __password : str

    def __init__(self, id, firstName, lastName, password):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
    
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