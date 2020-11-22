package unit_test;

import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Medicine;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Management_Test {
    @Test
    public void AddMedicineToPatientTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        boolean addedMedicine;
        Management m =  new Management(20,114, true);
        addedMedicine = persistenceController.addMedicineToPatient(m);

        assertEquals(isAdded, addedMedicine);

    }

//    @Test
//    public void getMedicineByPatientIdTest(){
//        PersistenceController persistenceController = new PersistenceController();
//        Medicine firstMedicine = null;
//        List<Medicine> foundMedicines;
//
//        foundMedicines = persistenceController.getMedicineByPatientId(18);
//
//        for(Medicine m: foundMedicines) {
//            firstMedicine = m;
//            break;
//        }
//
//        assertEquals(firstMedicine.getMedName(), "New Medicine");
//
//    }
}
