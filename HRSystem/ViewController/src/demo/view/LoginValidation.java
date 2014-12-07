package demo.view;

import java.sql.*;
import oracle.jdbc.driver.*;

public class LoginValidation {
   
    private String _username;
    private String _password;
    private Connection conn;
    private Statement stmt;
    
    //This is a comment


    public void setUsername(String _username) {
        this._username = _username;
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }
    
    public void initialize() throws Exception{
        
        DriverManager.registerDriver(new OracleDriver());
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","password");
        System.out.println("Connected to database");
    }
    
    public Boolean validLogin(String userName, String password){

        try {
            stmt = conn.createStatement();
            System.out.println("Username: "+userName);
            ResultSet rset = stmt.executeQuery("SELECT PASSWORD FROM LOGIN WHERE EMAILID ='"+userName+"'");
       
            while(rset.next()){
                System.out.println("Password: "+rset.getString(1));
                if(password.equals(rset.getString(1)))
                    return true;
                else{
                    System.out.println("Invalid User");
                    return false;
                }                    
            }
        } catch (SQLException e) {
            System.out.println("Failed SQL query");
            e.printStackTrace();
        }
        System.out.println("No records found");
        return false;
    }
    
    public String doLogin() {
    
    LoginValidation loginValidation = new LoginValidation();

        try {
            loginValidation.initialize();
            System.out.println("Username: "+_username);
            System.out.println("Password: "+_password);
            if(loginValidation.validLogin(_username, _password))
                return "login";
            else
                return "failed";            
        } catch (Exception e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
        return null;
    }
}
