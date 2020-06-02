package service;

import exceptions.LocationNotFoundException;
import model.Location;
import repository.LocationRepository;

public class LocationService {
    private static final LocationService instance = new LocationService();
    LocationRepository locationRepository = LocationRepository.getInstance();
    AuditService auditService = new AuditService();

    private LocationService() {}

    public static LocationService getInstance() {
        return instance;
    }

    public void addLocation(Location location) {
        locationRepository.addLocation(location);
        auditService.timeStamp("A location was added to database.", Thread.currentThread().getName());
    }

    public Location getLocationById(Integer Id) throws LocationNotFoundException {
        Location location = locationRepository.getLocationById(Id);
        if(location == null)
            throw new LocationNotFoundException("This location id does not exist!");

        auditService.timeStamp("A location was selected from database.", Thread.currentThread().getName());
        return location;
    }

    public Integer getNumberOfSeatsById(Integer id) throws LocationNotFoundException {
        auditService.timeStamp("The number of seats for a specific location was selected from database.", Thread.currentThread().getName());
        return locationRepository.getNumberOfSeatsById(id);
    }
}
