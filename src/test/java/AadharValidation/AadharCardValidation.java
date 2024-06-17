package AadharValidation;

import DataBaseConnection.DBConnection;
import FileReader.DataRead;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.List;

import static FileReader.DataRead.getAadharCardDetailsFromDB;
import static FileReader.DataRead.readAadharPropertyFile;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

public class AadharCardValidation {
    DataRead DR;
    @BeforeClass
    public void setUp() throws SQLException {
        DR = new DataRead();
        DBConnection DB = new DBConnection();
        DB.Connect();
        DB.CreateDatabase();
        DB.CreateNewTable();
        DB.InsertData();

    }

    @Parameters({"url"})
    @Test
    public void ValidatingAadharNoFromPropertyFile(String url){
        String AadharNoProperty =null;
        List<String> aadharDetailsFromDB = null;
        boolean matchFound =false;
        AadharNoProperty= readAadharPropertyFile();
        aadharDetailsFromDB = getAadharCardDetailsFromDB(AadharNoProperty);
        matchFound =aadharDetailsFromDB.contains(AadharNoProperty);
        if(matchFound){
            System.out.println("Aadhar Number Matches:");
            CreateBankAccount(url, aadharDetailsFromDB.get(0),aadharDetailsFromDB.get(1),aadharDetailsFromDB.get(2),aadharDetailsFromDB.get(3),aadharDetailsFromDB.get(4));

        }else{
            System.out.println("Aadhar Number doesnot exists");
        }

    }
    public void CreateBankAccount(String url,String firstName,String lastName,String aadharNumber,String address,String phoneNumber){
        Response response = given()
                .contentType(ContentType.JSON)
                .body(DR.createJsonBody(firstName, lastName,aadharNumber,address,phoneNumber))
                .when()
                .post(url);

        int statuscode = response.getStatusCode();
        String responseBody = response.getBody().asString();
        System.out.println(statuscode);
        System.out.println(responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);

        String responseFirstName = jsonObject.getString("Fname");
        String responseLastName = jsonObject.getString("Lname");
        String responseAadharNumber = jsonObject.getString("Aadhar_No");
        String responseAddress = jsonObject.getString("Address");
        String responsePhone = jsonObject.getString("Phone");

        Assert.assertEquals(responseFirstName, firstName);
        Assert.assertEquals(responseLastName, lastName);
        Assert.assertEquals(responseAadharNumber, aadharNumber);
        Assert.assertEquals(responseAddress, address);
        Assert.assertEquals(responsePhone, phoneNumber);

        String accountId = jsonObject.getString("AccountId");
        assertTrue(accountId.matches("\\d+"));

        String createdAt = jsonObject.getString("createdAt");

        System.out.println("Account ID: " + accountId);
        System.out.println("createdAt: " + createdAt);

    }

}
