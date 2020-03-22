/*
 * This file was automatically generated by EvoSuite
 * Sun Mar 22 21:49:50 GMT 2020
 */

package nl.tudelft.oopp.group43.project.controllers;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import nl.tudelft.oopp.group43.project.controllers.FoodOrderDetailsController;
import nl.tudelft.oopp.group43.project.models.FoodOrderDetails;
import nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.javaee.injection.Injector;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class FoodOrderDetailsController_ESTest extends FoodOrderDetailsController_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      FoodOrderDetailsController foodOrderDetailsController0 = new FoodOrderDetailsController();
      Integer integer0 = new Integer((-1787));
      FoodOrderDetailsRepository foodOrderDetailsRepository0 = mock(FoodOrderDetailsRepository.class, new ViolatedAssumptionAnswer());
      doReturn(integer0).when(foodOrderDetailsRepository0).save(nullable(java.lang.Object.class));
      Injector.inject(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class, "repository", (Object) foodOrderDetailsRepository0);
      Injector.validateBean(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class);
      FoodOrderDetails foodOrderDetails0 = mock(FoodOrderDetails.class, new ViolatedAssumptionAnswer());
      // Undeclared exception!
      try { 
        foodOrderDetailsController0.createFoodOrderDetails(foodOrderDetails0);
        fail("Expecting exception: ClassCastException");
      
      } catch(ClassCastException e) {
         //
         // java.lang.Integer cannot be cast to nl.tudelft.oopp.group43.project.models.FoodOrderDetails
         //
         verifyException("nl.tudelft.oopp.group43.project.repositories.FoodOrderDetailsRepository$MockitoMock$263106556", e);
      }
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      FoodOrderDetailsController foodOrderDetailsController0 = new FoodOrderDetailsController();
      FoodOrderDetailsRepository foodOrderDetailsRepository0 = mock(FoodOrderDetailsRepository.class, new ViolatedAssumptionAnswer());
      doReturn((Object) null).when(foodOrderDetailsRepository0).save(nullable(java.lang.Object.class));
      Injector.inject(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class, "repository", (Object) foodOrderDetailsRepository0);
      Injector.validateBean(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class);
      FoodOrderDetails foodOrderDetails0 = mock(FoodOrderDetails.class, new ViolatedAssumptionAnswer());
      String string0 = foodOrderDetailsController0.createFoodOrderDetails(foodOrderDetails0);
      assertEquals("ok", string0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      FoodOrderDetailsController foodOrderDetailsController0 = new FoodOrderDetailsController();
      FoodOrderDetailsRepository foodOrderDetailsRepository0 = mock(FoodOrderDetailsRepository.class, new ViolatedAssumptionAnswer());
      doReturn((List) null).when(foodOrderDetailsRepository0).findAll();
      Injector.inject(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class, "repository", (Object) foodOrderDetailsRepository0);
      Injector.validateBean(foodOrderDetailsController0, (Class<?>) FoodOrderDetailsController.class);
      List<FoodOrderDetails> list0 = foodOrderDetailsController0.getFoodOrderDetails();
      assertNull(list0);
  }
}
