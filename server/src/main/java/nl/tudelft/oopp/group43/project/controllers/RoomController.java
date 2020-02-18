package nl.tudelft.oopp.group43.project.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.tudelft.oopp.group43.project.models.Room;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoomController {
    /**
     * This is the summary for checkstyle.
     *
     * @param buildingName describes the name of the building that we want to take data from
     * @param roomName     same but for room name
     * @return some data
     * @throws SQLException just an exception
     */
    @GetMapping("/roomInfo")
    public String getRoom(@RequestParam(value = "buildingName", defaultValue = "10") String buildingName, @RequestParam(value = "roomName", defaultValue = "10") String roomName) throws SQLException {
        try {
            return new Room(buildingName, roomName).getPlainTextData();
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return "ERROR OCCURED";
    }

}
