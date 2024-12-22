


class Classroom :
    __id : str
    __capacity : int

    def __init__(self, id : str = "", capacity : int = 0):
        self.__id = id
        self.__capacity = capacity

    def getId(self):
        return self.__id

    def setId(self, id : str):
        self.__id = id

    def getCapacity(self):
        return self.__capacity

    def setCapacity(self, capacity : int):
        self.__capacity = capacity

    def toDict(self):
        return {
            "id": self.__id,
            "capacity": self.__capacity
        }