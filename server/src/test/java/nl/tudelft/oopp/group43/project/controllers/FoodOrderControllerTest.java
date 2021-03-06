package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetailsId;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderRepository;
import nl.tudelft.oopp.group43.project.repositories.ReservationRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class FoodOrderControllerTest {

    @Mock
    private FoodOrderRepository mockFoodOrderrepository;
    @Mock
    private ReservationRepository mockReservationRepository;
    @Mock
    private FoodOrderDetailsRepository mockFoodOrderDetailsRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private BuildingFoodProductRepository mockBuildingFoodProductRepository;
    @Mock
    private BuildingRepository mockRepository;

    @InjectMocks
    private FoodOrderController foodOrderControllerUnderTest;

    @InjectMocks
    private BuildingController buildingControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetFoodOrder() {

        final List<FoodOrder> foodOrders = Arrays.asList(new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        when(mockFoodOrderrepository.findAll()).thenReturn(foodOrders);

        final List<FoodOrder> result = foodOrderControllerUnderTest.getFoodOrder();

        assertNotNull(result);
    }

    @Test
    public void testCreateFoodOrder() {

        final FoodOrder newFoodOrder = new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockReservationRepository.findByReservationId(0)).thenReturn(new Reservation(0));
        when(mockBuildingFoodProductRepository.existsBuildingFoodProductByBuildingAndAndFoodProduct(any(Building.class), any(FoodProduct.class))).thenReturn(false);

        // Configure FoodOrderRepository.save(...).
        final FoodOrder foodOrder = new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        when(mockFoodOrderrepository.save(any(FoodOrder.class))).thenReturn(foodOrder);

        // Configure FoodOrderDetailsRepository.save(...).
        final FoodOrderDetails foodOrderDetails = new FoodOrderDetails(new FoodOrderDetailsId(0, 0), new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>()), new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()), 0);
        when(mockFoodOrderDetailsRepository.save(any(FoodOrderDetails.class))).thenReturn(foodOrderDetails);

        // Run the test
        final ResponseEntity result = foodOrderControllerUnderTest.createFoodOrder(newFoodOrder, "token", "order", "true");

        assertNotNull(result);
    }

    @Test
    public void testGetMoreDetails() {
        final Building newBuilding = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());


        final User user = new User("admin@tudelft.nl", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.existsBuildingByBuildingNumber(0)).thenReturn(false);


        final Building building = new Building(0, "buildingName", "address", "openingHours", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockRepository.save(any(Building.class))).thenReturn(building);

        buildingControllerUnderTest.createBuilding(newBuilding, "token");

        final List<FoodOrder> foodOrders = Arrays.asList(new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
        when(mockFoodOrderrepository.findAll()).thenReturn(foodOrders);

        final List<FoodOrder> result = foodOrderControllerUnderTest.getFoodOrderByUser("token");

        assertNotNull(result);
    }
}
