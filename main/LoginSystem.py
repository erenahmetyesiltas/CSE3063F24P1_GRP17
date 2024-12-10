from databaseController.AdvisorDBController import AdvisorDBController
from main.Advisor import Advisor


class LoginSystem:

    __advisor : Advisor
    __advisorDBController : AdvisorDBController

    def getAdvisor(self):
        return self.__advisor

    def setAdvisor(self, advisor):
        self.__advisor = advisor

    def getAdvisorDBController(self):
        return self.__advisorDBController

    def setAdvisorDBController(self,advisorDBController):
        self.__advisorDBController = advisorDBController

    def __init__(self, advisorDBController):
        self.__advisorDBController = advisorDBController

    def authenticateAdvisorUser(self, advisorId, password):

        if self.__advisorDBController.loadAdvisor(advisorId):
            self.__advisor = self.__advisorDBController.getAdvisor()
        else:
            return False

        if self.__advisor.getPassword().__eq__(password):
            return True
        else:
            self.__advisor = None
            self.__advisorDBController.setAdvisor(None)
            return False