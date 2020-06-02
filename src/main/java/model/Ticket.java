package model;

public class Ticket {
    private Integer id;
    private String clientEmail;
    private Integer eventDetailsId;

    public Ticket() {
    }

    public Ticket(String clientEmail, Integer eventDetailsId) {
        this.clientEmail = clientEmail;
        this.eventDetailsId = eventDetailsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Integer getEventDetailsId() {
        return eventDetailsId;
    }

    public void setEventDetailsId(Integer eventDetailsId) {
        this.eventDetailsId = eventDetailsId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "clientEmail=" + clientEmail+
                ", eventDetailsId=" + eventDetailsId +
                '}';
    }

}
