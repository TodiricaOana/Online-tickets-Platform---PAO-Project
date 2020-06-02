package threads;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.EventDetailsNotFoundException;
import exceptions.NoAvailableSeatsException;
import model.Ticket;
import service.TicketService;

public class AddTicketThread extends Thread{
    Ticket ticket;
    String json;

    AddTicketThread() {

    }

    public AddTicketThread(Ticket ticket){
        this.ticket = ticket;
    }

    public String getJson() {
        return json;
    }

    @Override
    public void run() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            try {
                TicketService.getInstance().addTicket(ticket);
                this.json = ow.withRootName("success").writeValueAsString("Succesfully purchased!");
                } catch (NoAvailableSeatsException e) {
                    this.json = ow.withRootName("failure").writeValueAsString("Sorry, there are no more available seats!");
                    e.printStackTrace();
                } catch (EventDetailsNotFoundException e) {
                    this.json = ow.withRootName("failure").writeValueAsString("Ticket couldn't be purchased due to some technical issue!");
                    e.printStackTrace();
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
    }
}
