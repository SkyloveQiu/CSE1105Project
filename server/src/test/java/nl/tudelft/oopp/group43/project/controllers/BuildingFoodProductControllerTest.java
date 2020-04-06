package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import nl.tudelft.oopp.group43.project.models.BuildingFoodProduct;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.repositories.BuildingFoodProductRepository;
import nl.tudelft.oopp.group43.project.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

public class BuildingFoodProductControllerTest {

    @Mock
    private BuildingFoodProductRepository mockRepository;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private BuildingFoodProductController buildingFoodProductControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetBuildingFoodProduct() {
        when(mockRepository.findAll()).thenReturn(Arrays.asList(new BuildingFoodProduct()));

        final List<BuildingFoodProduct> result = buildingFoodProductControllerUnderTest.getBuildingFoodProduct();

    }

    @Test
    public void testCreateNewBuildingFoodProduct() {
        final BuildingFoodProduct newBuildingFoodProduct = new BuildingFoodProduct();

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.save(any(BuildingFoodProduct.class))).thenReturn(new BuildingFoodProduct());

        final ResponseEntity result = buildingFoodProductControllerUnderTest.createNewBuildingFoodProduct(newBuildingFoodProduct, "token");

        assertNotNull(result);
    }


    @Test
    public void testGetMoreDetailsWithNoDetails() {
        final BuildingFoodProduct newBuildingFoodProduct = new BuildingFoodProduct();

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.save(any(BuildingFoodProduct.class))).thenReturn(new BuildingFoodProduct());

        final List<BuildingFoodProduct> result = buildingFoodProductControllerUnderTest.getBuildingFoodProductBasedOnBuilding(0);

        assertEquals(0,result.size());
    }

    @Test
    public void testGetMoreDetailsWithDetails() {
        final BuildingFoodProduct newBuildingFoodProduct = new BuildingFoodProduct();

        final User user = new User("email", "firstName", "lastName", "password", "role", "token", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        when(mockUserRepository.findUserByToken("token")).thenReturn(user);

        when(mockRepository.save(any(BuildingFoodProduct.class))).thenReturn(new BuildingFoodProduct());

        final List<BuildingFoodProduct> result = buildingFoodProductControllerUnderTest.getBuildingFoodProductBasedOnBuilding(0);

        assertEquals(0,result.size());
    }
}
