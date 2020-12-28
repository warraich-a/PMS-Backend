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
import service.model.Medicine;

import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.glassfish.jersey.message.internal.ReaderWriter.UTF8;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MedicineRepositoryTest {

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
    private String medicineName;

    @InjectMocks
    MedicineRepository medicineRepository;

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
    public void getMedicinesTest() throws DatabaseException, URISyntaxException, SQLException {
        List<Medicine> medicines = medicineRepository.getMedicines();

        assertEquals(2, medicines.size());
    }

    @Test
    public void getMedicineByIdTest() throws DatabaseException, URISyntaxException, SQLException {
        Medicine expectedMedicine = new Medicine(1,"kdd", 12,22);

        Medicine actualMedicine = medicineRepository.getMedicine(1);

        assertEquals(expectedMedicine.getMedName(), actualMedicine.getMedName());
    }
    @Test
    public void deleteMedicineTest() throws DatabaseException, URISyntaxException, SQLException {
        boolean isDeleted = medicineRepository.deleteMedicine(1);

        assertEquals(true, isDeleted);
    }
    @Test
    public void deleteMedicine_doesNotExist_returnsFalse() throws DatabaseException, URISyntaxException, SQLException {
        boolean result = medicineRepository.deleteMedicine(11);

        assertFalse(result);
    }
    @Test
    public void updateMedicineTest() throws DatabaseException, URISyntaxException, SQLException {
        medicineName = randomIdentifier();
        Medicine expectedMedicine = new Medicine(1,"kdde", 12,22);
        boolean isUpdated = medicineRepository.updateMedicine(expectedMedicine);

        assertEquals(true, isUpdated);

    }
}
