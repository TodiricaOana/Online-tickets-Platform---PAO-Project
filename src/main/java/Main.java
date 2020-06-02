import exceptions.*;
import model.*;
import service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        EventService eventService = EventService.getInstance();

        eventService.addEvent(new Event("O scrisoare pierduta", 20.0));
        eventService.addEvent(new Event("Visul unei nopti de vara", 40.0));
        eventService.addEvent(new Event("Concert de Craciun", 70.0));
        eventService.addEvent(new Event("Concert simfonic", 80.0));
        eventService.addEvent(new Event("Concert Holograf", 34.5));
        eventService.addEvent(new Event("Concert Carla's Dreams", 30.0));
        eventService.addEvent(new Event("Concert Delia", 35.0));
        eventService.addEvent(new Event("Concert Coma", 22.0));
        eventService.addEvent(new Event("Concert Firma", 15.0));
        eventService.addEvent(new Event("Stand-up Micutzu", 15.0));
        eventService.addEvent(new Event("Stand-up Andrei Ciobanu", 15.0));

        LocationService locationService = LocationService.getInstance();

        locationService.addLocation(new Location("Teatrul National", 2800));
        locationService.addLocation(new Location("Sala Palatului", 4000));
        locationService.addLocation(new Location("Beraria H", 2000));
        locationService.addLocation(new Location("Expirat", 500));
        locationService.addLocation(new Location("Club 99", 200));

        try {
            EventDetailsService eventDetailsService = EventDetailsService.getInstance();

            eventDetailsService.addEventDetails(new EventDetails(1, 1, new SimpleDateFormat("dd.MM.yyyy").parse("12.06.2020"), "Theater"));
            eventDetailsService.addEventDetails(new EventDetails(2, 1, new SimpleDateFormat("dd.MM.yyyy").parse("25.06.2020"), "Theater"));
            eventDetailsService.addEventDetails(new EventDetails(3, 2, new SimpleDateFormat("dd.MM.yyyy").parse("23.12.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(4, 2, new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(5, 3, new SimpleDateFormat("dd.MM.yyyy").parse("04.07.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(5, 2, new SimpleDateFormat("dd.MM.yyyy").parse("10.07.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(6, 3, new SimpleDateFormat("dd.MM.yyyy").parse("20.06.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(7, 3, new SimpleDateFormat("dd.MM.yyyy").parse("09.08.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(8, 4, new SimpleDateFormat("dd.MM.yyyy").parse("21.06.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(9, 4, new SimpleDateFormat("dd.MM.yyyy").parse("28.07.2020"), "Concert"));
            eventDetailsService.addEventDetails(new EventDetails(10, 5, new SimpleDateFormat("dd.MM.yyyy").parse("05.08.2020"), "Stand-up"));
            eventDetailsService.addEventDetails(new EventDetails(11, 5, new SimpleDateFormat("dd.MM.yyyy").parse("12.08.2020"), "Stand-up"));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
