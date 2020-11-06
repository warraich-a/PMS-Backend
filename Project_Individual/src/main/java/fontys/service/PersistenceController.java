package fontys.service;

import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.repository.JDBCManagementRepository;
import fontys.service.repository.JDBCMedicineRepository;
import fontys.service.repository.JDBCPatientRepository;
import fontys.service.repository.DatabaseException;

import java.util.List;

public class PersistenceController {

    public List<Patient> getPatients() {
        JDBCPatientRepository jdbcPatientRepository = new JDBCPatientRepository();

        try {
            List<Patient> patients =  jdbcPatientRepository.getPatients();
            return patients;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
    // get medicine by id
    public Patient getPatientById(int patientId) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();

        try {
            Patient patient = patientRepository.getPatientById(patientId);
            return  patient;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //add medicine
    public boolean addPatient(Patient patient) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.createPatient(patient)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }
    //update patient
    public boolean updatePatient(Patient patient) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.updatePatient(patient)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }
    //delete medicine
    public boolean deletePatient(int patientId) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.deletePatient(patientId)) {
                return true;
            }
            else
            {
                return false;
            }
//            System.out.println("Created student: " + medicine);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get all medicines
    public List<Medicine> getMedicines() {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            List<Medicine> medicines =  medicineRepository.getMedicines();
            return medicines;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get medicine by id
    public Medicine getMedicineById(int medicineId) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            Medicine medicine = medicineRepository.getMedicine(medicineId);
            return  medicine;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //add medicine
    public boolean addMedicine(Medicine medicine) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.createMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }
    //delete medicine
    public boolean deleteMedicine(int medicineId) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.deleteMedicine(medicineId)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //update medicine
    public boolean updateMedicine(Medicine medicine) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();
        try {
            if(medicineRepository.updateMedicine(medicine)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }


    //add medicine
    public List<Management> getManagements() {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();

        try {
            List<Management> managements = (List<Management>) managementRepository.getManagements();
            return managements;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //add medicine
    public boolean addMedicineToPatient(Management management) {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.addMedicineToPatient(management)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public List<Medicine> getMedicineByPatientId(int patientId) {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            List<Medicine> medicines = (List<Medicine>) managementRepository.getMedicinesByPatientId(patientId);
            return medicines;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
