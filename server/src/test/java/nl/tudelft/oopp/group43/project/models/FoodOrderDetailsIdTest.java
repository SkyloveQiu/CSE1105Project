package nl.tudelft.oopp.group43.project.models;

import static org.junit.Assert.assertEquals;

import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true)
public class FoodOrderDetailsIdTest extends FoodOrderDetailsIdTestScaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId();
        int int0 = foodOrderDetailsId0.getFoodProductId();
        assertEquals(0, int0);
    }

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId();
        foodOrderDetailsId0.setFoodProductId(4206);
        int int0 = foodOrderDetailsId0.getFoodProductId();
        assertEquals(4206, int0);
    }

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId();
        int int0 = foodOrderDetailsId0.getFoodOrderId();
        assertEquals(0, int0);
    }

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId();
        foodOrderDetailsId0.setFoodOrderId(1271);
        int int0 = foodOrderDetailsId0.getFoodOrderId();
        assertEquals(1271, int0);
    }

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId((-344), (-344));
        foodOrderDetailsId0.setFoodProductId(1960);
        assertEquals(1960, foodOrderDetailsId0.getFoodProductId());
    }

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId((-344), (-344));
        int int0 = foodOrderDetailsId0.getFoodOrderId();
        assertEquals((-344), foodOrderDetailsId0.getFoodProductId());
        assertEquals((-344), int0);
    }

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        FoodOrderDetailsId foodOrderDetailsId0 = new FoodOrderDetailsId((-344), (-344));
        int int0 = foodOrderDetailsId0.getFoodProductId();
        assertEquals((-344), int0);
        assertEquals((-344), foodOrderDetailsId0.getFoodOrderId());
    }
}
