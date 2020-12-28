package service.model;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ManagementTest {

    @Test
    void ManagementTest(){

        Management m1 = new Management(1, 1, 1, true, "2020-12-12", "2020-12-20");

        assertEquals(1, m1.getId());
        assertEquals("2020-12-12", m1.getStartDate());

    }

    @Rule // this rule is added to throw exceptions when its needed
    ExpectedException thrown = ExpectedException.none();

    @Test // user first name null
    void NullValueException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("None of the fields should be null");

        Management a= new Management();
        a.setStartDate(null);
        a.setPatientId(0);

    }
}
