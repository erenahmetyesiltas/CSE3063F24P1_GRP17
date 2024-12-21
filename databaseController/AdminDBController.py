from pathlib import Path
from main.Admin import Admin
import json

class AdminDBController:

    __admin : Admin

    def loadAdmin(self,adminId):

        current_dir = Path(__file__).parent

        adminJsonPath = "{}{}".format(adminId,".json")

        relative_path = current_dir / "../database/admins" / adminJsonPath

        if not relative_path.is_file():
            return False
        else:
            with open(relative_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

            admin = Admin(**data)
            self.setAdmin(admin)
            return True



    def getAdmin(self):
        return self.__admin

    def setAdmin(self,admin):
        self.__admin = admin