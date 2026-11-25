# Employee Management System (Lab 7)

Programming II, Fall 2026
Alexandria University, Faculty of Engineering
Computer and Communication Engineering, SSP

## 1. System Description

A desktop Employee Management System built with Java Swing for the GUI and
Object-Oriented Programming for the backend. An administrator logs in and can
add, view, search, update and delete employee records. Salaries are calculated
according to the employee type, and all records are saved to a file so they are
still there after the application is closed.

### Features

- Login screen with credential validation and an Exit button
- Dashboard with a welcome message, navigation and Log Out
- Add employees (Full-Time or Part-Time) with input validation
- View all employees in a table with the correct calculated salary per type
- Search / filter by employee ID, name and department
- Update an employee: select a row, edit the form, validate, save, refresh
- Delete an employee with a confirmation dialog
- Automatic saving after every add, update and delete, and loading on startup
- Informative error messages (JOptionPane) for invalid input and file errors

### Employee types and salary calculation

| Type | Attributes | Monthly salary |
|------|------------|----------------|
| Full-Time | basic monthly salary, bonus | Basic Salary + Bonus |
| Part-Time | hourly rate, hours worked | Hourly Rate x Hours Worked |

Each type overrides `calculateSalary()`, so the salary is always computed
according to the employee's actual type.

### OOP principles

- **Encapsulation:** all fields are private, accessed through getters and
  validating setters.
- **Inheritance:** `FullTimeEmployee` and `PartTimeEmployee` extend `Employee`.
- **Polymorphism:** `calculateSalary()` behaves differently per employee type.
- **Abstraction:** `Employee` is an abstract class with the abstract method
  `calculateSalary()`.

### Project structure

| File | Role |
|------|------|
| `Main.java` | Entry point, loads data and opens the login screen |
| `LoginFrame.java` | Login GUI |
| `DashboardFrame.java` | Dashboard GUI (welcome, navigation, log out) |
| `AddEmployeeFrame.java` | Add employee GUI |
| `ManageEmployeesFrame.java` | View, search, update and delete GUI |
| `EmployeeManager.java` | Business logic for employees and login |
| `DepartmentManager.java` | Department list and lookups |
| `EmployeeFileHandler.java` | Reads and writes the data file |
| `Employee.java` | Abstract base class |
| `FullTimeEmployee.java` | Full-time employee |
| `PartTimeEmployee.java` | Part-time employee |
| `Department.java` | Department (ID and name) |

The GUI classes contain only user interaction code. Business rules live in the
manager classes and the employee classes, and file access lives in
`EmployeeFileHandler`.

## 2. Requirements

- JDK 11 or later (the code uses `String.isBlank()`)

## 3. Compile and Run

Open a terminal in the folder that contains all the `.java` files, then:

```
javac *.java
java Main
```

Or open the folder in any Java IDE (IntelliJ, Eclipse, NetBeans) and run
`Main.java`.

Run the program from the same folder every time so it finds its data file.

## 4. Login Credentials

| Username | Password |
|----------|----------|
| `admin` | `admin123` |

These are fixed demonstration credentials.

## 5. Data Storage

- **Format:** Java object serialization (the whole `ArrayList<Employee>` is
  written as objects, so employee types and their salary information are
  restored exactly).
- **File:** `employees.ser`
- **Location:** the folder the program is run from (the working directory). The
  file is created automatically the first time an employee is added.
- **First launch:** if the file does not exist, the system starts with an empty
  employee list.
- **File errors:** a corrupted or unreadable file shows an error message instead
  of crashing, and failed saves show an error dialog.
- **Employee IDs:** generated automatically and unique. After a restart, new IDs
  continue from the highest saved ID.
- **Departments:** the five departments (IT, Finance, Marketing, Human
  Resources, Sales) are fixed in `DepartmentManager` and are selected from a
  combo box, so they are not stored in a file.
- **Reset:** delete `employees.ser` to start with no employees.

## 6. Validation Rules

- Required fields must not be empty
- Age must be a whole number of at least 20
- Salary, bonus and hourly rate must be valid numbers (salary and hourly rate
  must be greater than 0, bonus must not be negative)
- Working hours must be a whole number that is not negative
- A department must be selected from the list
- An employee must be selected before update or delete
- A failed update leaves the existing record unchanged
