package service.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import service.model.User;
import service.repository.DatabaseException;
import service.repository.PatientRepository;
import service.repository.UserRepository;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

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
        return userRepository.getUser(email, encryptedPassword);
    }
    public User getUserByEmail(String email) throws DatabaseException, SQLException, URISyntaxException {
//        String encryptedPassword = doHashing(password);
        User u = userRepository.getUserByEmail(email);

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

//            String encryptedPassword = doHashing(user.getPassword());
//            user.setPassword(encryptedPassword);

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

    //update patient
    public boolean updatePassword(User user) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        User user1 = getPatientById(user.getId());
        try {
            String encryptedOldPassword = doHashing(user.getOldPassword());
            if(!user1.getPassword().equals(encryptedOldPassword))
            {
            return false;
            }
          else{
                String encryptedPassword = doHashing(user.getPassword());
                user.setPassword(encryptedPassword);
                if(patientRepository.updatePassword(user)) {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } catch (DatabaseException | SQLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
    //update patient
    public boolean updatePasswordByPharmacist(User user) {
//        JDBCPatientRepository patientRepository = new JDBCPatientRepository();
        try {

                String encryptedPassword = doHashing(user.getPassword());
                user.setPassword(encryptedPassword);
                if(patientRepository.updatePassword(user)) {
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
    public User getUser(int userId){

//        ProfileRepository profileRepository = new ProfileRepository();


        User user = getPatientById(userId);

        System.out.println("ok");

        return user;

    }

    public User getUserFromToken(String token) {
        Claims decoded = decodeJWT(token);

        String id = decoded.getId();

        User u = getUser(parseInt(id));

        return u;
    }
    public static String SECRET_KEY = "oeRaYY";

    public String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

//        //if it has been specified, let's add the expiration
//        if (ttlMillis >= 0) {
//            long expMillis = nowMillis + ttlMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
    public Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
