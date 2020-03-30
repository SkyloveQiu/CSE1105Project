package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.FoodProduct;
import nl.tudelft.oopp.group43.project.repositories.FoodProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class FoodProductControllerTest {

    @Mock
    private FoodProductRepository mockRepository;

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
        final FoodProduct newFoodProduct = new FoodProduct("admin@tudelft.nl", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>());

        final FoodProduct foodProduct = new FoodProduct("name", "description", new BigDecimal("0.00"), new HashSet<>(), new HashSet<>());
        when(mockRepository.save(any(FoodProduct.class))).thenReturn(foodProduct);

        final String result = foodProductControllerUnderTest.createFoodProduct(newFoodProduct);

        assertNotNull(result);
    }
}
