from typing import List

#from CourseSection import CourseSection


class Registration:
    __id : str
    __courseSections : List
    __registrationStatus : int


    def __init__(self, id = "", courseSections = [], registrationStatus = 1):
        self.__id = id

        if (len(courseSections) != 0 and type(courseSections[0]) == dict):
            for i in range(len(courseSections)):
                from CourseSection import CourseSection
                courseSections[i] = CourseSection(**courseSections[i])

            self.__courseSections = courseSections
        else:
            self.__courseSections = courseSections

        self.__registrationStatus = registrationStatus

    def to_dict(self):
        return{
            "id": self.__id,
            "courseSections": [courseSection.to_dict() if hasattr(courseSection, "to_dict") else courseSection for courseSection in self.__courseSections] if self.__courseSections else [],
            "registrationStatus": self.__registrationStatus
        }
    def getId (self):
        return self.__id

    def setId (self, id):
        self.__id = id

    def addCourseSection(self,courseSectionAdded):
        from CourseSection import CourseSection
        courseSectionAdded: CourseSection
        if(len(self.__courseSections) >= 5):
            print("WARNING ! : (Total course number exceeds 5" + courseSectionAdded.getCourse.getId()+ " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
            return False
        if (courseSectionAdded != None) :
            for courseSection in self.__courseSections :
                if (courseSectionAdded.getCourse().getId() == (courseSection.getCourse().getId()) and courseSectionAdded.getSectionNumber() == courseSection.getSectionNumber()):
                    print("WARNING ! : (Same course and section exists in your registration) " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() , " cannot added to registration")
                    return False

                elif (courseSectionAdded.getCourse().getId() == (courseSection.getCourse().getId()) and courseSectionAdded.getSectionNumber() != courseSection.getSectionNumber()):
                    print("WARNING ! : (You try to select a second section for the same course) " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() , " cannot added to registration")
                    return False



            self.__courseSections.append(courseSectionAdded)
            print(courseSectionAdded.getCourse().getId() + " - ", courseSectionAdded.getSectionNumber() , " added to registration")
            return True

        else:
            print("WARNING ! : " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() ," cannot added to registration")
            return False


    def getCourseSections(self) :
        return self.__courseSections


    def setCourseSections(self, course_sections):
        self.__courseSections = course_sections


    def deleteCourseSectionList(self):
        self.__courseSections.clear()


    def getRegistrationStatus(self):
        return self.__registrationStatus


    def setRegistrationStatus(self, status):
        self.__registrationStatus = status