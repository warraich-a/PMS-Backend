package fontys.service.repository;

import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.model.Pharmacist;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeData {

    private final List<Patient> patientList = new ArrayList<>();
    private final List<Pharmacist> pharmacistList = new ArrayList<>();
    private final List<Medicine> medicineList = new ArrayList<>();

    // singleton pattern
    private static final FakeData INSTANCE = new FakeData();
    public static FakeData getInstance() {
        return INSTANCE;
    }

    private FakeData() {

        Patient a = new Patient(1 , "John", "Doe", "john@gmai,com",  LocalDate.of(1998,01,01),"Heart Patient");
        Patient b = new Patient(2 , "Johnson", "Goerde", "johnson@gmai,com",  LocalDate.of(1998,01,01),"Kidney");
        Patient c = new Patient(3 , "Sania", "Dil", "sania@gmai,com",  LocalDate.of(1998,01,01),"Lungs");
        Patient d = new Patient(4 , "kai", "Eujl", "kai@gmai,com",  LocalDate.of(1998,01,01),"Cough");


        patientList.add(a);
        patientList.add(b);
        patientList.add(c);
        patientList.add(d);

        Pharmacist p1 = new Pharmacist(1, "Anas", "Ahmad", "anas@gmail.com" );
        Pharmacist p2 = new Pharmacist(2, "Shay", "Day", "shay@gmail.com" );
        Pharmacist p3 = new Pharmacist(3, "Dill", "Jaa", "dill@gmail.com" );

        pharmacistList.add(p1);
        pharmacistList.add(p2);
        pharmacistList.add(p3);

        Medicine m1 = new Medicine(1, "abc", 12, 15);
        Medicine m2 = new Medicine(2, "efg", 15, 17);
        Medicine m3 = new Medicine(3, "hig", 11, 18);

        medicineList.add(m1);
        medicineList.add(m2);
        medicineList.add(m3);
    }

    //Get Lists

    //Patients
    public List<Patient> getPatientsList() {return patientList;}

    //Pharmacist
    public List<Pharmacist> getPharmacistList(){return pharmacistList;}

    //Medicines
    public List<Medicine> getMedicineList(){return medicineList;}


    ///Patients
    // to delete patient
    public boolean deletePatient(int stNr) {
        Patient patient = getPatientById(stNr);
        if (patient == null){
            return false;
        }

        return patientList.remove(patient);
    }
    //add
    public boolean addPatient(Patient p) {
        if (this.getPatientById(p.getId()) == null){
            patientList.add(p);
            return true;
        }
        return false;
    }

    //get
    public Patient getPatientById(int id){
        for (Patient patient: patientList){
            if(patient.getId() == id){
                return patient;
            }
        }
        return null;
    }

    // update
    public boolean updatePatient(int id, Patient p) {
        Patient old = this.getPatientById(id);
        if (old == null) {
            return false;
        }
        old.setDisease(p.getDisease());
        old.setEmail(p.getEmail());

        return true;
    }

    public boolean updateMedcine(int id, Medicine m) {
        Medicine old = this.getMedicineById(id);
        if (old == null) {
            return false;
        }
        old.setMedName(m.getMedName());
        old.setPrice(m.getPrice());
        old.setSellingPrice(m.getSellingPrice());

        return true;
    }

  /*  public Patients getPatient(String name){
        for (Patients patient: patientsList){
            if(patient.getPatientName().equals(name)){
                return patient;
            }
        }
        return null;
    }*/


    //Medicines
    //add
    public boolean addMedicines(Medicine m) {
        if (this.getMedicineById(m.getId()) == null){
            medicineList.add(m);
            return true;
        }
        return false;
    }
    //get
    public Medicine getMedicineById(int id){
        for (Medicine m: medicineList){
            if(m.getId() == id){
                return m;
            }
        }
        return null;
    }
    //delete
    public boolean DeleteMedicine(int medId) {
        Medicine m = getMedicineById(medId);
        if (m == null){
            return false;
        }

        return medicineList.remove(m);
    }




}
