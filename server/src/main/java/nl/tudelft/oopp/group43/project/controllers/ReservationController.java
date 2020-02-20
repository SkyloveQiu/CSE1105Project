package nl.tudelft.oopp.group43.project.controllers;

import com.sun.xml.bind.v2.TODO;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository repository;

    @GetMapping("/reservation")
    @ResponseBody
    public List<Reservation> getBuilding(){
        return repository.findAll();
    }

    //TODO Make it secure.

    @PutMapping("/reservation")
    @ResponseBody
    public String CreateBuilding(@RequestBody Reservation newReservation){
        repository.save(newReservation);
        return "NEW BUILDING: " + newReservation.getReservationId();
    }

}
