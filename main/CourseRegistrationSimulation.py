from LoginSystem import LoginSystem
from databaseController.AdvisorDBController import AdvisorDBController
from databaseController.DepartmentSchedulerDBController import DepartmentSchedulerDBController
from databaseController.StudentDBController import StudentDBController
from databaseController.RegistrationDBController import RegistrationDBController


class CourseRegistrationSimulation:
    #CourseRegistrationSystem courseRegSystem
    #LoginSystem loginSystem;
    #StudentDBController studentDBController;
    #AdvisorDBController advisorDBController;
    #DepartmentSchedulerDBController departmentSchedulerDBController;
    #CourseDBController courseDBController;
    #Logger logger; // Logger instance
    #Scanner scanner;

    #RegistrationDBController registrationDBController;
    def __init__(self, courseRegSystem):
        """
        self.student_db_controller = StudentDBController()
        self.advisor_db_controller = AdvisorDBController()
        self.department_scheduler_db_controller = DepartmentSchedulerDBController()
        self.registration_db_controller = RegistrationDBController()
        self.course_db_controller = CourseDBController()
        self.course_reg_system = course_reg_system
        self.login_system = LoginSystem(
            self.student_db_controller,
            self.advisor_db_controller,
            self.department_scheduler_db_controller,
        )
        self.logger = SingletonLogger.getInstance().getLogger()
        self.scanner = input  # Python'da giriş alırken kullanılacak
        """

        self.__registrationDBController = RegistrationDBController()
        self.__advisorDBController = AdvisorDBController()
        self.__student_db_controller = StudentDBController()
        self.__departmentSchedulerDBController = DepartmentSchedulerDBController()
        self.__courseRegistrationSystem = courseRegSystem
        self.__loginSystem = LoginSystem(self.__student_db_controller,
                                         self.__advisorDBController,
                                         self.__departmentSchedulerDBController)
        self.attribute = 0
    
    def run(self):
        try:
            #self.logger.info("Simulation started.")
            while True:
                print("Please select an option:")
                print("1- Advisor Login")
                print("2- Student Login")
                print("3- Department Scheduler Login")
                print("4- Log Out")

                choice = input("Enter your choice: ")

                # Check whether input is an integer or not
                if not choice.isdigit():
                    print("Invalid input. Please enter a number.")
                    continue  # Return back to menu

                user_choice = int(choice)

                if user_choice == 1:
                    self.loginAdvisor()
                elif user_choice == 2:
                    self.login_student()
                elif user_choice == 3:
                    self.loginDepartmentScheduler()
                elif user_choice == 4:
                    self.logout()
                else:
                    print("Invalid choice.")
        except Exception as e:
            #self.logger.severe(f"Unexpected error: {str(e)}")
            print(f"An error occurred: {str(e)}")
        #self.logger.info("Simulation ended.")

    def login_student(self):
        """Handles student login functionality."""
        try:
            print("----------Student Login----------")
            student_id = input("Enter your Student ID: ")
            password = input("Enter your password: ")

            if self.__loginSystem.authenticateStudentUser(student_id, password):
                student = self.__loginSystem.getStudent()
                if student is not None:
                    print("Login successful!")
                    self.handleStudentActions(student)
                else:
                    print("Login failed. No matching student found.")
            else:
                print("Invalid ID or password.")
        except Exception as e:
            print(f"An error occurred during student login: {str(e)}")
    

    def handleStudentActions(self, student):
        """Handles post-login actions for students."""
        try:
            while True:
                print("----------Student Actions----------")
                print("1- Create a Registration")
                print("2- Check Registration Status")
                print("3- Print Weekly Schedule")
                print("4- Log Out")

                choice = input("Please choose an action: ")

                if not choice.isdigit():
                    print("Invalid input. Please enter a number.")
                    continue

                user_choice = int(choice)

                if user_choice == 1:
                    self.createRegistration(student)
                elif user_choice == 2:
                    self.__courseRegistrationSystem.getStudentRegistrationStatus(student)
                elif user_choice == 3:
                    student.printWeeklyScheduleAsTable(student)
                elif user_choice == 4:
                    self.logout()
                    break
                else:
                    print("Invalid choice.")
        except Exception as e:
            print(f"An error occurred while handling student actions: {str(e)}")


    def createRegistration(self, student):
        #self.logger.info("Creating a new registration")
        try:
            #self.logger.info(f"Creating registration for student: {student.getId()}")
            # self.courseRegSystem.printSuitableCourses()
            self.courseRegSystem.printSuitableCoursesRemake(student)

            while True:
                addCourse = input("\nDo you want to add courses? (y/n): ").strip()
                if addCourse.lower() == "y":
                    self.courseRegSystem.readCourses(student)
                elif addCourse.lower() == "n":
                    break
                else:
                    print("Invalid input. Please enter 'y' or 'n'.")

            print("The courses inside your registration are:")
            for courseSection in student.getRegistration().getCourseSections():
                print(f"{courseSection.getCourseId()} - {courseSection.getSectionNumber()}")

            print("\n----------System is checking eligibility----------\n")
            # Eligibility check implemented here

            requestChoice = input("Are you sure you want to send the registration request to your advisor? (y/n): ").strip()
            if requestChoice.lower() == "y":
                self.courseRegSystem.sendRegistrationToAdvisor(student.getRegistration(), student)
                print("SUCCESS: The registration request has been sent to your advisor\n")
            else:
                student.setRegistration(None)
                print("WARNING: The registration you have created has been deleted. Make a new one\n")

        except Exception as e:
            pass
            #self.logger.severe(f"An error occurred creating registration for student: {str(e)}")



    def loginAdvisor(self):
        try:
            #self.logger.info("Handling advisor login")

            nickname : str = input("Enter your nickname: ")
            password : str = input("Enter your password: ")

            if self.__loginSystem.authenticateAdvisorUser(nickname,password):
                if self.__loginSystem.getAdvisor() is not None:
                    print("Login successful!")
                    self.handleAdvisorActions(self.__advisorDBController.getAdvisor())
            else:
                print("Invalid nickname or password. \n")
        except IOError as e:
            #self.logger.severe(f"Unexpected error during login advisor: {str(e)}")
            print(f"An error occurred: {str(e)}")
        except Exception as e:
            #self.logger.severe(f"Unexpected error during login advisor: {str(e)}")
            print(f"An error occurred: {str(e)}")


    def handleAdvisorActions(self, advisor):
        try:
            #self.logger.info("Handling advisor actions")
            while True:
                print()
                print("----------ACTIONS----------")
                # print("1- Check students")  # Yorumlanmış satır Python'a da eklenebilir
                print("1- Approve/Reject student registration requests")
                print("2- Log out!")
                print("Please choose an action: ", end="")

                try:
                    choice = int(input())

                    while choice not in [1, 2]:
                        print()
                        print("Please enter a valid option: ", end="")
                        choice = int(input())
                except ValueError:
                    print("Invalid input. Please enter a number.")
                    continue

                if choice == 1:
                    self.handleRegistrationRequests(advisor)
                elif choice == 2:
                    self.logout()
                    break

                continueChoice = input("Do you want to continue? (If not you will logout) (y/n): ").strip()
                if continueChoice.lower() == "n":
                    self.logout()
                    break
        except Exception as e:
            pass
            #self.logger.severe(f"Unexpected error during handleAdvisorActions: {str(e)}")


    def handleRegistrationRequests(self, advisor):
        try:
            #self.logger.info("Handling Registration Requests")

            registrations = self.__registrationDBController.getRegistrationsOfAdvisor(advisor)

            if len(registrations) == 0:
                print("There are no registrations for the advisor.")
            else:
                print()

                for i, registration in enumerate(registrations):
                    print(f"Student-{i + 1}: {registration.getId()[1:]}")

                    print("-----Course Sections-----")

                    for j, course_section in enumerate(registration.getCourseSections()):
                        print(f"Selected Course Section-{j + 1}")
                        print(course_section.getId())

                    print(f"The current status of this registration: {registration.getRegistrationStatus()}")
                    print()

                    print("In order to specify as not approved print 0")
                    print("In order to specify as not checked yet 1")
                    print("In order to specify as approved print 2")
                    status = input("Print the status to change the state of the registration listed(0/1/2): ")

                    while status not in ["0", "1", "2"]:
                        status = input("Please enter a valid number (0/1/2): ")

                    status = int(status)

                    if status == 0:
                        registration.setRegistrationStatus(status)
                        registration.setCourseSections(None)
                        self.registrationDBController.removeRegistrations(registration)
                        self.registrationDBController.updateRegistrations(registration, status)  # Update status
                    else:
                        registration.setRegistrationStatus(status)
                        self.registrationDBController.updateRegistrations(registration, status)

                print()

        except Exception as e:
            pass
            #self.logger.severe(f"Unexpected error during handleRegistrationRequests: {str(e)}")


    def loginDepartmentScheduler(self):
        try:
            #self.logger.info("Login department scheduler")

            nickname = input("Enter your nickname: ").strip()
            password = input("Enter your password: ").strip()

            if self.__loginSystem.authenticateDepartmentSchedulerUser(nickname, password):
                if self.__loginSystem.getDepartmentScheduler() is not None:
                    print("Login successful!")

                    self.handleDepartmentSchedulerActions(self.__departmentSchedulerDBController.getDepartmentScheduler())
            else:
                print("Invalid nickname or password.")
                print()

        except IOError as e:
            print(f"An error occurred: {e}")
            #self.logger.severe(f"Unexpected error during login departmentScheduler:: {str(e)}")
        except Exception as e:
            #self.logger.severe(f"Unexpected error during login departmentScheduler: {str(e)}")
            print(f"An error occurred: {str(e)}")


    def handleDepartmentSchedulerActions(self, departmentScheduler):
        try:
            while True:
                print()
                print("----------ACTIONS----------")
                print("1- Assign time and classroom to the course sections of the department")
                print("2- Logout")
                print("Please choose an action: ", end="")

                try:
                    choice = int(input())

                    while choice < 1 and choice > 2:
                        print()
                        print("Please enter a valid option: ", end="")
                        choice = int(input())
                except ValueError:
                    print("Invalid input. Please enter a number.")
                    continue

                if choice == 1:
                    self.handleCourseSectionTimesAndClassroom(departmentScheduler)
                elif choice == 2:
                    self.logout()

                continueChoice = input("Do you want to continue? (If not you will logout) (y/n): ").strip()
                if continueChoice.lower() == "n":
                    self.logout()
                    break
        except Exception as e:
            pass
            #self.logger.severe(f"Unexpected error during handleDepartmentSchedulerActions: {str(e)}")

    def handleCourseSectionTimesAndClassroom(self, departmentScheduler):
        try:
            #self.logger.info("Handling Course Section times")
            # CourseSectionList
            self.courseDBController.loadCourseSectionListOfDepartmentScheduler(departmentScheduler)

            print()
            print("------------Course Sections------------")
            for courseSection in departmentScheduler.getCourseSectionList():
                print(courseSection.getId())

            print("Please select a course section to change/assign its time and classroom")

            courseName = input("Print the course name: ").strip()
            sectionId = input("Print the course section id: ").strip()

            courseSectionId = f"{courseName}-{sectionId}"

            # to define if printed course section exists, call this function
            self.isCourseSectionExistInDepartment(courseSectionId, departmentScheduler.getCourseSectionList())

            # handle the operation about this course section
            self.handleCourseSectionSettingsMenus(courseSectionId)

        except Exception as e:
            pass
            #self.logger.severe(f"Unexpected error during handleCourseSectionTimesAndClassroom: {str(e)}")


    def handleCourseSectionSettingsMenus(self, courseSectionId):
        try:
            courseSection = self.courseDBController.loadCourseSection(courseSectionId)

            isTimeChange = False
            isClassroomChange = False
            isTimeValid = False
            isClassroomValid = False
            result = ""

            # Classroom Settings
            if courseSection.getClassroom() is None:
                yesOrNo = input(f"{courseSection.getId()}'s classroom is empty.\nDo you want to add classroom? (y/n): ").strip()

                isTrueInput = False
                while not isTrueInput:
                    if yesOrNo.lower() == "y":
                        isTrueInput = True
                        croomName = input("Please enter the classroom name: ").strip()
                        isClassroomValid = self.courseDBController.assignClassroomToCourseSection(courseSection, croomName)

                        if not isClassroomValid:
                            yesOrNo = input("Do you want to change the classroom of this course section? (y/n): ").strip()
                            isTrueInputInner = False

                            while not isTrueInputInner:
                                if yesOrNo.lower() == "y":
                                    isTrueInputInner = True
                                    isTrueInput = False
                                elif yesOrNo.lower() == "n":
                                    result += "There is no classroom for this course section.\n"
                                    isTrueInputInner = True
                                    isTrueInput = True
                                else:
                                    isTrueInput = False
                                    yesOrNo = input("Please enter a valid input (y/n): ").strip()

                    elif yesOrNo.lower() == "n":
                        result += "There is no classroom for this course section.\n"
                        break
                    else:
                        isTrueInput = False
                        yesOrNo = input("Please enter a valid input (y/n): ").strip()

            else:
                yesOrNo = input(f"{courseSection.getId()}'s classroom is {courseSection.getClassroom().getId()}.\nDo you want to change the classroom? (y/n): ").strip()

                isTrueInput = False
                while not isTrueInput:
                    if yesOrNo.lower() == "y":
                        isTrueInput = True
                        croomName = input("Please enter the classroom name: ").strip()
                        isClassroomValid = self.courseDBController.assignClassroomToCourseSection(courseSection, croomName)

                        if not isClassroomValid:
                            yesOrNo = input("Do you want to change the classroom of this course section? (y/n): ").strip()
                            isTrueInputInner = False

                            while not isTrueInputInner:
                                if yesOrNo.lower() == "y":
                                    isTrueInputInner = True
                                    isTrueInput = False
                                elif yesOrNo.lower() == "n":
                                    isTrueInputInner = True
                                    isTrueInput = True
                                else:
                                    isTrueInput = False
                                    yesOrNo = input("Please enter a valid input (y/n): ").strip()

                    elif yesOrNo.lower() == "n":
                        isClassroomValid = True
                        break
                    else:
                        isTrueInput = False
                        yesOrNo = input("Please enter a valid input (y/n): ").strip()

            # Time Settings
            if not courseSection.getScheduledTimes():
                yesOrNo = input(f"{courseSection.getId()}'s time is empty.\nDo you want to add times for this course section? (y/n): ").strip()

                isTrueInput = False
                while not isTrueInput:
                    if yesOrNo.lower() == "y":
                        isTrueInput = True
                        isTimeValid = self.courseDBController.assignTimesToCourseSection(courseSection, self.departmentSchedulerDBController.getDepartmentScheduler())

                        if not isTimeValid:
                            yesOrNo = input("Do you want to change the classroom of this course section? (y/n): ").strip()
                            isTrueInputInner = False

                            while not isTrueInputInner:
                                if yesOrNo.lower() == "y":
                                    isTrueInputInner = True
                                    isTrueInput = False
                                elif yesOrNo.lower() == "n":
                                    result += "There are no saved course section times for this course section."
                                    isTrueInputInner = True
                                    isTrueInput = True
                                else:
                                    isTrueInput = False
                                    yesOrNo = input("Please enter a valid input (y/n): ").strip()

                    elif yesOrNo.lower() == "n":
                        result += "There are no saved course section times for this course section."
                        break
                    else:
                        isTrueInput = False
                        yesOrNo = input("Please enter a valid input (y/n): ").strip()

            else:
                print(f"{courseSection.getId()}'s classroom times are:")
                for i, scheduledTime in enumerate(courseSection.getScheduledTimes()):
                    print(f"{i} ==> Day is: {scheduledTime.getCourseDay()}")
                    print(f"Start time: {scheduledTime.getStartTime()}")
                    print(f"End time: {scheduledTime.getEndTime()}")

                yesOrNo = input("Do you want to change the times (y/n): ").strip()

                isTrueInput = False
                while not isTrueInput:
                    if yesOrNo.lower() == "y":
                        isTrueInput = True
                        isTimeValid = self.courseDBController.assignTimesToCourseSection(courseSection, self.departmentSchedulerDBController.getDepartmentScheduler())

                        if not isTimeValid:
                            yesOrNo = input("Do you want to change the classroom of this course section? (y/n): ").strip()
                            isTrueInputInner = False

                            while not isTrueInputInner:
                                if yesOrNo.lower() == "y":
                                    isTrueInputInner = True
                                    isTrueInput = False
                                elif yesOrNo.lower() == "n":
                                    isTrueInputInner = True
                                    isTrueInput = True
                                else:
                                    isTrueInput = False
                                    yesOrNo = input("Please enter a valid input (y/n): ").strip()

                    elif yesOrNo.lower() == "n":
                        isTimeValid = True
                        break
                    else:
                        isTrueInput = False
                        yesOrNo = input("Please enter a valid input (y/n): ").strip()

            if isClassroomValid and isTimeValid:
                self.courseDBController.isClassroomAvailable(courseSection)
            else:
                print(f"\nAny course section hour cannot be saved because:\n{result}\n")

        except Exception as e:
            pass
            #self.logger.severe(f"Error during handling Course Section Settings Menus: {str(e)}")

    def isCourseSectionExistInDepartment(self, courseSectionId, courseSectionList):
        try:
            #self.logger.info("Handling isCourseSectionExistInDepartment")
            for courseSection in courseSectionList:
                if courseSection.getId() == courseSectionId:
                    return True

            print("Please enter the inputs correctly.")
            print()
            return False
        except Exception as e:
            #self.logger.severe(f"An error occurred during isCourseSectionExistInDepartment: {str(e)}")
            return False

    def checkStudents(self, advisor):
        print("IN THE NEXT ITERATION IT WILL BE IMPLEMENTED.")

    def logout(self):
        # Save the final state to JSON or database
        #self.logger.info("User logged out.")
        print("\nLogged out successfully!")
        exit(0)
