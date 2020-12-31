package service.model;

import java.util.ArrayList;
import java.util.List;

public class Management {
    private int patientId;
    private int medicineId;
    private int id;
    private boolean isActive;
    private Medicine medicine;
    private static int idSeeder = 1;
    private String startDate;
    private String endDate;

    List<Medicine> medicines = new ArrayList<>();



    public Management(int id, int patientId, int medicineId, boolean isActive, String startDate, String endDate) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.id = id;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Management(int patientId, int medicineId,  boolean isActive) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.id = id;
        this.isActive = isActive;
    }

    public Management(int patientId, int medicineId,String startDate, String endDate) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Management() {
    }


    public void ListOfMedicinesByPatient(int id, Medicine medicine, boolean isActive, String startDate, String endDate) {
        medicine.setManagementId(id);
        medicine.setActive(isActive);
        medicine.setStartDate(startDate);
        medicine.setEndDate(endDate);
        medicines.add(medicine);

    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static int getIdSeeder() {
        return idSeeder;
    }

    public static void setIdSeeder(int idSeeder) {
        Management.idSeeder = idSeeder;
    }
    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
