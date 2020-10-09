package fontys.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
public class Patient {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String disease;

    public Patient(int id, String firstName, String lastName, String email, LocalDate dateOfBirth, String disease) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.disease = disease;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
