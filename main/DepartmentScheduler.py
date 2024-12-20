import json
from dataclasses import asdict
from datetime import time, datetime
from importlib.metadata import files
from pathlib import Path

#from databaseController.CourseDBController import CourseDBController
from main.Classroom import Classroom
from main.Course import Course
from main.CourseSection import CourseSection
from main.CourseTime import CourseTime
from main.Staff import Staff
from typing import List

class DepartmentScheduler(Staff):

    __id : str
    __firstName : str
    __lastName : str
    __password : str
    __departmentId : str
    __courseSectionList : List[CourseSection]

    def __init__(self, id: str, firstName: str, lastName: str, password: str, departmentId: str, courseSectionList: List[CourseSection]):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password
        self.__departmentId = departmentId
        self.__courseSectionList = courseSectionList

    def getId(self):
        return self.__id

    def setId(self, id : str):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self,firstName : str):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self, lastName: str):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self, password : str):
        self.__password = password

    def getDepartmentId(self):
        return self.__departmentId

    def setDepartmentId(self, departmentId : str):
        self.__departmentId = departmentId

    def getCourseSectionList(self):
        return self.__courseSectionList

    def setCourseSectionList(self, courseSectionList : List[CourseSection]):
        self.__courseSectionList = courseSectionList

    def assignClassroomToCourseSection(self, courseSection : CourseSection, croomName : str):

        # Find the classroom json path
        current_dir = Path(__file__).parent

        classroomJsonPath = "{}{}".format(croomName, ".json")

        relative_path = current_dir / "../database/classrooms" / classroomJsonPath

        classroom: Classroom

        # verify that is the file exist
        if not relative_path.is_file():
            print("There is an error about the classroom path.")
            return False
        else:

            # get the json file data
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            # get the json data to department type
            classroom = Classroom(**data)

        # compare the course section quota and classroom capacity
        if int(courseSection.getCapacity()) > int(classroom.getCapacity()):
            print("The classroom capacity is not enough for this course section.")
            return False
        else:
            courseSection.setClassroom(classroom)
            return True

        """ # Find the course section json path
        current_dir = Path(__file__).parent

        courseSectionJsonPath = "{}{}".format(croomName, ".json")

        relative_pathCourseSection = current_dir / "../database/courseSections" / courseSectionJsonPath

        courseSection: CourseSection

        # verify that is the file exist
        if not relative_pathCourseSection.is_file():
            print("There is an error about the course section path.")
            return False
        else:

            # get the json file data
            with open(relative_path, 'r', encoding='utf-8') as file:
                data2 = json.load(file)

            # get the json data to department type
            courseSection = CourseSection(**data2)
            return True
        """

    def assignClassroomToCourseSectionWithNoDoubt(self, courseSection : CourseSection):
        #with open(file_path, "w") as file:
        #    json.dump(data, file, indent=4)
        # değişmiş dosyayı pathe aktar

        # Find the department json path
        current_dir = Path(__file__).parent

        courseSectionJsonPath = "{}{}".format(courseSection.getId(), ".json")

        relative_path = current_dir / "../database/courseSections" / courseSectionJsonPath

        #courseSectionDictionary = asdict(courseSection)

        with open(relative_path,"w") as file:
            #json.dump(courseSectionDictionary, file, indent=4)
            json.dump(courseSection.toDict(), file, indent=4)



    def assignTimesToCourseSection(self, courseSection : CourseSection):

        # checking the course section has any course time before
        if len(courseSection.getScheduledTimes()) > 0:
            print("WARNING!!! If you want to change not all the courses please enter the",
                    "course section hours you want to keep it same and new time for what you want to change course section hours")

        # clearing the old course times
        courseSection.setScheduledTimes([])

        print("There are",courseSection.getCourse()["weeklyCourseHours"],"lesson hourses for this course.")



        # Course Section Hours Enrolling Attempt
        for i in range(courseSection.getCourse()["weeklyCourseHours"]):

            nthCourseSectionHour = i+1
            courseTime = CourseTime()

            day = input(f"Enter a day for the course hour {nthCourseSectionHour}:").strip()

            #input(
            #    f"{courseSection.getId()}'s classroom is empty.\nDo you want to add classroom? (y/n): ").strip()

            courseTime.setCourseDay(day)
            #print(courseTime.getCourseDay())

            #print("Enter a start time for the course hour " , nthCourseSectionHour , ": ")
            startTime = input(f"Enter a start time for the course hour {nthCourseSectionHour}:").strip()
            #courseTime.setStartTime(datetime.strptime(startTime, "%H:%M:%S").time())
            courseTime.setStartTime(startTime)
            #print(courseTime.getStartTime())

            endTime = "{}{}".format(startTime[0:3],"50:00")
            #courseTime.setEndTime(datetime.strptime(endTime, "%H:%M:%S").time())
            courseTime.setEndTime(endTime)

            courseSection.getScheduledTimes().append(courseTime)

        enteredValidCourseSectionTime = len(courseSection.getScheduledTimes())


        # generate a new course section list without the selected course section
        courseSectionWithoutTheCourseSection : List[CourseSection]
        courseSectionWithoutTheCourseSection = self.getCourseSectionList()

        # remove the selected course section from course section list
        for i in range(len(self.getCourseSectionList())):
            if self.getCourseSectionList()[i].getId() == courseSection.getId():
                courseSectionWithoutTheCourseSection.remove(self.getCourseSectionList()[i])
                break

        # If conflict its times with other same department course sections
        # Iterator should be used (!!! must be change !!!)

        iterator = iter(courseSection.getScheduledTimes())

        while True:

            try:
                scheduledTime = next(iterator)

                for item in list(iterator):
                    for j in range((len(courseSectionWithoutTheCourseSection))):

                        if courseSectionWithoutTheCourseSection[j].getCourse()["term"] != courseSection.getCourse()["term"]:
                            continue
                        elif len(courseSectionWithoutTheCourseSection[j].getScheduledTimes()) == 0:
                            continue
                        #elif courseSection.getClassroom()["id"] == "UZEM":
                        #    continue
                        else:
                            for k in range(len(self.getCourseSectionList()[j].getScheduledTimes())):

                                if (item.getCourseDay() ==
                                        courseSectionWithoutTheCourseSection[j].getScheduledTimes()[k]["courseDay"]
                                        and item.getStartTime() ==
                                        courseSectionWithoutTheCourseSection[j].getScheduledTimes()[k]["startTime"]
                                        and item.getEndTime() ==
                                        courseSectionWithoutTheCourseSection[j].getScheduledTimes()[k]["endTime"]):
                                    print(
                                        "\nThere is a time conflict with another course section hours, for this reason the time removed.")
                                    print("Day:", item.getCourseDay())
                                    print("Start Time:", item.getStartTime())
                                    print("End Time:", item.getEndTime())
                                    courseSection.getScheduledTimes().remove(item)

            except StopIteration:
                break

        if enteredValidCourseSectionTime > 0:
            return True
        else:
            print("Any course section is not added.")
            return False


    def assignTimesToCourseSectionWithNoDoubt(self, courseSection: CourseSection):

        current_dir = Path(__file__).parent

        courseSectionJsonPath = "{}{}".format(courseSection.getId(), ".json")

        relative_path = current_dir / "../database/courseSections" / courseSectionJsonPath

        #courseSectionDictionary = asdict(courseSection)

        with open(relative_path, "w") as file:
            json.dump(courseSection.toDict(), file, indent=4)

    def isClassroomAvailable(self, courseSection : CourseSection):

        #courseDBController = CourseDBController()
        # allCourseSectionsList = courseDBController.getAllCourseSections()
        allCourseSectionsList = self.getAllCourseSections()

        index : int
        removedCourseSection : CourseSection

        # discard the course section from the all course section list to compare others
        for i in range(len(allCourseSectionsList)):
            if courseSection.getId() == allCourseSectionsList[i].getId():
                removedCourseSection = allCourseSectionsList[i]
                break

        allCourseSectionsList.remove(removedCourseSection)

        # Iterator usage for is the classroom and time is available for only one course section
        iterator = iter(courseSection.getScheduledTimes())



        while True:
            try:
                scheduledTime = next(iterator)

                if(courseSection.getClassroom() is None):
                    print("None dur ha")
                else:

                    classroom = courseSection.getClassroom()
                    #print(classroom.getId())

                for i in range(len(allCourseSectionsList)):
                    if allCourseSectionsList[i].getClassroom() == None:
                        continue
                    # if they have the different classroom then continue
                    elif (classroom["id"] != allCourseSectionsList[i].getClassroom()["id"]
                          or classroom["id"] == "UZEM"):
                        continue
                    # if any course section hour not in a course section, then continue
                    elif len(allCourseSectionsList[i].getScheduledTimes()) == 0:
                        continue

                    for j in range(len(allCourseSectionsList[i].getScheduledTimes())):
                        if (scheduledTime.getCourseDay() == allCourseSectionsList[i].getScheduledTimes()[j]["courseDay"]
                                and scheduledTime.getStartTime() == allCourseSectionsList[i].getScheduledTimes()[j]["startTime"]
                                and scheduledTime.getEndTime() == allCourseSectionsList[i].getScheduledTimes()[j]["endTime"]):
                            print("The course section was removed since the classroom was already used for another course section hours at the time.")
                            print("Day:",scheduledTime.getCourseDay())
                            print("Start Time:",scheduledTime.getStartTime())
                            print("End Time:",scheduledTime.getEndTime())
                            print("Classroom:",courseSection.getClassroom()["id"])

                            # be careful, it may be wrong
                            courseSection.getScheduledTimes().remove(scheduledTime)

            except StopIteration:
                break


        # control is there any course section hour valid
        if len(courseSection.getScheduledTimes()) == 0:
            print("Any course section hours cannot be saved.")
        else:

            self.assignClassroomToCourseSectionWithNoDoubt(courseSection)
            self.assignTimesToCourseSectionWithNoDoubt(courseSection)

            print()
            print("The course section hours", len(courseSection.getScheduledTimes()),"/",courseSection.getCourse()["weeklyCourseHours"],"are saved.")
            print("The saved course sections are:")

            croom = courseSection.getClassroom()

            print("Classroom:",courseSection.getClassroom()["id"])

            for i in range(len(courseSection.getScheduledTimes())):
                print((i+1),"==> Day:",courseSection.getScheduledTimes()[i].getCourseDay())
                print("Start Time:",courseSection.getScheduledTimes()[i].getStartTime())
                print("End Time:",courseSection.getScheduledTimes()[i].getEndTime())

            print("Course section hours are saved successfully.")

    def getAllCourseSections(self):

        courseSections: List[CourseSection]

        courseSections = []

        # Find the course section json path
        current_dir = Path(__file__).parent

        # courseSectionJsonPath = "{}{}".format(courseSectionId, ".json")

        relative_path = current_dir / "../database/courseSections"

        courseSectionJsonFiles = relative_path.glob("*.json")

        courseSection: CourseSection

        for courseSectionJsonFile in courseSectionJsonFiles:
            try:
                with courseSectionJsonFile.open("r") as file:
                    data = json.load(file)
                    courseSection = CourseSection(**data)
                    courseSections.append(courseSection)

            except Exception as e:
                print("Error json file course section")

        #print(len(courseSections))

        return courseSections