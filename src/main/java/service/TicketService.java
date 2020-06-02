package service;

import exceptions.EventDetailsNotFoundException;
import exceptions.NoAvailableSeatsException;
import model.Ticket;
import repository.TicketRepository;

import java.util.ArrayList;

public class TicketService {
    private static final TicketService instance = new TicketService();
    TicketRepository ticketRepository = TicketRepository.getInstance();
    AuditService auditService = new AuditService();

    private TicketService() {}

    public static TicketService getInstance() {
        return instance;
    }

    public void addTicket(Ticket ticket) throws EventDetailsNotFoundException, NoAvailableSeatsException {
        EventDetailsService eventDetailsService = EventDetailsService.getInstance();
        if(eventDetailsService.getNumberAvailableSeatsById(ticket.getEventDetailsId()) > 0) {
            ticketRepository.addTicket(ticket);
            auditService.timeStamp("A ticket was added to database.", Thread.currentThread().getName());
            eventDetailsService.decreaseNumberAvailableSeatsById(ticket.getEventDetailsId());
        }
        else
            throw new NoAvailableSeatsException("No available seats at this event!");
    }

    public Ticket getTicketById(Integer Id){
        auditService.timeStamp("A ticket was selected from database.", Thread.currentThread().getName());
        return ticketRepository.getTicketById(Id);
    }

    public ArrayList<Ticket> getTicketsByEmail (String email){
        auditService.timeStamp("The tickets of a client were selected from database.", Thread.currentThread().getName());
        return ticketRepository.getAllTicketsByEmail(email);
    }

    public void removeTicketById (Integer Id) throws EventDetailsNotFoundException {
        EventDetailsService eventDetailsService = EventDetailsService.getInstance();
        eventDetailsService.increaseNumberAvailableSeatsById(getTicketById(Id).getEventDetailsId());
        auditService.timeStamp("A ticket was removed from database.", Thread.currentThread().getName());
        ticketRepository.removeTicketById(Id);
    }
}
