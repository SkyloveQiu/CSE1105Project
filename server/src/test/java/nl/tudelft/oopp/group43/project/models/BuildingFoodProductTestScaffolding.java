package nl.tudelft.oopp.group43.project.models;

import static org.mockito.Mockito.mock;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

@EvoSuiteClassExclude
public class BuildingFoodProductTestScaffolding {

    private static final java.util.Properties defaultProperties = (java.util.Properties) System.getProperties().clone();
    @org.junit.Rule
    public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();
    private org.evosuite.runtime.thread.ThreadStopper threadStopper = new org.evosuite.runtime.thread.ThreadStopper(org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

    /**
     * init the test.
     */
    @BeforeClass
    public static void initEvoSuiteFramework() {
        org.evosuite.runtime.RuntimeSettings.className = "nl.tudelft.oopp.group43.project.models.BuildingFoodProduct";
        org.evosuite.runtime.GuiSupport.initialize();
        org.evosuite.runtime.RuntimeSettings.maxNumberOfThreads = 100;
        org.evosuite.runtime.RuntimeSettings.maxNumberOfIterationsPerLoop = 10000;
        org.evosuite.runtime.RuntimeSettings.mockSystemIn = true;
        org.evosuite.runtime.RuntimeSettings.sandboxMode = SandboxMode.RECOMMENDED;
        Sandbox.initializeSecurityManagerForSUT();
        org.evosuite.runtime.classhandling.JDKClassResetter.init();
        setSystemProperties();
        initializeClasses();
        org.evosuite.runtime.Runtime.getInstance().resetRuntime();
        try {
            initMocksToAvoidTimeoutsInTheTests();
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
    }

    @AfterClass
    public static void clearEvoSuiteFramework() {
        Sandbox.resetDefaultSecurityManager();
        System.setProperties((java.util.Properties) defaultProperties.clone());
    }

    /**
     * init the test.
     */
    public static void setSystemProperties() {

        System.setProperties((java.util.Properties) defaultProperties.clone());

    }

    private static void initializeClasses() {
        org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(BuildingFoodProductTestScaffolding.class.getClassLoader(),
                "com.fasterxml.jackson.annotation.JsonProperty",
                "com.fasterxml.jackson.annotation.JsonBackReference",
                "com.fasterxml.jackson.annotation.JsonCreator$Mode",
                "nl.tudelft.oopp.group43.project.models.FoodProduct",
                "com.fasterxml.jackson.annotation.JacksonAnnotation",
                "nl.tudelft.oopp.group43.project.models.FoodOrder",
                "nl.tudelft.oopp.group43.project.models.Building",
                "com.fasterxml.jackson.annotation.JsonCreator",
                "nl.tudelft.oopp.group43.project.models.BuildingFoodProductId",
                "nl.tudelft.oopp.group43.project.models.FoodOrderDetails",
                "com.fasterxml.jackson.annotation.JsonProperty$Access",
                "nl.tudelft.oopp.group43.project.models.BuildingFoodProduct",
                "nl.tudelft.oopp.group43.project.models.Room"
        );
    }

    private static void initMocksToAvoidTimeoutsInTheTests() throws ClassNotFoundException {
        mock(Building.class);
        mock(BuildingFoodProductId.class);
        mock(FoodProduct.class);
    }

    private static void resetClasses() {
        org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(BuildingFoodProductTestScaffolding.class.getClassLoader());

        org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
                "nl.tudelft.oopp.group43.project.models.BuildingFoodProduct",
                "nl.tudelft.oopp.group43.project.models.FoodProduct",
                "nl.tudelft.oopp.group43.project.models.BuildingFoodProductId",
                "com.fasterxml.jackson.annotation.JsonProperty$Access",
                "com.fasterxml.jackson.annotation.JsonCreator$Mode",
                "nl.tudelft.oopp.group43.project.models.Building"
        );
    }

    /**
     * init the test.
     */
    @Before
    public void initTestCase() {
        threadStopper.storeCurrentThreads();
        threadStopper.startRecordingTime();
        org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().initHandler();
        Sandbox.goingToExecuteSUTCode();
        setSystemProperties();
        org.evosuite.runtime.GuiSupport.setHeadless();
        org.evosuite.runtime.Runtime.getInstance().resetRuntime();
        org.evosuite.runtime.agent.InstrumentingAgent.activate();
    }
    
    /**
     * init the test.
     */
    @After
    public void doneWithTestCase() {
        threadStopper.killAndJoinClientThreads();
        org.evosuite.runtime.jvm.ShutdownHookHandler.getInstance().safeExecuteAddedHooks();
        org.evosuite.runtime.classhandling.JDKClassResetter.reset();
        resetClasses();
        Sandbox.doneWithExecutingSUTCode();
        org.evosuite.runtime.agent.InstrumentingAgent.deactivate();
        org.evosuite.runtime.GuiSupport.restoreHeadlessMode();
    }
}
