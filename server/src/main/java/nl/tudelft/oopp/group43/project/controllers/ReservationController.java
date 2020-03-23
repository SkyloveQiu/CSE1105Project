package nl.tudelft.oopp.group43.project.controllers;

import java.util.Date;
import java.util.List;

import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.BikeReservationResponse;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.payload.ReservationResponse;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return repository.findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(startDate, endDate, room);

    }


    //  {"user":{"username":"thom@gmail.com"},"room_id":1,"starting_date":"2020-03-06T12:00:00.000+0000",
    //  "end_date":"2020-03-06T13:00:00.000+0000"}

    /**
     * create the building reservation.
     *
     * @param newReservation the request body.
     * @return the building details and result.
     * @throws Exception the exception of the create.
     */
    @PostMapping("/reservation")
    @ResponseBody
    public ResponseEntity createBuildingReservation(@RequestBody Reservation newReservation,
                                                    @RequestParam(value = "token", defaultValue = "invalid") String token) throws Exception {

        try {
            if (!userRepository.findUserByToken(token).getUsername().equals(newReservation.getUser().getUsername())) {
                ErrorResponse errorResponse = new ErrorResponse("Booking Error", "This user does not exist or the token is invalid.", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {

            ErrorResponse errorResponse = new ErrorResponse("Booking Error", "This user does not exist or the token is invalid.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }


        if (repository.existsReservationByStartingDateAndAndEndDateAndRoomId(newReservation.getStartingDate(),
            newReservation.getEndDate(),
            newReservation.getRoomId())) {
            ErrorResponse errorResponse = new ErrorResponse("Booking Error", "This room is already booked for this time slot.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        } else {
            if (newReservation.getStartingDate().compareTo(newReservation.getEndDate()) > 0) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid time slot", "Starting date is after end date", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

            double diffInMinutes = Math.abs((double) ((newReservation.getStartingDate().getTime() - newReservation.getEndDate().getTime())
                / (1000 * 60)));

            if (diffInMinutes > 60) {

                ErrorResponse errorResponse = new ErrorResponse("Invalid time slot", "SLOT IS NOT ONE HOUR", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }
            if (newReservation.getStartingDate().getMinutes() != 00 || newReservation.getStartingDate().getSeconds() != 00) {
                ErrorResponse errorResponse = new ErrorResponse("Invalid time slot", "SLOT DOES NOT START AT FIXED HOUR", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

            if (!roomRepository.existsRoomById(newReservation.getRoomId())) {
                ErrorResponse errorResponse = new ErrorResponse("Booking Error", "INVALID ROOM NUMBER", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

            if (!userRepository.existsUserByUsername(newReservation.getUser().getUsername())) {
                ErrorResponse errorResponse = new ErrorResponse("Booking Error", "This user does not exist.", HttpStatus.FORBIDDEN.value());
                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            }

            repository.save(newReservation);
            ReservationResponse roomReservation = new ReservationResponse("Room RESERVED", HttpStatus.OK.value());
            return new ResponseEntity<>(roomReservation, HttpStatus.OK);
        }
    }

}
