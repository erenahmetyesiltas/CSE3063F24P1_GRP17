from pathlib import Path
from main.Advisor import Advisor
import json

class AdvisorDBController:

    __advisor : Advisor

    def __init__(self):
        pass

    def loadAdvisor(self,advisorId):

        current_dir = Path(__file__).parent

        advisorJsonPath = "{}{}".format(advisorId,".json")

        relative_path = current_dir / "../database/advisors" / advisorJsonPath

        if not relative_path.is_file():
            return False
        else:
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            advisor = Advisor(**data)
            self.setAdvisor(advisor)
            return True
    
    def createNewAdvisor(self, advisor):
        current_dir = Path(__file__).parent

        advisorJsonPath = "{}{}".format(advisor["id"],".json")

        relative_path = current_dir / "../database/advisors" / advisorJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(advisor, json_file, indent=4)


    def getAdvisor(self):
        return self.__advisor

    def setAdvisor(self,advisor):
        self.__advisor = advisor