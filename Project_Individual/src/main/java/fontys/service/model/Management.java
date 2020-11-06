package fontys.service.model;

public class Management {
    private int patientId;
    private int medicineId;
    private int id;
    private boolean isActive;
    private static int idSeeder = 1;

    public Management(int id, int patientId, int medicineId,  boolean isActive) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.id = id;
        this.isActive = isActive;
    }
    public Management(int patientId, int medicineId,  boolean isActive) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.id = id;
        this.isActive = isActive;
    }

    public Management() {
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
}
