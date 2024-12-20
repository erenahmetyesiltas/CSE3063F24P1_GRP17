from contextlib import nullcontext
from pathlib import Path
from io import StringIO
import json

class Admin:
    __id : str
    __firstName : str
    __lastName : str 
    __password : str

    def __init__(self, id, firstName, lastName, password):
        self.__id = id
        self.__firstName = firstName
        self.__lastName = lastName
        self.__password = password

    def createNewStudent(self, student):
        current_dir = Path(__file__).parent

        studentJsonPath = "{}{}".format(student["id"],".json")

        relative_path = current_dir / "../database/students" / studentJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(student, json_file, indent=4)

        self.createNewRegisterForStudent(student)
        
    def createNewRegisterForStudent(self, student):
        current_dir = Path(__file__).parent
        studentRegisterJsonPath = "{}{}".format(student["registrationId"],".json")

        relative_path = current_dir / "../database/registrations" / studentRegisterJsonPath

        register = {
                "id": student["registrationId"],
                "courseSections": [],
                "registrationStatus": 0
            }

        # Save Student Register JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(register, json_file, indent=4)
        

    def createNewAdvisor(self, advisor):
        current_dir = Path(__file__).parent

        advisorJsonPath = "{}{}".format(advisor["id"],".json")

        relative_path = current_dir / "../database/advisors" / advisorJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(advisor, json_file, indent=4)

    
    def createNewDepartmentScheduler(self, departmentScheduler):
        current_dir = Path(__file__).parent

        departmentSchedulerJsonPath = "{}{}".format(departmentScheduler["id"],".json")

        relative_path = current_dir / "../database/departmentSchedulers" / departmentSchedulerJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(departmentScheduler, json_file, indent=4)

    

    #Getter , Setter methods
    def getId(self):
        return self.__id

    def setId(self,id):
        self.__id = id

    def getFirstName(self):
        return self.__firstName

    def setFirstName(self, firstName):
        self.__firstName = firstName

    def getLastName(self):
        return self.__lastName

    def setLastName(self,lastName):
        self.__lastName = lastName

    def getPassword(self):
        return self.__password

    def setPassword(self,password):
        self.__password = password