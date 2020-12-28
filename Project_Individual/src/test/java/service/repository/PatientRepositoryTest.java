package service.repository;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import service.model.User;
import service.model.UserType;

import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.glassfish.jersey.message.internal.ReaderWriter.UTF8;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PatientRepositoryTest {

    // class variable
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    final java.util.Random rand = new java.util.Random();

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    final Set<String> identifiers = new HashSet<String>();

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if(identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }
    private String firstName;
    private String lastName;
    private String email;
    private String streetName;
    private int id;
    private String dateOfBirth;

    @InjectMocks
    PatientRepository patientRepository;

    @Mock
    JDBCRepository jdbcRepository;

    @BeforeEach
    public void setup() throws SQLException, ClassNotFoundException, URISyntaxException {
        Class.forName ("org.h2.Driver");

        when(jdbcRepository.getDatabaseConnection()).thenReturn(
                DriverManager.getConnection("jdbc:h2:mem:~/test") // test is the name of the folder inside db
        );

        RunScript.execute("jdbc:h2:mem:~/test", "", "", "classpath:data.sql", UTF8, false);
    }

    @Test
    public void getPatients() throws DatabaseException, URISyntaxException, SQLException {
        List<User> patients = patientRepository.getPatients();

        assertEquals(2, patients.size());
    }

    @Test
    public void createPatient_alreadyExists_throwsException() {
        User user = new User("Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);

        assertThrows(DatabaseException.class, () -> {
            patientRepository.createPatient(user);
        });
    }

    @Test
    public void getPatientByIdTest() throws DatabaseException, URISyntaxException, SQLException {
        User expectedUser = patientRepository.getPatientById(1);
        User actualUser = new User(1, "Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);

        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }

    @Test
    public void updatePatientTest() throws DatabaseException, URISyntaxException, SQLException {
        firstName = randomIdentifier();
        lastName = randomIdentifier();
        streetName = randomIdentifier();
        email = firstName+"@gmail.com";
        dateOfBirth = "1997-12-01";
        User expectedUser = new User(1, firstName, lastName, email, dateOfBirth, "1324", streetName, 5, "Eindhoven","5455FD", UserType.Patient);
        boolean isAdded = patientRepository.updatePatient(expectedUser);

        assertEquals(true, isAdded);

    }
    @Test
    public void deletePatientTest() throws DatabaseException, URISyntaxException, SQLException {
        boolean isDeleted= patientRepository.deletePatient(1);

        assertEquals(true, isDeleted);

    }
    @Test
    public void deletePatient_doesNotExist_returnsFalse() throws DatabaseException, URISyntaxException, SQLException {
        boolean result = patientRepository.deletePatient(11);

        assertFalse(result);
    }
}
