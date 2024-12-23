from pathlib import Path
from main.Student import Student
from main.Registration import Registration
from main.CustomEncoder import CustomEncoder
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
                self.loadStudentRegistration(student)
                self.setStudent(student)
                return True
            except json.JSONDecodeError:
                print(f"Error decoding JSON for student ID {studentId}")
                return False
            except Exception as e:
                print(f"An unexpected error occurred: {e}")
                return False

    def createNewStudent(self, student : Student):
        current_dir = Path(__file__).parent

        studentJsonPath = "{}{}".format(student.getId(),".json")

        relative_path = current_dir / "../database/students" / studentJsonPath
        
        # Save Student JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(student, json_file, indent=4)

        self.createNewRegisterForStudent(student)
        
    def createNewRegisterForStudent(self, student : Student):
        current_dir = Path(__file__).parent
        studentRegisterJsonPath = "{}{}".format(student.getRegistrationId(),".json")

        relative_path = current_dir / "../database/registrations" / studentRegisterJsonPath

        register = {
                "id": student.getRegistrationId(),
                "courseSections": [],
                "registrationStatus": 1
            }

        # Save Student Register JSON file.
        with open(relative_path, "w") as json_file:
            json.dump(register, json_file, indent=4)

    def loadStudentRegistration(self, student : Student):
        current_dir = Path(__file__).parent
        id = "r" + student.getId()
        regJsonPath = f"{id}.json"
        relative_path = current_dir / "../database/registrations" / regJsonPath

        if not relative_path.is_file():
            return False  # File not found
        else:
            try:
                with open(relative_path, 'r', encoding='utf-8') as file:
                    data = json.load(file)
                    reg = Registration(**data)
                    student.setRegistration(reg)
            except json.JSONDecodeError:
                print(f"Error decoding JSON for registration ID {id}")
                return False
            except Exception as e:
                print(f"An unexpected error occurred: {e}")
                return False

    def saveStudent(self, student : Student):
        current_dir = Path(__file__).parent
        studentId = student.getId()
        studentJsonPath = f"{studentId}.json"
        relative_path = current_dir / "../database/students" / studentJsonPath

        with open(relative_path, "w") as json_file:
            json.dump(student.to_dict(), json_file, indent=4)

    def saveStudentRegistration(self, student:Student):
        current_dir = Path(__file__).parent
        registration = student.getRegistration()
        registrationJsonPath = f"{registration.getId()}.json"
        relative_path = current_dir / "../database/registrations" / registrationJsonPath

        with open(relative_path, "w") as json_file:
            json.dump(registration.to_dict(), json_file, indent=4)


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
