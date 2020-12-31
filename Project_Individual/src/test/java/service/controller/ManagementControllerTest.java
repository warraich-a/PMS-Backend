package service.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.model.*;
import service.repository.DatabaseException;
import service.repository.ManagementRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManagementControllerTest {

    @InjectMocks
    ManagementController managementController;

    @Mock
    ManagementRepository managementRepository;

    @Test
    public void getManagementsTest() throws DatabaseException, SQLException, URISyntaxException {
        Management m1 = new Management(1, 1, 1, true, "2020-12-12", "2020-12-20");
        Management m2 = new Management(1, 1, 1, true, "2020-12-12", "2020-12-20");

        List<Management> expectedList = Arrays.asList( m1, m2);

        when(managementRepository.getManagements())
                .thenReturn(
                        Arrays.asList( m1, m2)
                );

        List<Management> actualList = managementController.getManagements();

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(1), actualList.get(1));
    }



    @Test
    public void addMedicineToPatientTest() throws DatabaseException, SQLException, URISyntaxException, ParseException {

        Management m1 = new Management(1, 1, 1, true, "2020-12-12", "2020-12-20");

        when(managementRepository.addMedicineToPatient(m1))
                .thenReturn(
                        true
                );

        boolean isAdded = managementController.addMedicineToPatient(m1);

        assertEquals(true, isAdded);
    }

    @Test
    public void getMedicineByPatientIdTest() throws DatabaseException, SQLException, URISyntaxException {
        Medicine m1 = new Medicine("new", 12,22);
        Medicine m2 = new Medicine("news", 132,211);

        List<Medicine> expectedList = Arrays.asList( m1, m2);

        when(managementRepository.getMedicinesByPatientId(1))
                .thenReturn(
                        Arrays.asList( m1, m2)
                );

        List<Medicine> actualList = managementController.getMedicineByPatientId(1);

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(1), actualList.get(1));
    }

    @Test
    public void deleteMedicinePatientTest() throws DatabaseException, SQLException, URISyntaxException {

        when(managementRepository.deleteMedicinePatient(1, 1))
                .thenReturn(
                        true
                );

        boolean deleted = managementController.deleteMedicinePatient(1, 1);

        assertEquals(true, deleted);
    }

    @Test
    public void setNotificationTest() throws DatabaseException, SQLException, URISyntaxException, ParseException {

        when(managementRepository.setNotification(1, "New notification"))
                .thenReturn(
                        true
                );

        boolean isAdded = managementController.setNotification(1, "New notification");

        assertEquals(true, isAdded);
    }

    @Test
    public void getNotificationTest() throws DatabaseException, SQLException, URISyntaxException {
        Notification n1 = new Notification(1, "New notification", 1, "2020-12-12");
        Notification n2 = new Notification(2, "New Medicine added", 1, "2020-12-12");

        List<Notification> expectedList = Arrays.asList( n1, n2);

        when(managementRepository.getNotificationsByPatientId(1))
                .thenReturn(
                        Arrays.asList( n1, n2)
                );

        List<Notification> actualList = managementController.getNotification(1);

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(1), actualList.get(1));
    }
}
