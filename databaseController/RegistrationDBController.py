from pathlib import Path
import json
from main import Advisor
from main.Registration import Registration
from main.CustomEncoder import CustomEncoder


class RegistrationDBController:
    __registration: Registration

    def main(self, registration = ""):
        self.__registration = registration

    def getRegistrationsOfAdvisor(self, advisor):

        advisor.getRegistrations().clear()

        for id in advisor.getRegistrationIDs() :
            current_dir = Path(__file__).parent

            regJsonPath = f"{id}.json"
            relative_path = current_dir / "../database/registrations" / regJsonPath

            if not relative_path.is_file():
                return False  # File not found
            else:
                try:
                    with open(relative_path, 'r', encoding='utf-8') as file:
                        data = json.load(file)
                        reg = Registration(**data)
                        advisor.getRegistrations.append(reg)
                except json.JSONDecodeError:
                    print(f"Error decoding JSON for registration ID {id}")
                    return False
                except Exception as e:
                    print(f"An unexpected error occurred: {e}")
                    return False

        return advisor.getRegistrations()

    def updateRegistrations(self, registration : Registration, status : int):
            id = registration.getId()

            current_dir = Path(__file__).parent

            regJsonPath = f"{id}.json"
            relative_path = current_dir / "../database/registrations" / regJsonPath

            if not relative_path.is_file():
                return False  # File not found
            else:
                try:
                    with open(relative_path, 'r', encoding='utf-8') as file:
                        data = json.load(file)
                        reg = Registration(**data)
                        reg.setRegistrationStatus(status)
                except json.JSONDecodeError:
                    print(f"Error decoding JSON for registration ID {id}")
                    return False
                except Exception as e:
                    print(f"An unexpected error occurred: {e}")
                    return False

            with open(relative_path, "w") as json_file:
                json.dump(reg, json_file,cls=CustomEncoder, indent=4)


