# Library Management System 📚

This is a **Library Management System** built using **Java Swing** and **AWT**. The system provides two modes of operation: **Admin** and **Student**, offering distinct functionalities tailored to their roles.

![main_window](/assets/Main_window.png)

---

## Features ✨

### Main Window
- The application starts with a main window featuring two buttons:
  - **Admin**
  - **Student**

### Admin Panel
- On selecting the **Admin** button, the user is prompted to log in with their **email** and **password**.

![password_window](/assets/password_window.png)

- After a successful login, the Admin Panel opens with the following tabs:
  1. **Profile**  
     Displays the admin's details including:
     - Admin ID
     - Username
     - Email
  2. **Books Panel**  
     - View all books in the library.
     - Add new books to the database.
  3. **Issue Records Panel**  
     - View all issue records.
     - Add a new issue record.
  4. **Return Records Panel**  
     - View all return records.
     - Add a new return record.
  5. **Exit**  
     - Close the Admin Panel.



### Student Panel
- On selecting the **Student** button, the Student Panel opens directly.
- The Student Panel includes the following tabs:
  1. **Available Books**  
     - View all available books in the library.
  2. **Issue Records**  
     - View issued book records.
  3. **Return Records**  
     - View returned book records.
  4. **Exit**  
     - Close the Student Panel.

> **Note:** Students can only view data and cannot edit any records.

---

## Screenshots 📷

Include screenshots of the main window, admin panel, and student panel here for better visualization.

![admin_window_profile_panel](/assets/profile_panel.png)
![admin_window_add_books_panel](/assets/add_book_panel.png)
![admin_window_issue_record_panel](/assets/issue_record_panel.png)
![admin_window_return_record_panel](/assets/return_record_panel.png)
![student_window_books_panel](/assets/student_books_panel.png)
![student_window_issue_record_panel](/assets/student_issue_records_panel.png)
![student_window_return_record_panel](/assets/student_return_records_panel.png)
---

## Technologies Used 🛠️
- **Java Swing**: For building the graphical user interface.
- **AWT**: For GUI event handling.
- **JDBC**: For database operations.

---

## Setup Instructions ⚙️

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/library-management-system.git
