package service.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.model.User;
import service.model.UserType;
import service.repository.DatabaseException;
import service.repository.PatientRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    PatientRepository patientRepository;

    @Test
    public void GetPatientsTest() throws DatabaseException, SQLException, URISyntaxException {

        User p1 = new User(1, "Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);
        User p2 = new User(2, "Naqsh", "Ahmad", "Naqsh@gmail.com", "1999-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);


        List<User> expectedList = Arrays.asList( p1, p2);

        when(patientRepository.getPatients())
                .thenReturn(
                        Arrays.asList(p1, p2)
                );

        List<User> actualList = userController.getPatients();

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(1), actualList.get(1));
    }

    @Test
    public void getPatientByIdTest() throws DatabaseException, SQLException, URISyntaxException {

        User p1 = new User(1, "Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);



        User expectedUser = p1;

        when(patientRepository.getPatientById(1))
                .thenReturn(
                        p1
                );

        User actualUser = userController.getPatientById(1);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void addPatientTest() throws DatabaseException, SQLException, URISyntaxException {

        User p1 = new User(1, "Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);

        when(patientRepository.createPatient(p1))
                .thenReturn(
                        true
                );

        boolean isAdded = userController.addPatient(p1);

        assertEquals(true, isAdded);
    }

    @Test
    public void updatePatientTest() throws DatabaseException, SQLException, URISyntaxException {

        User p1 = new User(1, "Anasa", "Ahmad", "Anasa@gmail.com", "1996-09-03", "1324", "straat", 55, "Mierlo", "4545DF", UserType.Patient);

        when(patientRepository.updatePatient(p1))
                .thenReturn(
                        true
                );

        boolean isAdded = userController.updatePatient(p1);

        assertEquals(true, isAdded);
    }

    @Test
    public void deletePatientTest() throws DatabaseException, SQLException, URISyntaxException {
        
        when(patientRepository.deletePatient(1))
                .thenReturn(
                        true
                );

        boolean deletePatient = userController.deletePatient(1);

        assertEquals(true, deletePatient);
    }
}
