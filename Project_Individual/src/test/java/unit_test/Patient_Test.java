package unit_test;

import fontys.service.PersistenceController;
import fontys.service.model.Management;
import fontys.service.model.Patient;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Patient_Test {

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
    private int id;

    @Test
    public void AddNewPatientTest(){
        firstName = randomIdentifier();
        lastName = randomIdentifier();
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        boolean newPatient;
        Patient a = new Patient(firstName, lastName, "john@gmai,com", 2000, "Mar",5, "Lungs", "1324", "Strraat", 5, "Eindhoven","5455FD");
        newPatient = persistenceController.addPatient(a);
//        id = a.getId();

        assertEquals(isAdded, newPatient);

    }
    @Test
    public void UpdatePatientTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        boolean updatedPatient;
        Patient a = new Patient(18,"John", "Doooooe", "john@gmai,com", 2000, "Mar",5, "Lungs", "1asfd324", "Straat", 5, "Eindhoven","5455FD");
        updatedPatient= persistenceController.updatePatient(a);

        assertEquals(isAdded, updatedPatient);

    }

    @Test
    public void deletePatientTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        boolean deletePatient;
        deletePatient= persistenceController.deletePatient(25);

        assertEquals(isAdded, deletePatient);

    }

    @Test
    public void getPatientTest(){
        PersistenceController persistenceController = new PersistenceController();
        boolean isAdded = true;
        String lastName;
        Patient p;
        p = persistenceController.getPatientById(18);
        lastName = p.getLastName();

        assertEquals(lastName, "Doe");
//        assertThat(p, samePropertyValuesAs(expected));


    }
}
