package fontys.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Patients {

    private String name;
    private int id;
    private String disease;

    public Patients(String name, String disease, int id) {
        this.id = id;
        this.name = name;
        this.disease = disease;

    }

    public Patients() {

    }


    public String getPatientName() {return name;}

    public void setPatientName(String givenName){
        this.name = givenName;
    }

    public int getPatientId() {return id;}

    public void setPatientId(int id){
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String givenDisease) {
        this.disease = givenDisease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patients p = (Patients) o;
        return Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Doctors{" +
                "name='" + name + '\'' +
                ", field='" + disease + '\'' +
                '}';
    }

}
