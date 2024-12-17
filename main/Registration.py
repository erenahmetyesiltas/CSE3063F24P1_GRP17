class Registration:
    __id : str
    __courseSections:list
    __registrationStatus : int

    def __init__(self):
        pass

    def getid (self):
        return self.__id

    def setid (self,id):
       self.__id = id

    def addCourseSection(self,courseSectionAdded):
        if(len(self.__courseSections) >= 5):
            print("WARNING ! : (Total course number exceeds 5" + courseSectionAdded.getCourse.getId() + " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
            return False
        if (courseSectionAdded != None) :
            for courseSection in self.__courseSections :
                if (courseSectionAdded.getCourse().getId().equals(courseSection.getCourse().getId()) and courseSectionAdded.getSectionNumber() == courseSection.getSectionNumber()):
                    print("WARNING ! : (Same course and section exists in your registration) " + courseSectionAdded.getCourseId() + " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
                    return False

                elif (courseSectionAdded.getCourse().getId()==(courseSection.getCourse().getId()) and courseSectionAdded.getSectionNumber() != courseSection.getSectionNumber()):
                    print("WARNING ! : (You try to select a second section for the same course) " + courseSectionAdded.getCourseId() + " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
                    return False



            self.__courseSections.append(courseSectionAdded)
            print(courseSectionAdded.getCourse().getId() + " - " + courseSectionAdded.getSectionNumber() + " added to registration")
            return True

        else:
            print("WARNING ! : " + courseSectionAdded.getCourseId() + " - " + courseSectionAdded.getSectionNumber() + " cannot added to registration")
            return False

    def __init__(self):
        self._course_sections = []
        self._registration_status = 0


    def get_course_sections(self):
        return self._course_sections


    def set_course_sections(self, course_sections):
        self._course_sections = course_sections


    def delete_course_section_list(self):
        self._course_sections.clear()


    def get_registration_status(self):
        return self._registration_status


    def set_registration_status(self, status):
        self._registration_status = status