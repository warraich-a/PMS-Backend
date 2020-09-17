package fontys.service.repository;

import fontys.service.model.Doctors;
import fontys.service.model.Patients;

import java.util.ArrayList;
import java.util.List;

public class FakeData {


    private final List<Doctors> doctorsList = new ArrayList<>();
    private final List<Patients> patientsList = new ArrayList<>();

    // singleton pattern
    private static final FakeData INSTANCE = new FakeData();
    public static FakeData getInstance() {
        return INSTANCE;
    }

    private FakeData() {
        // work this out better, add few more countries and students
        Doctors dill = new Doctors("Dill", "General");
        Doctors anas = new Doctors("Anas", "Heart");

        doctorsList.add(dill);
        doctorsList.add(anas);


        Patients b = new Patients("john", "HeartIssue", 2);
        Patients a = new Patients("Hammy", "Brain issue",1);

        patientsList.add(a);
        patientsList.add(b);

    }

  /*  public List<Student> getStudents() {
        return studentList;
    }*/
    public List<Doctors> getDoctorsList() { return doctorsList; }
    public List<Patients> getPatientsList() {return patientsList;}

    public boolean deletePatient(int stNr) {
        Patients patient = getPatientById(stNr);
        if (patient == null){
            return false;
        }

        return patientsList.remove(patient);
    }


    public boolean addPatient(Patients p) {
        if (this.getPatientById(p.getPatientId()) == null){
            patientsList.add(p);
            return true;
        }
        return false;
    }


    public Doctors getDoctor(String name) {
        for (Doctors doctor : doctorsList) {
            if (doctor.getName().equals(name)) {
                return doctor;
            }
        }
        return null;
    }


    public Patients getPatient(String name){
        for (Patients patient: patientsList){
            if(patient.getPatientName().equals(name)){
                return patient;
            }
        }
        return null;
    }

    public Patients getPatientById(int id){
        for (Patients patient: patientsList){
            if(patient.getPatientId() == id){
                return patient;
            }
        }
        return null;
    }
}
