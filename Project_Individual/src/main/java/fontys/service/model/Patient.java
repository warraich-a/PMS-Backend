package fontys.service.model;
import org.glassfish.grizzly.websockets.WebSocket;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.Socket;

@XmlRootElement
public class Patient {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
//    private int year;
//    private String month;
//    private int day;
//    private String disease;
    private String password;
    private String streetName;
    private int houseNr;
    private String city;
    private String zipcode;
    private UserType userType;
    private WebSocket webSocket;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Patient(int id, String firstName, String lastName, String email, String dateOfBirth, String password, String streetName, int houseNr, String city, String zipcode, UserType u) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
//        this.year = year;
//        this.month = month;
//        this.day = day;

//        this.disease = disease;
        this.password = password;
        this.streetName = streetName;
        this.houseNr = houseNr;
        this.city = city;
        this.zipcode = zipcode;
        this.userType = u;


    }


    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public Patient(String firstName, String lastName,
                   String email, String dateOfBirth, String password,
                   String streetName, int houseNr,
                   String city, String zipcode, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.streetName = streetName;
        this.houseNr = houseNr;
        this.city = city;
        this.zipcode = zipcode;
        this.userType = userType;
//        this.webSocket = socket;
    }


    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public int getYear() {
//        return year;
//    }
//
//    public void setYear(int year) {
//        this.year = year;
//    }
//
//    public String getMonth() {
//        return month;
//    }
//
//    public void setMonth(String month) {
//        this.month = month;
//    }
//
//    public int getDay() {
//        return day;
//    }
//
//    public void setDay(int day) {
//        this.day = day;
//    }

//    public String getDisease() {
//        return disease;
//    }
//
//    public void setDisease(String disease) {
//        this.disease = disease;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
