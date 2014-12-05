package demo.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.ValidatorException;

import oracle.jdbc.driver.OracleDriver;

public class RegistrationBean {
   
   private String _fname;
   private String _lname;
   private String _emailID;
   private String _jobRole;
   private String _password;
   private String _confirmPassword;
   private String _question;
   private String _answer;
   private Connection conn3;
   private PreparedStatement pstmt;


    public void setFname(String _fname) {
        this._fname = _fname;
    }

    public String getFname() {
        return _fname;
    }

    public void setLname(String _lname) {
        this._lname = _lname;
    }

    public String getLname() {
        return _lname;
    }

    public void setEmailID(String _emailID) {
        this._emailID = _emailID;
    }

    public String getEmailID() {
        return _emailID;
    }

    public void setJobRole(String _jobRole) {
        this._jobRole = _jobRole;
    }

    public String getJobRole() {
        return _jobRole;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }

    public void setQuestion(String _question) {
        this._question = _question;
    }

    public String getQuestion() {
        return _question;
    }

    public void setAnswer(String _answer) {
        this._answer = _answer;
    }

    public String getAnswer() {
        return _answer;
    }
    
    public String registerUser(){
        
        try{
            DriverManager.registerDriver(new OracleDriver());
            conn3 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","password");
            pstmt = conn3.prepareStatement("INSERT INTO LOGIN VALUES ( ? , ? , ? , ? , ? , ? , ? )");
            pstmt.setString(1, _emailID);
            pstmt.setString(2, _password);
            pstmt.setString(3, _question);
            pstmt.setString(4, _answer);
            pstmt.setString(5, _fname);
            pstmt.setString(6, _lname);
            pstmt.setString(7, _jobRole);
            pstmt.executeUpdate();
            conn3.commit();
            return "registered";            
        }
        catch(SQLException sqle){
            System.out.println("Error Inserting record");
            sqle.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Some other exception");
            e.printStackTrace();
        }
        return "failed";
    }

    public void setConfirmPassword(String _confirmPassword) {
        this._confirmPassword = _confirmPassword;
    }

    public String getConfirmPassword() {
        return _confirmPassword;
    }

    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(object!=null){
                    String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Email is not in Proper Format";
            if (!matcher.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }


    public void passwordValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        if(object!=null){
                    String name = object.toString();
            String expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%])(?=\\S+$).{8,}$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Password is not as per the criteria given";
            if (!matcher.matches()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }
}
