package controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import exceptions.EventDetailsNotFoundException;
import exceptions.EventNotFoundException;
import exceptions.LocationNotFoundException;
import exceptions.NoAvailableSeatsException;
import model.EventDetails;
import model.Ticket;
import service.EventDetailsService;
import service.EventService;
import service.LocationService;
import service.TicketService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import threads.AddTicketThread;

@WebServlet("/events")
public class EventDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ArrayList<EventDetails> eventDetailsArrayList = EventDetailsService.getInstance().getAllEventDetails();
        ArrayList<String> jsonArray = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer(new DefaultPrettyPrinter());

        for (EventDetails eventDetails : eventDetailsArrayList) {
            Map<String, Object> json = new HashMap<>();

            json.put("eventDetailsID", eventDetails.getId());
            try {
                json.put("eventName", EventService.getInstance().getEventById(eventDetails.getEventId()).getName());
                json.put("price", EventService.getInstance().getEventById(eventDetails.getEventId()).getPrice());
                json.put("location", LocationService.getInstance().getLocationById(eventDetails.getLocationId()).getName());

            } catch (LocationNotFoundException | EventNotFoundException e) {
                e.printStackTrace();
            }

            String Date = eventDetails.getDate().toString();

            json.put("date", Date);
            json.put("numberAvailableSeats", eventDetails.getNumberAvailableSeats());
            json.put("category", eventDetails.getCategory());

            jsonArray.add(ow.writeValueAsString(json));
        }
        resp.getWriter().write(String.valueOf(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        Integer eventDetailsID = Integer.parseInt(req.getParameter("eventDetailsID"));

        Ticket ticket = new Ticket(email, eventDetailsID);

        String json;

        AddTicketThread addTicketThread = new AddTicketThread(ticket);
        addTicketThread.start();

        try {
            addTicketThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        json = addTicketThread.getJson();

        resp.getWriter().write(json);
    }
}