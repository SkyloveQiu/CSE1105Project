package nl.tudelft.oopp.group43.project.keys;

import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

@EvoSuiteClassExclude
public class OrderDetailsKeyTestScaffolding {

    @org.junit.Rule
    public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();

    private static final java.util.Properties defaultProperties = (java.util.Properties) System.getProperties().clone();

    private org.evosuite.runtime.thread.ThreadStopper threadStopper = new org.evosuite.runtime.thread.ThreadStopper(org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);

    /**
     * init the tests.
     */
    @BeforeClass
    public static void initEvoSuiteFramework() {
        org.evosuite.runtime.RuntimeSettings.className = "nl.tudelft.oopp.group43.project.keys.OrderDetailsKey";
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
    }

    @AfterClass
    public static void clearEvoSuiteFramework() {
        Sandbox.resetDefaultSecurityManager();
        System.setProperties((java.util.Properties) defaultProperties.clone());
    }

    /**
     * init the test case.
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
     * init the test case.
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

    /**
     * init the system properties.
     */
    public static void setSystemProperties() {

        System.setProperties((java.util.Properties) defaultProperties.clone());

    }

    private static void initializeClasses() {
        org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(OrderDetailsKeyTestScaffolding.class.getClassLoader(),
                "nl.tudelft.oopp.group43.project.keys.OrderDetailsKey"
        );
    }

    private static void resetClasses() {
        org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(OrderDetailsKeyTestScaffolding.class.getClassLoader());

        org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
                "nl.tudelft.oopp.group43.project.keys.OrderDetailsKey"
        );
    }
}
