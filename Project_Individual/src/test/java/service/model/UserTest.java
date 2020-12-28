package service.model;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    void PatientTest(){

        User p1 = new User(1, "Anas", "Ahmad", "Anas@gmail.com", "1996-09-03", "1324", "straat", 5, "Mierlo", "4545DF", UserType.Patient);

        assertEquals(1, p1.getId());
        assertEquals("Ahmad", p1.getLastName());

    }

    @Rule // this rule is added to throw exceptions when its needed
    ExpectedException thrown = ExpectedException.none();

    @Test // user first name null
    void NullValueException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("None of the fields should be null");

        User a= new User();
        a.setEmail(null);
        a.setCity(null);

    }
}
