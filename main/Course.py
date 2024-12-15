

class Course :
    __id : str
    __credit : int
    __departments : []
    __term : int
    __prerequisiteCourses : []
    __name : str
    __weeklyCourseHours : int

    def __init__(self):
        pass

    def getId(self):
        return self.__id