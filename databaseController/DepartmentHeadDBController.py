from main.DepartmentHead import DepartmentHead
from pathlib import Path
import json

class DepartmentHeadDBController:

    __departmentHead : DepartmentHead

    def loadDepartmentHead(self,departmentHeadID):

        current_dir = Path(__file__).parent

        departmentHeadJsonPath = "{}{}".format(departmentHeadID, ".json")

        relative_path = current_dir / "../database/departmentHeads" / departmentHeadJsonPath

        if not relative_path.is_file():
            return False
        else:
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            departmentHead = DepartmentHead(**data)
            self.setDepartmentHead(departmentHead)
            return True

    def createNewDepartmentHead(self, departmentHead):
        current_dir = Path(__file__).parent

        DepartmentHeadJsonPath = "{}{}".format(departmentHead["id"],".json")

        relative_path = current_dir / "../database/departmentHeads" / DepartmentHeadJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(departmentHead, json_file, indent=4)

    def getDepartmentHead(self):
        return self.__departmentHead

    def setDepartmentHead(self, departmentHead):
        self.__departmentHead = departmentHead