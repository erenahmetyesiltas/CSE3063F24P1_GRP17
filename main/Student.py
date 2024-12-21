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
    __transcript = None

    def __init__(self, id, firstName, lastName, password, gpa, courses=None, advisorId=0,
                 departments=None, departmentIds=None, startYear=0, year=0, term=0, registrationId="", transcript=None):
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
        self.__transcript = transcript or {}


    def getTranscript(self):
        return self.__transcript

    def setTranscript(self, transcript):
        self.__transcript = transcript

    def addCourseRecordToTranscript(self, course, grade, term):
        if "courseRecords" not in self.__transcript:
            self.__transcript["courseRecords"] = []
        
        self.__transcript["courseRecords"].append({
            "course": course,
            "grade": grade,
            "term": term
        })

    def calculateGPA(self):
        if "courseRecords" not in self.__transcript or not self.__transcript["courseRecords"]:
            return 0.0

        total_credits = 0
        total_points = 0
        grade_to_points = {
            "AA": 4.0, "BA": 3.5, "BB": 3.0, "CB": 2.5, "CC": 2.0,
            "DC": 1.5, "DD": 1.0, "FD": 0.5, "FF": 0.0
        }

        for record in self.__transcript["courseRecords"]:
            grade = record["grade"]
            credit = record["course"]["credit"]
            
            if grade in grade_to_points:
                total_credits += credit
                total_points += grade_to_points[grade] * credit

        self.__gpa = total_points / total_credits if total_credits > 0 else 0.0
        return self.__gpa

    def printTranscript(self):
        if not self.__transcript or "courseRecords" not in self.__transcript:
            print("Transcript is empty.")
            return

        term_gpa = {}
        term_credits = {}
        cumulative_credits = 0
        cumulative_points = 0
        grade_to_points = {
            "AA": 4.0, "BA": 3.5, "BB": 3.0, "CB": 2.5, "CC": 2.0,
            "DC": 1.5, "DD": 1.0, "FD": 0.5, "FF": 0.0
        }

        # Calculate term GPAs
        for record in self.__transcript["courseRecords"]:
            term = record["term"]
            course = record["course"]
            grade = record["grade"]
            credit = course["credit"]

            if term not in term_gpa:
                term_gpa[term] = 0
                term_credits[term] = 0

            if grade in grade_to_points:
                term_gpa[term] += grade_to_points[grade] * credit
                term_credits[term] += credit

        # Print transcript
        print(f"{'Course ID':<10} {'Course Name':<50} {'Credit':<10} {'Grade':<10} {'Term':<5}")
        print("-" * 90)

        current_term = None
        for record in self.__transcript["courseRecords"]:
            course = record["course"]
            term = record["term"]

            # Print separator for new terms
            if term != current_term:
                if current_term is not None:
                    print("-" * 90)
                    term_gpa_value = term_gpa[current_term] / term_credits[current_term] if term_credits[current_term] > 0 else 0.0
                    cumulative_credits += term_credits[current_term]
                    cumulative_points += term_gpa[current_term]
                    cumulative_gpa = cumulative_points / cumulative_credits if cumulative_credits > 0 else 0.0
                    print(f"Term {current_term} GPA: {term_gpa_value:.2f} | Cumulative GPA: {cumulative_gpa:.2f}")
                    print("-" * 90)
                current_term = term

            print(f"{course['id']:<10} {course['name']:<50} {course['credit']:<10} {record['grade']:<10} {term:<5}")

        # Print final term's GPA and cumulative GPA
        if current_term is not None:
            print("-" * 90)
            term_gpa_value = term_gpa[current_term] / term_credits[current_term] if term_credits[current_term] > 0 else 0.0
            cumulative_credits += term_credits[current_term]
            cumulative_points += term_gpa[current_term]
            cumulative_gpa = cumulative_points / cumulative_credits if cumulative_credits > 0 else 0.0
            print(f"Term {current_term} GPA: {term_gpa_value:.2f} | Cumulative GPA: {cumulative_gpa:.2f}")
            print("-" * 90)

        # Print overall summary
        print("Summary:")
        print("-" * 90)
        print(f"Total Credits: {self.__transcript.get('totalCredit', 0):<10}")
        print(f"Final GPA: {self.__transcript.get('GPA', 0.0):.2f}")



    # Existing methods remain unchanged...

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