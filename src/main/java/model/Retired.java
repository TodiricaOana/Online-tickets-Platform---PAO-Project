package model;

public class Retired extends Client {
    private static Double discount;

    static {
        discount = 25.0;
    }

    public Retired() {
    }

    public Retired(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    public Double getDiscount() {
        return discount;
    }

    public String getType(){
        return "Retired";
    }

    @Override
    public Double getPrice(Event event){
        Double newPrice = event.getPrice() * (1 -  discount / 100);
        return newPrice;
    }

    @Override
    public String toString() {
        return "Retired{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
