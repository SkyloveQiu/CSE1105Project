package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class ReservationControllerTest {

    @Mock
    private ReservationRepository mockRepository;
    @Mock
    private RoomRepository mockRoomRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private ReservationController reservationControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetReservation() {

        when(mockRepository.findAll()).thenReturn(Arrays.asList(new Reservation(0)));


        final List<Reservation> result = reservationControllerUnderTest.getReservation();

        assertNotNull(result);
    }

    @Test
    public void testGetReservationsByUser() {
        when(mockRepository.findByUser(any(User.class))).thenReturn(Arrays.asList(new Reservation(0)));

        final List<Reservation> result = reservationControllerUnderTest.getReservationsByUser("email");

        assertNotNull(result);
    }

    @Test
    public void testGetReservationsByUser1() {

        when(mockRepository.findByStartingDateGreaterThanEqualAndEndDateLessThanEqualAndRoomId(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0)).thenReturn(Arrays.asList(new Reservation(0)));


        final List<Reservation> result = reservationControllerUnderTest.getReservationsByUser(0, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        assertNotNull(result);
    }

    @Test
    public void testCreateBuildingReservation() throws Exception {

        final Reservation newReservation = new Reservation(0);

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.existsReservationByStartingDateAndAndEndDateAndRoomId(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0)).thenReturn(false);
        when(mockRoomRepository.existsRoomById(0)).thenReturn(false);
        when(mockUserRepository.existsUserByUsername("username")).thenReturn(false);
        when(mockRepository.save(any(Reservation.class))).thenReturn(new Reservation(0));


        final ResponseEntity result = reservationControllerUnderTest.createBuildingReservation(newReservation, "token", "name");

        assertNotNull(result);
    }

    //    @Test(expected = Exception.class)
    //    public void testCreateBuildingReservation_ThrowsException() throws Exception {
    //
    //            Assertions.assertThrows(Exception.class, () -> {
    //            final Reservation newReservation = new Reservation(0);
    //
    //
    //            final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    //            when(mockUserRepository.findUserByToken("token")).thenReturn(user);
    //
    //            when(mockRepository.existsReservationByStartingDateAndAndEndDateAndRoomId(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0)).thenReturn(false);
    //            when(mockRoomRepository.existsRoomById(0)).thenReturn(false);
    //            when(mockUserRepository.existsUserByUsername("username")).thenReturn(false);
    //            when(mockRepository.save(any(Reservation.class))).thenReturn(new Reservation(0));
    //
    //            reservationControllerUnderTest.createBuildingReservation(newReservation, "token");
    //        });
    //    }
}
