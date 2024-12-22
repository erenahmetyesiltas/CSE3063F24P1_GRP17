

class Lecturer :
    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __departmentIds : []

    def __init__(self, id = "", firstName = "", lastName = "" ,password = "", departmentIds = ""):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__departmentIds = departmentIds

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
