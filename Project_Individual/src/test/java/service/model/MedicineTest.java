package service.model;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class MedicineTest {

    @Test
    void MedicineTest(){

        Medicine m1 = new Medicine(1, "new", 12,22);

        assertEquals(1, m1.getId());
        assertEquals("new", m1.getMedName());

    }

    @Rule // this rule is added to throw exceptions when its needed
    ExpectedException thrown = ExpectedException.none();

    @Test // user first name null
    void NullValueException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("None of the fields should be null");

        Medicine a= new Medicine();
        a.setEndDate(null);
        a.setMedName(null);

    }
}
