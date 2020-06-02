package model;

import java.util.Date;

public class EventDetails {
    private Integer Id;
    private Integer eventId;
    private Integer locationId;
    private Integer numberAvailableSeats;
    private Date date;
    private String category;

    public EventDetails() {
    }

    public EventDetails(Integer eventId, Integer locationId, Date date, String category) {
        this.eventId = eventId;
        this.locationId = locationId;
        this.date = date;
        this.category = category;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getNumberAvailableSeats() {
        return numberAvailableSeats;
    }

    public void setNumberAvailableSeats(Integer numberAvailableSeats) {
        this.numberAvailableSeats = numberAvailableSeats;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "EventDetails{" +
                "Id=" + Id +
                ", eventId=" + eventId +
                ", locationId=" + locationId +
                ", numberAvailableSeats=" + numberAvailableSeats +
                ", date=" + date +
                ", category='" + category + '\'' +
                '}';
    }
}
