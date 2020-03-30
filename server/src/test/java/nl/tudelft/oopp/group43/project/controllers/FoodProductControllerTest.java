package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class FoodProductControllerTest {

    @Mock
    private FoodProductRepository mockRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private FoodProductController foodProductControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetFoodProduct() {
        final List<FoodProduct> foodProducts = Arrays.asList(new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>()));
        when(mockRepository.findAll()).thenReturn(foodProducts);

        final List<FoodProduct> result = foodProductControllerUnderTest.getFoodProduct();

    }

    @Test
    public void testCreateFoodProduct() {
        final FoodProduct newFoodProduct = new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>());

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        final FoodProduct foodProduct = new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>());
        when(mockRepository.save(any(FoodProduct.class))).thenReturn(foodProduct);

        final ResponseEntity result = foodProductControllerUnderTest.createFoodProduct(newFoodProduct, "token");

        assertNotNull(result);
    }
}
