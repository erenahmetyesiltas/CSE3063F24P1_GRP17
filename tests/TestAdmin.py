import os
import sys


sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

import unittest
from unittest.mock import MagicMock, patch
from main.Admin import Admin  

class TestAdmin(unittest.TestCase):
    def setUp(self):
        """Create an admin object before test.."""
        self.admin = Admin(id="admin001", firstName="Test", lastName="Admin", password="test123")

    def test_getters(self):
        """Test getter methods."""
        self.assertEqual(self.admin.getId(), "admin001")
        self.assertEqual(self.admin.getFirstName(), "Test")
        self.assertEqual(self.admin.getLastName(), "Admin")
        self.assertEqual(self.admin.getPassword(), "test123")

    def test_setters(self):
        """Test setter methods"""
        self.admin.setId("new_id")
        self.admin.setFirstName("NewName")
        self.admin.setLastName("NewLastName")
        self.admin.setPassword("newpass123")
        
        self.assertEqual(self.admin.getId(), "new_id")
        self.assertEqual(self.admin.getFirstName(), "NewName")
        self.assertEqual(self.admin.getLastName(), "NewLastName")
        self.assertEqual(self.admin.getPassword(), "newpass123")

    @patch('databaseController.StudentDBController')
    def test_create_new_student(self, MockStudentDBController):
        """Test createNewStudent method."""
        mock_student_db_controller = MockStudentDBController.return_value
        mock_student_db_controller.createNewStudent = MagicMock()

        # Create a new partition planner with test data..
        mock_student = {"id": "123", "firstName": "John", "lastName": "Doe", "password": "password123", "registrationId":"r123000333"}
        result = self.admin.createNewStudent(mock_student)

        # Test that the method is called correctly.
        mock_student_db_controller.createNewStudent.assert_called_once_with(mock_student)

"""
    @patch('databaseController.AdvisorDBController')
    def test_create_new_advisor(self, MockAdvisorDBController):
        """"""Test create new advisor method."""""""
        mock_advisor_db_controller = MockAdvisorDBController.return_value
        mock_advisor_db_controller.createNewAdvisor = MagicMock()

        # Create a new partition planner with test data..
        mock_advisor = {"id": "321", "firstName": "Jane", "lastName": "Smith", "password": "advisorpass"}
        self.admin.createNewAdvisor(mock_advisor)

        # Test that the method is called correctly.
        mock_advisor_db_controller.createNewAdvisor.assert_called_once_with(mock_advisor)

    @patch('databaseController.DepartmentSchedulerDBController')
    def test_create_new_department_scheduler(self, MockDepartmentSchedulerDBController):
        """"""Test create new department scheduler method.""""""
        mock_department_scheduler_db_controller = MockDepartmentSchedulerDBController.return_value
        mock_department_scheduler_db_controller.createNewDepartmentScheduler = MagicMock()

        # Create a new partition planner with test data.
        mock_scheduler = {"id": "456", "name": "Scheduler1"}
        self.admin.createNewDepartmentScheduler(mock_scheduler)

        # Test that the method is called correctly.
        mock_department_scheduler_db_controller.createNewDepartmentScheduler.assert_called_once_with(mock_scheduler)
"""
if __name__ == "__main__":
    unittest.main()
