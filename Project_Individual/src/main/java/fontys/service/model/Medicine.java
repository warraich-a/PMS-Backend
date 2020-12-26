package fontys.service.model;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlRootElement
public class Medicine {

    private int id;
    private String medName;
    private double price;
    private double sellingPrice;
    private boolean isActive;
    private String startDate;
    private String endDate;
    private int managementId;


    public Medicine(int givenId, String givenName, double givenPrice , double givenSellingPrice)
    {
        this.id = givenId;
        this.medName = givenName;
        this.price = givenPrice;
        this.sellingPrice = givenSellingPrice;

    }

    public int getManagementId() {
        return managementId;
    }

    public void setManagementId(int managementId) {
        this.managementId = managementId;
    }

    public Medicine(String givenName, double givenPrice , double givenSellingPrice)
    {
        this.medName = givenName;
        this.price = givenPrice;
        this.sellingPrice = givenSellingPrice;

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Medicine(int givenId, String givenName, double givenPrice , double givenSellingPrice, boolean isActive)
    {
        this.id = givenId;
        this.medName = givenName;
        this.price = givenPrice;
        this.sellingPrice = givenSellingPrice;
        this.isActive = isActive;

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


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
