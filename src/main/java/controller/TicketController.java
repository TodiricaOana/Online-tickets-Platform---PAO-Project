package controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.EventDetailsNotFoundException;
import exceptions.EventNotFoundException;
import exceptions.LocationNotFoundException;
import model.EventDetails;
import model.Ticket;
import service.*;
import threads.AddTicketThread;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@WebServlet("/tickets")
public class TicketController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");

        TicketService ticketService = TicketService.getInstance();
        ArrayList<Ticket> ticketArrayList = ticketService.getTicketsByEmail(email);
        ArrayList<String> jsonArray = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer(new DefaultPrettyPrinter());

        for (Ticket ticket : ticketArrayList) {
            Map<String, Object> json = new HashMap<>();
            EventDetails eventDetails = EventDetailsService.getInstance().getEventDetailsById(ticket.getEventDetailsId());

            json.put("ticketID", ticket.getId());
            try {
               json.put("eventName", EventService.getInstance().getEventById(eventDetails.getEventId()).getName());
               json.put("oldPrice", EventService.getInstance().getEventById(eventDetails.getEventId()).getPrice());
               json.put("newPrice", ClientService.getInstance().getClientByEmail(email).getPrice(EventService.getInstance().getEventById(eventDetails.getEventId())));
               json.put("location", LocationService.getInstance().getLocationById(eventDetails.getLocationId()).getName());

            } catch (LocationNotFoundException | EventNotFoundException e) {
                e.printStackTrace();
            }
            String Date = eventDetails.getDate().toString();

            json.put("date", Date);
            json.put("numberAvailableSeats", eventDetails.getNumberAvailableSeats());
            json.put("category", eventDetails.getCategory());
            json.put("type", ClientService.getInstance().getClientByEmail(email).getType());

            jsonArray.add(ow.writeValueAsString(json));
        }

        resp.getWriter().write(String.valueOf(jsonArray));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer ticketId = Integer.parseInt(req.getParameter("ticketId"));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;

        try {
            TicketService.getInstance().removeTicketById(ticketId);
            json = ow.withRootName("success").writeValueAsString("success");
        } catch (EventDetailsNotFoundException e) {
            json = ow.withRootName("failure").writeValueAsString("Ticket couldn't be removed!");
            e.printStackTrace();
        }

        resp.getWriter().write(String.valueOf(json));
    }
}
