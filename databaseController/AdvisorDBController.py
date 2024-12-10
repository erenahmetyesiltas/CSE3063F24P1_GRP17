from pathlib import Path
from main.Advisor import Advisor
import json

class AdvisorDBController:

    __advisor : Advisor

    def loadAdvisor(self,advisorId):

        current_dir = Path(__file__).parent

        advisorJsonPath = "{}{}".format(advisorId,".json")

        relative_path = current_dir / "../database/advisor" / advisorJsonPath

        if not relative_path.is_file():
            return False
        else:
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            advisor = Advisor(**data)
            self.setAdvisor(advisor)
            return True



    def getAdvisor(self):
        return self.__advisor

    def setAdvisor(self,advisor):
        self.__advisor = advisor