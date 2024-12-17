from main.DepartmentScheduler import DepartmentScheduler
from pathlib import Path
import json


class DepartmentSchedulerDBController:

    __departmentScheduler : DepartmentScheduler

    def loadDepartmentScheduler(self,departmentSchedulerID):

        current_dir = Path(__file__).parent

        departmentSchedulerJsonPath = "{}{}".format(departmentSchedulerID, ".json")

        relative_path = current_dir / "../database/departmentSchedulers" / departmentSchedulerJsonPath

        if not relative_path.is_file():
            return False
        else:
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            departmentScheduler = DepartmentScheduler(**data)
            self.setDepartmentScheduler(departmentScheduler)
            return True

    def getDepartmentScheduler(self):
        return self.__departmentScheduler

    def setDepartmentScheduler(self, departmentScheduler):
        self.__departmentScheduler = departmentScheduler