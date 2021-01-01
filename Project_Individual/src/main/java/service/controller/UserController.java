package service.controller;

import service.model.User;
import service.repository.DatabaseException;
import service.repository.PatientRepository;
import service.repository.UserRepository;

import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    PatientRepository patientRepository = new PatientRepository();
    UserRepository userRepository = new UserRepository();

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
    public User getUser(String email, String password) throws DatabaseException, SQLException, URISyntaxException {
        String encryptedPassword = doHashing(password);
        User u = userRepository.getUser(email, encryptedPassword);

        if(u != null){
            return  u;
        }
        return null;
    }

    //add medicine
    public boolean addPatient(User user) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {
            String encryptedPassword = doHashing(user.getPassword());
            user.setPassword(encryptedPassword);
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

            String encryptedPassword = doHashing(user.getPassword());
            user.setPassword(encryptedPassword);
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

//        User u = getPatientById(user.getId());
//        try {
//            if(!u.getPassword().equals(user.getPassword())){
//                String encryptedPassword = doHashing(user.getPassword());
//                user.setPassword(encryptedPassword);
//            }
//            else {
//                if (patientRepository.updatePatient(user)) {
//                    User u1 = getPatientById(user.getId());
//                    return u1;
//                } else {
//                    return null;
//                }
//            }
//        } catch (DatabaseException | SQLException | URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return null;
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
    public static String doHashing (String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}
