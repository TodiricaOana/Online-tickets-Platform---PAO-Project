package repository;

import model.Child;
import model.Client;
import model.Retired;
import model.Student;

import java.sql.*;

public class ClientRepository {
    private static final ClientRepository clientRepositoryInstance = new ClientRepository();
    String url = "jdbc:mysql://localhost/bilete";
    String username = "root";
    String password = "";

    private ClientRepository() { }

    public static ClientRepository getInstance() {
        return clientRepositoryInstance;
    }

    public void addClient(Client client) {
        try{
            String sqlInsert = "INSERT INTO CLIENTS (FIRSTNAME, LASTNAME, EMAIL, PASSWORD, TYPE, DISCOUNT) VALUES (?, ?, ?, ?, ?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPassword());
            statement.setString(5, client.getType());
            statement.setDouble(6, client.getDiscount());

            statement.executeUpdate();
            dbConnection.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public Client getClientByEmail(String email) {
        Client client = null;

        try {
            String sqlSelect = "SELECT FIRSTNAME, LASTNAME, PASSWORD, TYPE, DISCOUNT FROM CLIENTS WHERE EMAIL = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                String firstName = result.getString("FIRSTNAME");
                String lastName = result.getString("LASTNAME");
                String password = result.getString("PASSWORD");
                String type= result.getString("TYPE");

                if(type.equals("Client"))
                    client = new Client(firstName, lastName, email, password);

                if(type.equals("Child"))
                    client = new Child(firstName, lastName, email, password);

                if (type.equals("Retired"))
                    client = new Retired(firstName, lastName, email, password);

                if(type.equals("Student"))
                    client = new Student(firstName, lastName, email, password);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }

        return client;
    }

    public void updatePassword (String email, String newPassword) {
        try {
            String sqlUpdate = "UPDATE CLIENTS SET PASSWORD = ? WHERE EMAIL = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlUpdate);

            statement.setString(1, newPassword);
            statement.setString(2, email);

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }


    public void removeClientByEmail(String email) {
        try {
            String sqlDelete = "DELETE FROM CLIENTS WHERE EMAIL = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlDelete);

            statement.setString(1, email);
            statement.execute();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }
}
