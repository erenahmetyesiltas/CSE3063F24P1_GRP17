from pathlib import Path
from main.Student import Student
import json

class StudentDBController:

    __student: Student


    def __init__(self):
        self.__student = None  # Initialize with None

    def loadStudent(self, studentId):
        """
        Load student data from a JSON file using the given student ID.
        """
        current_dir = Path(__file__).parent

        studentJsonPath = f"{studentId}.json"
        relative_path = current_dir / "../database/students" / studentJsonPath

        if not relative_path.is_file():
            return False  # File not found
        else:
            try:
                with open(relative_path, 'r', encoding='utf-8') as file:
                    data = json.load(file)

                # Create a Student object using the JSON data
                student = Student(**data)
                self.setStudent(student)
                return True
            except json.JSONDecodeError:
                print(f"Error decoding JSON for student ID {studentId}")
                return False
            except Exception as e:
                print(f"An unexpected error occurred: {e}")
                return False

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


    def getStudent(self):
        """
        Get the currently loaded student.
        """
        if self.__student is None:
            raise ValueError("No student loaded. Please call loadStudent() first.")
        return self.__student

    def setStudent(self, student):
        """
        Set the current student.
        """
        self.__student = student
