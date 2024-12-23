from datetime import datetime, time


class CourseTime:
    __startTime : str
    __endTime : str
    __courseDay : str

    #def __init__(self, startTime : datetime, endTime : datetime, courseDay : str):
    #    self.__startTime = startTime
    #    self.__endTime = endTime
    #    self.__courseDay = courseDay


    def getStartTime(self):
        return self.__startTime

    def setStartTime(self, startTime : str):
        self.__startTime = startTime

    def getEndTime(self):
        return self.__endTime

    def setEndTime(self, endTime: str):
        self.__endTime = endTime

    def getCourseDay(self):
        return self.__courseDay

    def setCourseDay(self, courseDay : str):
        self.__courseDay = courseDay

    def toDict(self):
        return {
            "startTime" : self.__startTime,
            "endTime" : self.__endTime,
            "courseDay" : self.__courseDay
        }