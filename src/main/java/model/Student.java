package model;

public class Student extends Client {
    private static Double discount;

    static {
        discount = 25.0;
    }

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    public Double getDiscount() {
        return discount;
    }

    public String getType(){
        return "Student";
    }

    @Override
    public Double getPrice(Event event){
        Double newPrice = event.getPrice() * (1 -  discount / 100);
        return newPrice;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
