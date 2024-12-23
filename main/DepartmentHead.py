from typing import List
from DepartmentScheduler import DepartmentScheduler
from databaseController.CourseDBController import CourseDBController

class DepartmentHead(DepartmentScheduler):

    def __init__(self, id: str, firstName: str, lastName: str, password: str, departmentId: str, courseSectionList: List):
        super().__init__(id, firstName, lastName, password, departmentId, courseSectionList)

    
    def changeCourseSectionCapacity(self, courseSectionId, newCapacity):
        courseDBController = CourseDBController()
        courseSection = courseDBController.loadCourseSection(courseSectionId)
        courseSection.setCapacity(newCapacity)
        courseDBController.saveCourseSection(courseSection)