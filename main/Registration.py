from main import CourseSection


class Registration:


    def __init__(self, id, courseSections, registrationStatus):
        self.__id = id
        self.__courseSections = courseSections
        self.__registrationStatus = registrationStatus

    def getid (self):
        return self.__id

    def setid (self,id):
       self.__id = id

    def addCourseSection(self,courseSectionAdded):
        courseSectionAdded: CourseSection
        if(len(self.__courseSections) >= 5):
            print("WARNING ! : (Total course number exceeds 5" + courseSectionAdded.getCourse['id'] + " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
            return False
        if (courseSectionAdded != None) :
            for courseSection in self.__courseSections :
                if (courseSectionAdded.getCourse()['id'] == (courseSection.getCourse()['id']) and courseSectionAdded.getSectionNumber() == courseSection.getSectionNumber()):
                    print("WARNING ! : (Same course and section exists in your registration) " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() , " cannot added to registration")
                    return False

                elif (courseSectionAdded.getCourse()['id'] == (courseSection.getCourse()['id']) and courseSectionAdded.getSectionNumber() != courseSection.getSectionNumber()):
                    print("WARNING ! : (You try to select a second section for the same course) " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() , " cannot added to registration")
                    return False



            self.__courseSections.append(courseSectionAdded)
            print(courseSectionAdded.getCourse()['id'] + " - ", courseSectionAdded.getSectionNumber() , " added to registration")
            return True

        else:
            print("WARNING ! : " + courseSectionAdded.getCourseId() + " - " , courseSectionAdded.getSectionNumber() ," cannot added to registration")
            return False

    def __init__(self):
        self.__courseSections = []
        self.__registrationStatus = 1


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