package service.controller;

import service.model.User;
import service.repository.DatabaseException;
import service.repository.PatientRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    PatientRepository patientRepository = new PatientRepository();

    public List<User> getPatients() {
//        JDBCPatientRepository jdbcPatientRepository = new JDBCPatientRepository();

        try {
            return patientRepository.getPatients();
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    // get medicine by id
    public User getPatientById(int patientId) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();

        try {
            return patientRepository.getPatientById(patientId);
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    //add medicine
    public boolean addPatient(User user) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.createPatient(user)) {
                return true;
            }
            else
            {
                return false;
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.getMessage();
            return false;
        }
    }
    //update patient
    public boolean updatePatient(User user) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.updatePatient(user)) {
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
    //delete medicine
    public boolean deletePatient(int patientId) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            if(patientRepository.deletePatient(patientId)) {
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

}
