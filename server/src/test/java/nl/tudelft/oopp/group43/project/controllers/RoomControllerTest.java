package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.xml.transform.Result;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RoomControllerTest {

    @Mock
    private RoomRepository mockRoomRepository;
    @Mock
    private BuildingRepository mockBuildingRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private RoomController roomControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetRoom() {
        when(mockRoomRepository.findAll()).thenReturn(Arrays.asList(new Room("roomName")));

        final List<Room> result = roomControllerUnderTest.getRoom();

    }

    @Test
    public void testCreateRoom() {
        final Room newRoom = new Room("roomName");


        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRoomRepository.save(any(Room.class))).thenReturn(new Room("roomName"));


        final ResponseEntity result = roomControllerUnderTest.createRoom(newRoom, "token");

    }

    @Test
    public void testGetRoomByBuildingNumber() {

        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockBuildingRepository.findBuildingByBuildingNumber(0)).thenReturn(building);

        final List<Room> result = roomControllerUnderTest.getRoomByBuildingNumber(0);

    }


    @Test
    public void testRemoveRoomWrongToken() {
        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        final ResponseEntity result = roomControllerUnderTest.removeRoom(0, "wrong");

        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}
