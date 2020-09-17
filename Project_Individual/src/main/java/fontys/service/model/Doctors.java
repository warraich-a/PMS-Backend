package fontys.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Doctors {

    private String name;
    private String field;

    public Doctors(String name, String field) {
        this.name = name;
        this.field = field;
    }

    public Doctors() {

    }


    public String getName() {return name;}

    public void setName(String givenName){
        this.name = givenName;
    }

    public String getField() {
        return field;
    }

    public void setField(String givenField) {
        this.field = givenField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctors doctors = (Doctors) o;
        return Objects.equals(name, doctors.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Doctors{" +
                "name='" + name + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}
