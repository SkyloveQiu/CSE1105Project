package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
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
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetailsId;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class FoodOrderDetailsControllerTest {

    @Mock
    private FoodOrderDetailsRepository mockRepository;

    @InjectMocks
    private FoodOrderDetailsController foodOrderDetailsControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetFoodOrderDetails() {

        final List<FoodOrderDetails> foodOrderDetails = Arrays.asList(new FoodOrderDetails(new FoodOrderDetailsId(0, 0), new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>()), new FoodOrder(0, 0, "user", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()), 0));
        when(mockRepository.findAll()).thenReturn(foodOrderDetails);

        final List<FoodOrderDetails> result = foodOrderDetailsControllerUnderTest.getFoodOrderDetails();

    }

    @Test
    public void testCreateFoodOrderDetails() {
        final FoodOrderDetails newFoodOrderDetails = new FoodOrderDetails(new FoodOrderDetailsId(0, 0), new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>()), new FoodOrder(0, 0, "admin@tudelft.nl", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()), 0);

        final FoodOrderDetails foodOrderDetails = new FoodOrderDetails(new FoodOrderDetailsId(0, 0), new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>()), new FoodOrder(0, 0, "admin@tudelft.nl", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()), 0);
        when(mockRepository.save(any(FoodOrderDetails.class))).thenReturn(foodOrderDetails);

        final String result = foodOrderDetailsControllerUnderTest.createFoodOrderDetails(newFoodOrderDetails);

        assertNotNull(result);
    }
}
