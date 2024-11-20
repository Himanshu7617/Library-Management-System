# Library-Management-System

topics to learn

java package 
java modules / import and export 
java jar files(if possible)
super keyword


okay so here's the thing i understand right now 

1. so there's gonna be a java "Main" class that'll control everything 
2. so ther's is gona be a user, which will be created  in a USER CLASS

    (interface) user identification - 
        -> id
        -> name 
        -> emailId
        -> role (abstract)
    
    user functionalities - 
        -> access main window 

3. Now this user class will extend two subclasses 
    
    ADMIN
        -> password

        functionalities
            -> can view, edit, delete all records
            -> can list books
    STUDENT
        -> can see and issue available books
        -> can return a book

4. then there's gon be four tables 

    USERS
        schema
        -> id
        -> name
        -> email
        -> role
    BOOKS
        schema 
        -> id (serial)
        -> name
        -> isbn number
        -> author
        -> genre
    ISSUED
        schema
        -> book_id (foreign key) ------------------------ FOREIGN 
        -> book_name (foreign key) ---------------------- KEY 
        -> book_isbn_number (foreign key) --------------- IMPLEMENTATION 
        -> book_author (foreign key) -------------------- WILL 
        -> student_id
        -> student_name
        -> student_email
    RETURNED
        schema
        -> book_id (foreign key)------------------------- BE 
        -> book_name (foreign key)----------------------- DONE 
        -> book_isbn_number (foreign key)---------------- LATER 
        -> book_author (foreign key)--------------------- OKAY! honey!!!
        -> student_id
        -> student_name
        -> student_email



STEPS TO CREATE THIS 

1. create main class and instantiate all required classes
2. connect the database
3. create the main window
4. create all button and frames and instantiate the function associated with those buttons
5. show the required tables from sql
6. write all the functions 
7. do some touch ups 
8. Project done see if you could upload it
