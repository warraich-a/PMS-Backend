package unit_test;

import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Medicine;
import fontys.service.model.Patient;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Medicine_Test {

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

    @Test
    public void AddNewMedicineTest(){
        medicineName = randomIdentifier();

        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        boolean newMedicine;
        Medicine m = new Medicine(medicineName, 10, 15);
        newMedicine = persistenceController.addMedicine(m);

        assertEquals(isAdded, newMedicine);

    }
    @Test
    public void UpdateMedicineTest(){
        medicineName = randomIdentifier();
        PersistenceController persistenceController = new PersistenceController();
        boolean isUpdate = true;
        boolean updateMedicine;
        Medicine m = new Medicine(117, medicineName, 18, 20);
        updateMedicine= persistenceController.updateMedicine(m);

        assertEquals(isUpdate, updateMedicine);

    }

    @Test
    public void deleteMedicineTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isDeleted = true;
        boolean deleteMedicine;
        deleteMedicine= persistenceController.deleteMedicine(116);

        assertEquals(isDeleted, deleteMedicine);

    }

    @Test
    public void getMedicineByIdTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        String medicineName;
        Medicine medicine;
        medicine = persistenceController.getMedicineById(114);
        medicineName = medicine.getMedName();

        assertEquals(medicineName, "New Medicine");

    }
}
