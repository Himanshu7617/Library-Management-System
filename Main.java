
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.swing.*;


public class Main{
   public static void main(String[] args) {
    Properties properties = new Properties();

    String URL = "jdbc:postgresql://localhost:5432/LibraryManagementSystem";
    String user ="";
    String password = "";
    try {
        FileInputStream fileInputStream = new FileInputStream("imp.properties");
        properties.load(fileInputStream);

        user = properties.getProperty("db.username");
        password = properties.getProperty("db.password");

    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error accessing properties");
    }

     //|_____________________________CONNECTING DATABASE___________________________________|\\


     Connection con = null;
     try {
         Class.forName("org.postgresql.Driver");
         con = DriverManager.getConnection(URL, user, password);

     } catch (Exception e) {
         System.out.println("Exception connecting to the database");
         e.printStackTrace();
     }

     System.out.println("Database successfully connected");
     Dao.createUserTable(con);
     Dao.createBooksTable(con);
     Dao.createIssuedAtTable(con);
     Dao.createReturnedAtTable(con);

   
     //|_________________________________CREATING MAIN WINDOW______________________________|\\

     JFrame main_window = new JFrame("Library Management System");
     main_window.setSize(1000, 600);
     main_window.setLocationRelativeTo(null);
     main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     main_window.setVisible(true);
     main_window.setLayout(null);
      
        //a title 
        //a button for login as admin
        //a button for login as student
    JLabel main_title = new JLabel("Library Managment System");
    main_title.setBounds(150,100,800,100);
    main_title.setFont(new Font("Arial", Font.BOLD, 48));
    main_window.add(main_title);

    JButton admin_button = new JButton();
    admin_button.setText("Admin");
    admin_button.setBounds(300, 300, 100, 50);
    admin_button.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            main_window.dispose();
            AdminUI.createAdminWindow();
        }
    });

    

    JButton student_button = new JButton();
    student_button.setText("Student");
    student_button.setBounds(600, 300, 100, 50);
    
    main_window.add(admin_button);
    main_window.add(student_button);

    


    
    
   }
}


















// import java.sql.Connection;
// import java.sql.DriverManager;

// public class Main{
//     public static void main(String[] args) {
//         Connection c = null;
//       try {
//          Class.forName("org.postgresql.Driver");
//          c = DriverManager
//             .getConnection("jdbc:postgresql://localhost:5432/LibraryManagementSystem",
//             "postgres", "database123");
//       } catch (Exception e) {
//          e.printStackTrace();
//          System.err.println(e.getClass().getName()+": "+e.getMessage());
//          System.exit(0);
//       }
//       System.out.println("Opened database successfully");
//       Table t = new Table(c);

//       UI.createFrame(c,500,500);


      
//     }
// }