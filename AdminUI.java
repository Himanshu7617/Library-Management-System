
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;



public class AdminUI{
    static String admin_user_ID = "";
    static String admin_user_name = "";
    static String admin_user_email = "";


    public static Connection createConnection(){
        Properties properties = new Properties();

        String URL = "jdbc:postgresql://localhost:5432/LibraryManagementSystem";
        String user = "";
        String password = "";

        try {
            FileInputStream fileInputStream = new FileInputStream("imp.properties");
            properties.load(fileInputStream);

            user = properties.getProperty("db.username");
            password = properties.getProperty("db.password");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error fetching username and password from properties file");
        }

        //____________CONNECTING DATABASE______________\\

        Connection con = null;

        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, user, password);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error connecting database");
            e.printStackTrace();
        }
        System.out.println("Database successfully connected");

        return con;
    }


    public static ArrayList<ArrayList<String>> getAllBooks(Connection con){
        String getSQLQuery = "SELECT id, name, author, isbn, genre FROM books ";

        
        ArrayList<ArrayList<String>> ans = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(getSQLQuery);

            while(resultSet.next()){
                ArrayList<String> temp = new ArrayList<>();
                String id = String.valueOf(resultSet.getInt("id"));
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                String genre = resultSet.getString("genre");

                temp.add(id);
                temp.add(name);
                temp.add(author);
                temp.add(isbn);
                temp.add(genre);

                ans.add(temp);
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error getting the books from the database");
            e.printStackTrace();
        }
        return ans;
    }
 
    
    public static ArrayList<ArrayList<String>> getAllUsers(Connection con){
        String getSQLQuery = "SELECT id, name, email, password FROM admin_users ";

        
        ArrayList<ArrayList<String>> ans = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(getSQLQuery);

            while(resultSet.next()){
                ArrayList<String> temp = new ArrayList<>();
                String id = String.valueOf(resultSet.getInt("id"));
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                temp.add(id);
                temp.add(name);
                temp.add(email);
                temp.add(password);

                ans.add(temp);
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error getting the admin users from the database");
            e.printStackTrace();
        }
        return ans;
    }
 
    
    public static ArrayList<ArrayList<String>> getAllIssueRecords(Connection con){
        String getAllIssueRecordsQuery = "SELECT book_id, book_name, author, isbn, student_id, student_name, student_email, issue_date FROM issued_at;";

        ArrayList<ArrayList<String>> ans = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getAllIssueRecordsQuery);

            while(resultSet.next()){
                ArrayList<String> temp = new ArrayList<>();

                String book_id = String.valueOf(resultSet.getInt("book_id"));
                String book_name = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                String student_id = String.valueOf(resultSet.getInt("student_id"));
                String student_name = resultSet.getString("student_name"); 
                String student_email = resultSet.getString("student_email");
                String issue_date = String.valueOf(resultSet.getDate("issue_date"));

                Collections.addAll(temp, book_id, book_name, author, isbn, student_id, student_name, student_email, issue_date);

                ans.add(temp);

            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error get the issue records from the table");
            e.printStackTrace();
        }

        return ans; 
    }
    
    
    public static ArrayList<ArrayList<String>> getAllReturnRecords(Connection con){
        String getAllIssueRecordsQuery = "SELECT book_id, book_name, author, isbn, student_id, student_name, student_email, issue_date, return_date FROM returned_at;";

        ArrayList<ArrayList<String>> ans = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getAllIssueRecordsQuery);

            while(resultSet.next()){
                ArrayList<String> temp = new ArrayList<>();

                String book_id = String.valueOf(resultSet.getInt("book_id"));
                String book_name = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                String student_id = String.valueOf(resultSet.getInt("student_id"));
                String student_name = resultSet.getString("student_name"); 
                String student_email = resultSet.getString("student_email");
                String issue_date = String.valueOf(resultSet.getDate("issue_date"));
                String return_date = String.valueOf(resultSet.getDate("return_date"));

                Collections.addAll(temp, book_id, book_name, author, isbn, student_id, student_name, student_email, issue_date, return_date);

                ans.add(temp);

            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error get the return records from the table");
            e.printStackTrace();
        }

        return ans; 
    }


    public static void insertBook(Connection con, String name, String author, String isbn, String genre){
        String insertBookSQLQuery = "INSERT INTO books (name, author, isbn, genre) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = con.prepareStatement(insertBookSQLQuery);
            
            stmt.setString(1, name);
            stmt.setString(2, author);
            stmt.setString(3, isbn);
            stmt.setString(4, genre);
            
            stmt.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error inserting new book into the book table");
            e.printStackTrace();
        }
    }
    
    
    public static void insertIssueRecords(Connection con, int book_ID, String book_name, String author, String isbn, int student_ID, String student_name, String student_email, LocalDate issue_date){
        String insertIssueRecordSQLQuery = "INSERT INTO issued_at (book_id, book_name,author, isbn, student_id, student_name, student_email, issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       
        try {
            PreparedStatement stmt = con.prepareStatement(insertIssueRecordSQLQuery);

            stmt.setInt(1, book_ID);
            stmt.setString(2, book_name);
            stmt.setString(3, author);
            stmt.setString(4, isbn);
            stmt.setInt(5, student_ID);
            stmt.setString(6, student_name);
            stmt.setString(7, student_email);
            stmt.setDate(8, Date.valueOf(issue_date));

            stmt.executeUpdate();

            

        } catch (Exception e) {
            // TODO: handle exception

            System.out.println("Error inserting issue records");
            e.printStackTrace();
        }
    }
    
    
    public static void insertReturnRecords(Connection con, int book_ID, String book_name, String author, String isbn, int student_ID, String student_name, String student_email,LocalDate issue_date, LocalDate return_date){
        String insertReturnRecordSQLQuery = "INSERT INTO returned_at (book_id, book_name, author, isbn, student_id, student_name, student_email, issue_date, return_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
       
        try {
            PreparedStatement stmt = con.prepareStatement(insertReturnRecordSQLQuery);

            stmt.setInt(1, book_ID);
            stmt.setString(2, book_name);
            stmt.setString(3, author);
            stmt.setString(4, isbn);
            stmt.setInt(5, student_ID);
            stmt.setString(6, student_name);
            stmt.setString(7, student_email);
            stmt.setDate(8, Date.valueOf(issue_date));
            stmt.setDate(9, Date.valueOf(return_date));

            stmt.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error inserting return records");
            e.printStackTrace();
        }
    }


    
    public static void createAdminUI(){


        //tabbed pane => profile books issuedAt returnedAt exit 
    
        JFrame admin_window = new JFrame("Library Management System");
        admin_window.setSize(1000,600);
        admin_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin_window.setVisible(true);
        admin_window.setLocationRelativeTo(null);
        admin_window.setLayout(new GridLayout(1,4));

        
        JPanel profile_panel = new JPanel();
        JPanel books_panel = new JPanel( new BorderLayout());
        JPanel issuedAt_panel = new JPanel();
        JPanel returnedAt_panel = new JPanel();


        profile_panel.setSize(1000,550);
        books_panel.setSize(1000,550);
        issuedAt_panel.setSize(1000,550);
        returnedAt_panel.setSize(1000,550);




        //||____________________________PROFILE PANEL___________________________||\\

        profile_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Font font = new Font("Sans Serif", Font.PLAIN, 24);

        JPanel mini_profile_panel = new JPanel();
        mini_profile_panel.setSize(300,300);
        mini_profile_panel.setLayout(new GridLayout(3,3));
        
        JLabel user_id = new JLabel("ID : ");
        JLabel user_name = new JLabel("Name : ");
        JLabel email = new JLabel("Email : ");

        JTextField user_id_ans = new JTextField(admin_user_ID);
        JTextField user_name_ans = new JTextField(admin_user_name);
        JTextField email_ans = new JTextField(admin_user_email);

        user_id_ans.setEditable(false);
        user_name_ans.setEditable(false);
        email_ans.setEditable(false);

        user_id.setFont(font);
        user_name.setFont(font);
        email.setFont(font);
        user_id_ans.setFont(font);
        user_name_ans.setFont(font);
        email_ans.setFont(font);


        mini_profile_panel.add(user_id);
        mini_profile_panel.add(user_id_ans);
        mini_profile_panel.add(user_name);
        mini_profile_panel.add(user_name_ans);
        mini_profile_panel.add(email);
        mini_profile_panel.add(email_ans);

        profile_panel.add(mini_profile_panel);



        //||_______________________________________BOOKS PANEL________________________________||\\


        JPanel mini_book_panel = new JPanel();
        mini_book_panel.setSize(1000,550);
        mini_book_panel.setLayout(new FlowLayout());


        JPanel mini_left_panel = new JPanel();
        mini_left_panel.setPreferredSize(new Dimension(300, 550));
        mini_left_panel.setBackground(Color.PINK);
        
        JPanel mini_right_panel = new JPanel();
        mini_right_panel.setPreferredSize(new Dimension(690,550));
        mini_right_panel.setBackground(Color.PINK);
        mini_book_panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mini_left_panel, mini_right_panel);
        sp.setDividerLocation(300);


        //__________________RIGHT PANEL FOR DISPLAYING ALL BOOKS_________________\\

        Connection con = createConnection();

        ArrayList<ArrayList<String>> all_books = getAllBooks(con);
        

        String[][] book_table_values = new String[all_books.size()][];

        for(int i = 0; i< all_books.size(); i++){
            int temp_size = all_books.get(i).size();
            String[] temp = new String[temp_size];
            for( int j = 0; j< temp_size; j++){
                temp[j] = all_books.get(i).get(j);
            }
            book_table_values[i] = temp;
        }

        String[] book_table_headings = {"Book ID", "Book Name", "Author", "ISBN", "Genre"};
    
        DefaultTableModel  dt = new DefaultTableModel(book_table_values, book_table_headings) {
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };

        JTable book_table = new JTable(dt);
        book_table.setPreferredScrollableViewportSize(new Dimension(650,500));
        JScrollPane table_scrolls = new JScrollPane(book_table);

        mini_right_panel.add(table_scrolls);

        //__________________LEFT PANEL FOR ADDING A BOOK_____________________\\

        JPanel add_book_panel = new JPanel();
        add_book_panel.setSize(260,400);
        add_book_panel.setLayout(new GridLayout(5,2));

        JLabel add_book_name_label = new JLabel("Book Name : ");
        JLabel add_book_author_label = new JLabel("Author : ");
        JLabel add_book_isbn_label = new JLabel("ISBN : ");
        JLabel add_book_genre_label = new JLabel("Genre : ");

        JTextField add_book_name_input = new JTextField();
        JTextField add_book_author_input = new JTextField();
        JTextField add_book_isbn_input = new JTextField();
        JTextField add_book_genre_input = new JTextField();
        
        add_book_name_label.setFont(font);
        add_book_author_label.setFont(font);
        add_book_isbn_label.setFont(font);
        add_book_genre_label.setFont(font);
        add_book_name_input.setFont(font); 
        add_book_author_input.setFont(font); 
        add_book_isbn_input.setFont(font); 
        add_book_genre_input.setFont(font); 
        
        add_book_panel.add(add_book_name_label);
        add_book_panel.add(add_book_name_input);
        add_book_panel.add(add_book_author_label);
        add_book_panel.add(add_book_author_input);
        add_book_panel.add(add_book_isbn_label);
        add_book_panel.add(add_book_isbn_input);
        add_book_panel.add(add_book_genre_label);
        add_book_panel.add(add_book_genre_input);




        JButton add_book_button = new JButton("Add Book");
        add_book_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String name = add_book_name_input.getText();
                String author = add_book_author_input.getText();
                String isbn = add_book_isbn_input.getText();
                String genre = add_book_genre_input.getText();
                

                insertBook(con, name, author, isbn, genre);
                add_book_name_input.setText("");
                add_book_author_input.setText("");
                add_book_isbn_input.setText("");
                add_book_genre_input.setText("");

                String[] rowData = {String.valueOf(book_table.getRowCount()+1),name, author, isbn, genre};
                dt.insertRow(0, rowData);
            }

        });

        add_book_panel.add(add_book_button);
        mini_left_panel.add(add_book_panel);

        books_panel.add(sp , BorderLayout.CENTER);


        //||_____________________________________ISSUE RECORDSD______________________________||\\

        JPanel mini_issue_record_panel = new JPanel();
        mini_issue_record_panel.setSize(1000,550);
        mini_issue_record_panel.setLayout(new FlowLayout());


        JPanel mini_issue_record_left_panel = new JPanel();
        mini_issue_record_left_panel.setPreferredSize(new Dimension(300, 550));
        mini_issue_record_left_panel.setBackground(Color.PINK);

        JPanel mini_issue_record_right_panel = new JPanel();
        mini_issue_record_right_panel.setPreferredSize(new Dimension(690,550));
        mini_issue_record_right_panel.setBackground(Color.PINK);
        mini_issue_record_panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JSplitPane sp_issue = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mini_issue_record_left_panel, mini_issue_record_right_panel);
        sp_issue.setDividerLocation(300);


        
        //________________________issue_record_right_panel_____________________\\


        ArrayList<ArrayList<String>> all_issue_records = getAllIssueRecords(con);
        

        String[][] issue_table_values = new String[all_issue_records.size()][];

        for(int i = 0; i< all_issue_records.size(); i++){
            int temp_size = all_issue_records.get(i).size();
            String[] temp = new String[temp_size];
            for( int j = 0; j< temp_size; j++){
                temp[j] = all_issue_records.get(i).get(j);
            }
            issue_table_values[i] = temp;
        }

        String[] issue_table_headings = {"Book ID", "Book Name", "Author", "ISBN", "Student ID", "Student Name", "Student Email", "Issue Date"};

        DefaultTableModel  dt_issue = new DefaultTableModel(issue_table_values, issue_table_headings) {
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };

        JTable issue_table = new JTable(dt_issue);
        issue_table.setPreferredScrollableViewportSize(new Dimension(650,500));
        JScrollPane issue_table_scrolls = new JScrollPane(issue_table);

        mini_issue_record_right_panel.add(issue_table_scrolls);


        //________________________issue_record_left_panel____________________\\

  
        JPanel add_issue_record_panel = new JPanel();
        add_issue_record_panel.setSize(260,400);
        add_issue_record_panel.setLayout(new GridLayout(9,2));


        JLabel add_issue_book_ID_label = new JLabel("Book ID : ");
        JLabel add_issue_book_name_label = new JLabel("Book Name : ");
        JLabel add_issue_book_author_label = new JLabel("Author : ");
        JLabel add_issue_book_isbn_label = new JLabel("ISBN : ");
        JLabel add_issue_student_ID_label = new JLabel("Student ID : ");
        JLabel add_issue_student_name_label = new JLabel("Student Name : ");
        JLabel add_issue_student_email_label = new JLabel("Student Email : ");
        JLabel add_issue_date_label = new JLabel("Issue Date : ");
        
        JTextField add_issue_book_ID_input = new JTextField();
        JTextField add_issue_book_name_input = new JTextField();
        JTextField add_issue_book_author_input = new JTextField();
        JTextField add_issue_book_isbn_input = new JTextField();
        JTextField add_issue_student_ID_input = new JTextField();
        JTextField add_issue_student_name_input = new JTextField();
        JTextField add_issue_student_email_input = new JTextField();
        JTextField add_issue_date_input = new JTextField();

        Font issue_font = new Font("Sans Serif", Font.PLAIN, 18);
        
        add_issue_book_ID_label.setFont(issue_font); 
        add_issue_book_name_label.setFont(issue_font);  
        add_issue_book_author_label.setFont(issue_font);  
        add_issue_book_isbn_label.setFont(issue_font);  
        add_issue_student_ID_label.setFont(issue_font); 
        add_issue_student_name_label.setFont(issue_font);  
        add_issue_student_email_label.setFont(issue_font);  
        add_issue_date_label.setFont(issue_font); 
        add_issue_book_ID_input.setFont(issue_font);  
        add_issue_book_name_input.setFont(issue_font); 
        add_issue_book_author_input.setFont(issue_font); 
        add_issue_book_isbn_input.setFont(issue_font);  
        add_issue_student_ID_input.setFont(issue_font);  
        add_issue_student_name_input.setFont(issue_font);  
        add_issue_student_email_input.setFont(issue_font);  
        add_issue_date_input.setFont(issue_font);  
        
        add_issue_record_panel.add(add_issue_book_ID_label);
        add_issue_record_panel.add(add_issue_book_ID_input);
        add_issue_record_panel.add(add_issue_book_name_label);
        add_issue_record_panel.add(add_issue_book_name_input);
        add_issue_record_panel.add(add_issue_book_author_label);
        add_issue_record_panel.add(add_issue_book_author_input);
        add_issue_record_panel.add(add_issue_book_isbn_label);
        add_issue_record_panel.add(add_issue_book_isbn_input);
        add_issue_record_panel.add(add_issue_student_ID_label);
        add_issue_record_panel.add(add_issue_student_ID_input);
        add_issue_record_panel.add(add_issue_student_name_label);
        add_issue_record_panel.add(add_issue_student_name_input);
        add_issue_record_panel.add(add_issue_student_email_label);
        add_issue_record_panel.add(add_issue_student_email_input);
        add_issue_record_panel.add(add_issue_date_label);
        add_issue_record_panel.add(add_issue_date_input);




        JButton add_issue_record_button = new JButton("Add Issue Record");
        add_issue_record_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int book_ID = Integer.parseInt(add_issue_book_ID_input.getText());
                String book_name = add_issue_book_name_input.getText();
                String author = add_issue_book_author_input.getText();
                String isbn = add_issue_book_isbn_input.getText();
                int student_ID = Integer.parseInt(add_issue_student_ID_input.getText());
                String student_name = add_issue_student_name_input.getText();
                String student_email = add_issue_student_email_input.getText();

                //date 
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate issue_date = LocalDate.parse(add_issue_date_input.getText(), formatter);

                insertIssueRecords(con, book_ID, book_name, author, isbn, student_ID, student_name, student_email, issue_date);
                add_issue_book_ID_input.setText("");  
                add_issue_book_name_input.setText(""); 
                add_issue_book_author_input.setText(""); 
                add_issue_book_isbn_input.setText("");  
                add_issue_student_ID_input.setText("");  
                add_issue_student_name_input.setText("");  
                add_issue_student_email_input.setText("");  
                add_issue_date_input.setText("");  

                String[] rowData = {String.valueOf(book_ID),book_name, author, isbn, String.valueOf(student_ID), student_name, student_email, String.valueOf(issue_date)};
                dt_issue.insertRow(0, rowData);
            }

        });

        add_issue_record_panel.add(add_issue_record_button);
        mini_issue_record_left_panel.add(add_issue_record_panel);

        issuedAt_panel.add(sp_issue);



        //||_____________________________________RETURN RECORDS________________________________||\\



        JPanel mini_return_record_panel = new JPanel();
        mini_return_record_panel.setSize(1000,550);
        mini_return_record_panel.setLayout(new FlowLayout());


        JPanel mini_return_record_left_panel = new JPanel();
        mini_return_record_left_panel.setPreferredSize(new Dimension(300, 550));
        mini_return_record_left_panel.setBackground(Color.PINK);
        
        JPanel mini_return_record_right_panel = new JPanel();
        mini_return_record_right_panel.setPreferredSize(new Dimension(690,550));
        mini_return_record_right_panel.setBackground(Color.PINK);
        mini_return_record_panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JSplitPane sp_return = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mini_return_record_left_panel, mini_return_record_right_panel);
        sp_return.setDividerLocation(300);


        
        //________________________return_record_right_panel_____________________\\


        ArrayList<ArrayList<String>> all_return_records = getAllReturnRecords(con);
        

        String[][] return_table_values = new String[all_return_records.size()][];

        for(int i = 0; i< all_return_records.size(); i++){
            int temp_size = all_return_records.get(i).size();
            String[] temp = new String[temp_size];
            for( int j = 0; j< temp_size; j++){
                temp[j] = all_return_records.get(i).get(j);
            }
            return_table_values[i] = temp;
        }

        String[] return_table_headings = {"Book ID", "Book Name", "Author", "ISBN", "Student ID", "Student Name", "Student Email", "Issue Date", "Return Date"};

        DefaultTableModel  dt_return = new DefaultTableModel(return_table_values, return_table_headings) {
            @Override
            public boolean isCellEditable(int rows, int columns){
                return false;
            }
        };

        JTable return_table = new JTable(dt_return);
        return_table.setPreferredScrollableViewportSize(new Dimension(650,500));
        JScrollPane return_table_scrolls = new JScrollPane(return_table);

        mini_return_record_right_panel.add(return_table_scrolls);


        //________________________return_record_left_panel____________________\\

  
        JPanel add_return_record_panel = new JPanel();
        add_return_record_panel.setSize(260,400);
        add_return_record_panel.setLayout(new GridLayout(10,2));


        JLabel add_return_book_ID_label = new JLabel("Book ID : ");
        JLabel add_return_book_name_label = new JLabel("Book Name : ");
        JLabel add_return_book_author_label = new JLabel("Author : ");
        JLabel add_return_book_isbn_label = new JLabel("ISBN : ");
        JLabel add_return_student_ID_label = new JLabel("Student ID : ");
        JLabel add_return_student_name_label = new JLabel("Student Name : ");
        JLabel add_return_student_email_label = new JLabel("Student Email : ");
        JLabel add_return_issue_date_label = new JLabel("Issue Date : ");
        JLabel add_return_date_label = new JLabel("Return Date : ");

        
        JTextField add_return_book_ID_input = new JTextField();
        JTextField add_return_book_name_input = new JTextField();
        JTextField add_return_book_author_input = new JTextField();
        JTextField add_return_book_isbn_input = new JTextField();
        JTextField add_return_student_ID_input = new JTextField();
        JTextField add_return_student_name_input = new JTextField();
        JTextField add_return_student_email_input = new JTextField();
        JTextField add_return_issue_date_input = new JTextField();
        JTextField add_return_date_input = new JTextField();

        Font return_font = new Font("Sans Serif", Font.PLAIN, 18);

        
        add_return_book_ID_label.setFont(return_font); 
        add_return_book_name_label.setFont(return_font);  
        add_return_book_author_label.setFont(return_font);  
        add_return_book_isbn_label.setFont(return_font);  
        add_return_student_ID_label.setFont(return_font); 
        add_return_student_name_label.setFont(return_font);  
        add_return_student_email_label.setFont(return_font);  
        add_return_issue_date_label.setFont(return_font); 
        add_return_date_label.setFont(return_font); 
        add_return_book_ID_input.setFont(return_font);  
        add_return_book_name_input.setFont(return_font); 
        add_return_book_author_input.setFont(return_font); 
        add_return_book_isbn_input.setFont(return_font);  
        add_return_student_ID_input.setFont(return_font);  
        add_return_student_name_input.setFont(return_font);  
        add_return_student_email_input.setFont(return_font);  
        add_return_date_input.setFont(return_font);  
        add_return_issue_date_input.setFont(return_font);  
        
        add_return_record_panel.add(add_return_book_ID_label);
        add_return_record_panel.add(add_return_book_ID_input);
        add_return_record_panel.add(add_return_book_name_label);
        add_return_record_panel.add(add_return_book_name_input);
        add_return_record_panel.add(add_return_book_author_label);
        add_return_record_panel.add(add_return_book_author_input);
        add_return_record_panel.add(add_return_book_isbn_label);
        add_return_record_panel.add(add_return_book_isbn_input);
        add_return_record_panel.add(add_return_student_ID_label);
        add_return_record_panel.add(add_return_student_ID_input);
        add_return_record_panel.add(add_return_student_name_label);
        add_return_record_panel.add(add_return_student_name_input);
        add_return_record_panel.add(add_return_student_email_label);
        add_return_record_panel.add(add_return_student_email_input);
        add_return_record_panel.add(add_return_issue_date_label);
        add_return_record_panel.add(add_return_issue_date_input);
        add_return_record_panel.add(add_return_date_label);
        add_return_record_panel.add(add_return_date_input);





        JButton add_return_record_button = new JButton("Add Record");
        add_return_record_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int book_ID = Integer.parseInt(add_return_book_ID_input.getText());
                String book_name = add_return_book_name_input.getText();
                String author = add_return_book_author_input.getText();
                String isbn = add_return_book_isbn_input.getText();
                int student_ID = Integer.parseInt(add_return_student_ID_input.getText());
                String student_name = add_return_student_name_input.getText();
                String student_email = add_return_student_email_input.getText();

                //date 
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate issue_date = LocalDate.parse(add_return_issue_date_input.getText(), formatter);
                LocalDate return_date = LocalDate.parse(add_return_date_input.getText(), formatter);

                insertReturnRecords(con, book_ID, book_name, author, isbn, student_ID, student_name, student_email, issue_date, return_date);

                add_return_book_ID_input.setText("");  
                add_return_book_name_input.setText(""); 
                add_return_book_author_input.setText(""); 
                add_return_book_isbn_input.setText("");  
                add_return_student_ID_input.setText("");  
                add_return_student_name_input.setText("");  
                add_return_student_email_input.setText("");  
                add_return_issue_date_input.setText("");  
                add_return_date_input.setText("");  

                String[] rowData = {String.valueOf(book_ID),book_name, author, isbn, String.valueOf(student_ID), student_name, student_email, String.valueOf(issue_date), String.valueOf(return_date)};
                dt_return.insertRow(0, rowData);
            }

        });

        add_return_record_panel.add(add_return_record_button);
        mini_return_record_left_panel.add(add_return_record_panel);

        returnedAt_panel.add(sp_return);





        //||_____________________________________EXIT__________________________________________||\\



        




        //||_________________________________________TABBED PANE_______________________________||\\

        JTabbedPane tb = new JTabbedPane();

        JButton admin_exit_button = new JButton("Exit");

        tb.setBounds(0,0,1000,500);
        tb.addTab("Profile", profile_panel);
        tb.addTab("Books", books_panel);
        tb.addTab("Issue Records", issuedAt_panel);
        tb.addTab("Return Records", returnedAt_panel);
        tb.addTab("Exit",null);

        tb.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e){
                if(tb.getSelectedIndex() == 4){
                    admin_window.dispose();
                }
            }
        });


        admin_window.add(tb);
    


    }
    
    
    public static void verifyUser() {
        JFrame password_window = new JFrame();
        password_window.setSize(300,300);
        password_window.setLayout(new FlowLayout(FlowLayout.CENTER));
        password_window.setVisible(true);
        password_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel password_panel = new JPanel();
        password_panel.setPreferredSize(new Dimension(200,100));
        password_panel.setLayout(new GridLayout(3,2));

        JLabel password_label = new JLabel("Password :");
        JLabel email_label = new JLabel("Email : ");
        JPasswordField password_input = new JPasswordField();
        JTextField email_input = new JTextField();
        JButton password_button = new JButton("Ok");

        password_panel.add(email_label);
        password_panel.add(email_input);
        password_panel.add(password_label);
        password_panel.add(password_input);
        password_panel.add(password_button);
        

       
        
        password_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                 //getting users
                Connection con = createConnection();
                ArrayList<ArrayList<String>> all_users = getAllUsers(con);

                String msg = "";
                String temp_email = email_input.getText();
                String temp_password = String.valueOf(password_input.getPassword());

                boolean userFound = false;

                //checking users 
                for(int i = 0 ; i < all_users.size(); i++){
                    if(all_users.get(i).get(2).equals(temp_email)){
                        userFound = true;
                        if(all_users.get(i).get(3).equals(temp_password)){
                            admin_user_ID = all_users.get(i).get(0);
                            admin_user_name = all_users.get(i).get(1);
                            admin_user_email = all_users.get(i).get(2);
                            password_window.dispose();
                            createAdminUI();
                        }else{
                            msg = "invalid password";
                            break;
                        }
                    }
                }

                //adding msg to window if any

                if(!userFound){
                    msg = "invalid Email";
                }
                
                if(msg.length()> 0){
                    // SwingUtilities.invokeLater(new Runnable() {
                    //     public void run(){
                    //         password_window.add(new Label(error_msg));
                    //         password_window.validate();
                    //         password_window.repaint();
                    //     }
                    // });
                    password_window.add(new Label(msg));
                    password_window.revalidate();
                    password_window.repaint();
                }
            }

        });
        password_window.add(password_panel);

    
        // SwingUtilities.invokeLater(new Runnable() {
        //     @Override
        //     public void run(){
        //         password_window.setVisible(true);
        //     }
        // });

        
        
    }
    
    public static void main(String[] args) {
             
    }
}


























// import java.awt.*;
// import java.util.Properties;
// import javax.swing.*;

// public class AdminUI {

//     Properties properties = new Properties();
//     String URL = "";

//     public static void createBooksPanel(JFrame f) {

//         JPanel left_book_panel = new JPanel();
//         left_book_panel.setBounds(0, 44, 300, 500);
//         left_book_panel.setBackground(new Color(247, 216, 129));
//         left_book_panel.setLayout(null);

//         JPanel right_book_panel = new JPanel();
//         right_book_panel.setBounds(310, 44, 690, 500);
//         right_book_panel.setBackground(new Color(247, 216, 129));
//         right_book_panel.setLayout(null);

//         f.add(left_book_panel);
//         f.add(right_book_panel);

//     }

//     public static void createProfilePanel(JFrame f) {
//         JPanel profile_panel = new JPanel();
//         profile_panel.setBounds(0, 44, 1000, 500);
//         profile_panel.setBackground(new Color(247, 216, 129));
//         profile_panel.setLayout(null);

//         JLabel id_label = new JLabel("ID : ");
//         JLabel name_label = new JLabel("NAME : ");
//         JLabel email_label = new JLabel("EMAIL : ");

//         id_label.setBounds(100, 100, 60, 40);
//         name_label.setBounds(100, 150, 60, 40);
//         email_label.setBounds(100, 200, 60, 40);

//         JTextField id_textfield = new JTextField("1");
//         JTextField name_textfield = new JTextField("Himanshu");
//         JTextField email_textfield = new JTextField("gghimanshu");

//         id_textfield.setBounds(300, 100, 150, 40);
//         name_textfield.setBounds(300, 150, 150, 40);
//         email_textfield.setBounds(300, 200, 150, 40);

//         profile_panel.add(id_label);
//         profile_panel.add(name_label);
//         profile_panel.add(email_label);
//         profile_panel.add(id_textfield);
//         profile_panel.add(name_textfield);
//         profile_panel.add(email_textfield);

//         f.add(profile_panel);

//     }

//     public static void createNavbar(JFrame f) {
//         JPanel navbar = new JPanel();
//         navbar.setBounds(0, 0, 1000, 40);
//         navbar.setBackground(new Color(247, 216, 129));

//         JButton profile_btn = new JButton("Profile");
//         JButton books_btn = new JButton("Books");
//         JButton issue_btn = new JButton("Issue Records");
//         JButton return_btn = new JButton("Return Records");
//         JButton exit_btn = new JButton("Exit");

//         profile_btn.setBounds(5, 5, 80, 35);
//         books_btn.setBounds(90, 5, 80, 35);
//         issue_btn.setBounds(175, 5, 80, 35);
//         return_btn.setBounds(260, 5, 80, 35);
//         exit_btn.setBounds(345, 5, 80, 35);

//         navbar.add(profile_btn);
//         navbar.add(books_btn);
//         navbar.add(issue_btn);
//         navbar.add(return_btn);
//         navbar.add(exit_btn);

//         f.add(navbar);
//     }

//     public static void createAdminWindow() {
//         JFrame admin_window = new JFrame();
//         admin_window.setSize(1000, 600);
//         admin_window.setLocationRelativeTo(null);
//         admin_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         admin_window.setVisible(true);
//         // admin_window.setLayout(null);

//         // |_________________________NAV BAR________________________________|\\

//         JPanel navbar = new JPanel();
//         navbar.setBounds(0, 0, 1000, 40);
//         navbar.setBackground(new Color(247, 216, 129));

//         JButton profile_btn = new JButton("Profile");
//         JButton books_btn = new JButton("Books");
//         JButton issue_btn = new JButton("Issue Records");
//         JButton return_btn = new JButton("Return Records");
//         JButton exit_btn = new JButton("Exit");

//         profile_btn.setBounds(5, 5, 80, 40);
//         books_btn.setBounds(90, 5, 80, 40);
//         issue_btn.setBounds(175, 5, 80, 40);
//         return_btn.setBounds(260, 5, 80, 40);
//         exit_btn.setBounds(345, 5, 80, 40);

//         navbar.add(profile_btn);
//         navbar.add(books_btn);
//         navbar.add(issue_btn);
//         navbar.add(return_btn);
//         navbar.add(exit_btn);

//         admin_window.add(navbar);

//         CardLayout cardLayout = new CardLayout();
//         JPanel mainPanel = new JPanel();

//         // |___________________________PROFILE_____________________________|\\

//         JPanel profile_panel = new JPanel();
//         profile_panel.setBounds(0, 44, 1000, 500);
//         profile_panel.setBackground(new Color(247, 216, 129));
//         profile_panel.setLayout(null);

//         JLabel id_label = new JLabel("ID : ");
//         JLabel name_label = new JLabel("NAME : ");
//         JLabel email_label = new JLabel("EMAIL : ");

//         id_label.setBounds(100, 100, 60, 40);
//         name_label.setBounds(100, 150, 60, 40);
//         email_label.setBounds(100, 200, 60, 40);

//         JTextField id_textfield = new JTextField("1");
//         JTextField name_textfield = new JTextField("Himanshu");
//         JTextField email_textfield = new JTextField("gghimanshu");

//         id_textfield.setBounds(300, 100, 150, 40);
//         name_textfield.setBounds(300, 150, 150, 40);
//         email_textfield.setBounds(300, 200, 150, 40);

//         profile_panel.add(id_label);
//         profile_panel.add(name_label);
//         profile_panel.add(email_label);
//         profile_panel.add(id_textfield);
//         profile_panel.add(name_textfield);
//         profile_panel.add(email_textfield);

//         mainPanel.add(profile_panel, "Profile");

//         // |___________________________BOOKS_____________________________|\\

//         JPanel left_book_panel = new JPanel();
//         left_book_panel.setBounds(0, 44, 300, 500);
//         left_book_panel.setBackground(new Color(247, 216, 129));
//         left_book_panel.setLayout(null);

//         JPanel right_book_panel = new JPanel();
//         right_book_panel.setBounds(310, 44, 690, 500);
//         right_book_panel.setBackground(new Color(247, 216, 129));
//         right_book_panel.setLayout(null);

//         JPanel book_panel = new JPanel();
//         book_panel.setBounds(0, 44, 1000, 500);
//         book_panel.setLayout(null);
//         book_panel.add(left_book_panel);
//         book_panel.add(right_book_panel);

//         mainPanel.add(book_panel, "Books");

//         profile_btn.addActionListener(e -> cardLayout.show(mainPanel, "Profile"));
//         books_btn.addActionListener(e -> cardLayout.show(mainPanel, "Books"));

//         admin_window.add(mainPanel);

//         // |___________________________ISSUE RECORDS_____________________________|\\

//         // |___________________________RETURN RECORDS_____________________________|\\

//     }

// }
