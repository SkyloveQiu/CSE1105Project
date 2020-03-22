/*
 * This file was automatically generated by EvoSuite
 * Sun Mar 22 22:57:29 GMT 2020
 */

package nl.tudelft.oopp.group43.project.models;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Date;
import java.util.Set;
import nl.tudelft.oopp.group43.project.models.FoodOrder;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class Reservation_ESTest extends Reservation_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      User user0 = mock(User.class, new ViolatedAssumptionAnswer());
      doReturn((String) null).when(user0).toString();
      Integer integer0 = new Integer((-2781));
      Date date0 = mock(Date.class, new ViolatedAssumptionAnswer());
      Reservation reservation0 = new Reservation(user0, integer0, date0, date0);
      User user1 = reservation0.getUser();
      assertNull(user1.getToken());
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Date date0 = mock(Date.class, new ViolatedAssumptionAnswer());
      doReturn((String) null).when(date0).toString();
      reservation0.setStartingDate(date0);
      Date date1 = reservation0.getStartingDate();
      assertSame(date1, date0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer(0);
      reservation0.setRoomId(integer0);
      Integer integer1 = reservation0.getRoomId();
      assertEquals(0, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer(3);
      reservation0.setRoomId(integer0);
      Integer integer1 = reservation0.getRoomId();
      assertEquals(3, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer(0);
      reservation0.setReservationId(integer0);
      Integer integer1 = reservation0.getReservationId();
      assertEquals(0, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer(1645);
      reservation0.setReservationId(integer0);
      Integer integer1 = reservation0.getReservationId();
      assertEquals(1645, (int)integer1);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      reservation0.setFoodOrders((Set) null);
      Set<FoodOrder> set0 = reservation0.getFoodOrders();
      assertNull(set0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Date date0 = mock(Date.class, new ViolatedAssumptionAnswer());
      doReturn((String) null).when(date0).toString();
      reservation0.setEndDate(date0);
      Date date1 = reservation0.getEndDate();
      assertSame(date1, date0);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer((-356));
      reservation0.setRoomId(integer0);
      Integer integer1 = reservation0.getRoomId();
      assertEquals((-356), (int)integer1);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = reservation0.getRoomId();
      assertNull(integer0);
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Date date0 = reservation0.getStartingDate();
      assertNull(date0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = reservation0.getReservationId();
      assertNull(integer0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      User user0 = mock(User.class, new ViolatedAssumptionAnswer());
      Integer integer0 = new Integer(0);
      Date date0 = mock(Date.class, new ViolatedAssumptionAnswer());
      Reservation reservation0 = new Reservation(user0, integer0, date0, date0, (Set) null);
      assertNull(reservation0.getReservationId());
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Integer integer0 = new Integer((-356));
      reservation0.setReservationId(integer0);
      Integer integer1 = reservation0.getReservationId();
      assertEquals((-356), (int)integer1);
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Set<FoodOrder> set0 = reservation0.getFoodOrders();
      reservation0.setFoodOrders(set0);
      assertEquals(0, set0.size());
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      Date date0 = reservation0.getEndDate();
      assertNull(date0);
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      User user0 = reservation0.getUser();
      assertNull(user0);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      Reservation reservation0 = new Reservation();
      reservation0.setUser((User) null);
      assertNull(reservation0.getRoomId());
  }
}
