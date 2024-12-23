import json
from pathlib import Path

from main.Advisor import Advisor
from main.Registration import Registration


class RegistrationDBController:

    def main(self):
        print()

    def getRegistrationsOfAdvisor(self, advisor : Advisor):

        # Find the registrations' json path
        current_dir = Path(__file__).parent

        # clear the list to begin the list get all the registrations
        # it must be done to provide duplicate data
        advisor.setRegistrations([])

        for i in range(len(advisor.getRegistrationIDs())):

            registrationJsonPath = "{}{}".format(advisor.getRegistrationIDs()[i], ".json")

            relative_path = current_dir / "../database/registrations" / registrationJsonPath

            registrationObj : Registration

            # verify that is the file exist
            if not relative_path.is_file():
                print("There is an error about registration path.")
                return []
            else:


                # get the json file data
                with open(relative_path, 'r', encoding='utf-8') as file:
                    data = json.load(file)

                # set the json data to registration type
                registrationObj = Registration(**data)

                advisor.getRegistrations().append(registrationObj)