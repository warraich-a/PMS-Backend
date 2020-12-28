package service.controller;

import service.model.Management;
import service.model.Medicine;
import service.model.Notification;
import service.model.User;
import service.repository.DatabaseException;
import service.repository.ManagementRepository;
import service.repository.UserRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ManagementController {
    ManagementRepository managementRepository = new ManagementRepository();
    UserRepository pharmacistRepository = new UserRepository();


    //add medicine
    public List<Management> getManagements() {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();

        try {
            return   managementRepository.getManagements();
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    //add medicine
    public boolean addMedicineToPatient(Management management) {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.addMedicineToPatient(management)) {

                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | ParseException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public List<Medicine> getMedicineByPatientId(int patientId) {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            return  managementRepository.getMedicinesByPatientId(patientId);
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUsers(String email, String password) throws DatabaseException, SQLException, URISyntaxException {
        User u = pharmacistRepository.getUser(email, password);
        if(u != null){
            return  u;
        }
        return null;
    }


    //delete medicine of a patient
    public boolean deleteMedicinePatient(int patientId, int medicineId) throws DatabaseException, SQLException {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.deleteMedicinePatient(patientId,medicineId)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public boolean setNotification(int patientId, String content) {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();
        try {
            if(managementRepository.setNotification(patientId, content)) {

                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | ParseException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    //add medicine
    public List<Notification> getNotification(int patientId) {
//        JDBCManagementRepository managementRepository = new JDBCManagementRepository();

        try {
            return managementRepository.getNotificationsByPatientId(patientId);

        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
