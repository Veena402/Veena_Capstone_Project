package FileReader;

import DataBaseConnection.DBConnection;

import javax.swing.plaf.nimbus.State;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataRead {
    public static String readAadharPropertyFile(){
        String AadharNumber = null;
        try {
            FileReader FR = new FileReader("C:\\Users\\veenana\\Music\\New folder (2)\\Capstone_BankAccountCreation\\src\\test\\java\\aadharcard.properties");
            Properties properties = new Properties();
            properties.load(FR);
            AadharNumber =properties.getProperty("Aadhar_Number");
            FR.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AadharNumber;
    }
    public static List<String> getAadharCardDetailsFromDB(String aadharNumber){
        Connection con = null;
        List<String> DetailsFromDB = new ArrayList<>();
        try{
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "root123");
            if(con!=null){
                System.out.println("Database Connected");
            }
            Statement stmt = con.createStatement();
            stmt.execute("use BankAccounts");
            String Query =  "SELECT * FROM AadharRecord WHERE Aadhar_Number = ?";
            PreparedStatement prepstmt =con.prepareStatement(Query);
            prepstmt.setString(1,aadharNumber);
            ResultSet resultSet =prepstmt.executeQuery();

            while (resultSet.next()){
                DetailsFromDB.add(resultSet.getString("Fname"));
                DetailsFromDB.add(resultSet.getString("Lname"));
                DetailsFromDB.add(resultSet.getString("Aadhar_Number"));
                DetailsFromDB.add(resultSet.getString("Address"));
                DetailsFromDB.add(resultSet.getString("Phone"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DetailsFromDB;
    }
    public String createJsonBody(String firstName,String lastName,String AadharNumber,String Address,String PhoneNumber) {
        String body = "{\n" +
                "    \"Fname\": \""+firstName+"\",\n" +
                "    \"Lname\": \""+lastName+"\",\n" +
                "    \"Aadhar_No\": \""+AadharNumber+"\",\n" +
                "    \"Address\": \""+Address+"\",\n" +
                "    \"Phone\": \""+PhoneNumber+"\",\n" +
                "    \"AccountId\": \"123456789\"\n" +
                "}";
        return body;
    }

}
