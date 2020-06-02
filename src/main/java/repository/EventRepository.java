package repository;

import model.Event;

import java.sql.*;

public class EventRepository {
    private static final EventRepository eventRepositoryInstance = new EventRepository();
    String url = "jdbc:mysql://localhost/bilete";
    String username = "root";
    String password = "";

    private EventRepository() { }

    public static EventRepository getInstance() {
        return eventRepositoryInstance;
    }

    public void addEvent(Event event) {
        try{
            String sqlInsert = "INSERT INTO EVENTS (NAME, PRICE) VALUES (?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);

            statement.setString(1, event.getName());
            statement.setDouble(2, event.getPrice());

            statement.executeUpdate();

            dbConnection.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public Event getEventById(Integer id) {
        Event event = null;

        try {
            String sqlSelect = "SELECT NAME, PRICE FROM EVENTS WHERE EVENT_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                String name = result.getString("NAME");
                Double price = result.getDouble("PRICE");

                event = new Event(name, price);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
        return event;
    }

    public void updateName (Integer id, String newName) {
        try {
            String sqlUpdate = "UPDATE EVENTS SET NAME = ? WHERE EVENT_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlUpdate);

            statement.setString(1, newName);
            statement.setInt(2, id);

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public void removeEventById(Integer id) {
        try {
            String sqlDelete = "DELETE FROM EVENTS WHERE EVENT_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlDelete);

            statement.setInt(1, id);
            statement.execute();

            dbConnection.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }
}
