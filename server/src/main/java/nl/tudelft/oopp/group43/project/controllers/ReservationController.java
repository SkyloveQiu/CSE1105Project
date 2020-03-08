package nl.tudelft.oopp.group43.project.controllers;

import com.sun.xml.bind.v2.TODO;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.joda.time.Interval;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import nl.tudelft.oopp.group43.project.models.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/reservation")
    @ResponseBody
    public List<Reservation> getReservation() {
        return repository.findAll();
    }

    @GetMapping("/reservation/{email}")
    @ResponseBody
    public List<Reservation> getReservationsByUser(@PathVariable String email) {
        return repository.findByUser(new User(email));

    }

    @GetMapping("/reservation/{room}/{startDate}/{endDate}")
    @ResponseBody
    public List<Reservation> getReservationsByUser(@PathVariable int room,
                                                   @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                   @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        return repository.findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(startDate, endDate, room);

    }


    //  {"user":{"username":"thom@gmail.com"},"room_id":1,"starting_date":"2020-03-06T12:00:00.000+0000",
    //  "end_date":"2020-03-06T13:00:00.000+0000"}
    @PostMapping("/reservation")
    @ResponseBody
    public String CreateBuilding(@RequestBody Reservation newReservation) throws Exception {
        if (repository.existsReservationByStartingDateAndAndEndDateAndRoomId(newReservation.getStartingDate(), newReservation.getEndDate(),newReservation.getRoomId()))
            return "ALREADY BOOKED";
        else {
            if (newReservation.getStartingDate().compareTo(newReservation.getEndDate()) > 0)
                return "Starting date is after end date";

            double diffInMinutes = Math.abs((double) ((newReservation.getStartingDate().getTime() - newReservation.getEndDate().getTime())
                    / (1000 * 60)));

            if (diffInMinutes > 60)
                return "SLOT IS NOT ONE HOUR";

            if (newReservation.getStartingDate().getMinutes() != 00 || newReservation.getStartingDate().getSeconds() != 00)
                return "INVALID TIME SLOT";

            if(!roomRepository.existsRoomById(newReservation.getRoomId()))
                return "INVALID ROOM NUMBER";

           if(!userRepository.existsUserByUsername(newReservation.getUser().getUsername()))
               return "INVALID USERNAME";

            repository.save(newReservation);
            return "NEW RESERVATION: " + newReservation.getReservationId();
        }
    }

}
