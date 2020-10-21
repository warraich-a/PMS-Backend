package fontys.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@XmlRootElement
public class Pharmacist {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    //private String password;
    private LocalDateTime dateOfBirth;


    public Pharmacist(int givenId, String givenFirstName, String givenLastName, String givenEmail) {
        id = givenId;
        firstName = givenFirstName;
        lastName = givenLastName;
        email = givenEmail;
    }

    public Pharmacist() {

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

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
