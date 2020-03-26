package nl.tudelft.oopp.group43.project.keys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true)
public class OrderDetailsKeyTest extends OrderDetailsKeyTestScaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        orderDetailsKey0.hashCode();
        assertEquals(1867, orderDetailsKey0.getProductId());
        assertEquals((-753), orderDetailsKey0.getOrderId());
    }

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        orderDetailsKey0.productId = 0;
        assertEquals((-753), orderDetailsKey0.getOrderId());

        orderDetailsKey0.orderId = 0;
        OrderDetailsKey orderDetailsKey1 = new OrderDetailsKey();
        orderDetailsKey1.productId = 2;
        boolean boolean0 = orderDetailsKey1.equals(orderDetailsKey0);
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        assertEquals((-753), orderDetailsKey0.getOrderId());

        orderDetailsKey0.orderId = 0;
        OrderDetailsKey orderDetailsKey1 = new OrderDetailsKey();
        orderDetailsKey1.productId = 2;
        boolean boolean0 = orderDetailsKey1.equals(orderDetailsKey0);
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        OrderDetailsKey orderDetailsKey1 = new OrderDetailsKey();
        orderDetailsKey1.orderId = (-2218);
        boolean boolean0 = orderDetailsKey0.equals(orderDetailsKey1);
        assertEquals((-753), orderDetailsKey0.getOrderId());
        assertEquals(1867, orderDetailsKey0.getProductId());
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-1126), (-1126));
        assertEquals((-1126), orderDetailsKey0.getProductId());

        orderDetailsKey0.productId = 0;
        int int0 = orderDetailsKey0.getProductId();
        assertEquals(0, int0);
    }

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey(2698, (-723));
        int int0 = orderDetailsKey0.getProductId();
        assertEquals(2698, orderDetailsKey0.getOrderId());
        assertEquals((-723), int0);
    }

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey(0, 0);
        int int0 = orderDetailsKey0.getOrderId();
        assertEquals(0, int0);
        assertEquals(0, orderDetailsKey0.getProductId());
    }

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey(2698, (-723));
        int int0 = orderDetailsKey0.getOrderId();
        assertEquals((-723), orderDetailsKey0.getProductId());
        assertEquals(2698, int0);
    }

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey(0, 0);
        OrderDetailsKey orderDetailsKey1 = new OrderDetailsKey();
        boolean boolean0 = orderDetailsKey0.equals(orderDetailsKey1);
        assertTrue(boolean0);
    }

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        OrderDetailsKey orderDetailsKey1 = new OrderDetailsKey();
        boolean boolean0 = orderDetailsKey0.equals(orderDetailsKey1);
        assertEquals((-753), orderDetailsKey0.getOrderId());
        assertEquals(1867, orderDetailsKey0.getProductId());
        assertFalse(boolean0);
    }

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-1126), (-1126));
        boolean boolean0 = orderDetailsKey0.equals(orderDetailsKey0);
        assertTrue(boolean0);
        assertEquals((-1126), orderDetailsKey0.getProductId());
        assertEquals((-1126), orderDetailsKey0.getOrderId());
    }

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        boolean boolean0 = orderDetailsKey0.equals("");
        assertFalse(boolean0);
        assertEquals((-753), orderDetailsKey0.getOrderId());
        assertEquals(1867, orderDetailsKey0.getProductId());
    }

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-753), 1867);
        int int0 = orderDetailsKey0.getProductId();
        assertEquals((-753), orderDetailsKey0.getOrderId());
        assertEquals(1867, int0);
    }

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        OrderDetailsKey orderDetailsKey0 = new OrderDetailsKey((-1126), (-1126));
        int int0 = orderDetailsKey0.getOrderId();
        assertEquals((-1126), orderDetailsKey0.getProductId());
        assertEquals((-1126), int0);
    }
}
