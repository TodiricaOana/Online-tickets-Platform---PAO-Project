package repository;

import model.EventDetails;
import model.Ticket;

import java.sql.*;
import java.util.ArrayList;

public class TicketRepository {
    private static final TicketRepository ticketRepositoryInstance = new TicketRepository();
    String url = "jdbc:mysql://localhost/bilete";
    String username = "root";
    String password = "";

    private TicketRepository() { }

    public static TicketRepository getInstance() {
        return ticketRepositoryInstance;
    }

    public void addTicket(Ticket ticket) {
        try{
            String sqlInsert = "INSERT INTO TICKETS (CLIENT_EMAIL, EVENT_DETAILS_ID) VALUES (?, ?)";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlInsert);

            statement.setString(1, ticket.getClientEmail());
            statement.setInt(2, ticket.getEventDetailsId());

            statement.executeUpdate();

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
    }

    public Ticket getTicketById(Integer id) {
        Ticket ticket = null;

        try {
            String sqlSelect = "SELECT CLIENT_EMAIL, EVENT_DETAILS_ID FROM TICKETS WHERE TICKET_ID = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                String clientEmail = result.getString("CLIENT_EMAIL");
                Integer eventDetailsId = result.getInt("EVENT_DETAILS_ID");

                ticket = new Ticket(clientEmail, eventDetailsId);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
        return ticket;
    }

    public void removeTicketById(Integer id) {
        try {
            String sqlDelete = "DELETE FROM TICKETS WHERE TICKET_ID = ?";

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


    public ArrayList<Ticket> getAllTicketsByEmail(String email) {
        ArrayList<Ticket> ticketArrayList = new ArrayList<>();

        try {
            String sqlSelect = "SELECT TICKET_ID, EVENT_DETAILS_ID FROM TICKETS WHERE CLIENT_EMAIL = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = dbConnection.prepareStatement(sqlSelect);

            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                Integer ticket_id = result.getInt("TICKET_ID");
                Integer event_details_id = result.getInt("EVENT_DETAILS_ID");

                Ticket ticket = new Ticket(email, event_details_id);
                ticket.setId(ticket_id);
                ticketArrayList.add(ticket);
            }

            dbConnection.close();
            statement.close();

        } catch (SQLException | ClassNotFoundException e)   {
            e.printStackTrace();
        }
        return ticketArrayList;
    }
}
