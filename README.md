# CSE3063F24P1_GRP17
* İlker and Eren working on this branch. Please do not push directly.

**Iteration 2** plans and coding standards:

---

# Enhanced Course Registration System - Iteration 2

## Overview
This project is part of the **CSE3063 Term Project - Fall 2024**. Our goal for **Iteration 2** is to enhance the core functionality of the course registration system by implementing scheduling features, conflict management, and waitlist handling.

The system currently supports:
- User authentication for students and advisors.
- Basic course registration with prerequisite checks.
- Data persistence through JSON files.

In **Iteration 2**, we will introduce the **Department Scheduler** role and implement advanced functionalities to provide a comprehensive course registration experience.

---

## Iteration 2 Plans

### Objectives
- Introduce and implement the **Department Scheduler** role.
- Add **time slot and classroom assignment** for course sections.
- Implement **conflict detection** for:
    - Time overlaps in schedules.
    - Room capacity constraints.
- Develop and manage **waitlists** for courses at capacity.
- Notify students automatically when their waitlist status changes.
- Enable students to view and manage their **weekly schedules**.

### Tasks
1. **Domain Model and Design Updates**
    - Refactor UML diagrams to reflect the new features.
    - Update Use Cases and System Sequence Diagrams for Scheduler functionalities.
2. **Implementation**
    - Develop the `Scheduler` class for assigning time slots and managing room capacities.
    - Extend the `Student` and `CourseSection` classes to handle waitlist operations.
    - Enhance the `CourseRegistrationSystem` to support conflict detection and waitlist notifications.
3. **Testing**
    - Write unit tests for the new functionalities, ensuring comprehensive coverage for:
        - Conflict detection.
        - Waitlist operations.
        - Schedule management.
4. **Data Persistence**
    - Continue to use JSON files for storing course and user data.
    - Modify JSON schema to accommodate new features (e.g., time slots, waitlists).
5. **Documentation**
    - Update the Requirement Analysis Document (RAD) and Software Design Document (DCD/DSD) with new diagrams and descriptions.

### Deliverables
- Updated RAD and DSD documents (`CSE3063F24P1_RAD_GRPX_iteration2.pdf` and `CSE3063F24P1_DCD_GRPX_iteration2.pdf`).
- Implemented and tested Java code stored in the GitHub repository.
- Unit tests for at least three classes, with a minimum of five tests per class.
- Functional demo ready for Week 9.

---

## Coding Standards

To maintain code quality and consistency, the following standards will be followed:

### General Principles
1. **Follow SOLID Principles**
    - **Single Responsibility Principle:** Each class should have a single, clear purpose.
    - **Open/Closed Principle:** Code should be open for extension but closed for modification.
    - **Liskov Substitution Principle:** Derived classes must be substitutable for their base classes.
    - **Interface Segregation Principle:** Interfaces should only include methods relevant to the specific implementing class.
    - **Dependency Inversion Principle:** High-level modules should not depend on low-level modules; both should depend on abstractions.

2. **Clean Code Practices**
    - Use meaningful and descriptive names for classes, methods, and variables.
    - Avoid magic numbers; define constants for fixed values.
    - Minimize duplicate code by following the DRY (Don't Repeat Yourself) principle.
    - Write small, focused functions that perform a single task.

3. **Commenting and Documentation**
    - Document all classes and methods using Javadoc.
    - Write meaningful inline comments for complex logic or calculations.
    - Use block comments to explain higher-level architectural decisions when necessary.

4. **Version Control Best Practices**
    - **Branching Strategy:** Each feature or bug fix must be implemented on a separate branch.
    - **Code Reviews:** Submit pull requests for review before merging to the `main` branch.
    - **Do Not Push Directly to Main:** All changes to the `main` branch must be reviewed and approved.

### Specific Coding Conventions
- **Naming Conventions:**
    - Class Names: PascalCase (`Student`, `CourseSection`).
    - Method Names: camelCase (`registerCourse`, `detectConflicts`).
    - Constants: ALL_CAPS_WITH_UNDERSCORES (`MAX_COURSES`).

- **Code Structure:**
    - Place each class in its own file.
    - Group related files into appropriate packages (e.g., `models`, `controllers`, `utilities`).

- **Testing:**
    - Use JUnit for unit testing.
    - Name test classes with a `Test` suffix (e.g., `StudentTest`).
    - Follow Arrange-Act-Assert (AAA) structure in test methods.
--- 

