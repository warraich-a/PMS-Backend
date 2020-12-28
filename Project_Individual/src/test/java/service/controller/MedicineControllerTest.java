package service.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.model.*;

import service.repository.DatabaseException;
import service.repository.MedicineRepository;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicineControllerTest {

    @InjectMocks
    MedicineController medicineController;

    @Mock
    MedicineRepository medicineRepository;

    @Test
    public void getMedicinesTest() throws DatabaseException, SQLException, URISyntaxException {
        Medicine m1 = new Medicine(1,"new", 12,22);
        Medicine m2 = new Medicine(2, "news", 132,211);

        List<Medicine> expectedList = Arrays.asList( m1, m2);

        when(medicineRepository.getMedicines())
                .thenReturn(
                        Arrays.asList( m1, m2)
                );

        List<Medicine> actualList = medicineController.getMedicines();

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(1), actualList.get(1));
    }

    @Test
    public void getMedicineByIdTest() throws DatabaseException, SQLException, URISyntaxException {

        Medicine m1 = new Medicine("new", 12,22);

        Medicine expectedMedicine = m1;

        when(medicineRepository.getMedicine(1))
                .thenReturn(
                        m1
                );

        Medicine actualMedicine = medicineController.getMedicineById(1);
        assertEquals(expectedMedicine, actualMedicine);
    }

    @Test
    public void addMedicineTest() throws DatabaseException, SQLException, URISyntaxException {

        Medicine m1 = new Medicine("new", 12,22);

        when(medicineRepository.createMedicine(m1))
                .thenReturn(
                        true
                );

        boolean isAdded = medicineController.addMedicine(m1);

        assertEquals(true, isAdded);
    }

    @Test
    public void updateMedicineTest() throws DatabaseException, SQLException, URISyntaxException {

        Medicine m1 = new Medicine("new test", 12,22);

        when(medicineRepository.updateMedicine(m1))
                .thenReturn(
                        true
                );

        boolean isUpdated = medicineController.updateMedicine(m1);

        assertEquals(true, isUpdated);
    }

    @Test
    public void deleteMedicineTest() throws DatabaseException, SQLException, URISyntaxException {

        when(medicineRepository.deleteMedicine(1))
                .thenReturn(
                        true
                );

        boolean deleteMedicine = medicineController.deleteMedicine(1);

        assertEquals(true, deleteMedicine);
    }
}
