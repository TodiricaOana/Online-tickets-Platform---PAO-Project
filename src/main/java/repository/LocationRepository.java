package repository;

import exceptions.LocationNotFoundException;
import model.Location;

import java.sql.*;

public class LocationRepository {
    private static final LocationRepository locationRepositoryInstance = new LocationRepository();
    String url = "jdbc:mysql://localhost/bilete";
    String username = "root";
    String password = "";

    private LocationRepository() { }

    public static LocationRepository getInstance() {
        return locationRepositoryInstance;
    }

    public void addLocation(Location location) {
        try{
            String sqlInsert = "INSERT INTO LOCATIONS (NAME, NUMBER_AVAILABLE_SEATS) VALUES (?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);

            statement.setString(1, location.getName());
            statement.setInt(2, location.getNumberAvailableSeats());

            statement.executeUpdate();

            dbConnection.close();
            statement.close();
        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public Location getLocationById(Integer id) {
        Location location = null;

        try {
            String sqlSelect = "SELECT NAME, NUMBER_AVAILABLE_SEATS FROM LOCATIONS WHERE LOCATION_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                String name = result.getString("NAME");
                Integer numberAvailableSeats = result.getInt("NUMBER_AVAILABLE_SEATS");

                location = new Location(name, numberAvailableSeats);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }

        return location;
    }

    public Integer getNumberOfSeatsById(Integer id) throws LocationNotFoundException {
        try {
            String sqlSelect = "SELECT NUMBER_AVAILABLE_SEATS FROM LOCATIONS WHERE LOCATION_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return result.getInt("NUMBER_AVAILABLE_SEATS");
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }

        throw new LocationNotFoundException("The location couldn't be found!");
    }
}
