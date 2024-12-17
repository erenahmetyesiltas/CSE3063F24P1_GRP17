class Student:
    __id: str
    __firstName: str
    __lastName: str
    __password: str
    __gpa: float
    __courses = []
    __advisorId: int
    __departments = []
    __departmentIds = []
    __startYear: int
    __year: int
    __term: int
    __registrationId: str

    def __init__(self, id, firstName, lastName, password, gpa, courses=None, advisorId=0,
                 departments=None, departmentIds=None, startYear=0, year=0, term=0, registrationId=""):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__gpa = gpa
        self.__courses = courses or []
        self.__advisorId = advisorId
        self.__departments = departments or []
        self.__departmentIds = departmentIds or []
        self.__startYear = startYear
        self.__year = year
        self.__term = term
        self.__registrationId = registrationId


        
    def printWeeklyScheduleAsTable(self):
        if not self.__registration or not self.__registration.getCourseSections():
            print("No courses registered for this student.")
            return

        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        time_slots = [
            "08:00 - 09:00", "09:00 - 10:00",
            "10:00 - 11:00", "11:00 - 12:00",
            "12:00 - 13:00", "13:00 - 14:00",
            "14:00 - 15:00", "15:00 - 16:00",
            "16:00 - 17:00", "17:00 - 18:00"
        ]

        column_width = 25
        schedule_table = [["" for _ in days] for _ in time_slots]

        for section in self.__registration.getCourseSections():
            for time in section.getScheduledTimes():
                course_day = time.getCourseDay()
                start_time = time.getStartTime()
                end_time = time.getEndTime()

                start_index = next((i for i, slot in enumerate(time_slots) if slot.startswith(start_time[:5])), -1)
                end_index = next((i for i, slot in enumerate(time_slots) if slot.endswith(end_time[:5])), -1)
                day_index = next((i for i, day in enumerate(days) if day.lower() == course_day.lower()), -1)

                if start_index != -1 and end_index != -1 and day_index != -1:
                    for i in range(start_index, end_index + 1):
                        schedule_table[i][day_index] = f"{section.getCourseId()} ({section.getClassroom().getId()})"

        print(f"Weekly Schedule for Student ID: {self.getId()}")
        print(f"{'Time':<15}" + "".join(f"| {day:<{column_width}}" for day in days))
        print("-" * (15 + (column_width + 3) * len(days)))

        for i, time_slot in enumerate(time_slots):
            print(f"{time_slot:<15}" + "".join(f"| {schedule_table[i][j]:<{column_width}}" for j in range(len(days))))
            print("-" * (15 + (column_width + 3) * len(days)))

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

    def getGpa(self):
        return self.__gpa

    def setGpa(self, gpa):
        self.__gpa = gpa

    def getCourses(self):
        return self.__courses

    def setCourses(self, courses):
        self.__courses = courses

    def getAdvisorId(self):
        return self.__advisorId

    def setAdvisorId(self, advisorId):
        self.__advisorId = advisorId

    def getDepartments(self):
        return self.__departments

    def setDepartments(self, departments):
        self.__departments = departments

    def getDepartmentIds(self):
        return self.__departmentIds

    def setDepartmentIds(self, departmentIds):
        self.__departmentIds = departmentIds

    def getStartYear(self):
        return self.__startYear

    def setStartYear(self, startYear):
        self.__startYear = startYear

    def getYear(self):
        return self.__year

    def setYear(self, year):
        self.__year = year

    def getTerm(self):
        return self.__term

    def setTerm(self, term):
        self.__term = term

    def getRegistrationId(self):
        return self.__registrationId

    def setRegistrationId(self, registrationId):
        self.__registrationId = registrationId
