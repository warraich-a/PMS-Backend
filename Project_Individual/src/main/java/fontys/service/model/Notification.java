package fontys.service.model;

public class Notification {
    private int patientId;
    private String content;

    public Notification() {

    }

    public Notification(int patientId, String content) {
        this.patientId = patientId;
        this.content = content;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
