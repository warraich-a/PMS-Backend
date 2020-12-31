package service.repository;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import service.model.*;

import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.glassfish.jersey.message.internal.ReaderWriter.UTF8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ManagementRepositoryTest {

    @InjectMocks
    ManagementRepository managementRepository;

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
    @AfterEach
    public void tearDown() throws Exception {
        RunScript.execute("jdbc:h2:mem:~/test", "", "", "classpath:shutdown.sql", UTF8, false);
    }

    @Test
    public void getManagementsTest() throws DatabaseException, URISyntaxException, SQLException {
        List<Management> managements = managementRepository.getManagements();

        assertEquals(2, managements.size());
    }

    @Test
    public void setNotificationTest() throws URISyntaxException, SQLException, ParseException, DatabaseException {
        boolean isAdded = managementRepository.setNotification(1, "New notification");

        assertTrue(isAdded);
    }

    @Test
    public void getNotificationTest() throws DatabaseException, URISyntaxException, SQLException {
        List<Notification> managements = managementRepository.getNotificationsByPatientId(1);

        assertEquals(1, managements.size());
    }

    @Test
    public void deleteMedicinePatientTest() throws DatabaseException, URISyntaxException, SQLException {
        boolean isDeleted = managementRepository.deleteMedicinePatient(1, 1);

        assertTrue(isDeleted);
    }




}
