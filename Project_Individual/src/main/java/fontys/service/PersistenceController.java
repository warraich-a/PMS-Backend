package fontys.service;

import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import fontys.service.model.User;
import fontys.service.repository.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class PersistenceController {

    public List<Patient> getPatients() {
        JDBCPatientRepository jdbcPatientRepository = new JDBCPatientRepository();

        try {
            return jdbcPatientRepository.getPatients();
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // get medicine by id
    public Patient getPatientById(int patientId) {
        JDBCPatientRepository patientRepository = new JDBCPatientRepository();

        try {
            return patientRepository.getPatientById(patientId);
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException e) {
            e.getMessage();
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
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get all medicines
    public List<Medicine> getMedicines() {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            return  medicineRepository.getMedicines();
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get medicine by id
    public Medicine getMedicineById(int medicineId) {
        JDBCMedicineRepository medicineRepository = new JDBCMedicineRepository();

        try {
            return  medicineRepository.getMedicine(medicineId);
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //add medicine
    public List<Management> getManagements() {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();

        try {
            return   managementRepository.getManagements();
        } catch (DatabaseException | SQLException e) {
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
        } catch (DatabaseException | SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public List<Medicine> getMedicineByPatientId(int patientId) {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            return  managementRepository.getMedicinesByPatientId(patientId);
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Patient getUsers(String email, String password) throws DatabaseException, SQLException {
        JDBCUserRepository pharmacistRepository = new JDBCUserRepository();
        Patient u = pharmacistRepository.getUser(email, password);
        if(u != null){
            return  u;
        }


        return null;
    }


    //delete medicine of a patient
    public boolean deleteMedicinePatient(int patientId, int medicineId) throws DatabaseException, SQLException {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.deleteMedicinePatient(patientId,medicineId)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public boolean setNotification(int patientId, String content) {
        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.setNotification(patientId, content)) {

                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
