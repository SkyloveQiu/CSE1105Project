package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Bike;
import nl.tudelft.oopp.group43.project.models.BikeReservation;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.BikeRepository;
import nl.tudelft.oopp.group43.project.repositories.BikeReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class BikeControllerTest {

    @Mock
    private BikeRepository mockBikeRepository;
    @Mock
    private BikeReservationRepository mockReservationRepository;
    @Mock
    private BuildingRepository mockBuildingRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private BikeController bikeControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetBike() {
        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockBuildingRepository.findBuildingByBuildingNumber(0)).thenReturn(building);


        final List<Bike> bikes = Arrays.asList(new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false));
        when(mockBikeRepository.findBikesByBuilding(any(Building.class))).thenReturn(bikes);


        final List<Bike> result = bikeControllerUnderTest.getBike(0);

        assertNotNull(result);


    }

    @Test
    public void testCreateBike() {

        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);


        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockBuildingRepository.findBuildingByBuildingNumber(0)).thenReturn(building);


        final Bike bike = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
        when(mockBikeRepository.save(any(Bike.class))).thenReturn(bike);


        final ResponseEntity result = bikeControllerUnderTest.createBike(0, "token");

        assertNotNull(result);
    }

    @Test
    public void testCreateReservation() {

        final Bike bike = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
        when(mockBikeRepository.findBikeBybikeId(0)).thenReturn(bike);


        final Bike bike1 = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
        when(mockBikeRepository.save(any(Bike.class))).thenReturn(bike1);


        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockReservationRepository.save(any(BikeReservation.class))).thenReturn(new BikeReservation());


        final ResponseEntity result = bikeControllerUnderTest.createReservation(0, "token");

        assertNotNull(result);
    }

    @Test
    public void testCreateReservationWithTime() {

        final Bike bike = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
        when(mockBikeRepository.findBikeBybikeId(0)).thenReturn(bike);


        final Bike bike1 = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
        when(mockBikeRepository.save(any(Bike.class))).thenReturn(bike1);


        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockReservationRepository.save(any(BikeReservation.class))).thenReturn(new BikeReservation());


        final ResponseEntity result = bikeControllerUnderTest.createReservationWithTime(0, "token", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        assertNotNull(result);
    }

    @Test
    public void testFindUserReservations() {
        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockReservationRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(new BikeReservation()));

        final List<BikeReservation> result = bikeControllerUnderTest.findUserReservations("token");

        assertNotNull(result);
    }

    //    @Test
    //    public void testCloseReservation() {
    //
    //        when(mockReservationRepository.findBybikeReservationId(0)).thenReturn(new BikeReservation());
    //
    //
    //        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //        when(mockBuildingRepository.findBuildingByBuildingNumber(0)).thenReturn(building);
    //
    //
    //        final Bike bike = new Bike(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()), false);
    //        when(mockBikeRepository.save(any(Bike.class))).thenReturn(bike);
    //
    //        when(mockReservationRepository.save(any(BikeReservation.class))).thenReturn(new BikeReservation());
    //
    //
    //        final ResponseEntity result = bikeControllerUnderTest.closeReservation(0, "token", 0);
    //
    //        assertNotNull(result);
    //    }
}
