package nl.tudelft.oopp.group43.project.models;

import static org.mockito.Mockito.mock;

import java.util.Date;
import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

@EvoSuiteClassExclude
public class ReservationTestScaffolding {

    private static final java.util.Properties defaultProperties = (java.util.Properties) System.getProperties().clone();
    @org.junit.Rule
    public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();
    private org.evosuite.runtime.thread.ThreadStopper threadStopper = new org.evosuite.runtime.thread.ThreadStopper(org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

    /**
     * init test.
     */
    @BeforeClass
    public static void initEvoSuiteFramework() {
        org.evosuite.runtime.RuntimeSettings.className = "nl.tudelft.oopp.group43.project.models.Reservation";
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
     * init test.
     */
    public static void setSystemProperties() {

        System.setProperties((java.util.Properties) defaultProperties.clone());

    }

    private static void initializeClasses() {
        org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(ReservationTestScaffolding.class.getClassLoader(),
                "com.fasterxml.jackson.annotation.JsonProperty",
                "nl.tudelft.oopp.group43.project.models.Role",
                "nl.tudelft.oopp.group43.project.models.BikeReservation",
                "nl.tudelft.oopp.group43.project.models.Reservation",
                "nl.tudelft.oopp.group43.project.models.FoodOrder",
                "com.fasterxml.jackson.annotation.JsonCreator$Mode",
                "com.fasterxml.jackson.annotation.JsonCreator",
                "com.fasterxml.jackson.annotation.JsonIgnore",
                "nl.tudelft.oopp.group43.project.models.User",
                "com.fasterxml.jackson.annotation.JsonProperty$Access",
                "com.fasterxml.jackson.annotation.JacksonAnnotation"
        );
    }

    private static void initMocksToAvoidTimeoutsInTheTests() throws ClassNotFoundException {
        mock(Date.class);
        mock(User.class);
    }

    private static void resetClasses() {
        org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(ReservationTestScaffolding.class.getClassLoader());

        org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
                "nl.tudelft.oopp.group43.project.models.Reservation",
                "com.fasterxml.jackson.annotation.JsonCreator$Mode",
                "nl.tudelft.oopp.group43.project.models.User",
                "com.fasterxml.jackson.annotation.JsonProperty$Access"
        );
    }

    /**
     * init test.
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
     * init test.
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
