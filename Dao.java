
import java.sql.Connection;
import java.sql.Statement;

public class Dao {

    //|____________________________CREATING TABLE_________________________________|\\


    private static String UserTableQueryString = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(30) NOT NULL,
                    email VARCHAR(60) UNIQUE NOT NULL, 
                    role VARCHAR(10) NOT NULL DEFAULT 'student',
                    password VARCHAR(20)
                );
            """;
    private static String BookTableQueryString = """
                CREATE TABLE IF NOT EXISTS books (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(30) NOT NULL,
                    author VARCHAR(30) NOT NULL, 
                    isbn VARCHAR(10) NOT NULL UNIQUE,
                    genre VARCHAR(20)
                );
            """;
    private static String IssuedAtTableQueryString = """
                CREATE TABLE IF NOT EXISTS issued_at (
                    book_id INT NOT NULL,
                    book_name VARCHAR(30) NOT NULL,
                    author VARCHAR(30) NOT NULL, 
                    isbn VARCHAR(10) NOT NULL,
                    student_id INT NOT NULL, 
                    student_name VARCHAR(20) NOT NULL,
                    student_email VARCHAR(30) NOT NULL,
                    issue_date DATE DEFAULT CURRENT_DATE
                );
            """;
    private static String ReturnedAtTableQueryString = """
                CREATE TABLE IF NOT EXISTS returned_at (
                    book_id INT NOT NULL,
                    book_name VARCHAR(30) NOT NULL,
                    author VARCHAR(30) NOT NULL, 
                    isbn VARCHAR(10) NOT NULL,
                    student_id INT NOT NULL, 
                    student_name VARCHAR(20) NOT NULL,
                    student_email VARCHAR(30) NOT NULL,
                    issue_date DATE DEFAULT CURRENT_DATE
                );
            """;



    public static void createUserTable(Connection con){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(UserTableQueryString);
            System.out.println("users Table created Successfully");
        } catch (Exception e) {
            System.out.println("Error creating users table");
            e.printStackTrace();
        }
    }
    public static void createBooksTable(Connection con){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(BookTableQueryString);
            System.out.println("books Table created Successfully");
        } catch (Exception e) {
            System.out.println("Error creating books table");
            e.printStackTrace();
        }
    }
    public static void createIssuedAtTable(Connection con){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(IssuedAtTableQueryString);
            System.out.println("issued_at Table created Successfully");
        } catch (Exception e) {
            System.out.println("Error creating issued_at table");
            e.printStackTrace();
        }
    }
    public static void createReturnedAtTable(Connection con){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(ReturnedAtTableQueryString);
            System.out.println("returned_at Table created Successfully");
        } catch (Exception e) {
            System.out.println("Error creating returned_at table");
            e.printStackTrace();
        }
    }


    //|__________________________________________TABLE OPERATIONS_________________________________|\\


    

}
