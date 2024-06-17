package DataBaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection con;
    private String url = "jdbc:mysql://127.0.0.1:3306";
    private String uid = "root";
    private String pass = "root123";

    public void Connect(){
        try{
            System.out.println("Connecting to Database");
            con =DriverManager.getConnection(url,uid,pass);
            System.out.println("Connected to Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void CreateDatabase() throws SQLException {
        Statement stmt =con.createStatement();
        stmt.execute("CREATE DATABASE IF NOT EXISTS BankAccounts");
        System.out.println("Database created successfully");
    }
    public void CreateNewTable() throws SQLException{
        Statement stmt = con.createStatement();
        stmt.execute("use BankAccounts");
        stmt.execute("CREATE TABLE IF NOT EXISTS BankAccounts.AadharRecord (\n" +
                "  Fname VARCHAR(45) NULL,\n" +
                "  Lname VARCHAR(45) NULL,\n" +
                "  Aadhar_Number VARCHAR(45) NULL,\n" +
                "  Address VARCHAR(45) NULL,\n" +
                "  Phone VARCHAR(45) NULL);");
        System.out.println("Table to store Aadhar Records has been Created");
    }
    public void InsertData() throws SQLException{
        Statement stmt = con.createStatement();
        stmt.execute("use BankAccounts");
        stmt.execute("Insert into AadharRecord(Fname,Lname,Aadhar_Number,Address,Phone)values ('Ravi','Naik','100987654345','KR Puram Bangalore','8978654421') ,\n" +
                "('Raju','Bhat','111987654340','KR Puram Bangalore','8978654334'),\n" +
                "('Ramya','Naik','123987654345','KR Puram Bangalore','8970654421'),\n" +
                "('Giri','Shanbag','133987654345','KR Puram Bangalore','8979694420'),\n" +
                "('Soumya','Ambig','234987654345','KR Puram Bangalore','8867865442'),\n" +
                "('Sridhar','Bhat','112987654378','KR Puram Bangalore','9978654490'),\n" +
                "('Manju','Ambig','234567898900','KR Puram Bangalore','8876453412'),\n" +
                "('Meena','Bhat','134566777888','KR Puram Bangalore','8876123451'),\n" +
                "('Khushi','Ambig','111987654123','KR Puram Bangalore','9087675645'),\n" +
                "('Jeni','Bhat','134567546341','KR Puram Bangalore','9007645341');");
        System.out.println("Data inserted into Aadhar reacrd table");
    }
}
