package fontys.service.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Medicine {

    private int id;
    private String medName;
    private double price;
    private double sellingPrice;

    public Medicine(int givenProductId, String givenName, double givenPrice , double givenSellingPrice)
    {
        this.id = givenProductId;
        this.medName = givenName;
        this.price = givenPrice;
        this.sellingPrice = givenSellingPrice;

    }

    public Medicine() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    /*  public String getMedName() {return medName;}

    public void setMedName(String givenName){
        this.medName = givenName;
    }

    public int getId() {return id;}
   *//* public void setId(int givenId){ this.id = givenId; }*//*

    public double getPrice(){return price;}
   *//* public void setPrice(double givenPrice) {
        this.price = givenPrice;
    }*//*


    public double getSellingPrice(){return sellingPrice;}
    public void setSellingPrice(double givenSellingPrice) {
        this.price = givenSellingPrice;
    }*/

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine m = (Medicine) o;
        return Objects.equals(medName, m.medName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medName);
    }*/

   /* @Override
    public String toString() {
        return "Medicine{" +
                "id='" + id + '\'' +
                "name='" + medName + '\'' +
                ", Price='" + price + '\'' +
                ", Selling Price ='" + sellingPrice + '\'' +
                '}';
    }*/

}
