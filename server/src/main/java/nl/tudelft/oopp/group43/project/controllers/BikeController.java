package nl.tudelft.oopp.group43.project.controllers;

import java.util.Date;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.BikeReservation;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.payload.BikeReservationResponse;
import nl.tudelft.oopp.group43.project.payload.ErrorResponse;
import nl.tudelft.oopp.group43.project.payload.SuccessResponse;
import nl.tudelft.oopp.group43.project.repositories.BikeRepository;
import nl.tudelft.oopp.group43.project.repositories.BikeReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BikeController {

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private BikeReservationRepository reservationRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bike/{buildingId}")
    @ResponseBody
    public List<Bike> getBike(@PathVariable int buildingId) {
        Building building = buildingRepository.findBuildingByBuildingNumber(buildingId);
        return bikeRepository.findBikesByBuilding(building);
    }

    //e.g. {"bikeId":2,"building":{"building_number":0},"bikesAvailable":5}

    /**
     * Creates a new bike entity.
     *
     * @param buildingNumber describes a new building
     * @return a message regarding the bike
     */
    @PostMapping(value = "/bike")
    @ResponseBody
    public ResponseEntity createBike(@RequestParam("Building") final int buildingNumber) {
        Building building = buildingRepository.findBuildingByBuildingNumber(buildingNumber);
        if (building == null) {
            ErrorResponse errorResponse = new ErrorResponse("creating fail", "no such building", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        Bike bike = new Bike(building, true);
        bikeRepository.save(bike);
        SuccessResponse response = new SuccessResponse("create success", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/bikeReservation/create")
    @ResponseBody
    public ResponseEntity createReservation(@RequestParam("BikeId") int bikeId, @RequestParam("token") String token) {

        Bike foundBike = bikeRepository.findBikeBybikeId(bikeId);
        if (foundBike.getBikesAvailable() == false) {
            ErrorResponse errorResponse = new ErrorResponse("creating fail", "bike has occupied or is not available now.", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }

        foundBike.setBikesAvailable(false);
        bikeRepository.save(foundBike);
        User user = userRepository.findByToken(token);
        BikeReservation bikeReservation = new BikeReservation(foundBike.getBuilding(), new Date(), user, foundBike);
        BikeReservationResponse bikeReservationResponse = new BikeReservationResponse(foundBike, bikeReservation, "rent bike success", HttpStatus.OK.value());
        reservationRepository.save(bikeReservation);
        return new ResponseEntity<>(bikeReservationResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/bikeReservation/user")
    @ResponseBody
    public List<BikeReservation> findUserReservations(@RequestParam("token") String token) {
        User user = userRepository.findByToken(token);
        return reservationRepository.findByUser(user);
    }

    @PostMapping(value = "/bikeReservation/return")
    @ResponseBody
    public ResponseEntity closeReservation(@RequestParam("reservationId") int reservationId, @RequestParam("token") String token, @RequestParam("building") int buildingNumber) {
        BikeReservation bikeReservation = reservationRepository.findBybikeReservationId(reservationId);
        if (bikeReservation == null) {
            ErrorResponse errorResponse = new ErrorResponse("close fail", "No such reservation is found", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        if (bikeReservation.getUser().getToken().equals(token) == false) {
            ErrorResponse errorResponse = new ErrorResponse("close fail", "You don't have permission", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        Building building = buildingRepository.findBuildingByBuildingNumber(buildingNumber);
        if (building == null) {
            ErrorResponse errorResponse = new ErrorResponse("creating fail", "no such building", HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
        Bike bike = bikeReservation.getBike();
        bike.setBuilding(building);
        bike.setBikesAvailable(true);
        bikeRepository.save(bike);
        bikeReservation.setBuildingByBuildingEnd(building);
        bikeReservation.setDatetimeEnd(new Date());
        reservationRepository.save(bikeReservation);
        BikeReservationResponse bikeReservationResponse = new BikeReservationResponse(bike, bikeReservation, "return bike success", HttpStatus.OK.value());
        return new ResponseEntity<>(bikeReservationResponse, HttpStatus.OK);
    }
}
