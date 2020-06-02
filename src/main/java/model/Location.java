package model;

import java.util.Objects;

public class Location {
    private Integer Id;
    private String name;
    private Integer numberAvailableSeats;

    public Location() {

    }

    public Location(String name, Integer numberAvailableSeats) {
        this.name = name;
        this.numberAvailableSeats = numberAvailableSeats;
    }

    public Location(Location location) {
        this.name = location.name;
        this.numberAvailableSeats = location.numberAvailableSeats.intValue();
    }

    public Integer getId() {
        return Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumberAvailableSeats(Integer numberAvailableSeats) {
        this.numberAvailableSeats = numberAvailableSeats;
    }

    public Integer getNumberAvailableSeats() {
        return numberAvailableSeats;
    }

    public void decreaseAvailableSeats() {
        this.numberAvailableSeats --;
    }

    @Override
    public String toString() {
        return  "Location{" +
                "name='" + name + '\'' +
                ", numberAvailableSeats=" + numberAvailableSeats +
                '}';
    }
}
