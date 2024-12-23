from typing import List
from pathlib import Path
import json

from main.Course import Course
from main.CourseSection import CourseSection
from main.Department import Department
from main.DepartmentScheduler import DepartmentScheduler


class CourseDBController:

    __courseSectionList : List[CourseSection]
    __courseList : List[Course]

    def __init__(self):
        pass

    def getCourseSectionList(self):
        return self.__courseSectionList

    def setCourseSectionList(self, courseSectionList : List[CourseSection]):
        self.__courseSectionList = courseSectionList

    def getCourseList(self):
        return self.__courseList

    def setCourseList(self, courseList : List[Course]):
        self.__courseList = courseList

    def loadCourseSectionListOfDepartmentScheduler(self, departmentScheduler : DepartmentScheduler):

        # Find the department json path
        current_dir = Path(__file__).parent

        departmentJsonPath = "{}{}".format(departmentScheduler.getDepartmentId(), ".json")

        relative_path = current_dir / "../database/departments" / departmentJsonPath

        department : Department

        # verify that is the file exist
        if not relative_path.is_file():
            print("There is an error about department path.")
            return []
        else:

            # get the json file data
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            # get the json data to department type
            department = Department(**data)


        # obtaining course sections from department json
        courseSection : CourseSection

        # cleaning the course section list of the department scheduler
        departmentScheduler.setCourseSectionList([])

        for i in range(len(department.getCourseSectionIds())):

            courseSectionJsonPath = "{}{}".format(department.getCourseSectionIds()[i], ".json")

            relative_path_courseSection = current_dir / "../database/courseSections" / courseSectionJsonPath

            if not relative_path_courseSection.is_file():
                print("There is an error about course section path.")
                return []

            else:
                # get the json file data
                with open(relative_path_courseSection, 'r', encoding='utf-8') as fileCS:
                    dataCourseSection = json.load(fileCS)

                # get the json data to department type
                courseSection = CourseSection(**dataCourseSection)

                # add the course section obtained to the department scheduler one by one.
                departmentScheduler.getCourseSectionList().append(courseSection)

    def loadCourseSection(self, courseSectionId : str) -> CourseSection:

        # Find the course section json path
        current_dir = Path(__file__).parent

        courseSectionJsonPath = "{}{}".format(courseSectionId, ".json")

        relative_path = current_dir / "../database/courseSections" / courseSectionJsonPath

        courseSection: CourseSection

        # verify that is the file exist
        if not relative_path.is_file():
            print("There is an error about department path.")
            return None
        else:

            # get the json file data
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            # get the json data to course section type
            courseSection = CourseSection(**data)
            return courseSection

    def getAllCourseSections(self):

        courseSections : List[CourseSection] = []

        # Find the course section json path
        current_dir = Path(__file__).parent

        #courseSectionJsonPath = "{}{}".format(courseSectionId, ".json")

        relative_path = current_dir / "../database/courseSections"

        courseSectionJsonFiles = relative_path.glob("*.json")

        courseSections = []

        for courseSectionJsonFile in courseSectionJsonFiles:
            try:
                with courseSectionJsonFile.open("r") as file:
                    data = json.load(file)
                    courseSection = CourseSection(**data)
                    courseSections.append(courseSection)

            except Exception as e:
                print("Error json file course section")

        return courseSections
    
    def saveCourseSection(self, courseSection):
        courseSectionId = courseSection.getId()

        # Find the course section json path
        current_dir = Path(__file__).parent

        courseSectionJsonPath = "{}{}".format(courseSectionId, ".json")

        relative_path = current_dir / "../database/courseSections" / courseSectionJsonPath
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(courseSection.toDict(), json_file, indent=2)