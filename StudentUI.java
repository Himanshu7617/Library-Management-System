
import java.awt.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;



public class StudentUI{



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


    
    public static void createStudentUI(){


        //tabbed pane => profile books issuedAt returnedAt exit 
    
        JFrame student_window = new JFrame("Library Management System");
        student_window.setSize(1000,600);
        student_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        student_window.setVisible(true);
        student_window.setLocationRelativeTo(null);
        student_window.setLayout(new GridLayout(1,4));

        
        JPanel books_panel = new JPanel( new BorderLayout());
        JPanel issuedAt_panel = new JPanel();
        JPanel returnedAt_panel = new JPanel();


        books_panel.setSize(1000,550);
        issuedAt_panel.setSize(1000,550);
        returnedAt_panel.setSize(1000,550);
 
        //__________________PANEL FOR DISPLAYING ALL BOOKS_________________\\

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
        book_table.setPreferredScrollableViewportSize(new Dimension(950,500));
        JScrollPane table_scrolls = new JScrollPane(book_table);

        books_panel.add(table_scrolls);

         
        //________________________issue_record_panel_____________________\\


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
        issue_table.setPreferredScrollableViewportSize(new Dimension(950,500));
        JScrollPane issue_table_scrolls = new JScrollPane(issue_table);

        issuedAt_panel.add(issue_table_scrolls);
        
        //________________________return_record_panel_____________________\\


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
        return_table.setPreferredScrollableViewportSize(new Dimension(950,500));
        JScrollPane return_table_scrolls = new JScrollPane(return_table);

        returnedAt_panel.add(return_table_scrolls);
 

        //||_________________________________________TABBED PANE_______________________________||\\

        JTabbedPane tb = new JTabbedPane();

        JButton admin_exit_button = new JButton("Exit");

        tb.setBounds(0,0,1000,500);
        tb.addTab("Books", books_panel);
        tb.addTab("Issue Records", issuedAt_panel);
        tb.addTab("Return Records", returnedAt_panel);
        tb.addTab("Exit",null);

        tb.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e){
                if(tb.getSelectedIndex() == 3){
                    student_window.dispose();
                }
            }
        });


        student_window.add(tb);
    


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
//         JFrame student_window = new JFrame();
//         student_window.setSize(1000, 600);
//         student_window.setLocationRelativeTo(null);
//         student_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         student_window.setVisible(true);
//         // student_window.setLayout(null);

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

//         student_window.add(navbar);

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

//         student_window.add(mainPanel);

//         // |___________________________ISSUE RECORDS_____________________________|\\

//         // |___________________________RETURN RECORDS_____________________________|\\

//     }

// }
