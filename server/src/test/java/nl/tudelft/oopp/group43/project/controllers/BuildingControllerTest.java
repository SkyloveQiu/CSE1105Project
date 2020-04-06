package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.ExceptionDates;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.ExceptionDatesRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BuildingControllerTest {

    @Mock
    private BuildingRepository mockRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ExceptionDatesRepository mockExceptionDatesRepository;

    @InjectMocks
    private BuildingController buildingControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetBuilding() {

        final List<Building> buildings = Arrays.asList(new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()));
        when(mockRepository.findAll()).thenReturn(buildings);

        final List<Building> result = buildingControllerUnderTest.getBuilding();

        assertNotNull(result);

    }

    @Test
    public void testCreateBuilding() {

        final Building newBuilding = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());


        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.existsBuildingByBuildingNumber(0)).thenReturn(false);


        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockRepository.save(any(Building.class))).thenReturn(building);

        final ResponseEntity result = buildingControllerUnderTest.createBuilding(newBuilding, "token");

        assertNotNull(result);
    }

    @Test
    public void testUpdateBuilding() {

        final Building newBuilding = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());


        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);


        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockRepository.save(any(Building.class))).thenReturn(building);


        final ResponseEntity result = buildingControllerUnderTest.updateBuilding(newBuilding, "token");

        System.out.println(result.getStatusCode());


    }

    @Test
    public void testRemoveBuildingInvalid() {

        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        final ResponseEntity result = buildingControllerUnderTest.removeBuilding(0, "token");

        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testAddExceptionDate() {
        final ExceptionDates exceptionDates = new ExceptionDates(0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0);

        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        final ExceptionDates exceptionDates1 = new ExceptionDates(0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0);
        when(mockExceptionDatesRepository.save(any(ExceptionDates.class))).thenReturn(exceptionDates1);

        final ResponseEntity result = buildingControllerUnderTest.addExceptionDate(exceptionDates, "token");

        assertNotNull(result);
    }

    @Test
    public void testGetExceptionDate() {
        final List<ExceptionDates> exceptionDates = Arrays.asList(new ExceptionDates(0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0));
        when(mockExceptionDatesRepository.findAllByBuildingNumber(0)).thenReturn(exceptionDates);

        final List<ExceptionDates> result = buildingControllerUnderTest.getExceptionDate(0);

        assertNotNull(result);


    }

    @Test
    public void testDeleteExceptionDate() {
        final ExceptionDates exceptionDates = new ExceptionDates(0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0);

        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        final ResponseEntity result = buildingControllerUnderTest.deleteExceptionDate(exceptionDates, "token");

        assertNotNull(result);
    }


}
