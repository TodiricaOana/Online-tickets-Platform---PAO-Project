package repository;

import exceptions.EventDetailsNotFoundException;
import exceptions.LocationNotFoundException;
import model.EventDetails;

import service.LocationService;

import java.sql.*;
import java.util.ArrayList;

public class EventDetailsRepository {
    private static final EventDetailsRepository eventDetailsRepositoryInstance = new EventDetailsRepository();
    String url = "jdbc:mysql://localhost/bilete";
    String username = "root";
    String password = "";

    private EventDetailsRepository() { }

    public static EventDetailsRepository getInstance() {
        return eventDetailsRepositoryInstance;
    }

    public void addEventDetails(EventDetails eventDetails) {
        try {
            String sqlInsert = "INSERT INTO EVENT_DETAILS (EVENT_ID, LOCATION_ID, NUMBER_AVAILABLE_SEATS, DATE, CATEGORY) VALUES (?, ?, ?, ?, ?)";

            Integer availableSeats = LocationService.getInstance().getNumberOfSeatsById(eventDetails.getLocationId());

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);

            statement.setInt(1, eventDetails.getEventId());
            statement.setInt(2, eventDetails.getLocationId());
            statement.setInt(3, availableSeats);
            statement.setDate(4, new java.sql.Date(eventDetails.getDate().getTime()));
            statement.setString(5, eventDetails.getCategory());

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | LocationNotFoundException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public EventDetails getEventDetailsById(Integer id) {
        EventDetails eventDetails = null;

        try {
            String sqlSelect = "SELECT EVENT_ID, LOCATION_ID, NUMBER_AVAILABLE_SEATS, DATE, CATEGORY FROM EVENT_DETAILS WHERE EVENT_DETAILS_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                Integer eventId = result.getInt("EVENT_ID");
                Integer locationId = result.getInt("LOCATION_ID");
                Integer numberSeats = result.getInt("NUMBER_AVAILABLE_SEATS");
                Date date = result.getDate("DATE");
                String category = result.getString("CATEGORY");

                eventDetails = new EventDetails(eventId, locationId, date, category);
                eventDetails.setNumberAvailableSeats(numberSeats);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }

        return eventDetails;
    }

    public Integer getNumberAvailableSeatsById(Integer id) throws EventDetailsNotFoundException {
        try {
            String sqlSelect = "SELECT NUMBER_AVAILABLE_SEATS FROM EVENT_DETAILS WHERE EVENT_DETAILS_ID = ?";

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
        throw new EventDetailsNotFoundException("The event detail couldn't be found!");
    }

    public void decreaseNumberById(Integer id) throws EventDetailsNotFoundException {
        try {
            int availableSeats = getNumberAvailableSeatsById(id) - 1;

            String sqlUpdate = "UPDATE EVENT_DETAILS SET NUMBER_AVAILABLE_SEATS = ? WHERE EVENT_DETAILS_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlUpdate);

            statement.setInt(1, availableSeats);
            statement.setInt(2, id);

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public void increaseNumberById(Integer id) throws EventDetailsNotFoundException {
        try {
            int availableSeats = getNumberAvailableSeatsById(id) + 1;

            String sqlUpdate = "UPDATE EVENT_DETAILS SET NUMBER_AVAILABLE_SEATS = ? WHERE EVENT_DETAILS_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlUpdate);

            statement.setInt(1, availableSeats);
            statement.setInt(2, id);

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }


    public ArrayList<EventDetails> getAllEventDetails() {
        ArrayList<EventDetails> eventDetailsList = new ArrayList<>();

        try {
            String sqlSelect = "SELECT EVENT_DETAILS_ID, EVENT_ID, LOCATION_ID, NUMBER_AVAILABLE_SEATS, DATE, CATEGORY FROM EVENT_DETAILS";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Integer event_details_id = result.getInt("EVENT_DETAILS_ID");
                Integer event_id = result.getInt("EVENT_ID");
                Integer location_id = result.getInt("LOCATION_ID");
                Integer number_seats = result.getInt("NUMBER_AVAILABLE_SEATS");
                Date date = result.getDate("DATE");
                String category = result.getString("CATEGORY");

                EventDetails eventDetails = new EventDetails(event_id, location_id, date, category);
                eventDetails.setId(event_details_id);
                eventDetails.setNumberAvailableSeats(number_seats);
                eventDetailsList.add(eventDetails);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }

        return eventDetailsList;
    }
}
