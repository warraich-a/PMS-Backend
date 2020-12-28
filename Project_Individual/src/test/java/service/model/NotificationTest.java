package service.model;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class NotificationTest {
    @Test
    void NotificationTest(){

        Notification n1 = new Notification(1, "New notification", 1, "2020-12-12");

        assertEquals(1, n1.getId());
        assertEquals("New notification", n1.getContent());

    }

    @Rule // this rule is added to throw exceptions when its needed
    ExpectedException thrown = ExpectedException.none();

    @Test // user first name null
    void NullValueException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("None of the fields should be null");

        Notification notification= new Notification();
        notification.setContent("");
        notification.setPatientId(0);

    }
}
