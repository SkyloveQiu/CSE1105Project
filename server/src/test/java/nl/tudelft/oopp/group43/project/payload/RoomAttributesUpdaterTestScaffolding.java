package nl.tudelft.oopp.group43.project.payload;

import static org.mockito.Mockito.mock;

import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.evosuite.runtime.annotation.EvoSuiteClassExclude;
import org.evosuite.runtime.sandbox.Sandbox;
import org.evosuite.runtime.sandbox.Sandbox.SandboxMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@EvoSuiteClassExclude
public class RoomAttributesUpdaterTestScaffolding {

    private static final java.util.Properties defaultProperties = (java.util.Properties) System.getProperties().clone();
    @org.junit.Rule
    public org.evosuite.runtime.vnet.NonFunctionalRequirementRule nfr = new org.evosuite.runtime.vnet.NonFunctionalRequirementRule();
    private org.evosuite.runtime.thread.ThreadStopper threadStopper = new org.evosuite.runtime.thread.ThreadStopper(org.evosuite.runtime.thread.KillSwitchHandler.getInstance(), 3000);


    /**
     * init the test.
     */
    @BeforeClass
    public static void initEvoSuiteFramework() {
        org.evosuite.runtime.RuntimeSettings.className = "nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater";
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
        org.evosuite.runtime.classhandling.ClassStateSupport.initializeClasses(RoomAttributesUpdaterTestScaffolding.class.getClassLoader(),
                "org.springframework.beans.factory.HierarchicalBeanFactory",
                "org.springframework.boot.context.event.ApplicationReadyEvent",
                "org.springframework.beans.factory.annotation.Autowired",
                "org.springframework.data.repository.CrudRepository",
                "org.springframework.boot.SpringApplication",
                "org.json.simple.parser.ParseException",
                "org.springframework.data.repository.Repository",
                "org.springframework.core.env.EnvironmentCapable",
                "org.springframework.context.ConfigurableApplicationContext",
                "org.springframework.context.ApplicationEventPublisher",
                "org.springframework.data.domain.Sort",
                "org.springframework.data.domain.Pageable",
                "org.springframework.stereotype.Indexed",
                "nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater",
                "org.springframework.stereotype.Repository",
                "nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater$BucketsList",
                "org.springframework.context.MessageSource",
                "org.springframework.core.io.ResourceLoader",
                "org.springframework.data.repository.PagingAndSortingRepository",
                "nl.tudelft.oopp.group43.project.repositories.RoomRepository",
                "org.springframework.data.domain.Page",
                "org.springframework.data.domain.Example",
                "org.springframework.context.ApplicationContext",
                "org.springframework.data.domain.Slice",
                "org.springframework.context.Lifecycle",
                "org.springframework.context.ApplicationEvent",
                "org.springframework.boot.context.event.SpringApplicationEvent",
                "org.springframework.data.repository.query.QueryByExampleExecutor",
                "org.springframework.stereotype.Component",
                "org.springframework.data.util.Streamable",
                "org.springframework.beans.factory.BeanFactory",
                "org.springframework.core.io.support.ResourcePatternResolver",
                "org.springframework.data.jpa.repository.JpaRepository",
                "nl.tudelft.oopp.group43.project.models.Room",
                "org.springframework.beans.factory.ListableBeanFactory"
        );
    }

    private static void initMocksToAvoidTimeoutsInTheTests() throws ClassNotFoundException {
        mock(RoomRepository.class);
        mock(ApplicationReadyEvent.class);
    }

    private static void resetClasses() {
        org.evosuite.runtime.classhandling.ClassResetter.getInstance().setClassLoader(RoomAttributesUpdaterTestScaffolding.class.getClassLoader());

        org.evosuite.runtime.classhandling.ClassStateSupport.resetClasses(
                "nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater",
                "nl.tudelft.oopp.group43.project.payload.RoomAttributesUpdater$BucketsList",
                "org.springframework.context.ApplicationEvent",
                "org.springframework.boot.context.event.SpringApplicationEvent",
                "org.springframework.boot.context.event.ApplicationReadyEvent",
                "com.fasterxml.jackson.annotation.JsonProperty$Access",
                "org.hibernate.annotations.OnDeleteAction",
                "nl.tudelft.oopp.group43.project.models.Room"
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
