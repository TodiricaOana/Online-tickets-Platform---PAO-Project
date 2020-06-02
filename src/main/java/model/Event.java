package model;

public class Event {
    private Integer Id;
    private String name;
    private Double price;


    public Event() {
    }

    public Event(String name, Double price) {
        this.name = name;
        this.price = price;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
