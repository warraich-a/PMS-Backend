package service.model;

public class Notification {
    private int patientId;
    private String content;
    private int id;
    private String date;

    public Notification() {
    }

    public Notification(int id, String content, int patientId, String date) {
        this.patientId = patientId;
        this.content = content;
        this.id = id;
        this.date = date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
