package service;

import exceptions.EventDetailsNotFoundException;
import model.EventDetails;
import repository.EventDetailsRepository;

import java.util.ArrayList;

public class EventDetailsService {
    private static final EventDetailsService instance = new EventDetailsService();
    EventDetailsRepository eventDetailsRepository = EventDetailsRepository.getInstance();
    AuditService auditService = new AuditService();

    private EventDetailsService() {}

    public static EventDetailsService getInstance() {
        return instance;
    }

    public void addEventDetails(EventDetails eventDetails) {
        eventDetailsRepository.addEventDetails(eventDetails);
        auditService.timeStamp("The details for an event were added to database.", Thread.currentThread().getName());
    }

    public EventDetails getEventDetailsById(Integer Id){
        auditService.timeStamp("The details for an event were selected from database.", Thread.currentThread().getName());
        return eventDetailsRepository.getEventDetailsById(Id);
    }

    public Integer getNumberAvailableSeatsById(Integer Id) throws EventDetailsNotFoundException {
        auditService.timeStamp("The number of available seats to an event was selected from database.", Thread.currentThread().getName());
        return eventDetailsRepository.getNumberAvailableSeatsById(Id);
    }

    public void decreaseNumberAvailableSeatsById(Integer Id) throws EventDetailsNotFoundException {
        eventDetailsRepository.decreaseNumberById(Id);
        auditService.timeStamp("The number of available seats to an event was decreased.", Thread.currentThread().getName());

    }

    public void increaseNumberAvailableSeatsById(Integer Id) throws EventDetailsNotFoundException {
        eventDetailsRepository.increaseNumberById(Id);
        auditService.timeStamp("The number of available seats to an event was increased.", Thread.currentThread().getName());
    }

    public ArrayList<EventDetails> getAllEventDetails () {
        auditService.timeStamp("All event details were selected from database.", Thread.currentThread().getName());
        return eventDetailsRepository.getAllEventDetails();
    }
}
