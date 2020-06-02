package service;

import exceptions.EventNotFoundException;
import model.Event;
import repository.EventRepository;

public class EventService {
    private static final EventService instance = new EventService();
    EventRepository eventRepository = EventRepository.getInstance();
    AuditService auditService = new AuditService();

    private EventService() {}

    public static EventService getInstance() {
        return instance;
    }

    public void addEvent(Event event) {
        eventRepository.addEvent(event);
        auditService.timeStamp("An event was added to database.", Thread.currentThread().getName());
    }

    public Event getEventById(Integer Id) throws EventNotFoundException {
        Event event = eventRepository.getEventById(Id);
        if(event == null)
            throw new EventNotFoundException("This event id does not exist!");

        auditService.timeStamp("An event was selected from database.", Thread.currentThread().getName());
        return event;
    }

    public void updateName(Integer Id, String name) throws EventNotFoundException {
        Event event = eventRepository.getEventById(Id);
        if(event == null)
            throw new EventNotFoundException("This event id does not exist!");
        else {
            eventRepository.updateName(Id, name);
            auditService.timeStamp("The event name was updated.", Thread.currentThread().getName());
        }
    }

    public void removeEvent (Integer Id) throws EventNotFoundException {
        Event event = eventRepository.getEventById(Id);
        if(event == null)
            throw new EventNotFoundException("This event id does not exist!");
        else{
            eventRepository.removeEventById(Id);
            auditService.timeStamp("An event was removed from database.", Thread.currentThread().getName());
        }
    }
}
